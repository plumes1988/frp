package com.figure.core.service.device.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.db.JavaMemoryDb;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.model.device.DeviceIndicatorParam;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.device.DeviceIndicatorParamRelMapper;
import com.figure.core.rocketmq.message.DeviceIndexSet;
import com.figure.core.service.device.*;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.sse.SseEmitterManager;
import com.figure.core.util.BusinessUtils;
import com.figure.core.util.StringUtils;
import com.figure.core.util.copycat.annotaion.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.figure.core.constant.ConstantsForSysPara.*;
import static com.figure.core.model.device.DeviceAlarmMessage.*;
import static com.figure.core.model.device.DeviceIndicatorParam.NUMBER_VARIABLE;
import static com.figure.core.model.device.DeviceIndicatorParamRel.IN_ALARM;
import static com.figure.core.model.device.DeviceIndicatorParamRel.NOT_IN_ALARM;
import static com.figure.core.sse.Constants.DEVICE_ALARM;
import static com.figure.core.sse.Constants.DEVICE_INDICATOR;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-06-28
 */
@Service
public class DeviceIndicatorParamRelServiceImpl extends ServiceImpl<DeviceIndicatorParamRelMapper, DeviceIndicatorParamRel> implements IDeviceIndicatorParamRelService {

    @Resource
    ISysParaService sysParaService;

    @Autowired
    private IDeviceInfoService deviceInfoService;

    @Autowired
    private IDeviceAlarmMessageCacheService deviceAlarmMessageCacheService;

    @Resource
    IRedisTemplateService redisTemplateService;

    @Resource
    private IDeviceIndicatorParamService deviceIndicatorParamService;


    //更新设备整体报警状态、以及推送消息
    @Override
    public void updateDeviceAlarmIndicatorParamRelAndSendSse(String deviceCode) {
        updateDeviceAlarmIndicatorParamRelAndSendSse(deviceCode,false);
    }

    //更新设备整体报警状态、在线状态告警、以及推送消息 （forOffline：true->在线状态，false->整体报警状态（包含在线状态报警））
    @Override
    public void updateDeviceAlarmIndicatorParamRelAndSendSse(String deviceCode,Boolean forOffline) {
        updateDeviceAlarmIndicatorParamRelAndSendSse(deviceCode,forOffline,null);
    }

    //indicatorCode不为空时，为实际指标状态，推送告警状态
    private void updateDeviceAlarmIndicatorParamRelAndSendSse(String deviceCode,Boolean forOffline,String indicatorCode) {

        DeviceIndicatorParamRel rel = new DeviceIndicatorParamRel();

        rel.setDeviceCode(deviceCode);

        Date updateTime = new Date();

        rel.setCollectTime(DateHelper.dateToStr(updateTime));

        String key  = null;

        if(indicatorCode!=null){
            // 设备真实指标
            rel.setIndicatorCode(indicatorCode);
            key = BusinessUtils.getLiveDeviceIndicatorKey(rel);
            //主要是更新报警状体
            rel.setAlarmStatus(BusinessUtils.getDeviceIndicatorAlarmStatusByKey(key));
            rel.setIndicatorValue(JavaMemoryDb.get_value_from_DEVICE_CUR_INDICATOR_STATUS(key));
        }else if(forOffline){
            indicatorCode = sysParaService.getParamByName(ONLINE_STATUS_INDICATOR_CODE);
            String alarmTypeId = sysParaService.getParamByName(OFFLINE_ALARM_TYPE_ID);
            DeviceAlarmMessage deviceAlarmMessage_  = BusinessUtils.buildAlarm(deviceCode,indicatorCode,alarmTypeId);
            boolean isAlarming = deviceAlarmMessageCacheService.isAlarming(deviceAlarmMessage_);
            rel.setIndicatorValue(isAlarming?NOT_END:ENDED);
            rel.setAlarmStatus(isAlarming?IN_ALARM:NOT_IN_ALARM);
        }else{
            indicatorCode = sysParaService.getParamByName(ALARM_STATUS_INDICATOR_CODE);
            Integer alarmStatus = BusinessUtils.getDeviceIndicatorAlarmStatusByKey(deviceCode+"_");
            rel.setIndicatorValue(alarmStatus==NOT_IN_ALARM?ENDED:NOT_END);
            rel.setAlarmStatus(alarmStatus);
        }
        rel.setIndicatorCode(indicatorCode);
        Integer deviceId = deviceInfoService.getDeviceIdByDeviceCode(rel.getDeviceCode());
        rel.setDeviceId(deviceId);
        rel.setIndicatorCode(indicatorCode);
        rel.setIsCritical(JavaMemoryDb.INDICATOR_CODE_IS_CRITICAL.get(indicatorCode));
        rel.setTopic(DEVICE_INDICATOR);
        SseEmitterManager.sendMessageToAllMatchSseEmitter(rel);
    }

