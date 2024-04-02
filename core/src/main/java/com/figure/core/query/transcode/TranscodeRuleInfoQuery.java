package com.figure.core.query.transcode;

import com.figure.core.model.transcode.TranscodeRuleInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TranscodeRuleInfoQuery extends AbstractQuery<TranscodeRuleInfo> {

  @Eq
  @ApiModelProperty("规则ID")
  private Integer ruleId;

  @Eq
  @ApiModelProperty("规则名称")
  private String ruleName;

  @Eq
  @ApiModelProperty("媒体类型 1-音频 2-标清 3-高清 4-4k")
  private Integer mediaType;

  @Eq
  @ApiModelProperty("工作模式：0：只做转发，1：编码转发")
  private Integer workMode;

  @Eq
  @ApiModelProperty("视频编码 默认H264")
  private String videoCode;

  @Eq
  @ApiModelProperty("视频高度")
  private String videoHeight;

  @Eq
  @ApiModelProperty("视频宽度")
  private String videoWidth;

  @Eq
  @ApiModelProperty("视频帧率 单位fps")
  private String frameRate;

  @Eq
  @ApiModelProperty("视频码率 单位Kbps")
  private String videoCodeRate;

  @Eq
  @ApiModelProperty("音频编码 默认 AC3，0不转码，沿用原始")
  private String audioCode;

  @Eq
  @ApiModelProperty("采样率")
  private String samplingRate;

  @Eq
  @ApiModelProperty("音频码率 单位Kbps")
  private String audioCodeRate;

  @Eq
  @ApiModelProperty("编码模式：1：性能（superfast），2：平衡（medium），3：质量（slow）")
  private Integer codingMode;

  @Eq
  @ApiModelProperty("开启RTMP,是否是默认规则（0，是。1，否）")
  private Integer showRTMP;

  @Eq
  @ApiModelProperty("开启UDP，是否是默认规则（0，是。1，否）")
  private Integer showUDP;

  @Eq
  @ApiModelProperty("开启HTTP_TS，是否是默认规则（0，是。1，否）")
  private Integer showHTTP_TS;

  @Eq
  @ApiModelProperty("开启HLS，是否是默认规则（0，是。1，否）")
  private Integer showHLS;

  @Eq
  @ApiModelProperty("开启OSD：0不开启  1开启")
  private Integer isOSD;

  @Eq
  @ApiModelProperty("文字位置")
  private String textXPosition;

  @Eq
  @ApiModelProperty("")
  private String textYPosition;

  @Eq
  @ApiModelProperty("文字字体大小")
  private String textFontSize;

  @Eq
  @ApiModelProperty("时间位置")
  private String timeXPosition;

  @Eq
  @ApiModelProperty("")
  private String timeYPosition;

  @Eq
  @ApiModelProperty("时间字体大小")
  private String timeFontSize;

  @Eq
  @ApiModelProperty("是否开启时间信息：0不显示，1显示")
  private Long showTimeInfo;

  @Eq
  @ApiModelProperty("是否开启位置信息：0不显示，1显示")
  private Long showFrontName;

  @Eq
  @ApiModelProperty("是否显示采集点：0不显示，1显示")
  private Long showMonitorPoint;

  @Eq
  @ApiModelProperty("是否显示信号类型信息：0不显示，1显示")
  private Long showSignal;

  @Eq
  @ApiModelProperty("是否显示频道名称信息：0不显示，1显示")
  private Long showChannelName;

  @Eq
  @ApiModelProperty("设为该媒体类型默认规则（0，是。1，否）")
  private Integer isDefault;

  @Eq
  @ApiModelProperty("同步状态 0：未同步 1：已同步 ")
  private Integer syncStatus;

  @Eq
  @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
  private Integer isDelete;

}