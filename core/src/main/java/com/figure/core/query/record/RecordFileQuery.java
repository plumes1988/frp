package com.figure.core.query.record;

import com.figure.core.model.record.RecordFile;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class RecordFileQuery extends AbstractQuery<RecordFile> {

    @Eq
    @ApiModelProperty("文件Id,自增")
    private Integer fileId;

    @Eq
    @ApiModelProperty("收录服务编号")
    private String serviceCode;

    @Eq
    @ApiModelProperty("收录服务IP")
    private String serviceIP;

    @Eq
    @ApiModelProperty("收录类型 0音频 1视频 2码流")
    private Integer mediaType;

    @Eq
    @ApiModelProperty("频道Id或者频率Id")
    private String signalId;

    @Eq
    @ApiModelProperty("转码规则Id，源码录制或者码流录制时为0")
    private Integer transcodeRuleId;

    @Eq
    @ApiModelProperty("文件路径")
    private String filePath;

    @Eq
    @ApiModelProperty("文件开始时间")
    private Date startTime;

    @Eq
    @ApiModelProperty("文件结束时间")
    private Date endTime;

    @Eq
    @ApiModelProperty("文件时长")
    private Integer fileTime;

    @Eq
    @ApiModelProperty("报警触发标识 0未关联报警 1已关联报警")
    private Integer alarmRecord;

    @Eq
    @ApiModelProperty("是否HLS 0不是HLS 1是HLS")
    private Integer isHLS;

    @Eq
    @ApiModelProperty("另存标识 每被另存为一次，标识+1，删除另存对象标识-1")
    private Integer saveFlag;

    @Eq
    @ApiModelProperty("保存时长，单位 天")
    private Integer saveTime;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}