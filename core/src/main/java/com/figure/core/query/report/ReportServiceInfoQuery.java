package com.figure.core.query.report;

import com.figure.core.model.report.ReportServiceInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Gt;
import com.figure.core.util.copycat.annotaion.Lt;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class ReportServiceInfoQuery extends AbstractQuery<ReportServiceInfo> {
  
  @Eq
  @ApiModelProperty("主键")
  private Integer id;
  
  @Eq
  @ApiModelProperty("服务名称")
  private String serviceName;
  
  @Eq
  @ApiModelProperty("服务编号")
  private String serviceCode;
  
  @Eq
  @ApiModelProperty("关联设备ID")
  private Integer deviceID;
  
  @Eq
  @ApiModelProperty("通讯IP")
  private String serviceIP;
  
  @Eq
  @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
  private Integer isDelete;
  
}