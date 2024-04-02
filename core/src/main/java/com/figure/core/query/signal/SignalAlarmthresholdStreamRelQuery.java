package com.figure.core.query.signal;

import com.figure.core.model.signal.SignalAlarmthresholdStreamRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Gt;
import com.figure.core.util.copycat.annotaion.Lt;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class SignalAlarmthresholdStreamRelQuery extends AbstractQuery<SignalAlarmthresholdStreamRel> {

    @Eq
    @ApiModelProperty("1码流 2逻辑频道 3频道")
    private Integer objectType;

    @Eq
    @ApiModelProperty("码流ID，逻辑频道ID，频道ID ")
    private String objectId;

    @Eq
    @ApiModelProperty("报警门限规则ID")
    private Integer ruleId;

    @Eq
    @ApiModelProperty("规则应用优先级")
    private Integer priority;

    @Eq
    @ApiModelProperty("启用状态 0不启用 1启用")
    private Integer isEnable;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:删除")
    private Integer isDelete;

}