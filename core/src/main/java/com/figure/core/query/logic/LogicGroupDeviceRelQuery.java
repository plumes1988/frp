package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicGroupDeviceRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicGroupDeviceRelQuery extends AbstractQuery<LogicGroupDeviceRel> {

    @Eq
    @ApiModelProperty("自增Id")
    private Integer id;

    @Eq
    @ApiModelProperty("播出系统编号")
    private String channelCode;

    @Eq
    @ApiModelProperty("关联网管设备编号")
    private String deviceCode;

    @Eq
    @ApiModelProperty("逻辑分析组Id")
    private Integer groupId;

    @Eq
    @ApiModelProperty("设备与信号关联模式 1自动，根据设备关联采集点链路属性自动关联   2手动  用户手动设置")
    private Integer linkMode;

    @Eq
    @ApiModelProperty("关联信号id，逗号隔开，从逻辑分析组配置的信源中选择")
    private String channelIds;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}