package com.figure.core.model.transcode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TranscodeTaskInfoList extends TranscodeTaskInfo {

    @ApiModelProperty("转码节点IP")
    private String deviceIP;

    @ApiModelProperty("信号类型来自 1广播 2电视")
    private Integer mediaType;

    @ApiModelProperty("信号类型名称")
    private String signalTypeName;

    @ApiModelProperty("信号类型")
    private Integer signalType;

    @ApiModelProperty("信号名称")
    private String signalName;

    @ApiModelProperty("前端名称")
    private String frontName;

    @ApiModelProperty("频道名称")
    private String channelName;

    @ApiModelProperty("采集点名称")
    private String positionName;

    @ApiModelProperty("码流名称")
    private String frequencyName;

    @ApiModelProperty("转码规则名称")
    private String transcodeRuleName;

    @ApiModelProperty("转码服务节点名称")
    private String transcodeServiceName;

    @ApiModelProperty("转码服务节点id")
    private Integer transcodeServiceId;

    @ApiModelProperty("转码规则是否是默认")
    private Integer isDefault;
}
