package com.figure.core.service.device.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.db.LevelDBManager;
import com.figure.core.disruptor.Event;
import com.figure.core.entity.DeviceIndicatorAlarmStatus;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.repository.device.DeviceAlarmMessageMapper;
import com.figure.core.service.device.IDeviceAlarmMessageCacheService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.util.BusinessUtils;
import com.figure.core.util.UniqueIdGenerator;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.figure.core.db.JavaMemoryDb.DEVICE_CUR_ALARM_STATUS_CACHE;
import static com.figure.core.model.device.DeviceAlarmMessage.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-07-01
 */
@Service
public class DeviceAlarmMessageServiceCacheImpl extends ServiceImpl<DeviceAlarmMessageMapper, DeviceAlarmMessage> implements IDeviceAlarmMessageCacheService {

    @Autowired
    ISysParaService sysParaService;

    @Resource
    LevelDBManager levelDBManager;

    @Resource
    Disruptor<Event> disruptor;

    public static Integer ALARM_UPDATE_TYPE_UN_KNOW = -1;

    public static Integer ALARM_UPDATE_TYPE_DELETE = 0;

    public static Integer ALARM_UPDATE_TYPE_CONFIRM_STATUS = 1;

    public static Integer ALARM_UPDATE_TYPE_ENDED = 2;

    public static Integer ALARM_UPDATE_TYPE_NEW = 3;

    public static Integer ALARM_UPDATE_TYPE_END_ALL_BY_KEY = 4;

