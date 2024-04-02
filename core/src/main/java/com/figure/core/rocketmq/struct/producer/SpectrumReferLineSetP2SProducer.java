package com.figure.core.rocketmq.struct.producer;

import com.figure.core.model.spectrum.SpectrumReferlineInfo;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.MessageBase;
import com.figure.core.util.StringUtils;
import lombok.Data;

import java.util.List;

@Data
public class SpectrumReferLineSetP2SProducer extends MessageBase {

    /**
     * 频谱仪设备编号
     */
    private String spectrumCode;

    /**
     * 参考频谱频谱
     */
    private List<Integer> referData;

    public SpectrumReferLineSetP2SProducer(SpectrumReferlineInfo spectrumReferlineInfo) {
        super(RocketMQConstants.SPECTRUM_REFERLINE_SET_P2S, spectrumReferlineInfo.getSpectrumCode());
        this.spectrumCode = spectrumReferlineInfo.getSpectrumCode();
        this.referData = StringUtils.StringToIntList(spectrumReferlineInfo.getSpectrumData());
    }
}
