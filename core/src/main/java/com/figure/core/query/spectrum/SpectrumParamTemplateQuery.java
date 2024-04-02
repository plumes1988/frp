package com.figure.core.query.spectrum;

import com.figure.core.model.spectrum.SpectrumParamTemplate;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Gt;
import com.figure.core.util.copycat.annotaion.Lt;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class SpectrumParamTemplateQuery extends AbstractQuery<SpectrumParamTemplate> {
  
  @Eq
  @ApiModelProperty("主键")
  private Integer id;

  @Eq
  @ApiModelProperty("模板名称")
  private String templateName;
  
  @Eq
  @ApiModelProperty("中心频率单位Hz")
  private Integer centerFrequency;
  
  @Eq
  @ApiModelProperty("带宽单位Hz")
  private Integer frequencySpan;
  
  @Eq
  @ApiModelProperty("RBW单位Hz")
  private Integer RBW;
  
  @Eq
  @ApiModelProperty("VBW单位Hz")
  private Integer VBW;
  
  @Eq
  @ApiModelProperty("频谱异常阈值dB")
  private Integer level;
  
  @Eq
  @ApiModelProperty("开始频率单位Hz")
  private Integer startFrequency;
  
  @Eq
  @ApiModelProperty("结束频率单位Hz")
  private Integer endFrequency;
  
  @Eq
  @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
  private Integer isDelete;
  
}