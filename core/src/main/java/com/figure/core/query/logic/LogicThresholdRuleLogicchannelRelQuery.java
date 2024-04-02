package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicThresholdRuleLogicchannelRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicThresholdRuleLogicchannelRelQuery extends AbstractQuery<LogicThresholdRuleLogicchannelRel> {

    @Eq
    @ApiModelProperty("")
    private Integer id;

    @Eq
    @ApiModelProperty("逻辑频道编号")
    private Integer channelCode;

    @Eq
    @ApiModelProperty("门限模板id")
    private Integer modelId;

}