package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicMonitorunitInterfaceRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicMonitorunitInterfaceRelQuery extends AbstractQuery<LogicMonitorunitInterfaceRel> {

    @Eq
    @ApiModelProperty("自增主键")
    private Integer id;

    @Eq
    @ApiModelProperty("监控单元Id")
    private Integer unitId;

    @Eq
    @ApiModelProperty("切换器接口Id,唯一")
    private String interfaceId;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}