package com.figure.core.service.others;

import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.rocketmq.message.DeviceItemInfo;
import com.figure.core.rocketmq.message.DeviceStatus;

public interface IDenseDataService {

    void saveHistoryIndicator(DeviceStatus deviceStatus, DeviceItemInfo indexInfo, String messageTime, Integer alarmStatus);

    void saveDeviceStateIndicatorChange(DeviceIndicatorParamRel rel);
}
