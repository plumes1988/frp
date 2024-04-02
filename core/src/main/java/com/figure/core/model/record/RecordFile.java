package com.figure.core.model.record;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.helper.DateHelper;
import com.figure.core.base.BaseModel;
import com.figure.core.rocketmq.struct.consumer.RecordDataUpdateS2PConsumer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 录制文件
 * </p>
 *
 * @author feather
 * @date 2023-03-30 10:01:29
 */
@Data
@Accessors(chain = true)
@TableName("record_file")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RecordFile", description = "录制文件")
public class RecordFile extends BaseModel {

    /**
     * 文件Id,自增
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("文件Id,自增")
    private Integer fileId;
    /**
     * 收录服务编号
     */
    @ApiModelProperty("收录服务编号")
    private String serviceCode;
    /**
     * 收录服务IP
     */
    @ApiModelProperty("收录服务IP")
    private String serviceIP;
    /**
     * 收录类型 0音频 1视频 2码流
     */
    @ApiModelProperty("收录类型 0音频 1视频 2码流")
    private Integer mediaType;
    /**
     * 频道Id或者频率Id
     */
    @ApiModelProperty("频道Id或者频率Id")
    private String signalId;
    /**
     * 转码规则Id，源码录制或者码流录制时为0
     */
    @ApiModelProperty("转码规则Id，源码录制或者码流录制时为0")
    private Integer transcodeRuleId;
    /**
     * 文件路径
     */
    @ApiModelProperty("文件路径")
    private String filePath;
    /**
     * 文件url 录制服务器提供nginx下载服务地址
     */
    @ApiModelProperty("文件url 录制服务器提供nginx下载服务地址")
    private String fileUrl;
    /**
     * 文件开始时间
     */
    @ApiModelProperty("文件开始时间")
    private Date startTime;
    /**
     * 文件结束时间
     */
    @ApiModelProperty("文件结束时间")
    private Date endTime;
    /**
     * 文件时长
     */
    @ApiModelProperty("文件时长")
    private Integer fileTime;
    /**
     * 报警触发标识 0未关联报警 1已关联报警
     */
    @ApiModelProperty("报警触发标识 0未关联报警 1已关联报警")
    private Integer alarmRecord;
    /**
     * 是否HLS 0不是HLS 1是HLS
     */
    @ApiModelProperty("是否HLS 0不是HLS 1是HLS")
    private Integer isHLS;
    /**
     * 另存标识 每被另存为一次，标识+1，删除另存对象标识-1
     */
    @ApiModelProperty("另存标识 每被另存为一次，标识+1，删除另存对象标识-1")
    private Integer saveFlag;
    /**
     * 保存时长，单位 天
     */
    @ApiModelProperty("保存时长，单位 天")
    private Integer saveTime;
    /**
     * 状态：0:未删除、1:已删除、2:停用
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

    @TableField(exist = false)
    private String tableName;

    public RecordFile() {
    }

    public RecordFile(RecordDataUpdateS2PConsumer consumer) {
        this.serviceCode = consumer.getServiceCode();
        this.serviceIP = consumer.getServiceIP();
        this.mediaType = consumer.getMediaType();
        this.signalId = consumer.getSignalID();
        this.transcodeRuleId = consumer.getTranscodeRuleID();
        this.filePath = consumer.getFilePath();
        this.fileUrl = consumer.getFileURL();
        this.startTime = DateHelper.parse(consumer.getStartTime());
        this.endTime = DateHelper.parse(consumer.getEndTime());
        this.fileTime = consumer.getFileTime();
        this.alarmRecord = 0;
        this.saveFlag = 1;
        this.isHLS = consumer.getIsHLS();
        this.saveTime = consumer.getSaveTime();
        this.tableName = "record_file_" + this.getMediaType() + "_" + this.getSignalId() + "_" + this.getTranscodeRuleId() + "_" + this.getIsHLS();
    }
}