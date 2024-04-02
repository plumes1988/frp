package com.figure.core.query.transcode;

import com.figure.core.model.transcode.TranscodeTaskInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TranscodeTaskInfoQuery extends AbstractQuery<TranscodeTaskInfo> {

  @Eq
  @ApiModelProperty("任务ID，自增")
  private Integer transcodeTaskId;

  @Eq
  @ApiModelProperty("任务名称")
  private String transcodeTaskName;

  @Eq
  @ApiModelProperty("任务类型 0常规任务 1直播监看任务 没有转码规则时，直播监看请求生成任务，每个用户ID只能有一个直播监看任务")
  private Integer taskMode;

  @Eq
  @ApiModelProperty("节目ID")
  private String signalId;

  @Eq
  @ApiModelProperty("信源地址")
  private String sourceURL;

  @Eq
  @ApiModelProperty("转码规则ID")
  private Integer transcodeRuleId;

  @Eq
  @ApiModelProperty("转码服务编号")
  private String serviceCode;

  @Eq
  @ApiModelProperty("转码服务名称")
  private String serviceName;

  @Eq
  @ApiModelProperty("GPU加速：0不开启，1开启")
  private Integer useGPU;

  @Eq
  @ApiModelProperty("信源输入网口")
  private String inputIP;

  @Eq
  @ApiModelProperty("转码输出网口")
  private String outputIP;

  @Eq
  @ApiModelProperty("运行状态")
  private Integer taskStatus;

  @Eq(alias = "tti.isDelete")
  @ApiModelProperty("状态：0:删除、1:正常、2:停用")
  private Integer isDelete;

}