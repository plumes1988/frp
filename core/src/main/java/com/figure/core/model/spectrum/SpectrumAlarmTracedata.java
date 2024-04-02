package com.figure.core.model.spectrum;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 频谱数据详细
 * </p>
 *
 * @author feather
 * @date 2024-03-01 14:10:45
 */
@Data
@Accessors(chain = true)
@TableName("spectrum_alarm_tracedata")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SpectrumAlarmTracedata", description = "频谱数据详细")
public class SpectrumAlarmTracedata extends BaseModel {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long alarmId;
    /**
     * 实时频谱数据
     */
    @ApiModelProperty("实时频谱数据")
    private String traceData;
    /**
     * 平均频谱数据
     */
    @ApiModelProperty("平均频谱数据")
    private String traceDataAverage;

    /**
     * 频谱服务编号
     */
    @ApiModelProperty("频谱服务编号")
    private String serviceCode;
    /**
     * 频谱设备编号
     */
    @ApiModelProperty("频谱设备编号")
    private String spectrumCode;

    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

    public SpectrumAlarmTracedata(SpectrumAlarmMessage spectrumAlarmMessage, SpectrumRecordTracedata spectrumRecordTracedata) {

        this.alarmId = spectrumAlarmMessage.getAlarmId();
        this.traceData = spectrumRecordTracedata.getTraceData();
        this.traceDataAverage = spectrumRecordTracedata.getTraceDataAverage();
        this.serviceCode = spectrumRecordTracedata.getServiceCode();
        this.spectrumCode = spectrumAlarmMessage.getSpectrumCode();
        this.setCreateTime(spectrumRecordTracedata.getCreateTime());
    }

    public SpectrumAlarmTracedata() {

    }
}