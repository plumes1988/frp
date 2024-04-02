package com.figure.core.rocketmq.struct.producer;

import com.alibaba.fastjson.annotation.JSONField;
import com.figure.core.model.spectrum.SpectrumServiceDeviceRel;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

@Data
public class SpectrumAnalysisSetP2SProducer extends MessageBase {

    /**
     * 频谱仪设备编号
     */
    private String spectrumCode;

    /**
     * 中心频率
     */
    private Integer centerFrequency;

    /**
     * 带宽
     */
    private Integer frequencySpan;

    /**
     * RBW
     */
    @JSONField(name = "RBW")
    private Integer RBW;

    /**
     * VBW
     */
    @JSONField(name = "VBW")
    private Integer VBW;

    /**
     * 开始频率
     */
    private Integer startFrequency;

    /**
     * 结束频率
     */
    private Integer endFrequency;

    public SpectrumAnalysisSetP2SProducer(SpectrumServiceDeviceRel spectrumServiceDeviceRel) {
        super(RocketMQConstants.SPECTRUM_ANALYSIS_SET_P2S, spectrumServiceDeviceRel.getSpectrumCode());
        this.centerFrequency = spectrumServiceDeviceRel.getCenterFrequency();
        this.frequencySpan = spectrumServiceDeviceRel.getFrequencySpan();
        this.RBW = spectrumServiceDeviceRel.getRBW();
        this.VBW = spectrumServiceDeviceRel.getVBW();
        this.spectrumCode = spectrumServiceDeviceRel.getSpectrumCode();
        this.startFrequency = spectrumServiceDeviceRel.getCenterFrequency() - (spectrumServiceDeviceRel.getFrequencySpan() / 2);
        this.endFrequency = spectrumServiceDeviceRel.getCenterFrequency() + (spectrumServiceDeviceRel.getFrequencySpan() / 2);
        this.spectrumStatus = spectrumServiceDeviceRel.getSpectrumStatus();
    }

    /**
     * 结束频率
     */
    private Integer spectrumStatus;
}
