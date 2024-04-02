package com.figure.core.query.alarm;

import com.figure.core.model.alarm.AlarmMessageInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Ge;
import com.figure.core.util.copycat.annotaion.Le;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AlarmMessageInfoQuery extends AbstractQuery<AlarmMessageInfo> {

    @Eq
    @ApiModelProperty("报警id")
    private Long alarmId;

    @Eq
    @ApiModelProperty("报警来源 1音频异常 2视频异常 3传输异常 4音频比对异常 5视频比对异常")
    private Integer mediaType;

    @Eq
    @ApiModelProperty("播出系统(逻辑频道)编号")
    private String systemCode;

    @Eq
    @ApiModelProperty("播出系统(逻辑频道)名称")
    private String systemName;

    @Eq
    @ApiModelProperty("前端ID")
    private Integer frontId;

    @Eq
    @ApiModelProperty("前端名称")
    private String frontName;

    @Eq
    @ApiModelProperty("采集点Id")
    private Integer logicPositionId;

    @Eq
    @ApiModelProperty("采集点名称")
    private String logicPositionName;

    @Eq
    @ApiModelProperty("信号Id")
    private String signalId;

    @Eq
    @ApiModelProperty("信号名称")
    private String signalName;

    @Eq
    @ApiModelProperty("参考播出系统(逻辑频道)编号")
    private String refSystemCode;

    @Eq
    @ApiModelProperty("参考播出系统(逻辑频道)名称")
    private String refSystemName;

    @Eq
    @ApiModelProperty("参考前端ID")
    private Integer refFrontId;

    @Eq
    @ApiModelProperty("参考前端名称")
    private String refFrontName;

    @Eq
    @ApiModelProperty("参考采集点Id")
    private Integer refLogicPositionId;

    @Eq
    @ApiModelProperty("参考采集点名称")
    private String refLogicPositionName;

    @Eq
    @ApiModelProperty("参考信号Id")
    private String refSignalId;

    @Eq
    @ApiModelProperty("参考信号名称")
    private String refSignalName;

    @Eq
    @ApiModelProperty("报警类型Id")
    private Integer alarmTypeId;

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
    @ApiModelProperty("报警持续时间 单位毫秒")
    private Integer duration;

    @Eq
    @ApiModelProperty("报警状态 0恢复 1报警中")
    private Integer alarmFlag;

    @Eq
    @ApiModelProperty("处理状态 0未确认 1事故 2误报")
    private Integer commitFlag;

    @Eq
    @ApiModelProperty("处理意见")
    private String commitInfo;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}