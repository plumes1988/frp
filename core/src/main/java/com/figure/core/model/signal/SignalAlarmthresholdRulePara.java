package com.figure.core.model.signal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.figure.core.base.BaseModel;


/**
 * <p>
 * 信号报警门限规则参数
 * </p>
 *
 * @author feather
 * @date 2021-12-13 16:56:37
 */
@Data
@Accessors(chain = true)
@TableName("signal_alarmthreshold_rule_para")
@EqualsAndHashCode(callSuper = false)
public class SignalAlarmthresholdRulePara extends BaseModel {

    /**
     * 记录ID
     */
    @TableId(type = IdType.AUTO)
    private Integer paraId;
    /**
     * 规则ID
     */
    private Integer ruleId;
    /**
     * 报警类型ID
     */
    private Integer alarmTypeId;
    /**
     * 报警类型名称
     */
    private String alarmTypeName;
    /**
     * 报警类型别名
     */
    private String alarmTypeAlias;
    /**
     * 报警来源 1、音频 ，2、视频 ，3、码流
     */
    private Integer alarmSource;
    /**
     * 关联指标编号
     */
    private Integer indicatorNO;
    /**
     * 报警阈值
     */
    private Integer alarmValue;
    /**
     * 第二阈值，在比对时作为判定次数
     */
    private Integer alarmSensitivity;
    /**
     * 报警判定延时
     */
    private Integer alarmDelay;
    /**
     * 报警恢复延时
     */
    private Integer alarmRecoveryDelay;
    /**
     * (左上X占比，左上Y占比，右下X占比，右下Y占比)
     */
    private Integer leftTopX;
    /**
     * (左上X占比，左上Y占比，右下X占比，右下Y占比)
     */
    private Integer leftTopY;
    /**
     * (左上X占比，左上Y占比，右下X占比，右下Y占比)
     */
    private Integer rightBottomX;
    /**
     * (左上X占比，左上Y占比，右下X占比，右下Y占比)
     */
    private Integer rightBottomY;
    /**
     * 启用状态 0不启用 1启用
     */
    private Integer isEnable;
    /**
     * 状态：0:未删除、1:删除
     */
    private Integer isDelete;

}