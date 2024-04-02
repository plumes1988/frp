package com.figure.core.service.device.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.db.JavaMemoryDb;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.repository.device.DeviceAlarmMessageMapper;
import com.figure.core.service.device.IDeviceAlarmMessageCacheService;
import com.figure.core.service.device.IDeviceAlarmMessageService;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.service.notice.INoticeMessageService;
import com.figure.core.service.others.ICommonService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.sse.Constants;
import com.figure.core.sse.SseEmitterManager;
import com.figure.core.util.BusinessUtils;
import com.figure.core.util.DateUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

import static com.figure.core.model.device.DeviceAlarmMessage.*;
import static com.figure.core.model.device.DeviceIndicatorParamRel.IN_ALARM;
import static com.figure.core.model.device.DeviceIndicatorParamRel.NOT_IN_ALARM;
import static com.figure.core.model.device.DeviceServiceAlarmMessage.UN_CONFIRMED;
import static com.figure.core.model.notice.NoticeMessage.DEVICE_ALARM;
import static com.figure.core.sse.Constants.DEVICE_INDICATOR;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-07-01
 */
@Service
public class DeviceAlarmMessageServiceImpl extends ServiceImpl<DeviceAlarmMessageMapper, DeviceAlarmMessage> implements IDeviceAlarmMessageService {

    @Resource
    private INoticeMessageService noticeMessageService;

    @Resource
    private IDeviceAlarmMessageCacheService deviceAlarmMessageCacheService;

    @Resource
    ICommonService commonService;

    @Resource
    ISysParaService sysParaService;

    @Override
    public void newAlarm(DeviceAlarmMessage deviceAlarmMessage) {
        this.save(deviceAlarmMessage);
        noticeMessageService.processNoticeMessage(DEVICE_ALARM,deviceAlarmMessage);
    }

    @Override
    public void endAlarm(DeviceAlarmMessage deviceAlarmMessage) {
       this.updateById(deviceAlarmMessage);
       // 使用musql，历史指标会定时移除，需将历史指标专门移到一张表里面供报警回看使用
       //redisTemplateService.sendToQueue(QUEUE_NAME_SAVE_INDICATOR_HISTORY_FOR_ALARM, JSONUtil.Object2JsonStr(deviceAlarmMessage));
       noticeMessageService.processNoticeMessage(DEVICE_ALARM,deviceAlarmMessage);
    }

    @Override
    public Boolean newOffLineAlarm(DeviceInfo deviceInfo, String indicatorCode, String alarmTypeId, Date date) {

        Boolean flag = false;

        DeviceAlarmMessage new_deviceAlarmMessage = new DeviceAlarmMessage();

        new_deviceAlarmMessage.setDeviceCode(deviceInfo.getDeviceCode());

        new_deviceAlarmMessage.setFrontId(deviceInfo.getMonitorStationId());

        new_deviceAlarmMessage.setDeviceId(deviceInfo.getDeviceId());

        new_deviceAlarmMessage.setHappenTime(date);

        new_deviceAlarmMessage.setIndicatorCode(indicatorCode);

        new_deviceAlarmMessage.setAlarmTypeId(Integer.parseInt(alarmTypeId));

        new_deviceAlarmMessage.setStatus(NOT_END);

        new_deviceAlarmMessage.setConfirm(UN_CONFIRMED);

        new_deviceAlarmMessage.setFaultType(COMMUNICATION_ANOMALY);

        List<DeviceAlarmMessage> list =  deviceAlarmMessageCacheService.getAlarmingAlarms(new_deviceAlarmMessage);

        if(list.size()==0){
            deviceAlarmMessageCacheService.newAlarm(new_deviceAlarmMessage);
            new_deviceAlarmMessage.setTopic(Constants.DEVICE_ALARM);
            SseEmitterManager.sendMessageToAllMatchSseEmitter(new_deviceAlarmMessage);
            flag = true;
        }

        DeviceIndicatorParamRel rel = new DeviceIndicatorParamRel();
        rel.setCollectTime(DateUtils.dateToStrLong(date));
        rel.setIndicatorValue(NOT_END);
        rel.setAlarmStatus(IN_ALARM);
        rel.setDeviceId(deviceInfo.getDeviceId());
        rel.setDeviceCode(deviceInfo.getDeviceCode());
        rel.setIndicatorCode(indicatorCode);
        rel.setIsCritical(JavaMemoryDb.INDICATOR_CODE_IS_CRITICAL.get(indicatorCode));
        rel.setTopic(DEVICE_INDICATOR);
        SseEmitterManager.sendMessageToAllMatchSseEmitter(rel);
        return flag;
    }

