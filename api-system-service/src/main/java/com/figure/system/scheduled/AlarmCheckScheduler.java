package com.figure.system.scheduled;

import com.figure.core.constant.Constants;
import com.figure.core.model.alarm.AlarmKey;
import com.figure.core.model.alarm.AlarmMessageInfo;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.service.alarm.IAlarmEventInfoService;
import com.figure.core.service.alarm.IAlarmMessageInfoService;
import com.figure.core.service.sys.ISysParaService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Component
@EnableScheduling
public class AlarmCheckScheduler {

    @Resource
    IAlarmMessageInfoService alarmMessageInfoService;

    @Resource
    IAlarmEventInfoService alarmEventInfoService;

    @Resource
    ISysParaService sysParaService;

    @Resource
    IRedisTemplateService redisTemplateService;

    @Scheduled(fixedRate = 1 * 1000, initialDelay = 20 * 1000)//间隔1秒
    private void alarmCheckTask() {
        long alarmTimeout = 10 * 1000;
        Map<String, String> alarmParamMap = sysParaService.getParamCacheByName(RedisConstants.ALARM_PARAM);
        if (alarmParamMap != null && alarmParamMap.get(Constants.ALARM_TIMEOUT) != null) {
            alarmTimeout = Integer.valueOf(alarmParamMap.get(Constants.ALARM_TIMEOUT)).intValue() * 1000;
        }
        if (redisTemplateService.lock(RedisConstants.CURR_ALARM_MAP_LOCK)) {
            try {
                Map<AlarmKey, AlarmMessageInfo> beginAlarmMap = alarmMessageInfoService.getBeginAlarmMap();

                for (Map.Entry entry : beginAlarmMap.entrySet()) {
                    AlarmMessageInfo alarmMessageInfo = (AlarmMessageInfo) entry.getValue();
                    long intervalTime = new Date().getTime() - alarmMessageInfo.getLastUpdateTime().getTime();
                    if (intervalTime > alarmTimeout) {
                        AlarmKey alarmKey = new AlarmKey(alarmMessageInfo);
                        this.alarmMessageInfoService.removeAlarmByAlarmKey(alarmKey);
                        alarmMessageInfo.setAlarmFlag(Constants.ALARM_STOP);
                        this.alarmMessageInfoService.updateById(alarmMessageInfo);
                        alarmEventInfoService.processEventInfo(alarmMessageInfo);
                    }
                }
            } finally {
                redisTemplateService.unlock(RedisConstants.CURR_ALARM_MAP_LOCK);
            }
        }
    }
}
