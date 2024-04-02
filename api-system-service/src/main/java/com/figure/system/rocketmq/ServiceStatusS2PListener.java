package com.figure.system.rocketmq;

import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.consumer.ServiceStatusS2PConsumer;
import com.figure.core.service.spectrum.ISpectrumServiceAlarmMessageService;
import com.figure.core.util.JSONUtil;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

@Component
@RocketMQMessageListener(topic = RocketMQConstants.SERVICE_STATUS, consumerGroup = RocketMQConstants.SERVICE_STATUS)
public class ServiceStatusS2PListener implements RocketMQListener<MessageExt> {


    @Resource
    private ISpectrumServiceAlarmMessageService spectrumServiceAlarmMessageService;

    @Override
    public void onMessage(MessageExt message) {
        ServiceStatusS2PConsumer consumer = JSONUtil.json2ObjectByT(new String(message.getBody()), ServiceStatusS2PConsumer.class);
        this.spectrumServiceAlarmMessageService.processSpectrumServiceAlarmMessage(consumer);
    }

}




