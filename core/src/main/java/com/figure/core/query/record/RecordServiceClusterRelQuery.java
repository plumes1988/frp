package com.figure.core.query.record;

import com.figure.core.model.record.RecordServiceClusterRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecordServiceClusterRelQuery extends AbstractQuery<RecordServiceClusterRel> {

    @Eq
    @ApiModelProperty("主键自增Id")
    private Integer id;

    @Eq
    @ApiModelProperty("录制集群Id")
    private Integer recordClusterId;

    @Eq
    @ApiModelProperty("录制资源Id")
    private Integer recordServiceId;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}