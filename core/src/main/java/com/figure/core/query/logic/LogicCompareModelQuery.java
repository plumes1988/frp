package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicCompareModel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogicCompareModelQuery extends AbstractQuery<LogicCompareModel> {

    @Eq
    @ApiModelProperty("主键id自增")
    private Integer id;

    @Eq
    @ApiModelProperty("异态频道分组Id")
    private Integer groupModelId;

    @Eq
    @ApiModelProperty("主采集点id")
    private Integer mainPositionId;

    @Eq
    @ApiModelProperty("对比采集点id")
    private Integer comparePositionId;

    @Eq
    @ApiModelProperty("是否关联切换器")
    private Integer relSwitcher;

    @Eq
    @ApiModelProperty("关联切换器Id")
    private String relSwitcherNumber;

    @Eq
    @ApiModelProperty("关联接口Id")
    private Integer relInterfaceId;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}