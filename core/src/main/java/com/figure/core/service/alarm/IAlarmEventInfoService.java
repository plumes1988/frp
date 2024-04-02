package com.figure.core.service.alarm;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.alarm.AlarmEventInfo;
import com.figure.core.model.alarm.AlarmMessageInfo;

import java.util.Map;

/**
 * <p>
 * 播出事件信息 IService
 * </p>
 *
 * @author feather
 * @date 2023-03-22 10:53:57
 */
public interface IAlarmEventInfoService extends IService<AlarmEventInfo> {

    void processEventInfo(AlarmMessageInfo alarmMessageInfo);

    Map<String, AlarmEventInfo> getCurrEventMap();

    void setCurrEventMap(Map currEventMap);

    void addEventBySystemCode(AlarmEventInfo alarmEventInfo);

    void removeEventBySystemCode(String systemCode);

}