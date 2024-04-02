package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicGroupModel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Gt;
import com.figure.core.util.copycat.annotaion.Lt;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class LogicGroupModelQuery extends AbstractQuery<LogicGroupModel> {

    @Eq
    @ApiModelProperty("逻辑分析组模板主键Id")
    private Integer id;

    @Eq
    @ApiModelProperty("逻辑分析组模板名称")
    private String modelName;

    @Eq
    @ApiModelProperty("采集站Id")
    private Integer monitorId;

    @Eq
    @ApiModelProperty("标准频道主键Id")
    private Integer standardId;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}