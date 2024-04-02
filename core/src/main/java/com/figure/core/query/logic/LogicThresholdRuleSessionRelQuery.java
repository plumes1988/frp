package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicThresholdRuleSessionRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Gt;
import com.figure.core.util.copycat.annotaion.Lt;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class LogicThresholdRuleSessionRelQuery extends AbstractQuery<LogicThresholdRuleSessionRel> {

    @Eq
    @ApiModelProperty("")
    private Integer id;

    @Eq
    @ApiModelProperty("重要保障期Id")
    private Integer sessionId;

    @Eq
    @ApiModelProperty("门限模板id")
    private Integer modelId;

}