package com.figure.system.scheduled;

import com.figure.core.constant.Constants;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumAnalysisAlarmKey;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.service.spectrum.ISpectrumAlarmMessageService;
import com.figure.core.service.sys.ISysParaService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Component
@EnableScheduling
public class SpectrumAlarmCheckScheduler {

    @Resource
    ISpectrumAlarmMessageService spectrumAlarmMessageService;

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
        if (redisTemplateService.lock(RedisConstants.CURR_SPECTRUM_ALARM_MAP_LOCK)) {
            try {
                Map<SpectrumAnalysisAlarmKey, SpectrumAlarmMessage> beginAlarmMap = spectrumAlarmMessageService.getBeginAlarmMap();

                for (Map.Entry entry : beginAlarmMap.entrySet()) {
                    SpectrumAlarmMessage spectrumAlarmMessage = (SpectrumAlarmMessage) entry.getValue();
                    long intervalTime = new Date().getTime() - spectrumAlarmMessage.getLastUpdateTime().getTime();
                    if (intervalTime > alarmTimeout) {
                        spectrumAlarmMessage.setAlarmFlag(Constants.ALARM_STOP);
//                        this.spectrumAlarmMessageService.updateById(spectrumAlarmMessage);
                        this.spectrumAlarmMessageService.save(spectrumAlarmMessage);
                        //停止报警频谱数据保存
                        //this.spectrumAlarmMessageService.saveTraceData(spectrumAlarmMessage);
                        SpectrumAnalysisAlarmKey spectrumAnalysisAlarmKey = new SpectrumAnalysisAlarmKey(spectrumAlarmMessage);
                        this.spectrumAlarmMessageService.removeAlarmByAlarmKey(spectrumAnalysisAlarmKey);
                    }
                }
            } finally {
                redisTemplateService.unlock(RedisConstants.CURR_SPECTRUM_ALARM_MAP_LOCK);
            }
        }
    }
}
