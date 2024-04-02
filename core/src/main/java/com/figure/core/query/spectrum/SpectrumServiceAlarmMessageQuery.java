package com.figure.core.query.spectrum;

import com.figure.core.model.spectrum.SpectrumServiceAlarmMessage;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Ge;
import com.figure.core.util.copycat.annotaion.Le;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SpectrumServiceAlarmMessageQuery extends AbstractQuery<SpectrumServiceAlarmMessage> {
  
  @Eq
  @ApiModelProperty("主键")
  private Long alarmId;
  
  @Eq
  @ApiModelProperty("1服务报警2任务报警")
  private Integer alarmClass;
  
  @Eq
  @ApiModelProperty("频谱服务编号")
  private String serviceCode;
  
  @Eq
  @ApiModelProperty("频谱服务名称")
  private String serviceName;
  
  @Eq
  @ApiModelProperty("频谱服务和频谱仪关联ID")
  private Integer relId;
  
  @Eq
  @ApiModelProperty("频谱仪设备编号")
  private String spectrumCode;
  
  @Eq
  @ApiModelProperty("频谱仪设备名称")
  private String spectrumName;
  
  @Eq
  @ApiModelProperty("报警类型")
  private Integer alarmType;
  
  @Eq
  @ApiModelProperty("报警类型名称")
  private String alarmTypeName;

  @Ge
  @ApiModelProperty("报警开始时间")
  private Date startTime;

  @Le
  @ApiModelProperty("报警结束时间")
  private Date endTime;
  
  @Eq
  @ApiModelProperty("报警时长")
  private Integer duration;
  
  @Eq
  @ApiModelProperty("0恢复1报警中")
  private Integer alarmFlag;
  
  @Eq
  @ApiModelProperty("报警信息")
  private String alarmContent;

  @Eq
  @ApiModelProperty("处理状态 0未确认 1事故 2误报")
  private Integer commitFlag;

  @Eq
  @ApiModelProperty("处理意见")
  private String commitInfo;

  @Eq
  @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
  private Integer isDelete;
  
}