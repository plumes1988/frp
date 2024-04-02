package com.figure.core.model.spectrum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpectrumServiceInfoList extends SpectrumServiceInfo {

    private String relServiceCode;

    private String spectrumCode;

    private Integer relId;

    /**
     * 中心频率单位Hz
     */
    @ApiModelProperty("中心频率单位Hz")
    private Integer centerFrequency;
    /**
     * 带宽单位Hz
     */
    @ApiModelProperty("带宽单位Hz")
    private Integer frequencySpan;
    /**
     * RBW单位Hz
     */
    @ApiModelProperty("RBW单位Hz")
    private Integer RBW;
    /**
     * VBW单位Hz
     */
    @ApiModelProperty("VBW单位Hz")
    private Integer VBW;

    /**
     * 频谱异常阈值
     */
    @ApiModelProperty("频谱异常阈值")
    private Integer level;

    /**
     * 启用状态 0停用 1启用
     */
    @ApiModelProperty("启用状态 0停用 1启用")
    private Integer spectrumStatus;
}
