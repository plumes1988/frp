package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicThresholdRuleChannelRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicThresholdRuleChannelRelQuery extends AbstractQuery<LogicThresholdRuleChannelRel> {

    @Eq
    @ApiModelProperty("")
    private Integer id;

    @Eq
    @ApiModelProperty("频道id")
    private String channelId;

    @Eq
    @ApiModelProperty("门限模板id")
    private Integer modelId;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}