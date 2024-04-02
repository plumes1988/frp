package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicThresholdRuleProgramRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicThresholdRuleProgramRelQuery extends AbstractQuery<LogicThresholdRuleProgramRel> {

    @Eq
    @ApiModelProperty("")
    private Integer id;

    @Eq
    @ApiModelProperty("节目id")
    private String programId;

    @Eq
    @ApiModelProperty("门限模板id")
    private Integer modelId;

}