package com.figure.core.query.report;

import com.figure.core.model.report.ReportDeviceIndicatorRel;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportDeviceIndicatorRelQuery extends AbstractQuery<ReportDeviceIndicatorRel> {

    @Eq
    @ApiModelProperty("主键")
    private Integer id;

    @Eq
    @ApiModelProperty("设备编号")
    private String deviceCode;

    @Eq(alias = "t.deviceCode")
    @ApiModelProperty("设备编号")
    private String tDeviceCode;

    @Eq
    @ApiModelProperty("指标编号")
    private String indicatorCode;

    @Eq
    @ApiModelProperty("指标排序")
    private Integer indicatorIdx;

    @Eq
  @ApiModelProperty("指标排序")
  private Integer isDelete;
  
}