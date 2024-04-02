package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicCustomizeInfoPara;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Gt;
import com.figure.core.util.copycat.annotaion.Lt;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class LogicCustomizeInfoParaQuery extends AbstractQuery<LogicCustomizeInfoPara> {

    @Eq
    @ApiModelProperty("参数自增Id")
    private Integer id;

    @Eq
    @ApiModelProperty("关联自定义业务逻辑Id")
    private Integer customizeId;

    @Eq
    @ApiModelProperty("逻辑 ,1:条件 ；2:结论")
    private Integer logic;

    @Eq
    @ApiModelProperty("来源 1:信源; 2:比对 3:消息")
    private Integer source;

    @Eq
    @ApiModelProperty("频道1")
    private String channelId1;

    @Eq
    @ApiModelProperty("频道2")
    private String channelId2;

    @Eq
    @ApiModelProperty("消息")
    private String message;

    @Eq
    @ApiModelProperty("链路结构 逗号隔开")
    private String linkStruct;

    @Eq
    @ApiModelProperty("条件状态 0:正常 1:异常")
    private Integer anomalStatus;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}