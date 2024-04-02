package com.figure.core.query.transcode;

import com.figure.core.model.transcode.TranscodeServiceClusterRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TranscodeServiceClusterRelQuery extends AbstractQuery<TranscodeServiceClusterRel> {

  @Eq
  @ApiModelProperty("主键自增Id")
  private Integer id;

  @Eq
  @ApiModelProperty("转码集群Id")
  private Integer transcodeClusterId;

  @Eq
  @ApiModelProperty("转码资源Id")
  private Integer transcodeServiceId;

  @Eq
  @ApiModelProperty("状态：0:删除、1:正常、2:停用")
  private Integer isDelete;
}