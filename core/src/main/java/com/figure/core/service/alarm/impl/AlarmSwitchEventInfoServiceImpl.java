package com.figure.core.service.alarm.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.alarm.AlarmSwitchEventInfo;
import com.figure.core.repository.alarm.AlarmSwitchEventInfoMapper;
import com.figure.core.rocketmq.struct.consumer.SignalSwitchLogS2PConsumer;
import com.figure.core.service.alarm.IAlarmSwitchEventInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 信号倒换记录信息Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2023-03-22 10:53:57
 */
@Service
public class AlarmSwitchEventInfoServiceImpl extends ServiceImpl<AlarmSwitchEventInfoMapper, AlarmSwitchEventInfo> implements IAlarmSwitchEventInfoService {

    @Override
    public void acceptAlarm(SignalSwitchLogS2PConsumer consumer) {
        AlarmSwitchEventInfo alarmSwitchEventInfo = new AlarmSwitchEventInfo(consumer);
//        this.save(alarmSwitchEventInfo);
    }
}