package com.figure.system.rocketmq;

import com.figure.core.util.JSONUtil;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.consumer.SignalAlarmUpdateS2PConsumer;
import com.figure.core.service.alarm.IAlarmMessageInfoService;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RocketMQMessageListener(topic = RocketMQConstants.SIGNAL_ALARM_UPDATE_S2P, consumerGroup = RocketMQConstants.SIGNAL_ALARM_UPDATE_S2P)
public class SignalAlarmUpdateS2PListener implements RocketMQListener<MessageExt> {

    @Resource
    IAlarmMessageInfoService alarmMessageInfoService;

    @Override
    public void onMessage(MessageExt message) {
        SignalAlarmUpdateS2PConsumer consumer = JSONUtil.json2ObjectByT(new String(message.getBody()),
                SignalAlarmUpdateS2PConsumer.class);
        this.alarmMessageInfoService.processAlarmUpdate(consumer);
    }
}
