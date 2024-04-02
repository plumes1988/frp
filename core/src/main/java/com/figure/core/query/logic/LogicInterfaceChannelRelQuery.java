package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicInterfaceChannelRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicInterfaceChannelRelQuery extends AbstractQuery<LogicInterfaceChannelRel> {

    @Eq
    @ApiModelProperty("自增主键")
    private Integer id;

    @Eq
    @ApiModelProperty("切换器接口Id")
    private String interfaceId;

    @Eq
    @ApiModelProperty("逻辑分析组Id")
    private String groupId;

    @Eq
    @ApiModelProperty("信源Id")
    private String channelId;

    @Eq
    @ApiModelProperty("权重")
    private Integer channelGrade;

    @Eq
    @ApiModelProperty("是否关联切换器 0不关联 1关联")
    private Integer isLinkSwitch;

    @Eq
    @ApiModelProperty("关联切换器的接口序号")
    private Integer linkSwitchSerialNo;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}