package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicServerInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicServerInfoQuery extends AbstractQuery<LogicServerInfo> {

    @Eq
    @ApiModelProperty("逻辑中心服务器自增Id")
    private Integer id;

    @Eq
    @ApiModelProperty("逻辑中心服务器编号")
    private String serverNumber;

    @Eq
    @ApiModelProperty("设备Id")
    private Integer deviceId;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}