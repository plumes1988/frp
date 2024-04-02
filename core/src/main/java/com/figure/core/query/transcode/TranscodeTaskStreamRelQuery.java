package com.figure.core.query.transcode;

import com.figure.core.model.transcode.TranscodeTaskStreamRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TranscodeTaskStreamRelQuery extends AbstractQuery<TranscodeTaskStreamRel> {

  @Eq
  @ApiModelProperty("任务ID")
  private Integer transcodeTaskId;

  @Eq
  @ApiModelProperty("数据字典获取媒体类型：1UDP、2HLS、3RTMP、4HTTP_FLV")
  private Integer streamType;

  @Eq
  @ApiModelProperty("流媒体输出地址")
  private String streamURL;

  @Eq
  @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
  private Integer isDelete;

}