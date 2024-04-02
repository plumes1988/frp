package com.figure.core.service.alarm;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.alarm.AlarmSwitchEventInfo;
import com.figure.core.rocketmq.struct.consumer.SignalSwitchLogS2PConsumer;

/**
 * <p>
 * 信号倒换记录信息 IService
 * </p>
 *
 * @author feather
 * @date 2023-03-22 10:53:57
 */
public interface IAlarmSwitchEventInfoService extends IService<AlarmSwitchEventInfo> {

    void acceptAlarm(SignalSwitchLogS2PConsumer consumer);
}