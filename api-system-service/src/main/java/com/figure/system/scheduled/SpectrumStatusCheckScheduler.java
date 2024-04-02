package com.figure.system.scheduled;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.figure.core.constant.Constants;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.device.DeviceAlarmtypeInfo;
import com.figure.core.model.spectrum.SpectrumDeviceAlarmKey;
import com.figure.core.model.spectrum.SpectrumServiceAlarmMessage;
import com.figure.core.model.spectrum.SpectrumServiceDeviceRel;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.service.device.IDeviceAlarmtypeInfoService;
import com.figure.core.service.spectrum.ISpectrumServiceAlarmMessageService;
import com.figure.core.service.spectrum.ISpectrumServiceDeviceRelService;
import com.figure.core.service.sys.ISysParaService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
@EnableScheduling
public class SpectrumStatusCheckScheduler {

    @Resource
    private ISysParaService sysParaService;

    @Resource
    private ISpectrumServiceAlarmMessageService spectrumServiceAlarmMessageService;
    @Resource
    private IRedisTemplateService redisTemplateService;
    @Resource
    private ISpectrumServiceDeviceRelService spectrumServiceDeviceRelService;
    @Resource
    private IDeviceAlarmtypeInfoService deviceAlarmtypeInfoService;

    private List<DeviceAlarmtypeInfo> deviceAlarmtypeInfoList = new ArrayList<>();

    @Scheduled(fixedRate = 1 * 1000, initialDelay = 30 * 1000)
    private void statusCheckTask() {

        int alarmTimeout = 10 * 1000;

        Map<Integer, String> deviceAlarmTypeMap = new HashMap<>();
        if (deviceAlarmtypeInfoList.size() == 0) {
            List<DeviceAlarmtypeInfo> deviceAlarmtypeInfoList = this.deviceAlarmtypeInfoService.list(Wrappers.lambdaQuery(DeviceAlarmtypeInfo.class).eq(DeviceAlarmtypeInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED));
            for (DeviceAlarmtypeInfo deviceAlarmtypeInfo : deviceAlarmtypeInfoList) {
                deviceAlarmTypeMap.put(deviceAlarmtypeInfo.getAlarmTypeId(), deviceAlarmtypeInfo.getAlarmTypeName());
            }
        }
        Date now = new Date();
        if (redisTemplateService.lock(RedisConstants.SERVICE_STATUS_ALARM_LOCK)) {
            try {
                Map<String, Map<String, SpectrumServiceDeviceRel>> spectrumServiceMap = this.spectrumServiceDeviceRelService.getSpectrumServiceData();
                for (String spectrumServiceKey : spectrumServiceMap.keySet()) {
                    String lastUpdateTimeString = redisTemplateService
                            .getObjectRedisCache(RedisConstants.SERVICE_STATUS_ALARM_LAST_UPDATE_TIME + "_" + spectrumServiceKey, String.class);
                    if (lastUpdateTimeString != null) {
                        Date lastUpdateTime = DateHelper.parse(lastUpdateTimeString);

                        if (DateHelper.diffMillis(now, lastUpdateTime) > alarmTimeout) {
                            processSpectrumServiceAlarm(deviceAlarmTypeMap, now, spectrumServiceMap, spectrumServiceKey);
                        } else {
                            LambdaQueryWrapper<SpectrumServiceAlarmMessage> spectrumServiceAlarmMessageLambdaQueryWrapper = Wrappers.lambdaQuery();
                            spectrumServiceAlarmMessageLambdaQueryWrapper.eq(SpectrumServiceAlarmMessage::getAlarmClass, 1)
                                    .eq(SpectrumServiceAlarmMessage::getServiceCode, spectrumServiceKey)
                                    .eq(SpectrumServiceAlarmMessage::getAlarmFlag, 1)
                                    .eq(SpectrumServiceAlarmMessage::getAlarmType, 90001);
                            SpectrumServiceAlarmMessage oldAlarm = this.spectrumServiceAlarmMessageService.getOne(spectrumServiceAlarmMessageLambdaQueryWrapper);
                            if (oldAlarm != null) {
                                oldAlarm.setAlarmFlag(0);
                                oldAlarm.setEndTime(now);
                                oldAlarm.setDuration((int) (DateHelper.diffMillis(oldAlarm.getEndTime(), oldAlarm.getStartTime()) / 1000));
                                oldAlarm.setLastUpdateTime(now);
                                oldAlarm.setUpdateTime(now);

                                this.spectrumServiceAlarmMessageService.updateById(oldAlarm);
                            }
                        }
                    } else {
                        processSpectrumServiceAlarm(deviceAlarmTypeMap, now, spectrumServiceMap, spectrumServiceKey);
                    }
                }


                Map<String, String> alarmParamMap = sysParaService.getParamCacheByName(RedisConstants.ALARM_PARAM);
                if (alarmParamMap != null && alarmParamMap.get(Constants.ALARM_TIMEOUT) != null) {
                    alarmTimeout = Integer.valueOf(alarmParamMap.get(Constants.ALARM_TIMEOUT)).intValue() * 1000;
                }
                Map<SpectrumDeviceAlarmKey, SpectrumServiceAlarmMessage> beginAlarmMap = spectrumServiceAlarmMessageService.getBeginAlarmMap();

                for (Map.Entry entry : beginAlarmMap.entrySet()) {
                    SpectrumServiceAlarmMessage spectrumServiceAlarmMessage = (SpectrumServiceAlarmMessage) entry.getValue();
                    long intervalTime = now.getTime() - spectrumServiceAlarmMessage.getLastUpdateTime().getTime();
                    if (intervalTime > alarmTimeout) {
                        SpectrumDeviceAlarmKey SpectrumDeviceAlarmKey = spectrumServiceAlarmMessage.getAlarmClass().intValue() == 1 ?
                                new SpectrumDeviceAlarmKey(spectrumServiceAlarmMessage.getServiceCode(), spectrumServiceAlarmMessage.getAlarmType())
                                : new SpectrumDeviceAlarmKey(spectrumServiceAlarmMessage.getSpectrumCode(), spectrumServiceAlarmMessage.getAlarmType());
                        this.spectrumServiceAlarmMessageService.removeAlarmByAlarmKey(SpectrumDeviceAlarmKey);
                        spectrumServiceAlarmMessage.setAlarmFlag(Constants.ALARM_STOP);
                        this.spectrumServiceAlarmMessageService.updateById(spectrumServiceAlarmMessage);
                    }
                }
            } finally {
                redisTemplateService.unlock(RedisConstants.SERVICE_STATUS_ALARM_LOCK);
            }
        }
    }

