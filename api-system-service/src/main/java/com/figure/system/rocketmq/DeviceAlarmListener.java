package com.figure.system.rocketmq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.entity.DeviceIndicatorAlarmStatus;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.message.DeviceAlarm;
import com.figure.core.service.device.IDeviceAlarmMessageCacheService;
import com.figure.core.service.device.IDeviceIndicatorParamRelService;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.sse.SseEmitterManager;
import com.figure.core.util.BusinessUtils;
import com.figure.core.util.DateUtils;
import com.figure.core.util.JSONUtil;
import lombok.SneakyThrows;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.figure.core.db.JavaMemoryDb.DEVICE_CUR_ALARM_STATUS_CACHE;
import static com.figure.core.model.device.DeviceAlarmMessage.*;
import static com.figure.core.sse.Constants.DEVICE_ALARM;

/**
 * 采集器会再报警开始发送一条开始报警的消息，结束时发送一条结束报警的消息
 */
@Component
@RocketMQMessageListener(topic = RocketMQConstants.DEVICE_ALARM, consumerGroup = RocketMQConstants.DEVICE_ALARM_GROUP,consumeMode = ConsumeMode.CONCURRENTLY, consumeThreadMax = 1)
public class DeviceAlarmListener implements RocketMQListener<MessageExt> {

    @Resource
    private IDeviceAlarmMessageCacheService deviceAlarmMessageCacheService;

    @Autowired
    private IDeviceInfoService deviceInfoService;

    @Autowired
    ISysParaService sysParaService;

    @Autowired
    private IDeviceIndicatorParamRelService deviceIndicatorParamRelService;

    @SneakyThrows
    @Override
    public void onMessage(MessageExt message) {
        String message_str = new String(message.getBody());
        DeviceAlarm deviceAlarm = JSONUtil.json2ObjectByT(message_str,
                DeviceAlarm.class);
        String deviceCode = deviceAlarm.getDeviceCode();
        QueryWrapper device_queryWrapper = new QueryWrapper();
        device_queryWrapper.eq("deviceCode",deviceCode);
        device_queryWrapper.eq("isDelete",com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED);
        DeviceInfo deviceInfo = deviceInfoService.getOne(device_queryWrapper);
        DeviceAlarmMessage alarmMessage = new DeviceAlarmMessage();
        alarmMessage.setDeviceCode(deviceAlarm.getDeviceCode());
        alarmMessage.setFrontId(deviceInfo.getMonitorStationId());
        alarmMessage.setIndicatorCode(deviceAlarm.getIndicatorCode());
        alarmMessage.setAlarmTypeId(deviceAlarm.getAlarmType());
        if(deviceInfo!=null){
            alarmMessage.setDeviceId(deviceInfo.getDeviceId());
        }
        alarmMessage.setAlarmMsg(deviceAlarm.getAlarmContent());
        SimpleDateFormat format = DateUtils.getDefaultDateFormat();
        Date parse = format.parse(deviceAlarm.getStartTime());
        alarmMessage.setHappenTime(parse);
        alarmMessage.setContinueTime(deviceAlarm.getDuration().intValue());
        alarmMessage.setStatus(deviceAlarm.getAlarmFlag()==0?NOT_END:ENDED);

        String key = BusinessUtils.getKeyOfDeviceAlarmMessage(alarmMessage);

        if( NOT_END.equals(alarmMessage.getStatus()) ){
            alarmMessage.setConfirm(UN_CONFIRMED);
            alarmMessage.setFaultType(INDICATOR_ANOMALY);
            List<DeviceAlarmMessage>  deviceAlarmMessages = deviceAlarmMessageCacheService.getAlarmingAlarms(alarmMessage);

            Long nowTimestamp =  new Date().getTime();

            if(deviceAlarmMessages.size()==0){
                deviceAlarmMessageCacheService.newAlarm(alarmMessage);
                alarmMessage.setTopic(DEVICE_ALARM);
                SseEmitterManager.sendMessageToAllMatchSseEmitter(alarmMessage);
                DEVICE_CUR_ALARM_STATUS_CACHE.put(key,new DeviceIndicatorAlarmStatus(nowTimestamp,UN_CONFIRMED));
            }else{
                if(MISINTERPRET.equals(deviceAlarmMessages.get(0).getConfirm())){
                    DEVICE_CUR_ALARM_STATUS_CACHE.put(key,new DeviceIndicatorAlarmStatus(nowTimestamp,MISINTERPRET));
                }else{
                    DEVICE_CUR_ALARM_STATUS_CACHE.put(key,new DeviceIndicatorAlarmStatus(nowTimestamp,deviceAlarmMessages.get(0).getConfirm()));
                }
            }
        }else{
            String messageTime =  deviceAlarm.getMessageHead().getMessageTime();
            Date endTime = DateHelper.strToDate(messageTime);
            List<DeviceAlarmMessage> find_alarmMessages  = deviceAlarmMessageCacheService.getAlarmingAlarms(alarmMessage);
            for(DeviceAlarmMessage deviceAlarmMessage:find_alarmMessages){
                deviceAlarmMessage.setEndTime(endTime);
                deviceAlarmMessage.setStatus(ENDED);
                deviceAlarmMessage.setTopic(DEVICE_ALARM);
                Integer deviceId = deviceInfoService.getDeviceIdByDeviceCode(deviceAlarmMessage.getDeviceCode());
                deviceAlarmMessage.setDeviceId(deviceId);
                SseEmitterManager.sendMessageToAllMatchSseEmitter(deviceAlarmMessage);
            }
            alarmMessage.setEndTime(endTime);
            deviceAlarmMessageCacheService.endAlarm(alarmMessage);
        }
        deviceIndicatorParamRelService.updateDeviceAlarmIndicatorParamRelAndSendSse(alarmMessage.getDeviceCode());
    }

    public static void main(String[] args) {
        Date endTime = DateHelper.strToDate("2024-03-02 23:03:20");
        System.out.println(endTime);
    }

}
