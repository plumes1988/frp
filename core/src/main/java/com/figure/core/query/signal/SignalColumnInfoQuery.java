package com.figure.core.query.signal;

import com.figure.core.model.signal.SignalColumnInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Gt;
import com.figure.core.util.copycat.annotaion.Lt;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class SignalColumnInfoQuery extends AbstractQuery<SignalColumnInfo> {

    @Eq
    @ApiModelProperty("栏目ID")
    private Integer id;

    @Eq
    @ApiModelProperty("逻辑频道ID")
    private Integer logicChannelId;

    @Eq
    @ApiModelProperty("栏目名称")
    private String columnName;

    @Eq
    @ApiModelProperty("0:删除、1:未删除，已删除数据只有系统管理员可以查看和编辑")
    private Integer isDelete;

}