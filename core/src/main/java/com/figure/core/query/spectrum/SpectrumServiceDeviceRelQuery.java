package com.figure.core.query.spectrum;

import com.figure.core.model.spectrum.SpectrumServiceDeviceRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpectrumServiceDeviceRelQuery extends AbstractQuery<SpectrumServiceDeviceRel> {

    @Eq
    @ApiModelProperty("主键")
    private Integer id;

    @Eq(alias = "t.serviceCode")
    @ApiModelProperty("频谱服务编号")
    private String serviceCode;

    @Eq
    @ApiModelProperty("频谱仪设备编号")
    private String spectrumCode;

    @Eq
    @ApiModelProperty("中心频率单位Hz")
    private Integer centerFrequency;

    @Eq
    @ApiModelProperty("带宽单位Hz")
    private Integer frequencySpan;

    @Eq
    @ApiModelProperty("RBW单位Hz")
    private Integer RBW;

    @Eq
    @ApiModelProperty("VBW单位Hz")
    private Integer VBW;

    @Eq
    @ApiModelProperty("频谱异常阈值dB")
    private Integer level;

    @Eq
    @ApiModelProperty("开始频率单位Hz")
    private Integer startFrequency;

    @Eq
    @ApiModelProperty("结束频率单位Hz")
    private Integer endFrequency;

    @Eq
    @ApiModelProperty("启用状态 0停用 1启用")
    private Integer spectrumStatus;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}