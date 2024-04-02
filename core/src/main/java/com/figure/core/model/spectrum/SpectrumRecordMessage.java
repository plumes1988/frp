package com.figure.core.model.spectrum;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import com.figure.core.helper.DateHelper;
import com.figure.core.rocketmq.struct.consumer.SpectrumAnalysisDataS2PConsumer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 频谱数据
 * </p>
 *
 * @author feather
 * @date 2024-01-25 13:33:41
 */
@Data
@Accessors(chain = true)
@TableName("spectrum_record_message")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SpectrumRecordMessage", description = "频谱数据")
public class SpectrumRecordMessage extends BaseModel {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;
    /**
     * 频谱服务编号
     */
    @ApiModelProperty("频谱服务编号")
    private String serviceCode;
    /**
     * 频谱服务名称
     */
    @ApiModelProperty("频谱服务名称")
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
     * 结束频率单位Hz
     */
    @ApiModelProperty("结束频率单位Hz")
    private Integer endFrequency;
    /**
     * 实时频谱数据
     */
    @TableField(exist = false)
    @ApiModelProperty("实时频谱数据")
    private String traceData;
    /**
     * 平均频谱数据
     */
    @TableField(exist = false)
    @ApiModelProperty("平均频谱数据")
    private String traceDataAverage;
    /**
     * 数据接收时间
     */
    @ApiModelProperty("数据接收时间")
    private Date recordTime;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

    public SpectrumRecordMessage(SpectrumAnalysisDataS2PConsumer consumer, String serviceName, String spectrumName) {
        this.id = consumer.getMessageHead().getMessageID();
        this.serviceCode = consumer.getServiceCode();
        this.serviceName = serviceName;
        this.spectrumCode = consumer.getSpectrumCode();
        this.spectrumName = spectrumName;
        this.centerFrequency = consumer.getCenterFrequency();
        this.frequencySpan = consumer.getFrequencySpan();
        this.level = consumer.getLevel();
        this.CNR = consumer.getCNR();
        this.startFrequency = consumer.getStartFrequency();
        this.endFrequency = consumer.getEndFrequency();
        this.traceData = consumer.getTraceData().stream().map(Objects::toString).collect(Collectors.joining(","));
        this.traceDataAverage = consumer.getTraceDataAverage().stream().map(Objects::toString).collect(Collectors.joining(","));
        this.recordTime = DateHelper.parse(consumer.getMessageHead().getMessageTime(), DateHelper.patterns_masks[1]);
        this.setCreateTime(new Date());
    }

    public SpectrumRecordMessage() {

    }
}