package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicThresholdRule;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicThresholdRuleQuery extends AbstractQuery<LogicThresholdRule> {

    @Eq
    @ApiModelProperty("报警规则模板Id")
    private Integer modelId;

    @Eq
    @ApiModelProperty("报警规则名称")
    private String modelName;

    @Eq
    @ApiModelProperty("规则描述")
    private String description;

    @Eq
    @ApiModelProperty("触发关键词 逗号隔开 栏目名或节目名包含")
    private String keywords;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}