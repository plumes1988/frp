package com.figure.core.query.signal;

import com.figure.core.model.signal.SignalAlarmthresholdRule;
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
public class SignalAlarmthresholdRuleQuery extends AbstractQuery<SignalAlarmthresholdRule> {

    @Eq
    @ApiModelProperty("规则ID")
    private String ruleId;

    @In(alias = "ruleId")
    @ApiModelProperty("规则ID")
    private List<Integer> ruleIdList;

    @Eq
    @ApiModelProperty("规则名称")
    private String ruleName;

    @Eq
    @ApiModelProperty("规则描述")
    private String ruleDescription;

    @Eq
    @ApiModelProperty("下发状态 1已下发 0未下发")
    private Integer synStatus;

    @Eq
    @ApiModelProperty("启用状态 0不启用 1启用")
    private Integer isEnable;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:删除")
    private Integer isDelete;

}