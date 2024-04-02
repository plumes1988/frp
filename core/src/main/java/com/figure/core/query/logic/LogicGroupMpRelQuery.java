package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicGroupMpRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicGroupMpRelQuery extends AbstractQuery<LogicGroupMpRel> {

    @Eq
    @ApiModelProperty("逻辑分组模板和采集点关联表")
    private Integer id;

    @Eq
    @ApiModelProperty("逻辑分析组模板关联Id")
    private Integer analysisModelId;

    @Eq
    @ApiModelProperty("采集点id")
    private Integer logicPositionId;

    @Eq
    @ApiModelProperty("传输链路级别1-255")
    private Integer chainGrade;

    @Eq
    @ApiModelProperty("默认为0， 0：否  ； 1：是 。不做异态分析，只做比对参考辅助分析")
    private Integer auxiliary;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}