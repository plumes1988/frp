package com.figure.core.rocketmq.struct.producer;

import com.figure.core.model.alarm.AlarmMessageInfo;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.MessageBase;
import com.figure.core.rocketmq.struct.info.AlarmInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SignalAlarmCheckP2SProducer extends MessageBase {

    private List<AlarmInfo> alarmInfoList = new ArrayList<>();

    public SignalAlarmCheckP2SProducer(List<AlarmMessageInfo> alarmMessageInfoList) {
        super(RocketMQConstants.SIGNAL_ALARM_CHECK_P2S, RocketMQConstants.ROCKETMQ_ALL_TAGS);
        for (AlarmMessageInfo alarmMessageInfo : alarmMessageInfoList) {
            AlarmInfo alarmInfo = new AlarmInfo(alarmMessageInfo);
            this.alarmInfoList.add(alarmInfo);
        }
    }
}
