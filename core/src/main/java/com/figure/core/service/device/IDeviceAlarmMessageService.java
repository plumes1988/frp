package com.figure.core.service.device;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.model.device.DeviceInfo;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2022-07-01
 */
public interface IDeviceAlarmMessageService extends IService<DeviceAlarmMessage> {

   void newAlarm(DeviceAlarmMessage deviceAlarmMessage);

   void endAlarm(DeviceAlarmMessage deviceAlarmMessage);

   Boolean newOffLineAlarm(DeviceInfo deviceInfo, String indicatorCode, String alarmTypeId, Date date);

   void endOffLineAlarm(DeviceInfo deviceInfo, String indicatorCode, String alarmTypeId, Date date);

    void fillEntityProps(List<DeviceAlarmMessage> records);
}
