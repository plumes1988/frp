package com.figure.system.scheduled;

import com.figure.core.constant.Constants;
import com.figure.core.model.alarm.AlarmEventInfo;
import com.figure.core.redis.RedisConstants;
import com.figure.core.service.alarm.IAlarmEventInfoService;
import com.figure.core.service.sys.ISysParaService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Component
@EnableScheduling
public class EventCheckScheduler {

    @Resource
    IAlarmEventInfoService alarmEventInfoService;

    @Resource
    ISysParaService sysParaService;

    @Scheduled(fixedRate = 1 * 1000, initialDelay = 20 * 1000)//间隔1秒
    private void eventCheckTask() {
        long eventTime = 30 * 1000;
        Map<String, String> alarmParamMap = sysParaService.getParamCacheByName(RedisConstants.ALARM_PARAM);
        if (alarmParamMap != null && alarmParamMap.get(Constants.EVENT_TIME) != null) {
            eventTime = Integer.valueOf(alarmParamMap.get(Constants.EVENT_TIME)).intValue() * 1000;
        }
        Map<String, AlarmEventInfo> eventInfoMap = alarmEventInfoService.getCurrEventMap();
        for (Map.Entry entry : eventInfoMap.entrySet()) {
            AlarmEventInfo alarmEventInfo = (AlarmEventInfo) entry.getValue();
            long intervalTime = new Date().getTime() - alarmEventInfo.getLastUpdateTime().getTime();
            if (alarmEventInfo.getAlarmCount() == 0 && intervalTime > eventTime) {
                alarmEventInfoService.removeEventBySystemCode(alarmEventInfo.getSystemCode());
                alarmEventInfo.setEventFlag(Constants.EVENT_STOP);
                alarmEventInfoService.updateById(alarmEventInfo);
            }
        }
    }
}
