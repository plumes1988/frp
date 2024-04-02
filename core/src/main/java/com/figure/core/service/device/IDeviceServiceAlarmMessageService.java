package com.figure.core.service.device;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.device.DeviceCollectServer;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.device.DeviceServiceAlarmMessage;

import java.util.List;

/**
 * <p>
 * 应用服务报警记录表 服务类
 * </p>
 *
 * @author jobob
 * @since 2024-03-04
 */
public interface IDeviceServiceAlarmMessageService extends IService<DeviceServiceAlarmMessage> {

    void fillEntityProps(List<DeviceServiceAlarmMessage> records);

    boolean isOnline(Integer serviceType, String keyName,String keyValue);

    public void newAlarm(String serviceName, Integer serviceType, Integer alarmType,String alarmMsg);

    public void newAlarm(String serviceCode, String serviceName, Integer serviceType, Integer alarmType,String alarmMsg);

    void endAlarm(String keyName,String keyValue,Integer serviceType);
}
