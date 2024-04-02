package com.figure.system.rocketmq;

import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.consumer.DeviceStateIndicatorChangeP2SConsumer;
import com.figure.core.service.log.ILogDeviceStateIndicatorChangeService;
import com.figure.core.util.JSONUtil;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
//@RocketMQMessageListener(topic = RocketMQConstants.DEVICE_STATE_INDICATOR_CHANGE_P2S, consumerGroup = RocketMQConstants.DEVICE_STATE_INDICATOR_CHANGE_P2S)
public class DeviceStateIndicatorChangeP2SListener implements RocketMQListener<MessageExt> {

    @Resource
    private ILogDeviceStateIndicatorChangeService logDeviceStateIndicatorChangeService;

    @Override
    public void onMessage(MessageExt message) {
        DeviceStateIndicatorChangeP2SConsumer consumer = JSONUtil.json2ObjectByT(new String(message.getBody()),
                DeviceStateIndicatorChangeP2SConsumer.class);
        this.logDeviceStateIndicatorChangeService.processDeviceStateIndicatorChange(consumer);
    }
}