    @Override
    public void updateDeviceIndicatorParamRelAndSendSseWhenUpdateAlarm(List<DeviceAlarmMessage> alarmMessages) {

        if(alarmMessages==null||alarmMessages.size()==0){
            return;
        }

        String onlineStatusIndicatorCode = sysParaService.getParamByName(ONLINE_STATUS_INDICATOR_CODE);

        List<DeviceAlarmMessage> alarms =  alarmMessages;

        Set<String> deviceCodes = new HashSet<>();

        Set<String> deviceCodes_has_onlineStatusIndicatorCode = new HashSet<>();

        Set<String> deviceCode_indicatorCode_only_once = new HashSet<>();

        for(DeviceAlarmMessage deviceAlarmMessage:alarms){
            String indicatorCode = deviceAlarmMessage.getIndicatorCode();
            String  deviceCode = deviceAlarmMessage.getDeviceCode();
            if(!StringUtils.isEmpty(deviceCode) && !StringUtils.isEmpty(indicatorCode) && !indicatorCode.equals(onlineStatusIndicatorCode) ){
                String deviceCode_indicatorCode_only_once_key = deviceCode+"_"+indicatorCode;
                if(!deviceCode_indicatorCode_only_once.contains(deviceCode_indicatorCode_only_once_key)){
                    updateDeviceAlarmIndicatorParamRelAndSendSse(deviceCode,null,indicatorCode);
                    deviceCode_indicatorCode_only_once.add(deviceCode_indicatorCode_only_once_key);
                }
            }
            if(!StringUtils.isEmpty(deviceCode) && !StringUtils.isEmpty(indicatorCode) && indicatorCode.equals(onlineStatusIndicatorCode) ){
                deviceCodes_has_onlineStatusIndicatorCode.add(deviceCode);
            }
            if(!StringUtils.isEmpty(deviceCode) && !StringUtils.isEmpty(indicatorCode) ){
                deviceCodes.add(deviceCode);
            }
        }

        for(String deviceCode:deviceCodes){
            if(deviceCodes_has_onlineStatusIndicatorCode.contains(deviceCode)){
                updateDeviceAlarmIndicatorParamRelAndSendSse(deviceCode,true);
            }
            updateDeviceAlarmIndicatorParamRelAndSendSse(deviceCode,false);
        }
    }

    @Override
    public Integer getRecordMode(int deviceId, String indicatorCode) {
        Integer recordMode =  null;
        Map<String, Integer>  cache_map = redisTemplateService.getMapRedisByKeyCache(RedisConstants.DEVICE_HISTORY_INDICATOR_RECORD_MODE, String.class, Integer.class);
        if(cache_map!=null){
            recordMode =  cache_map.get(deviceId+"_"+indicatorCode);
        }
        if(recordMode==null){
            QueryWrapper  queryWrapper = new QueryWrapper();
            queryWrapper.eq("deviceId",deviceId);
            queryWrapper.eq("indicatorCode",indicatorCode);
            List<DeviceIndicatorParamRel> results = list(queryWrapper);
            if(results.size()>0){
                recordMode = results.get(0).getRecordMode();
            }
        }
        return recordMode;
    }

