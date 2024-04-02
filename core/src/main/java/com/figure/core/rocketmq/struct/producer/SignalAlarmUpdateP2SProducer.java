package com.figure.core.rocketmq.struct.producer;

import com.figure.core.model.alarm.AlarmMessageInfo;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.MessageBase;
import com.figure.core.rocketmq.struct.info.AlarmInfo;
import lombok.Data;

@Data
public class SignalAlarmUpdateP2SProducer extends MessageBase {

    private AlarmInfo alarmInfo;

    public SignalAlarmUpdateP2SProducer(AlarmMessageInfo alarmMessageInfo) {
        super(RocketMQConstants.SIGNAL_ALARM_UPDATE_P2S, RocketMQConstants.ROCKETMQ_ALL_TAGS);
        this.alarmInfo = new AlarmInfo(alarmMessageInfo);
    }
}
