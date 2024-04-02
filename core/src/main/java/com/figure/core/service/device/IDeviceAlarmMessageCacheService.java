package com.figure.core.service.device;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.device.DeviceAlarmMessage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2022-07-01
 */
public interface IDeviceAlarmMessageCacheService extends IService<DeviceAlarmMessage> {

    List<DeviceAlarmMessage> getAlarmingAlarms(DeviceAlarmMessage deviceAlarmMessage);

    Boolean isAlarming(DeviceAlarmMessage deviceAlarmMessage) ;

    void newAlarm(DeviceAlarmMessage deviceAlarmMessage);

    void endAlarm(DeviceAlarmMessage deviceAlarmMessage);

    DeviceAlarmMessage getTheNewestAlarmingAlarm(DeviceAlarmMessage deviceAlarmMessage) ;

    void updateAlarms(List<DeviceAlarmMessage> alarms, Integer updateAlarmType) ;

    void loadAlarmingAlarmsFromDb();

    Boolean newestAlarmsIsNotEndAndMisinterpret(String deviceCode, String indicatorCode, String alarmTypeId) ;

}