    @Override
    @PostConstruct
    public void setRecordModeCache() {
        QueryWrapper<DeviceIndicatorParamRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("deviceId", "indicatorCode", "recordMode");
        List<DeviceIndicatorParamRel> deviceIndicatorParamRels = this.baseMapper.selectList(queryWrapper);
        Map<String, Integer> alarmParamMap = new HashMap<>();
        for (DeviceIndicatorParamRel rel : deviceIndicatorParamRels) {
            alarmParamMap.put(rel.getDeviceId()+"_"+rel.getIndicatorCode(), rel.getRecordMode());
        }
        redisTemplateService.setRedisCache(RedisConstants.DEVICE_HISTORY_INDICATOR_RECORD_MODE, alarmParamMap);
    }

    @Override
    public boolean indicatorSetCheckSetValueIsSameWithCurValue(DeviceIndexSet deviceIndexSet) {
        String indicatorValue = deviceIndexSet.getSetData();
        String indicatorCode =  deviceIndexSet.getIndexCode();
        String deviceCode =  deviceIndexSet.getDeviceCode();
        String key = deviceCode+"_"+indicatorCode;
        String cur_value = JavaMemoryDb.get_value_from_DEVICE_CUR_INDICATOR_STATUS(key);
        return indicatorValue.equals(cur_value);
    }

