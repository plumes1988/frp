package com.figure.op.duty.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("repair_event_info")
public class RepairEventInfo extends BaseEntity {

    @TableId(value = "repairEventId")
    private Integer repairEventId;

    @TableField("repairTaskId")
    private Integer repairTaskId;

    @TableField("eventDate")
    private Date eventDate;

    @TableField("workerId")
    private Integer workerId;

    @TableField("worker")
    private String worker;

    @TableField("startTime")
    private Date startTime;

    @TableField("endTime")
    private Date endTime;

    @TableField("deviceId")
    private String deviceId;

    @TableField(value = "stationId")
    private Integer stationId;

    @TableField("device")
    private String device;

    @TableField("station")
    private String station;

    @TableField("repairAct")
    private String repairAct;

    @TableField("confirmStatus")
    private String confirmStatus;

    @TableField("confirmer")
    private String confirmer;

    @TableField("confirmerId")
    private Integer confirmerId;

    @TableField("actStatus")
    private String actStatus;

    @TableField("executeUpdateTime")
    private Date executeUpdateTime;

    @TableField("isDelete")
    private String isDelete;
}
