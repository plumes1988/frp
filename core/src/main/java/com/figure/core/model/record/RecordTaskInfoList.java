package com.figure.core.model.record;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecordTaskInfoList extends RecordTaskInfo {

    @ApiModelProperty("前端名称")
    private String frontName;

    @ApiModelProperty("信号名称")
    private String signalName;

    @ApiModelProperty("采集点名称")
    private String positionName;

    @ApiModelProperty("码流名称")
    private String frequencyName;

    @ApiModelProperty("转码规则名称")
    private String transcodeRuleName;

    @ApiModelProperty("转码服务节点名称")
    private String transcodeServiceName;

    @ApiModelProperty("信号类型")
    private String signalTypeName;

    @ApiModelProperty("信源地址")
    private String channelSource;

    @ApiModelProperty("播出系统名称")
    private String logicChannelName;

    @ApiModelProperty("转码规则名称")
    private String ruleName;

    @ApiModelProperty("集群名称")
    private String recordClusterName;

    @ApiModelProperty("录制节点id")
    private Integer recordServiceId;

    @Override
    public Integer getRecordServiceId() {
        return recordServiceId;
    }

    @Override
    public void setRecordServiceId(Integer recordServiceId) {
        this.recordServiceId = recordServiceId;
    }
}
