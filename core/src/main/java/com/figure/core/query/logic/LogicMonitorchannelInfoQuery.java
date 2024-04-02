package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicMonitorchannelInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicMonitorchannelInfoQuery extends AbstractQuery<LogicMonitorchannelInfo> {

    @Eq
    @ApiModelProperty("监控频道编号")
    private String monitorChannelCode;

    @Eq
    @ApiModelProperty("监控频道名称")
    private String monitorChannelName;

    @Eq
    @ApiModelProperty("监控频道简称")
    private String monitorChannelAlias;

    @Eq
    @ApiModelProperty("0自动比对 1末级比对 2源木比对 3比对关闭")
    private Integer comparisonMode;

    @Eq
    @ApiModelProperty("0关闭 1仅录播 2仅直播 3全部开启")
    private Integer autoEmergency;

    @Eq
    @ApiModelProperty("0关闭 1启用")
    private Integer autoSwitchBack;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}