    private void processSpectrumServiceAlarm(Map<Integer, String> deviceAlarmTypeMap, Date now, Map<String, Map<String, SpectrumServiceDeviceRel>> spectrumServiceMap, String spectrumServiceKey) {
        LambdaQueryWrapper<SpectrumServiceAlarmMessage> spectrumServiceAlarmMessageLambdaQueryWrapper = Wrappers.lambdaQuery();
        spectrumServiceAlarmMessageLambdaQueryWrapper.eq(SpectrumServiceAlarmMessage::getAlarmClass, 1)
                .eq(SpectrumServiceAlarmMessage::getServiceCode, spectrumServiceKey)
                .eq(SpectrumServiceAlarmMessage::getAlarmFlag, 1)
                .eq(SpectrumServiceAlarmMessage::getAlarmType, 90001);
        SpectrumServiceAlarmMessage oldAlarm = this.spectrumServiceAlarmMessageService.getOne(spectrumServiceAlarmMessageLambdaQueryWrapper);
        if (oldAlarm == null) {
            Map<String, SpectrumServiceDeviceRel> spectrumServiceDeviceRelMap = spectrumServiceMap.get(spectrumServiceKey);
            String serviceName = null;
            for (String serviceDeviceRelKey : spectrumServiceDeviceRelMap.keySet()) {
                serviceName = spectrumServiceDeviceRelMap.get(serviceDeviceRelKey).getServiceName();
                break;
            }
            SpectrumServiceAlarmMessage spectrumServiceAlarmMessage = new SpectrumServiceAlarmMessage();
            spectrumServiceAlarmMessage.setAlarmClass(1);
            spectrumServiceAlarmMessage.setAlarmType(90001);
            spectrumServiceAlarmMessage.setAlarmTypeName(deviceAlarmTypeMap.get(90001));
            spectrumServiceAlarmMessage.setServiceCode(spectrumServiceKey);
            spectrumServiceAlarmMessage.setServiceName(serviceName);
            spectrumServiceAlarmMessage.setStartTime(now);
            spectrumServiceAlarmMessage.setDuration(10);
            spectrumServiceAlarmMessage.setAlarmFlag(1);
            spectrumServiceAlarmMessage.setAlarmContent("频谱采集服务离线");
            spectrumServiceAlarmMessage.setCreateTime(now);
            spectrumServiceAlarmMessage.setCreateUserId(1);

            this.spectrumServiceAlarmMessageService.save(spectrumServiceAlarmMessage);
//        } else {
//            oldAlarm.setDuration(oldAlarm.getDuration() + 1);
//            oldAlarm.setLastUpdateTime(now);
//            oldAlarm.setUpdateTime(now);
//            oldAlarm.setUpdateUserId(1);
//            this.spectrumServiceAlarmMessageService.updateById(oldAlarm);
        }
    }

}
