package com.figure.system.rocketmq;

import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.consumer.SpectrumAnalysisDataS2PConsumer;
import com.figure.core.service.spectrum.ISpectrumAnalysisDataService;
import com.figure.core.util.JSONUtil;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RocketMQMessageListener(topic = RocketMQConstants.SPECTRUM_ANALYSIS_DATA_S2P, consumerGroup = RocketMQConstants.SPECTRUM_ANALYSIS_DATA_S2P)
public class SpectrumAnalysisDataS2PListener implements RocketMQListener<MessageExt> {

    @Resource
    private ISpectrumAnalysisDataService spectrumAnalysisDataService;

    @Override
    public void onMessage(MessageExt message) {
        SpectrumAnalysisDataS2PConsumer consumer = JSONUtil.json2ObjectByT(new String(message.getBody()), SpectrumAnalysisDataS2PConsumer.class);
        spectrumAnalysisDataService.acceptData(consumer);
    }
}
