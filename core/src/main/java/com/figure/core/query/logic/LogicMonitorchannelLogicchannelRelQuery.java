package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicMonitorchannelLogicchannelRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicMonitorchannelLogicchannelRelQuery extends AbstractQuery<LogicMonitorchannelLogicchannelRel> {

    @Eq
    @ApiModelProperty("自增主键")
    private Integer id;

    @Eq
    @ApiModelProperty("监控频道编号")
    private String monitorChannelCode;

    @Eq
    @ApiModelProperty("逻辑频道编号")
    private String logicChannelCode;

}