package com.figure.core.model.alarm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.helper.DateHelper;
import com.figure.core.base.BaseModel;
import com.figure.core.rocketmq.struct.consumer.SignalSwitchLogS2PConsumer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 信号倒换记录信息
 * </p>
 *
 * @author feather
 * @date 2023-03-22 10:53:57
 */
@Data
@Accessors(chain = true)
@TableName("alarm_switch_event_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlarmSwitchEventInfo", description = "信号倒换记录信息")
public class AlarmSwitchEventInfo extends BaseModel {

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("")
    private Integer eventId;
    /**
     * 前端Id
     */
    @ApiModelProperty("前端Id")
    private Integer frontId;
    /**
     * 前端名称
     */
    @ApiModelProperty("前端名称")
    private String frontName;
    /**
     * 播出系统编号
     */
    @ApiModelProperty("播出系统编号")
    private String systemCode;
    /**
     * 播出系统名称
     */
    @ApiModelProperty("播出系统名称")
    private String systemName;
    /**
     * 倒换服务编号
     */
    @ApiModelProperty("倒换服务编号")
    private String serviceCode;
    /**
     * 倒换服务名称
     */
    @ApiModelProperty("倒换服务名称")
    private String serviceName;
    /**
     * 设备编号
     */
    @ApiModelProperty("设备编号")
    private String deviceCode;
    /**
     * 设备名称
     */
    @ApiModelProperty("设备名称")
    private String deviceName;
    /**
     * 是否自动倒换 1自动 2手动
     */
    @ApiModelProperty("是否自动倒换 1自动 2手动")
    private Integer isAuto;
    /**
     * 手动倒换用户Id
     */
    @ApiModelProperty("手动倒换用户Id")
    private Integer userId;
    /**
     * 倒换模式 1应急 2回切 3强制
     */
    @ApiModelProperty("倒换模式 1应急 2回切 3强制")
    private Integer actionMode;
    /**
     * 倒换动作
     */
    @ApiModelProperty("倒换动作")
    private String actionInfo;
    /**
     * 倒换动作时间
     */
    @ApiModelProperty("倒换动作时间")
    private Date actionTime;

    /**
     * 倒换耗费时间，单位毫秒
     */
    @ApiModelProperty("倒换耗费时间，单位毫秒")
    private Integer actionDuration;
    /**
     * 是否成功 0失败 1成功
     */
    @ApiModelProperty("是否成功 0失败 1成功")
    private Integer isSuccess;
    /**
     * 失败原因
     */
    @ApiModelProperty("失败原因")
    private String alarmContent;
    /**
     * 是否删除：0未删除、1已删除
     */
    @ApiModelProperty("是否删除：0未删除、1已删除")
    private Integer isDelete;

    public AlarmSwitchEventInfo(){}
    public AlarmSwitchEventInfo(SignalSwitchLogS2PConsumer consumer) {
        this.serviceCode = consumer.getServiceCode();
        this.deviceCode = consumer.getDeviceCode();
        this.isAuto = consumer.getIsAuto();
        this.actionMode = consumer.getActionMode();
        this.actionInfo = consumer.getActionInfo();
        this.actionTime = DateHelper.parse(consumer.getActionTime());
        this.alarmContent = consumer.getAlarmContent();
        this.userId = consumer.getUserId();
    }
}