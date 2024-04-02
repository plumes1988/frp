package com.figure.core.query.signal;

import com.figure.core.model.signal.SignalAlarmthresholdRulePara;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Gt;
import com.figure.core.util.copycat.annotaion.In;
import com.figure.core.util.copycat.annotaion.Lt;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class SignalAlarmthresholdRuleParaQuery extends AbstractQuery<SignalAlarmthresholdRulePara> {

    @Eq
    @ApiModelProperty("记录ID")
    private String paraId;

    @In(alias = "paraId")
    @ApiModelProperty("记录ID")
    private List<Integer> paraIdList;

    @Eq
    @ApiModelProperty("规则ID")
    private Integer ruleId;

    @Eq
    @ApiModelProperty("报警类型ID")
    private Integer alarmTypeId;

    @Eq
    @ApiModelProperty("报警类型名称")
    private String alarmTypeName;

    @Eq
    @ApiModelProperty("报警类型别名")
    private String alarmTypeAlias;

    @Eq
    @ApiModelProperty("报警来源 1、音频 ，2、视频 ，3、码流")
    private Integer alarmSource;

    @Eq
    @ApiModelProperty("关联指标编号")
    private Integer indicatorNO;

    @Eq
    @ApiModelProperty("报警阈值")
    private Integer alarmValue;

    @Eq
    @ApiModelProperty("第二阈值，在比对时作为判定次数")
    private Integer alarmSensitivity;

    @Eq
    @ApiModelProperty("报警判定延时")
    private Integer alarmDelay;

    @Eq
    @ApiModelProperty("报警恢复延时")
    private Integer alarmRecoveryDelay;

    @Eq
    @ApiModelProperty("(左上X占比，左上Y占比，右下X占比，右下Y占比)")
    private Integer leftTopX;

    @Eq
    @ApiModelProperty("(左上X占比，左上Y占比，右下X占比，右下Y占比)")
    private Integer leftTopY;

    @Eq
    @ApiModelProperty("(左上X占比，左上Y占比，右下X占比，右下Y占比)")
    private Integer rightBottomX;

    @Eq
    @ApiModelProperty("(左上X占比，左上Y占比，右下X占比，右下Y占比)")
    private Integer rightBottomY;

    @Eq
    @ApiModelProperty("启用状态 0不启用 1启用")
    private Integer isEnable;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:删除")
    private Integer isDelete;

}