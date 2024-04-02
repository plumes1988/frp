package com.figure.core.query.record;

import com.figure.core.model.record.RecordTaskInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.In;
import com.figure.core.util.copycat.annotaion.Like;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RecordTaskInfoQuery extends AbstractQuery<RecordTaskInfo> {

    @Eq
    @ApiModelProperty("任务ID")
    private Integer recordTaskId;

    @Like(alias = "rti.recordTaskId")
    @ApiModelProperty("任务名称")
    private String recordTaskName;

    @Eq(alias = "rti.mediaType")
    @ApiModelProperty("收录类型 0音频录制 1视频录制 2码流录制")
    private Integer mediaType;

    @Eq(alias = "rti.signalId")
    @ApiModelProperty("信源ID")
    private String signalId;

    @In(alias = "rti.signalId")
    @ApiModelProperty("节目ID的List")
    private List<String> signalIdList;

    @Eq(alias = "rti.signalURL")
    @ApiModelProperty("信源URL")
    private String signalURL;

    @Eq(alias = "rti.bitRate")
    @ApiModelProperty("信源码率")
    private Integer bitRate;

    @Eq(alias = "rti.transcodeRuleId")
    @ApiModelProperty("转码规则Id，源码录制或者码流录制时为0")
    private Integer transcodeRuleId;

    @Eq(alias = "rti.serviceCode")
    @ApiModelProperty("收录服务编号")
    private String serviceCode;

    @Eq(alias = "rti.serviceName")
    @ApiModelProperty("收录服务名称")
    private String serviceName;

    @Eq(alias = "rti.serviceIP")
    @ApiModelProperty("收录服务管理IP")
    private String serviceIP;

    @Eq(alias = "rti.inputIP")
    @ApiModelProperty("数据输入网口IP,默认用管理口IP")
    private String inputIP;

    @Eq(alias = "rti.recordMode")
    @ApiModelProperty("任务模式：0全程收录、1定时收录、2报警触发录制、3节目单触发录制、4手动录制")
    private Integer recordMode;

    @Eq(alias = "rti.isHLS")
    @ApiModelProperty("是否HLS 0不是HLS 1是HLS")
    private Integer isHLS;

    @Eq(alias = "rti.saveTime")
    @ApiModelProperty("保存时间")
    private Integer saveTime;

    @Eq(alias = "rti.taskStatus")
    @ApiModelProperty("运行状态 0待执行 1正常执行 2自动停止 3手动停止 4执行故障 5信源中断")
    private Integer taskStatus;

    @Eq(alias = "rti.errorInfo")
    @ApiModelProperty("故障信息")
    private String errorInfo;

    @Eq(alias = "rti.isDelete")
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;
}