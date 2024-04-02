package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicCompareInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicCompareInfoQuery extends AbstractQuery<LogicCompareInfo> {

    @Eq
    @ApiModelProperty("主键id自增")
    private Integer id;

    @Eq
    @ApiModelProperty("异态频道分组Id")
    private Integer groupId;

    @Eq
    @ApiModelProperty("主频道id")
    private String mainChannelId;

    @Eq
    @ApiModelProperty("对比频道Id")
    private String compareChannelId;

    @Eq
    @ApiModelProperty("是否关联切换器")
    private Integer relSwitcher;

    @Eq
    @ApiModelProperty("关联切换器Id")
    private String relSwitcherNumber;

    @Eq
    @ApiModelProperty("关联接口Id")
    private String relInterfaceId;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}