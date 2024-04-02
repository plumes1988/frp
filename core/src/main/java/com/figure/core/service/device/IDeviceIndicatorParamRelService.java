package com.figure.core.service.device;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.rocketmq.message.DeviceIndexSet;
import com.figure.core.rocketmq.message.DeviceItemInfo;
import com.figure.core.rocketmq.message.DeviceStatus;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2022-06-28
 */
public interface IDeviceIndicatorParamRelService extends IService<DeviceIndicatorParamRel> {

    void updateDeviceAlarmIndicatorParamRelAndSendSse(String deviceCode);

    void updateDeviceAlarmIndicatorParamRelAndSendSse(String deviceCode,Boolean forOffline);

    Integer getRecordMode(int deviceId, String indicatorCode);

    void setRecordModeCache();

    boolean indicatorSetCheckSetValueIsSameWithCurValue(DeviceIndexSet deviceIndexSet);

    boolean dataOutOfBounds(DeviceIndexSet deviceIndexSet);

    void applyParamsSetting2SameProductAndModelDevices(Integer deviceId);

    void loadAllDeviceCurIndicatorValueIntoJavaMemoryDb();

    void updateDeviceIndicatorParamRelAndSendSseWhenUpdateAlarm(List<DeviceAlarmMessage> alarmMessages);

    void send2WebForDelete(List<DeviceAlarmMessage> alarms);

    Integer getChangeThreshold(int deviceId, String indexCode);

    void setChangeThresholdCache();

    void updateCache();

    void sendCurDeviceIndicator(Map<String, Object> params);
}
