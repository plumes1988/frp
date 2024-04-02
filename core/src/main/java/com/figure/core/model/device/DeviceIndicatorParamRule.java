package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_indicator_param_rule")
public class DeviceIndicatorParamRule implements Serializable {

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 规则名称
     */
    @TableField("ruleName")
    private String ruleName;

    /**
     * 报警类型ID
     */
    @TableField("alarmTypeId")
    private String alarmTypeId;

    /**
     * 指标编号
     */
    @TableField("indicatorCode")
    private String indicatorCode;

    /**
     * 报警阈值
     */
    @TableField("alarmThreshold")
    private String alarmThreshold;

    /**
     * 报警延时
     */
    @TableField("alarmDelay")
    private String alarmDelay;

    /**
     * 恢复延时
     */
    @TableField("recoveryDelay")
    private String recoveryDelay;

    /**
     * 判定模式(0小于、1等于、2大于、3不等于)
     */
    @TableField("judgmentMode")
    private String judgmentMode;


    /**
     * 逻辑关系。0: 或  1: 与
     */
    @TableField("logic")
    private String logic;


}
