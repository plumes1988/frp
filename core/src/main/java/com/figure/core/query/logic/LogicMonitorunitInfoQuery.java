package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicMonitorunitInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicMonitorunitInfoQuery extends AbstractQuery<LogicMonitorunitInfo> {

    @Eq(alias = "t.unitId")
    @ApiModelProperty("是否关联切换器0无关联 1关联")
    private Integer unitId;

    @Eq
    @ApiModelProperty("监控单元名称")
    private String monitorName;

    @Eq
    @ApiModelProperty("监控单元简称")
    private String monitorAlias;

    @Eq
    @ApiModelProperty("关联切换器编号，从设备关联选择")
    private String switchCode;

    @Eq
    @ApiModelProperty("是否联动切换器 0无关联 1关联")
    private Integer linkSwitch;

    @Eq
    @ApiModelProperty("联动切换器的编号，从设备关联选择")
    private Integer linkSwitchCode;

    @Eq
    @ApiModelProperty("监控单元模式")
    private Integer unitMode;

    @Eq
    @ApiModelProperty("输入接口数")
    private Integer inputCount;

    @Eq
    @ApiModelProperty("输出接口数")
    private Integer outputCount;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}