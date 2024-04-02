package com.figure.core.service.spectrum.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.spectrum.SpectrumReferlineInfo;
import com.figure.core.repository.spectrum.SpectrumReferlineInfoMapper;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.RocketMQProducer;
import com.figure.core.rocketmq.struct.producer.SpectrumReferLineSetP2SProducer;
import com.figure.core.service.spectrum.ISpectrumReferlineInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 频谱仪参考频谱Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2023-12-07 13:48:22
 */
@Service
public class SpectrumReferlineInfoServiceImpl extends ServiceImpl<SpectrumReferlineInfoMapper, SpectrumReferlineInfo> implements ISpectrumReferlineInfoService {

    @Resource
    RocketMQProducer rocketMQProducer;

    @Override
    public boolean save(SpectrumReferlineInfo spectrumReferlineInfo) {
        boolean result = super.save(spectrumReferlineInfo);
        if (result) {
            SpectrumReferLineSetP2SProducer producer = new SpectrumReferLineSetP2SProducer(spectrumReferlineInfo);
            rocketMQProducer.send(RocketMQConstants.SPECTRUM_REFERLINE_SET_P2S, "", producer);
        }
        return result;
    }

    @Override
    public boolean updateById(SpectrumReferlineInfo spectrumReferlineInfo) {
        boolean result = super.updateById(spectrumReferlineInfo);
        if (result) {
            SpectrumReferLineSetP2SProducer producer = new SpectrumReferLineSetP2SProducer(spectrumReferlineInfo);
            rocketMQProducer.send(RocketMQConstants.SPECTRUM_REFERLINE_SET_P2S, "", producer);
        }
        return result;
    }
}