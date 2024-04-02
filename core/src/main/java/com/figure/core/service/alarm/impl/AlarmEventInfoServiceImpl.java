package com.figure.core.service.alarm.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.alarm.AlarmEventInfo;
import com.figure.core.model.alarm.AlarmMessageInfo;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.alarm.AlarmEventInfoMapper;
import com.figure.core.service.alarm.IAlarmEventInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 播出事件信息Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2023-03-22 10:53:57
 */
@Service
public class AlarmEventInfoServiceImpl extends ServiceImpl<AlarmEventInfoMapper, AlarmEventInfo> implements IAlarmEventInfoService {

    @Resource
    IRedisTemplateService redisTemplateService;

    @Override
    public void processEventInfo(AlarmMessageInfo alarmMessageInfo) {
        Map<String, AlarmEventInfo> currEventMap = getCurrEventMap();
        AlarmEventInfo alarmEventInfo = currEventMap.remove(alarmMessageInfo.getSystemCode());

        if (alarmEventInfo == null) {
            alarmEventInfo = new AlarmEventInfo();
            alarmEventInfo.setFrontId(alarmMessageInfo.getFrontId());
            alarmEventInfo.setFrontName(alarmMessageInfo.getFrontName());
            alarmEventInfo.setSystemCode(alarmMessageInfo.getSystemCode());
            alarmEventInfo.setSystemName(alarmMessageInfo.getSystemName());
            alarmEventInfo.setStartTime(alarmMessageInfo.getStartTime());
            alarmEventInfo.setDuration(alarmMessageInfo.getDuration());
            alarmEventInfo.setEventFlag(Constants.EVENT_ONGOING);
            //判断事故等级
            //alarmEventInfo.setEventType();
            //alarmEventInfo.setEventLevel();
            this.baseMapper.insert(alarmEventInfo);
        } else {
            if (alarmMessageInfo.getAlarmFlag().intValue() == Constants.ALARM_STOP) {
                alarmEventInfo.setAlarmCount(alarmEventInfo.getAlarmCount() - 1);
                if (alarmEventInfo.getAlarmCount() == 0) {
                    alarmEventInfo.setEndTime(alarmMessageInfo.getEndTime());
                    Long duration = (DateHelper.diffMillis(alarmEventInfo.getEndTime(), alarmEventInfo.getStartTime())) / 1000;
                    alarmEventInfo.setDuration(duration.intValue() * 1000);
                    //判断事故等级
                    //alarmEventInfo.setEventType();
                    //alarmEventInfo.setEventLevel();
                }
            } else if (alarmMessageInfo.getAlarmFlag().intValue() == Constants.ALARM_ONGOING) {
                alarmEventInfo.setAlarmCount(alarmEventInfo.getAlarmCount() + 1);
            }
            //判断事故等级
            //alarmEventInfo.setEventType();
            this.baseMapper.updateById(alarmEventInfo);
        }
        alarmEventInfo.setLastUpdateTime(new Date());
        addEventBySystemCode(alarmEventInfo);
    }

    @Override
    public Map<String, AlarmEventInfo> getCurrEventMap() {
        Map<String, AlarmEventInfo> alarmEventInfoMap = redisTemplateService.getMapRedisByKeyCache(RedisConstants.CURR_EVENT_MAP, String.class, AlarmEventInfo.class);
        if (alarmEventInfoMap == null) {
            alarmEventInfoMap = new HashMap<>();
            setCurrEventMap(alarmEventInfoMap);
        }
        return alarmEventInfoMap;
    }

    @Override
    public void setCurrEventMap(Map currEventMap) {
        redisTemplateService.setRedisCache(RedisConstants.CURR_EVENT_MAP, currEventMap);
    }

    @Override
    public void addEventBySystemCode(AlarmEventInfo alarmEventInfo) {
        Map<String, AlarmEventInfo> currEventMap = getCurrEventMap();
        currEventMap.put(alarmEventInfo.getSystemCode(), alarmEventInfo);
        setCurrEventMap(currEventMap);
    }

    @Override
    public void removeEventBySystemCode(String systemCode) {
        Map<String, AlarmEventInfo> currEventMap = getCurrEventMap();
        currEventMap.remove(systemCode);
        setCurrEventMap(currEventMap);
    }
}