package com.figure.core.query.spectrum;

import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Ge;
import com.figure.core.util.copycat.annotaion.Le;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SpectrumAlarmMessageQuery extends AbstractQuery<SpectrumAlarmMessage> {

    @Eq
    @ApiModelProperty("频谱报警Id")
    private Long alarmId;

    @Eq
    @ApiModelProperty("500001 频率异常 500002频宽异常 500003频偏异常")
    private Integer alarmType;

    @Eq
    @ApiModelProperty("采集服务编号")
    private String serviceCode;

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

    @Ge
    @ApiModelProperty("报警开始时间")
    private Date startTime;

    @Le
    @ApiModelProperty("报警结束时间")
    private Date endTime;

    @Eq
    @ApiModelProperty("报警持续时间")
    private Integer alarmDuration;

    @Eq
    @ApiModelProperty("报警状态 0恢复 1报警中")
    private Integer alarmFlag;

    @Eq
    @ApiModelProperty("最后更新时间")
    private Date lastUpdateTime;

    @Eq
    @ApiModelProperty("处理状态 0未确认 1事故 2误报")
    private Integer commitFlag;

    @Eq
    @ApiModelProperty("处理意见")
    private String commitInfo;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}