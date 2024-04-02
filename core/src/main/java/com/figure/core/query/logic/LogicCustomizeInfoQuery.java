package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicCustomizeInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicCustomizeInfoQuery extends AbstractQuery<LogicCustomizeInfo> {

    @Eq
    @ApiModelProperty("自定义业务逻辑自增Id")
    private Integer id;

    @Eq
    @ApiModelProperty("自定义业务逻辑名称")
    private String customizeName;

    @Eq
    @ApiModelProperty("所属逻辑分组Id")
    private Integer groupId;

    @Eq
    @ApiModelProperty("业务逻辑排序优先级")
    private Integer priority;

    @Eq
    @ApiModelProperty("自定义业务逻辑描述")
    private String description;

    @Eq
    @ApiModelProperty("链路结构 1四边形 2退化单源 3强制单源")
    private Integer lineMode;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}