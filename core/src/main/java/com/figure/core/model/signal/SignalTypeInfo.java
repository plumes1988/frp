package com.figure.core.model.signal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 信号类型表
 * </p>
 *
 * @author jobob
 * @since 2021-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("signal_type_info")
public class SignalTypeInfo implements Serializable {


    /**
     * 信号类型编号
     */
    @TableId(value = "signalCode")
    private Integer signalCode;

    /**
     * 信号类型名
     */
    @TableField("signalName")
    private String signalName;

    /**
     * 信号载体  1：音频信号  2：视音频信号
     */
    @TableField("signalCarrier")
    private String signalCarrier;

    /**
     * 是否调制信号  0不是  1是
     */
    @TableField("isModSignal")
    private Integer isModSignal;

    /**
     * 单通道最大转发节目数
     */
    @TableField("maxStreamCount")
    private Integer maxStreamCount;

    /**
     * 默认频点起
     */
    @TableField("defaultFrequencyStart")
    private String defaultFrequencyStart;

    /**
     * 默认频点止
     */
    @TableField("defaultFrequencyEnd")
    private String defaultFrequencyEnd;

    /**
     * 信号频点单位：1:KHZ 2: MHZ 3:GHZ
     */
    @TableField("signalUnit")
    private String signalUnit;

    /**
     * 扫描步长方式 1：自动 2：手动
     */
    @TableField("scanStepStyle")
    private String scanStepStyle;

    /**
     * 默认步长
     */
    @TableField("scanStep")
    private String scanStep;

    /**
     * 扫描步长单位 1:KHZ 2:MHZ 3: GHZ
     */
    @TableField("scanStepUnit")
    private String scanStepUnit;

    /**
     * 实时指标pcr刷新时间间隔
     */
    @TableField("indicatorsPcrTime")
    private Integer indicatorsPcrTime;

    /**
     * 实时指标cmt刷新时间间隔
     */
    @TableField("indicatorsCmtTime")
    private Integer indicatorsCmtTime;

    /**
     * 实时指标刷新时间间隔
     */
    @TableField("indicatorsTime")
    private Integer indicatorsTime;


}
