package com.figure.core.query.spectrum;

import com.figure.core.model.spectrum.SpectrumRecordTracedata;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Ge;
import com.figure.core.util.copycat.annotaion.Le;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SpectrumRecordTracedataQuery extends AbstractQuery<SpectrumRecordTracedata> {

  @Eq
  @ApiModelProperty("主键")
  private Long id;

  @Eq
  @ApiModelProperty("频谱设备编号")
  private String spectrumCode;

  @Eq
  @ApiModelProperty("实时频谱数据")
  private String traceData;

  @Eq
  @ApiModelProperty("平均频谱数据")
  private String traceDataAverage;

  @Eq
  @ApiModelProperty("创建时间")
  private Date createTime;

  @Ge(alias = "createTime")
  @ApiModelProperty("创建时间大于")
  private Date GEcreateTime;

  @Le(alias = "createTime")
  @ApiModelProperty("创建时间小于")
  private Date LEcreateTime;


}