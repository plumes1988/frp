package com.figure.core.service.log;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.model.log.LogDeviceStateIndicatorChange;
import com.figure.core.rocketmq.struct.consumer.DeviceStateIndicatorChangeP2SConsumer;

import java.util.List;

/**
 * <p>
 * 设备状态直播变更日志 IService
 * </p>
 *
 * @author feather
 * @date 2024-01-17 14:21:47
 */
public interface ILogDeviceStateIndicatorChangeService extends IService<LogDeviceStateIndicatorChange> {

    void processDeviceStateIndicatorChange(DeviceStateIndicatorChangeP2SConsumer consumer);

    void fillEntityProps(List<LogDeviceStateIndicatorChange> records);
}