    @Override
    public boolean dataOutOfBounds(DeviceIndexSet deviceIndexSet) {

        String indicatorValue = deviceIndexSet.getSetData();
        String indicatorCode =  deviceIndexSet.getIndexCode();
        String deviceCode =  deviceIndexSet.getDeviceCode();

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("indicatorCode",indicatorCode);

        List<DeviceIndicatorParam> deviceIndicatorParams =  deviceIndicatorParamService.list(queryWrapper);

        if(deviceIndicatorParams.size()>0){

            DeviceIndicatorParam deviceIndicatorParam = deviceIndicatorParams.get(0);

            if(deviceIndicatorParam.getDataType()==NUMBER_VARIABLE){

                queryWrapper = new QueryWrapper();
                queryWrapper.eq("indicatorCode",indicatorCode);
                queryWrapper.eq("deviceCode",deviceCode);
                List<DeviceIndicatorParamRel>  deviceIndicatorParamRels = this.list(queryWrapper);

                if(deviceIndicatorParamRels.size()>0){
                    DeviceIndicatorParamRel rel = deviceIndicatorParamRels.get(0);
                    Double setLowerLimit  = rel.getSetLowerLimit();
                    Double setUpperLimit = rel.getSetUpperLimit();
                    Double setValue = BusinessUtils.string2Double(indicatorValue);
                    if(setLowerLimit!=null && setValue!=null && setValue<setLowerLimit){
                        return true;
                    }
                    if(setUpperLimit!=null && setValue!=null && setValue>setUpperLimit){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void applyParamsSetting2SameProductAndModelDevices(Integer deviceId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("deviceId",deviceId);
        List<DeviceIndicatorParamRel> rel_list = this.list(queryWrapper);
        DeviceInfo deviceInfo_ = deviceInfoService.getById(deviceId);
        Integer productId = deviceInfo_.getProductId();
        Integer modelId = deviceInfo_.getModelId();

        queryWrapper = new QueryWrapper();
        queryWrapper.eq("productId",productId);
        queryWrapper.eq("modelId",modelId);
        queryWrapper.ne("deviceId",deviceId);
        List<DeviceInfo> deviceInfos =  deviceInfoService.list(queryWrapper);

        List<Integer> deviceIds = deviceInfos.stream()
                .map(DeviceInfo::getDeviceId)
                .collect(Collectors.toList());
        queryWrapper = new QueryWrapper();

        if(deviceIds.size()>0){
            queryWrapper.in("deviceId",deviceIds);

            this.remove(queryWrapper);

            for (DeviceInfo deviceInfo:deviceInfos) {

                for(DeviceIndicatorParamRel deviceIndicatorParamRel:rel_list){
                    deviceIndicatorParamRel.setDeviceId(deviceInfo.getDeviceId());
                    deviceIndicatorParamRel.setDeviceCode(deviceInfo.getDeviceCode());
                    deviceIndicatorParamRel.setId(null);
                }
                this.saveBatch(rel_list);

            }
        }
    }

    @Override
    public void loadAllDeviceCurIndicatorValueIntoJavaMemoryDb() {
        // todo 从历史表加载
    }

    @Override
    public void send2WebForDelete(List<DeviceAlarmMessage> alarms) {
        if(alarms==null||alarms.size()==0){
            return;
        }
        for(DeviceAlarmMessage deviceAlarmMessage:alarms){
            Integer deviceId = deviceInfoService.getDeviceIdByDeviceCode(deviceAlarmMessage.getDeviceCode());
            deviceAlarmMessage.setDeviceId(deviceId);
            deviceAlarmMessage.setTopic(DEVICE_ALARM);
            deviceAlarmMessage.setIsDelete(1);
            SseEmitterManager.sendMessageToAllMatchSseEmitter(deviceAlarmMessage);
        }
    }

    @Override
    public Integer getChangeThreshold(int deviceId, String indicatorCode) {
        Integer recordMode =  null;
        Map<String, Integer>  cache_map = redisTemplateService.getMapRedisByKeyCache(RedisConstants.DEVICE_HISTORY_INDICATOR_CHANGE_THRESHOLD, String.class, Integer.class);
        if(cache_map!=null){
            recordMode =  cache_map.get(deviceId+"_"+indicatorCode);
        }
        if(recordMode==null){
            QueryWrapper  queryWrapper = new QueryWrapper();
            queryWrapper.eq("deviceId",deviceId);
            queryWrapper.eq("indicatorCode",indicatorCode);
            List<DeviceIndicatorParamRel> results = list(queryWrapper);
            if(results.size()>0){
                recordMode = results.get(0).getChangeThreshold();
            }
        }
        return recordMode;
    }

    @Override
    @PostConstruct
    public void setChangeThresholdCache() {
        QueryWrapper<DeviceIndicatorParamRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("deviceId", "indicatorCode", "changeThreshold");
        List<DeviceIndicatorParamRel> deviceIndicatorParamRels = this.baseMapper.selectList(queryWrapper);
        Map<String, Integer> alarmParamMap = new HashMap<>();
        for (DeviceIndicatorParamRel rel : deviceIndicatorParamRels) {
            alarmParamMap.put(rel.getDeviceId()+"_"+rel.getIndicatorCode(), rel.getChangeThreshold());
        }
        redisTemplateService.setRedisCache(RedisConstants.DEVICE_HISTORY_INDICATOR_CHANGE_THRESHOLD, alarmParamMap);
    }

    @Override
    public void updateCache() {
        setRecordModeCache();
        setChangeThresholdCache();
    }

    @Override
    public void sendCurDeviceIndicator(Map<String, Object> params) {
        String deviceCode = params.get("deviceCode").toString();
        String indicatorCode = params.get("indicatorCode").toString();
        DeviceIndicatorParamRel rel = new DeviceIndicatorParamRel();
        rel.setDeviceCode(deviceCode);
        rel.setIndicatorCode(indicatorCode);
        String key = BusinessUtils.getLiveDeviceIndicatorKey(rel);
        //主要是更新报警状体
        rel.setAlarmStatus(BusinessUtils.getDeviceIndicatorAlarmStatusByKey(key));
        rel.setIndicatorValue(JavaMemoryDb.get_value_from_DEVICE_CUR_INDICATOR_STATUS(key));
        Integer deviceId = deviceInfoService.getDeviceIdByDeviceCode(rel.getDeviceCode());
        rel.setDeviceId(deviceId);
        rel.setIndicatorCode(indicatorCode);
        rel.setIsCritical(JavaMemoryDb.INDICATOR_CODE_IS_CRITICAL.get(indicatorCode));
        rel.setTopic(DEVICE_INDICATOR);
        SseEmitterManager.sendMessageToAllMatchSseEmitter(rel);
    }

}
