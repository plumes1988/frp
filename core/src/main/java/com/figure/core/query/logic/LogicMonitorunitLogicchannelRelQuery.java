package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicMonitorunitLogicchannelRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicMonitorunitLogicchannelRelQuery extends AbstractQuery<LogicMonitorunitLogicchannelRel> {

    @Eq
    @ApiModelProperty("主键自增")
    private Integer id;

    @Eq
    @ApiModelProperty("监控单元Id")
    private Integer monitorUnitId;

    @Eq
    @ApiModelProperty("逻辑分析组Id")
    private Integer logicGroupId;

}