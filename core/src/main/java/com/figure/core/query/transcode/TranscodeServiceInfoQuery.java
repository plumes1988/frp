package com.figure.core.query.transcode;

import com.figure.core.model.transcode.TranscodeServiceInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TranscodeServiceInfoQuery extends AbstractQuery<TranscodeServiceInfo> {

  @Eq
  @ApiModelProperty("转码服务ID")
  private Integer transcodeServiceId;

  @Eq
  @ApiModelProperty("转码服务名称（有别于设备名称）")
  private String transcodeServiceName;

  @Eq
  @ApiModelProperty("前端ID")
  private Integer frontId;

  @Eq
  @ApiModelProperty("设备编号")
  private String serviceCode;

  @Eq
  @ApiModelProperty("设备名称")
  private String serviceName;

  @Eq
  @ApiModelProperty("转码服务IP（从设备获取）")
  private String transcodeServiceIP;

  @Eq
  @ApiModelProperty("节点总资源")
  private Integer totalResources;

  @Eq
  @ApiModelProperty("已使用资源")
  private Integer usedResources;

  @Eq
  @ApiModelProperty("是否使用GPU：0不用，1使用")
  private Integer useGPU;

  @Eq
  @ApiModelProperty("状态：0:删除、1:正常、2:停用")
  private Integer isDelete;

}