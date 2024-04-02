package com.figure.core.query.alarm;

import com.figure.core.model.alarm.AlarmSwitchEventInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Ge;
import com.figure.core.util.copycat.annotaion.Le;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AlarmSwitchEventInfoQuery extends AbstractQuery<AlarmSwitchEventInfo> {

    @Eq
    @ApiModelProperty("")
    private Integer eventId;

    @Eq
    @ApiModelProperty("前端Id")
    private Integer frontId;

    @Eq
    @ApiModelProperty("前端名称")
    private String frontName;

    @Eq
    @ApiModelProperty("播出系统编号")
    private String systemCode;

    @Eq
    @ApiModelProperty("播出系统名称")
    private String systemName;

    @Eq
    @ApiModelProperty("倒换服务编号")
    private String serviceCode;

    @Eq
    @ApiModelProperty("倒换服务名称")
    private String serviceName;

    @Eq
    @ApiModelProperty("设备编号")
    private String deviceCode;

    @Eq
    @ApiModelProperty("设备名称")
    private String deviceName;

    @Eq
    @ApiModelProperty("是否自动倒换 1自动 2手动")
    private Integer isAuto;

    @Eq
    @ApiModelProperty("手动倒换用户Id")
    private Integer userId;

    @Eq
    @ApiModelProperty("倒换模式 1应急 2回切 3强制")
    private Integer actionMode;

    @Eq
    @ApiModelProperty("倒换动作")
    private String actionInfo;

    @Eq
    @ApiModelProperty("倒换动作时间")
    private Date actionTime;

    @Ge(alias = "actionTime")
    @ApiModelProperty("倒换动作开始时间")
    private Date actionTimeStart;

    @Le(alias = "actionTime")
    @ApiModelProperty("倒换动作结束时间")
    private Date actionTimeEnd;

    @Eq
    @ApiModelProperty("是否成功 0失败 1成功")
    private Integer isSuccess;

    @Eq
    @ApiModelProperty("失败原因")
    private String alarmContent;

    @Eq
    @ApiModelProperty("是否删除：0未删除、1已删除")
    private Integer isDelete;

}