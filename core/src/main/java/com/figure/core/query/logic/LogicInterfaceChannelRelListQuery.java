package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicInterfaceChannelRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicInterfaceChannelRelListQuery extends AbstractQuery<LogicInterfaceChannelRel> {

    @Eq(alias = "t.id")
    @ApiModelProperty("自增主键")
    private Integer id;

    @Eq(alias = "t.interfaceId")
    @ApiModelProperty("切换器接口Id")
    private String interfaceId;

    @Eq(alias = "t.groupId")
    @ApiModelProperty("逻辑分析组Id")
    private String groupId;

    @Eq(alias = "t.channelId")
    @ApiModelProperty("信源Id")
    private String channelId;

    @Eq(alias = "t.channelGrade")
    @ApiModelProperty("权重")
    private Integer channelGrade;

    @Eq(alias = "t.isLinkSwitch")
    @ApiModelProperty("是否关联切换器 0不关联 1关联")
    private Integer isLinkSwitch;

    @Eq(alias = "t.linkSwitchSerialNo")
    @ApiModelProperty("关联切换器的接口序号")
    private Integer linkSwitchSerialNo;

    @Eq(alias = "t.isDelete")
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}