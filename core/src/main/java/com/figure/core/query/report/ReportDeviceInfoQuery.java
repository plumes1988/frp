package com.figure.core.query.report;

import com.figure.core.model.report.ReportDeviceInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Gt;
import com.figure.core.util.copycat.annotaion.Lt;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class ReportDeviceInfoQuery extends AbstractQuery<ReportDeviceInfo> {
  
  @Eq
  @ApiModelProperty("主键")
  private Integer id;
  
  @Eq
  @ApiModelProperty("设备编号")
  private String deviceCode;
  
  @Eq
  @ApiModelProperty("系统ID,数据字典配置")
  private Integer systemID;
  
  @Eq
  @ApiModelProperty("设备类型关键字,数据字典配置")
  private String typeKeywords;
  
  @Eq
  @ApiModelProperty("主备特性, M:主 B:备 SW:切换器")
  private String featureType;
  
  @Eq
  @ApiModelProperty("序号")
  private String deviceIdx;
  
  @Eq
  @ApiModelProperty("关联上报服务编号")
  private String reportServiceCode;
  
  @Eq
  @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
  private Integer isDelete;
  
}