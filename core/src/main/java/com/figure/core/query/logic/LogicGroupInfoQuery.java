package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicGroupInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.In;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LogicGroupInfoQuery extends AbstractQuery<LogicGroupInfo> {

    @Eq
    @ApiModelProperty("逻辑分析组主键Id")
    private Integer id;

    @Eq
    @ApiModelProperty("逻辑分析组名称")
    private String groupName;

    @Eq
    @ApiModelProperty("前端Id")
    private Integer frontId;

    @Eq
    @ApiModelProperty("业务类型：1综合逻辑分析 2播出预监测")
    private Integer serviceType;

    @Eq
    @ApiModelProperty("逻辑频道Id")
    private Integer logicChannelCode;

    @In(alias = "logicChannelCode")
    @ApiModelProperty("多个逻辑频道id")
    private List<String> logicChannelCodeList;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}