    @Override
    public List<DeviceAlarmMessage> getAlarmingAlarms(DeviceAlarmMessage deviceAlarmMessage) {
        List<DeviceAlarmMessage> list = new ArrayList<>();
        try{
            String key = BusinessUtils.getKeyOfDeviceAlarmMessage(deviceAlarmMessage);
            return getAlarmingAlarms(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    private List<DeviceAlarmMessage> getAlarmingAlarms(String key) {
        List<DeviceAlarmMessage> list = new ArrayList<>();
        try{
            list = levelDBManager.getObjectList(key);
            BusinessUtils.sortAlarmsDesc(list);
            if(list==null){
                list = new ArrayList<>();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public Boolean isAlarming(DeviceAlarmMessage deviceAlarmMessage) {
        List<DeviceAlarmMessage>  deviceAlarmMessages = this.getAlarmingAlarms(deviceAlarmMessage);
        return deviceAlarmMessages.size()>0 && !MISINTERPRET.equals(deviceAlarmMessages.get(0).getConfirm());
    }


    @Override
    public void newAlarm(DeviceAlarmMessage deviceAlarmMessage) {
        Long alarmId = UniqueIdGenerator.generateUniqueId();
        deviceAlarmMessage.setAlarmId(alarmId);
        String key = BusinessUtils.getKeyOfDeviceAlarmMessage(deviceAlarmMessage);
        try {
            List<DeviceAlarmMessage> alarms = levelDBManager.getObjectList(key);
            if(alarms==null){
                alarms = new ArrayList<>();
            }
            alarms.add(deviceAlarmMessage);
            levelDBManager.putObjectList(key,alarms);
            DEVICE_CUR_ALARM_STATUS_CACHE.put(key,new DeviceIndicatorAlarmStatus(new Date().getTime(),deviceAlarmMessage.getConfirm()));
            deviceAlarmMessage.setAlarmUpdateType(ALARM_UPDATE_TYPE_NEW);
            sendToDisruptor(deviceAlarmMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void endAlarm(DeviceAlarmMessage deviceAlarmMessage) {
        String key = BusinessUtils.getKeyOfDeviceAlarmMessage(deviceAlarmMessage);
        levelDBManager.remove(key);
        DEVICE_CUR_ALARM_STATUS_CACHE.remove(key);
        deviceAlarmMessage.setAlarmUpdateType(ALARM_UPDATE_TYPE_END_ALL_BY_KEY);
        sendToDisruptor(deviceAlarmMessage);
    }

    @Override
    public DeviceAlarmMessage getTheNewestAlarmingAlarm(DeviceAlarmMessage deviceAlarmMessage) {
        try {
            String key = BusinessUtils.getKeyOfDeviceAlarmMessage(deviceAlarmMessage);
            List<DeviceAlarmMessage> alarms = levelDBManager.getObjectList(key);
            BusinessUtils.sortAlarmsDesc(alarms);
            if(alarms!=null && alarms.size()>0){
                return alarms.get(alarms.size()-1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Boolean newestAlarmsIsNotEndAndMisinterpret(String deviceCode, String indicatorCode, String alarmTypeId) {
        try {
            DeviceAlarmMessage deviceAlarmMessage  = getTheNewestAlarmingAlarm(BusinessUtils.buildAlarm(deviceCode,indicatorCode,alarmTypeId));
            return deviceAlarmMessage!=null && MISINTERPRET.equals(deviceAlarmMessage.getConfirm()) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void updateAlarms(List<DeviceAlarmMessage> deviceAlarmMessages, Integer updateAlarmType) {
        try{
            if(ALARM_UPDATE_TYPE_UN_KNOW==updateAlarmType){
                return;
            }
            Set<String> keys = new HashSet<>();
            // 已结束报警，报警状态不可修改为未结束
            // 所有报警，可以标记为误报;
            // 标记为误报的未结束报警的报警状态可以修改为已结束；
            // 1、删除报警 2、标记为误报、标记为已结束
            for (DeviceAlarmMessage deviceAlarmMessage : deviceAlarmMessages) {
                //删除报警
                if(ALARM_UPDATE_TYPE_DELETE==updateAlarmType){
                    //未结束的直接移除
                    if(NOT_END.equals(deviceAlarmMessage.getStatus())){
                        removeAlarm(deviceAlarmMessage);
                    }
                }
                //标记为误报
                if(ALARM_UPDATE_TYPE_CONFIRM_STATUS==updateAlarmType){
                    //未结束的更新缓存
                    if(NOT_END.equals(deviceAlarmMessage.getStatus())){
                        updateAlarmConfirmStatus(deviceAlarmMessage);
                    }
                }
                //未结束且误报的报警标记为已结束
                if(ALARM_UPDATE_TYPE_ENDED==updateAlarmType){
                    removeAlarm(deviceAlarmMessage);
                }
                keys.add(BusinessUtils.getKeyOfDeviceAlarmMessage(deviceAlarmMessage));
            }
            for(String key:keys){
                List<DeviceAlarmMessage> list = levelDBManager.getObjectList(key);
                BusinessUtils.sortAlarmsDesc(list);
                if(list==null||list.size()==0){
                    DEVICE_CUR_ALARM_STATUS_CACHE.remove(key);
                }else{
                    DEVICE_CUR_ALARM_STATUS_CACHE.put(key,new DeviceIndicatorAlarmStatus(new Date().getTime(),list.get(0).getConfirm()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private void removeAlarm(DeviceAlarmMessage deviceAlarmMessage){
        try{
            String key = BusinessUtils.getKeyOfDeviceAlarmMessage(deviceAlarmMessage);
            List<DeviceAlarmMessage> alarms = levelDBManager.getObjectList(key);
            alarms.removeIf(item ->deviceAlarmMessage.getAlarmId().longValue()==item.getAlarmId().longValue());
            levelDBManager.putObjectList(key,alarms);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateAlarmConfirmStatus(DeviceAlarmMessage deviceAlarmMessage){
        try{
            String key = BusinessUtils.getKeyOfDeviceAlarmMessage(deviceAlarmMessage);
            List<DeviceAlarmMessage> alarms = levelDBManager.getObjectList(key);
            // 查找唯一匹配项且不为null
            Optional<DeviceAlarmMessage> matchingItem = alarms.stream()
                    .filter(item -> deviceAlarmMessage.getAlarmId().longValue()==item.getAlarmId().longValue())
                    .findFirst();

            if (matchingItem.isPresent()) {
                System.out.println("匹配项: " + matchingItem.get());
                DeviceAlarmMessage deviceAlarmMessage_found = matchingItem.get();
                deviceAlarmMessage_found.setConfirm(deviceAlarmMessage.getConfirm());
            }
            levelDBManager.putObjectList(key,alarms);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadAlarmingAlarmsFromDb() {
        try{
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.orderByDesc("alarmId");
            queryWrapper.in("faultType", new Integer[]{INDICATOR_ANOMALY, COMMUNICATION_ANOMALY});
            queryWrapper.eq("status",NOT_END);
            List<DeviceAlarmMessage> alarms =  this.list(queryWrapper);
            Map<String,List<DeviceAlarmMessage>> key_alarms_map = new HashMap<>();
            for(DeviceAlarmMessage deviceAlarmMessage:alarms){
                String key = BusinessUtils.getKeyOfDeviceAlarmMessage(deviceAlarmMessage);
                List<DeviceAlarmMessage> list = key_alarms_map.get(key);
                if(list==null){
                    list = new ArrayList<>();
                    key_alarms_map.put(key,list);
                }
                list.add(deviceAlarmMessage);
                if(!DEVICE_CUR_ALARM_STATUS_CACHE.containsKey(key)){
                    DEVICE_CUR_ALARM_STATUS_CACHE.put(key,new DeviceIndicatorAlarmStatus(new Date().getTime(),deviceAlarmMessage.getConfirm()));
                }
            }
            for(String key:key_alarms_map.keySet()){
                levelDBManager.putObjectList(key,key_alarms_map.get(key));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void sendToDisruptor(DeviceAlarmMessage deviceAlarmMessage) {
        RingBuffer<Event> ringBuffer = disruptor.getRingBuffer();
        long sequence = ringBuffer.next();
        Event event = ringBuffer.get(sequence);
        event.setDeviceAlarmMessage(deviceAlarmMessage);
        ringBuffer.publish(sequence);
    }

    public static void main(String[] args) {
        Long val01 = new Long(1212);
        Long val02 = new Long(1212);
        System.out.println(val01.longValue()==val02.longValue());
    }

}
