package com.figure.core.query.transcode;

import com.figure.core.model.transcode.TranscodeClusterInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TranscodeClusterInfoQuery extends AbstractQuery<TranscodeClusterInfo> {

  @Eq
  @ApiModelProperty("转码集群Id")
  private Integer transcodeClusterId;

  @Eq
  @ApiModelProperty("转码集群名称")
  private String transcodeClusterName;

  @Eq
  @ApiModelProperty("集群总资源")
  private Integer totalResources;

  @Eq
  @ApiModelProperty("已使用资源")
  private Integer usedResources;

  @Eq
  @ApiModelProperty("所属前端Id")
  private Integer frontId;

  @Eq
  @ApiModelProperty("状态：0:删除、1:正常、2:停用")
  private Integer isDelete;

}