package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_data_analysis_strategy")
public class DeviceDataAnalysisStrategy implements Serializable {


    /**
     * 策略ID
     */
    @TableField("strategyId")
    private Integer strategyId;

    /**
     * 策略名称
     */
    @TableField("strategyName")
    private String strategyName;

    /**
     * 指标编号
     */
    @TableField("indicatorCode")
    private String indicatorCode;

    /**
     * 分析模式；0:短时突变；1:趋势异常
     */
    @TableField("analysisMode")
    private Integer analysisMode;

    /**
     * 参数设置
     */
    @TableField("config")
    private String config;


}
