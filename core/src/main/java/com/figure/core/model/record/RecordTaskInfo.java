package com.figure.core.model.record;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import com.figure.core.rocketmq.struct.info.RecordTaskStatusInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * <p>
 * 录制任务
 * </p>
 *
 * @author feather
 * @date 2023-04-11 11:21:04
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("record_task_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RecordTaskInfo", description = "录制任务")
public class RecordTaskInfo extends BaseModel {

    /**
     * 任务ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("任务ID")
    private Integer recordTaskId;
    /**
     * 任务名称
     */
    @ApiModelProperty("任务名称")
    private String recordTaskName;
    /**
     * 收录类型 0音频录制 1视频录制 2码流录制
     */
    @ApiModelProperty("收录类型 0音频录制 1视频录制 2码流录制")
    private Integer mediaType;
    /**
     * 信源ID
     */
    @ApiModelProperty("信源ID")
    private String signalId;
    /**
     * 信源URL
     */
    @ApiModelProperty("信源URL")
    private String signalURL;
    /**
     * 信源码率
     */
    @ApiModelProperty("信源码率")
    private Integer bitRate;
    /**
     * 转码规则Id，源码录制或者码流录制时为0
     */
    @ApiModelProperty("转码规则Id，源码录制或者码流录制时为0")
    private Integer transcodeRuleId;
    /**
     * 收录服务编号
     */
    @ApiModelProperty("收录服务编号")
    private String serviceCode;
    /**
     * 收录服务名称
     */
    @ApiModelProperty("收录服务名称")
    private String serviceName;
    /**
     * 收录服务管理IP
     */
    @ApiModelProperty("收录服务管理IP")
    private String serviceIP;
    /**
     * 数据输入网口IP,默认用管理口IP
     */
    @ApiModelProperty("数据输入网口IP,默认用管理口IP")
    private String inputIP;
    /**
     * 任务模式：0全程收录、1定时收录、2报警触发录制、3节目单触发录制、4手动录制
     */
    @ApiModelProperty("任务模式：0全程收录、1定时收录、2报警触发录制、3节目单触发录制、4手动录制")
    private Integer recordMode;
    /**
     * 是否HLS 0不是HLS 1是HLS
     */
    @ApiModelProperty("是否HLS 0不是HLS 1是HLS")
    private Integer isHLS;
    /**
     * 保存时间
     */
    @ApiModelProperty("保存时间")
    private Integer saveTime;
    /**
     * 运行状态 0待执行 1正常执行 2自动停止 3手动停止 4执行故障 5信源中断
     */
    @ApiModelProperty("运行状态 0待执行 1正常执行 2自动停止 3手动停止 4执行故障 5信源中断")
    private Integer taskStatus;
    /**
     * 故障信息
     */
    @ApiModelProperty("故障信息")
    private String errorInfo;

    @TableField(exist = false)
    private Integer type = 0;

    @TableField(exist = false)
    private Integer videoType;

    @TableField(exist = false)
    private Integer recordClusterId;

    @TableField(exist = false)
    private Integer recordServiceId;

    @TableField(exist = false)
    private Integer operation;

    public Integer getRecordServiceId() {
        return recordServiceId;
    }

    public void setRecordServiceId(Integer recordServiceId) {
        this.recordServiceId = recordServiceId;
    }

    /**
     * 状态：0:未删除、1:已删除、2:停用
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

    public RecordTaskInfo(RecordTaskStatusInfo recordTaskStatusInfo) {
        this.recordTaskId = recordTaskStatusInfo.getTaskID();
        this.taskStatus = recordTaskStatusInfo.getTaskState();
        this.errorInfo = recordTaskStatusInfo.getAlarmContent();
    }

}