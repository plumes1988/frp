package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicGroupChannelRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicGroupChannelRelQuery extends AbstractQuery<LogicGroupChannelRel> {

    @Eq
    @ApiModelProperty("逻辑分组频道关联表")
    private Integer id;

    @Eq
    @ApiModelProperty("逻辑分析组关联Id")
    private Integer analysisGroupId;

    @Eq
    @ApiModelProperty("频道Id")
    private String channelId;

    @Eq
    @ApiModelProperty("频道名称")
    private String channelName;

    /**
     * 链路属性
     */
    @Eq
    @ApiModelProperty("链路属性")
    private Integer linkType;
    /**
     * 链路级别
     */
    @Eq
    @ApiModelProperty("链路级别")
    private Integer linkLevel;

    @Eq
    @ApiModelProperty("默认为0， 0：否  ； 1：是 。不做异态分析，只做比对参考辅助分析")
    private Integer auxiliary;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}