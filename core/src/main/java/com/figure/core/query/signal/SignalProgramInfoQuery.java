package com.figure.core.query.signal;

import com.figure.core.model.signal.SignalProgramInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Gt;
import com.figure.core.util.copycat.annotaion.Lt;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class SignalProgramInfoQuery extends AbstractQuery<SignalProgramInfo> {

    @Eq
    @ApiModelProperty("节目ID")
    private Integer id;

    @Eq
    @ApiModelProperty("栏目ID")
    private String columnId;

    @Eq
    @ApiModelProperty("逻辑频道ID")
    private Integer logicChannelId;

    @Eq
    @ApiModelProperty("节目单ID")
    private String epgId;

    @Eq
    @ApiModelProperty("节目名称")
    private String programName;

    @Eq
    @ApiModelProperty("开始日期")
    private Date startDate;

    @Eq
    @ApiModelProperty("节目时长 单位秒")
    private Integer programLen;

    @Eq
    @ApiModelProperty("0:删除、1:未删除，已删除数据只有系统管理员可以查看和编辑")
    private Integer isDelete;

}