package com.figure.system.rocketmq;

import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.consumer.SpectrumAnalysisAlarmS2PConsumer;
import com.figure.core.service.spectrum.ISpectrumAlarmMessageService;
import com.figure.core.util.JSONUtil;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RocketMQMessageListener(topic = RocketMQConstants.SPECTRUM_ANALYSIS_ALARM_S2P, consumerGroup = RocketMQConstants.SPECTRUM_ANALYSIS_ALARM_S2P)
public class SpectrumAnalysisAlarmS2PListener implements RocketMQListener<MessageExt> {

    @Resource
    private ISpectrumAlarmMessageService spectrumAlarmMessageService;


    @Override
    public void onMessage(MessageExt message) {
        SpectrumAnalysisAlarmS2PConsumer consumer = JSONUtil.json2ObjectByT(new String(message.getBody()), SpectrumAnalysisAlarmS2PConsumer.class);
        spectrumAlarmMessageService.acceptAlarm(consumer);
    }
}
