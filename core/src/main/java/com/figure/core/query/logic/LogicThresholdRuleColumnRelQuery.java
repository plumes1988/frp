package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicThresholdRuleColumnRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicThresholdRuleColumnRelQuery extends AbstractQuery<LogicThresholdRuleColumnRel> {

    @Eq
    @ApiModelProperty("")
    private Integer id;

    @Eq
    @ApiModelProperty("栏目id")
    private String columnId;

    @Eq
    @ApiModelProperty("门限模板id")
    private Integer modelId;

}