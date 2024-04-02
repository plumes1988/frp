package com.figure.core.query.record;

import com.figure.core.model.record.RecordClusterInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecordClusterInfoQuery extends AbstractQuery<RecordClusterInfo> {

    @Eq
    @ApiModelProperty("录制集群Id")
    private Integer recordClusterId;

    @Eq
    @ApiModelProperty("录制集群名称")
    private String recordClusterName;

    @Eq
    @ApiModelProperty("集群总资源")
    private Integer totalBitRate;

    @Eq
    @ApiModelProperty("已使用资源")
    private Integer usedBitRate;

    @Eq
    @ApiModelProperty("所属前端Id")
    private Integer frontId;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}