package com.figure.core.model.spectrum;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.figure.core.base.BaseModel;
import com.figure.core.helper.DateHelper;
import com.figure.core.rocketmq.struct.consumer.SpectrumAnalysisAlarmS2PConsumer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * <p>
 * 频谱报警记录
 * </p>
 *
 *@author feather
 *@date 2024-01-24 14:26:20
 */
@Data
@Accessors(chain = true)
@TableName("spectrum_alarm_message")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SpectrumAlarmMessage", description = "频谱报警记录")
public class SpectrumAlarmMessage extends BaseModel {

    /**
     *频谱报警Id 
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("频谱报警Id")
    private Long alarmId;
    /**
     * 500001 频率异常 500002频宽异常 500003频偏异常
     */
    @ApiModelProperty("500001 频率异常 500002频宽异常 500003频偏异常")
    private Integer alarmType;
    /**
     * 采集服务编号
     */
    @ApiModelProperty("采集服务编号")
    private String serviceCode;
    /**
     * 采集服务名称
     */
    @ApiModelProperty("采集服务名称")
    private String serviceName;
    /**
     * 频谱设备编号
     */
    @ApiModelProperty("频谱设备编号")
    private String spectrumCode;
    /**
     * 频谱设备名称
     */
    @ApiModelProperty("频谱设备名称")
    private String spectrumName;
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
     * 电平单位dBμV
     */
    @ApiModelProperty("电平单位dBμV")
    private Integer level;
    /**
     * 载噪比单位dB
     */
    @ApiModelProperty("载噪比单位dB")
    private String CNR;
    /**
     * 开始频率单位Hz
     */
    @ApiModelProperty("开始频率单位Hz")
    private Integer startFrequency;
    /**
     *结束频率单位Hz 
     */
    @ApiModelProperty("结束频率单位Hz")
    private Integer endFrequency;
    /**
     *实时频谱数据 
     */
    @TableField(exist = false)
    @ApiModelProperty("实时频谱数据")
    private String traceData;
    /**
     *平均频谱数据 
     */
    @TableField(exist = false)
    @ApiModelProperty("平均频谱数据")
    private String traceDataAverage;
    /**
     *报警开始时间 
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    @ApiModelProperty("报警开始时间")
    private Date startTime;
    /**
     *报警结束时间 
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    @ApiModelProperty("报警结束时间")
    private Date endTime;
    /**
     *报警持续时间 
     */
    @ApiModelProperty("报警持续时间")
    private Integer alarmDuration;
    /**
     *报警状态 0恢复 1报警中 
     */
    @ApiModelProperty("报警状态 0恢复 1报警中")
    private Integer alarmFlag;
    /**
     * 最后更新时间
     */
    @ApiModelProperty("最后更新时间")
    private Date lastUpdateTime;
    /**
     * 处理状态 0未确认 1事故 2误报
     */
    @ApiModelProperty("处理状态 0未确认 1事故 2误报")
    private Integer commitFlag;
    /**
     * 处理意见
     */
    @ApiModelProperty("处理意见")
    private String commitInfo;
    /**
     *状态：0:正常、1:删除、2:停用 
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;


    public SpectrumAlarmMessage(SpectrumAnalysisAlarmS2PConsumer consumer, String serviceName, String spectrumName) {
        this.alarmId = consumer.getMessageHead().getMessageID();
        this.serviceCode = consumer.getServiceCode();
        this.alarmType = consumer.getAlarmID().intValue();
        this.centerFrequency = consumer.getCenterFrequency();
        this.frequencySpan = consumer.getFrequencySpan();
        this.spectrumName = spectrumName;
        this.serviceName = serviceName;
        this.spectrumCode = consumer.getSpectrumCode();
        this.startFrequency = consumer.getStartFrequency();
        this.endFrequency = consumer.getEndFrequency();
        Date startTime = DateHelper.parse(consumer.getStartTime(), DateHelper.patterns_masks[1]);
        Date endTime = DateHelper.add(startTime, Calendar.SECOND, consumer.getAlarmDuration());
        this.startTime = startTime;
        this.endTime = endTime;
        this.alarmDuration = consumer.getAlarmDuration();
        this.traceData = consumer.getTraceData().stream().map(Object::toString).collect(Collectors.joining(","));
        this.traceDataAverage = consumer.getTraceDataAverage().stream().map(Object::toString).collect(Collectors.joining(","));
        this.alarmFlag = consumer.getAlarmFlag();
        this.lastUpdateTime = new Date();
        this.level = consumer.getLevel();
        this.CNR = consumer.getCNR();
        this.setCreateTime(new Date());
    }

    public SpectrumAlarmMessage(){

    }
}