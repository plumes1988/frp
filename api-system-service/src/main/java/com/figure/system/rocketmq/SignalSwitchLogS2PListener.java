package com.figure.system.rocketmq;

import com.figure.core.util.JSONUtil;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.consumer.SignalSwitchLogS2PConsumer;
import com.figure.core.service.alarm.IAlarmSwitchEventInfoService;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RocketMQMessageListener(topic = RocketMQConstants.SIGNAL_SWITCH_LOG_S2P, consumerGroup = RocketMQConstants.SIGNAL_SWITCH_LOG_S2P)
public class SignalSwitchLogS2PListener implements RocketMQListener<MessageExt> {
    @Resource
    private IAlarmSwitchEventInfoService alarmSwitchEventInfoService;

    @Override
    public void onMessage(MessageExt message) {
        SignalSwitchLogS2PConsumer consumer = JSONUtil.json2ObjectByT(new String(message.getBody()),
                SignalSwitchLogS2PConsumer.class);
        this.alarmSwitchEventInfoService.acceptAlarm(consumer);
    }
}
