package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicMonitorChannelUnitRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicMonitorChannelUnitRelQuery extends AbstractQuery<LogicMonitorChannelUnitRel> {

    @Eq
    @ApiModelProperty("主键自增")
    private Integer id;

    @Eq
    @ApiModelProperty("监控频道编号")
    private String monitorChannelCode;

    @Eq
    @ApiModelProperty("监控单元Id")
    private Integer unitId;

}