    @Override
    public void endOffLineAlarm(DeviceInfo deviceInfo, String indicatorCode,String alarmTypeId, Date date) {

        DeviceAlarmMessage deviceAlarmMessage_ =  BusinessUtils.buildAlarm(deviceInfo.getDeviceCode(),indicatorCode,alarmTypeId);

        List<DeviceAlarmMessage> deviceAlarmMessages = deviceAlarmMessageCacheService.getAlarmingAlarms(deviceAlarmMessage_);

        for(DeviceAlarmMessage alarm:deviceAlarmMessages){

            alarm.setEndTime(date);

            alarm.setDeviceId(deviceInfo.getDeviceId());

            alarm.setStatus(ENDED);

            alarm.setTopic(Constants.DEVICE_ALARM);

            SseEmitterManager.sendMessageToAllMatchSseEmitter(alarm);

        }
        deviceAlarmMessage_.setEndTime(date);
        deviceAlarmMessageCacheService.endAlarm(deviceAlarmMessage_);
        DeviceIndicatorParamRel rel = new DeviceIndicatorParamRel();
        rel.setCollectTime(DateUtils.dateToStrLong(date));
        rel.setIndicatorValue(ENDED);
        rel.setAlarmStatus(NOT_IN_ALARM);
        rel.setDeviceId(deviceInfo.getDeviceId());
        rel.setDeviceCode(deviceInfo.getDeviceCode());
        rel.setIndicatorCode(indicatorCode);
        rel.setIsCritical(JavaMemoryDb.INDICATOR_CODE_IS_CRITICAL.get(indicatorCode));
        rel.setTopic(DEVICE_INDICATOR);
        SseEmitterManager.sendMessageToAllMatchSseEmitter(rel);
    }

    @Override
    public void fillEntityProps(List<DeviceAlarmMessage> records) {

        Map<String,String> deviceCode_deviceName_map = commonService.get_deviceCode_deviceName_map();

        Map<String,String> indicatorCode_indicatorName_map = commonService.get_indicatorCode_indicatorName_map();

        Map<Integer,String> alarmTypeId_alarmTypeName_map =  commonService.get_alarmTypeId_alarmTypeName_map();

        Map<Integer, String> frontId_frontName_map = commonService.get_frontId_frontName_map();

        for (DeviceAlarmMessage deviceAlarmMessage:records){
            Date endTime = deviceAlarmMessage.getEndTime();
            if(NOT_END.equals(deviceAlarmMessage.getStatus())){
                endTime = new Date();
            }else{
                if(endTime==null){
                    endTime = new Date();
                }
            }
            Integer continueTime =  (int)(endTime.getTime() -  deviceAlarmMessage.getHappenTime().getTime());
            deviceAlarmMessage.setContinueTime(continueTime);
            deviceAlarmMessage.setIndicatorName(indicatorCode_indicatorName_map.get(deviceAlarmMessage.getIndicatorCode()));
            deviceAlarmMessage.setDeviceName(deviceCode_deviceName_map.get(deviceAlarmMessage.getDeviceCode()));
            deviceAlarmMessage.setAlarmTypeName(alarmTypeId_alarmTypeName_map.get(deviceAlarmMessage.getAlarmTypeId()));
            deviceAlarmMessage.setFrontName(frontId_frontName_map.get(deviceAlarmMessage.getFrontId()));
        }
    }

}
