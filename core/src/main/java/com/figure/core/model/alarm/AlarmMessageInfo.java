package com.figure.core.model.alarm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>
 * 信号报警记录
 * </p>
 *
 * @author feather
 * @date 2023-03-22 10:53:57
 */
@Data
@Accessors(chain = true)
@TableName("alarm_message_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlarmMessageInfo", description = "信号报警记录")
public class AlarmMessageInfo extends BaseModel {

    /**
     * 报警id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("报警id")
    private Long alarmId;
    /**
     * 报警来源 1音频异常 2视频异常 3传输异常 4音频比对异常 5视频比对异常
     */
    @ApiModelProperty("报警来源 1音频异常 2视频异常 3传输异常 4音频比对异常 5视频比对异常")
    private Integer mediaType;
    /**
     * 播出系统(逻辑频道)编号
     */
    @ApiModelProperty("播出系统(逻辑频道)编号")
    private String systemCode;
    /**
     * 播出系统(逻辑频道)名称
     */
    @ApiModelProperty("播出系统(逻辑频道)名称")
    private String systemName;
    /**
     * 前端ID
     */
    @ApiModelProperty("前端ID")
    private Integer frontId;
    /**
     * 前端名称
     */
    @ApiModelProperty("前端名称")
    private String frontName;
    /**
     * 采集点Id
     */
    @ApiModelProperty("采集点Id")
    private Integer logicPositionId;
    /**
     * 采集点名称
     */
    @ApiModelProperty("采集点名称")
    private String logicPositionName;
    /**
     * 信号Id
     */
    @ApiModelProperty("信号Id")
    private String signalId;
    /**
     * 信号名称
     */
    @ApiModelProperty("信号名称")
    private String signalName;
    /**
     * 参考播出系统(逻辑频道)编号
     */
    @ApiModelProperty("参考播出系统(逻辑频道)编号")
    private String refSystemCode;
    /**
     * 参考播出系统(逻辑频道)名称
     */
    @ApiModelProperty("参考播出系统(逻辑频道)名称")
    private String refSystemName;
    /**
     * 参考前端ID
     */
    @ApiModelProperty("参考前端ID")
    private Integer refFrontId;
    /**
     * 参考前端名称
     */
    @ApiModelProperty("参考前端名称")
    private String refFrontName;
    /**
     * 参考采集点Id
     */
    @ApiModelProperty("参考采集点Id")
    private Integer refLogicPositionId;
    /**
     * 参考采集点名称
     */
    @ApiModelProperty("参考采集点名称")
    private String refLogicPositionName;
    /**
     * 参考信号Id
     */
    @ApiModelProperty("参考信号Id")
    private String refSignalId;
    /**
     * 参考信号名称
     */
    @ApiModelProperty("参考信号名称")
    private String refSignalName;
    /**
     * 报警类型Id
     */
    @ApiModelProperty("报警类型Id")
    private Integer alarmTypeId;
    /**
     * 报警类型名称
     */
    @ApiModelProperty("报警类型名称")
    private String alarmTypeName;
    /**
     * 报警开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @ApiModelProperty("报警开始时间")
    private Timestamp startTime;
    /**
     * 报警结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @ApiModelProperty("报警结束时间")
    private Timestamp endTime;
    /**
     * 报警持续时间 单位毫秒
     */
    @ApiModelProperty("报警持续时间 单位毫秒")
    private Integer duration;
    /**
     * 报警状态 0恢复 1报警中
     */
    @ApiModelProperty("报警状态 0恢复 1报警中")
    private Integer alarmFlag;
    /**
     * 处理状态 0未确认 1事故 2误报
     */
    @ApiModelProperty("处理状态 0未确认 1事故 2误报")
    private Integer commitFlag;
    /**
     * 处理意见
     */
    @ApiModelProperty("处理意见")
    private String commitInfo;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

    private Date lastUpdateTime;

    @TableField(exist = false)
    private Long startTimeMillion;
    @TableField(exist = false)
    private Long endTimeMillion;

    public Long getStartTimeMillion() {
        return startTime.getTime();
    }

    public Long getEndTimeMillion() {
        return endTime.getTime();
    }

}