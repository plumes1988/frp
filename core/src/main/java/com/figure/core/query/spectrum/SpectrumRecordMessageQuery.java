package com.figure.core.query.spectrum;

import com.figure.core.model.spectrum.SpectrumRecordMessage;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Ge;
import com.figure.core.util.copycat.annotaion.Le;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SpectrumRecordMessageQuery extends AbstractQuery<SpectrumRecordMessage> {

    @Eq
    @ApiModelProperty("主键")
    private Long id;

    @Eq
    @ApiModelProperty("频谱服务编号")
    private String serviceCode;

    @Eq
    @ApiModelProperty("频谱服务名称")
    private String serviceName;

    @Eq
    @ApiModelProperty("频谱设备编号")
    private String spectrumCode;

    @Eq
    @ApiModelProperty("频谱设备名称")
    private String spectrumName;

    @Eq
    @ApiModelProperty("中心频率单位Hz")
    private Integer centerFrequency;

    @Eq
    @ApiModelProperty("带宽单位Hz")
    private Integer frequencySpan;

    @Eq
    @ApiModelProperty("电平单位dBμV")
    private Integer level;

    @Eq
    @ApiModelProperty("载噪比单位dB")
    private String CNR;

    @Eq
    @ApiModelProperty("开始频率单位Hz")
    private Integer startFrequency;

    @Eq
    @ApiModelProperty("结束频率单位Hz")
    private Integer endFrequency;

    @Eq
    @ApiModelProperty("实时频谱数据")
    private String traceData;

    @Eq
    @ApiModelProperty("平均频谱数据")
    private String traceDataAverage;

    @Eq
    @ApiModelProperty("数据接收时间")
    private Date recordTime;

    @Ge(alias = "createTime")
    @ApiModelProperty("数据记录时间查询开始时间")
    private Date createStart;

    @Le(alias = "createTime")
    @ApiModelProperty("数据记录时间查询结束时间")
    private Date createEnd;

    @Ge(alias = "recordTime")
    @ApiModelProperty("数据接收时间查询开始时间")
    private Date startTime;

    @Le(alias = "recordTime")
    @ApiModelProperty("数据接收时间查询结束时间")
    private Date endTime;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}