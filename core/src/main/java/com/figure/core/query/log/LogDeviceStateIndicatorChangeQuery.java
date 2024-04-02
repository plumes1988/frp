package com.figure.core.query.log;

import com.figure.core.model.log.LogDeviceStateIndicatorChange;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class LogDeviceStateIndicatorChangeQuery extends AbstractQuery<LogDeviceStateIndicatorChange> {

    @Eq
    @ApiModelProperty("")
    private Integer id;

    @Eq
    @ApiModelProperty("前端站点ID")
    private Integer frontId;

    @Eq
    @ApiModelProperty("设备编号")
    private String deviceCode;

    @Eq
    @ApiModelProperty("指标编号")
    private String indicatorCode;

    @Eq
    @ApiModelProperty("旧值")
    private String oldIndicatorValue;

    @Eq
    @ApiModelProperty("新值")
    private String newIndicatorValue;

    @Eq
    @ApiModelProperty("变更时间")
    private Date changeTime;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}