package com.figure.system.scheduled;

import com.figure.core.constant.Constants;
import com.figure.core.constant.LogConstants;
import com.figure.core.entity.DeviceIndicatorAlarmStatus;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.service.device.IDeviceAlarmMessageCacheService;
import com.figure.core.service.device.IDeviceIndicatorParamRelService;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.log.ILogServiceService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.sse.SseEmitterManager;
import com.figure.core.util.BusinessUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.figure.core.constant.ConstantsForSysPara.ONLINE_STATUS_INDICATOR_CODE;
import static com.figure.core.db.JavaMemoryDb.DEVICE_CUR_ALARM_STATUS_CACHE;
import static com.figure.core.model.device.DeviceAlarmMessage.ENDED;
import static com.figure.core.model.log.LogService.SERVICE_TYPE_PLATFORM;
import static com.figure.core.model.log.LogService.SOURCE_SERVICE_PLATFORM;
import static com.figure.core.sse.Constants.DEVICE_ALARM;

@Component
@EnableScheduling
public class DeviceIndicatorAlarmEndCheckScheduler {

    @Resource
    private IDeviceAlarmMessageCacheService deviceAlarmMessageCacheService;

    @Resource
    private IDeviceInfoService deviceInfoService;

    @Resource
    private IDeviceIndicatorParamRelService deviceIndicatorParamRelService;

    @Resource
    ISysParaService sysParaService;

    @Resource
    private ILogServiceService logServiceService;

    @Scheduled(fixedRate = 5 * 1000, initialDelay = 20 * 1000)//间隔1秒
    private void alarmCheckTask() {

        String onlineStatusIndicatorCode = sysParaService.getParamByName(ONLINE_STATUS_INDICATOR_CODE);

        try {
            long alarmTimeout = 10 * 1000;
            String alarmTimeout_str = sysParaService.getParamByName(Constants.ALARM_TIMEOUT);
            if (alarmTimeout_str != null) {
                alarmTimeout = Integer.valueOf(alarmTimeout_str).intValue() * 1000;
            }
            Set<String> keys = DEVICE_CUR_ALARM_STATUS_CACHE.keySet();
            Date now = new Date();
            List<String> alarmIds = new ArrayList<>();
            List<DeviceAlarmMessage> alarmMessages = new ArrayList<>();
            for (String key : keys) {
                String[] values = BusinessUtils.parseKey(key);
                String deviceCode = values[0];
                String indicatorCode = values[1];
                if(indicatorCode.equals(onlineStatusIndicatorCode)){
                    continue;
                }
                String alarmTypeId = values[2];
                DeviceIndicatorAlarmStatus deviceIndicatorAlarmStatus = DEVICE_CUR_ALARM_STATUS_CACHE.get(key);
                long intervalTime = now.getTime() - deviceIndicatorAlarmStatus.getTimestamp();
                if (intervalTime > alarmTimeout) {
                    String message = StringUtils.concat(alarmTimeout/1000,"秒中，未收到设备编号为[",deviceCode,"],指标编号为：[",indicatorCode,"],报警类型ID为[",alarmTypeId,"]的报警中或者报警结束的消息，系统自动结束该报警");

                    DeviceAlarmMessage deviceAlarmMessage_ = BusinessUtils.buildAlarm(deviceCode,indicatorCode,alarmTypeId);

                    List<DeviceAlarmMessage> find_alarmMessages = deviceAlarmMessageCacheService.getAlarmingAlarms(deviceAlarmMessage_);

                    for (DeviceAlarmMessage deviceAlarmMessage : find_alarmMessages) {
                        deviceAlarmMessage.setEndTime(now);
                        deviceAlarmMessage.setStatus(ENDED);
                        deviceAlarmMessage.setTopic(DEVICE_ALARM);
                        Integer deviceId = deviceInfoService.getDeviceIdByDeviceCode(deviceAlarmMessage.getDeviceCode());
                        deviceAlarmMessage.setDeviceId(deviceId);
                        deviceAlarmMessage.setAlarmMsg(message);
                        SseEmitterManager.sendMessageToAllMatchSseEmitter(deviceAlarmMessage);
                        alarmIds.add(deviceAlarmMessage.getAlarmId().toString());
                        alarmMessages.add(deviceAlarmMessage);
                    }

                    deviceAlarmMessage_.setEndTime(now);

                    deviceAlarmMessageCacheService.endAlarm(deviceAlarmMessage_);
                    message = StringUtils.concat(message,",清除DEVICE_CUR_ALARM_STATUS_CACHE中的告警数据!!!");
                    logServiceService.builderLogServiceAndSave(SOURCE_SERVICE_PLATFORM, LogConstants.INFO,message,SERVICE_TYPE_PLATFORM);
                }
            }
            deviceIndicatorParamRelService.updateDeviceIndicatorParamRelAndSendSseWhenUpdateAlarm(alarmMessages);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Long> alarmIds = new ArrayList<>();
        alarmIds.toArray(new String[0]);
    }
}
