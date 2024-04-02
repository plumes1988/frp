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
@TableName("repair_order_info")
public class RepairOrderInfo extends BaseEntity {

    @TableId(value = "repairOrderId")
    private Integer repairOrderId;

    @TableField("stationId")
    private Integer stationId;

    @TableField("station")
    private String station;

    @TableField("deviceId")
    private Integer deviceId;

    @TableField("device")
    private String device;

    @TableField("fault")
    private String fault;

    @TableField("imgs")
    private String imgs;

    @TableField("submitTime")
    private Date submitTime;

    @TableField("submitterId")
    private Integer submitterId;

    @TableField("submitter")
    private String submitter;

    @TableField("submitStatus")
    private String submitStatus;

    // 退回理由
    @TableField("submitReason")
    private String submitReason;

    @TableField("reviewTime")
    private Date reviewTime;

    @TableField("reviewerId")
    private Integer reviewerId;

    @TableField("reviewer")
    private String reviewer;

    @TableField("reviewStatus")
    private String reviewStatus;

    @TableField("reviewReason")
    private String reviewReason;

    @TableField("actTime")
    private Date actTime;

    @TableField("actorId")
    private Integer actorId;

    @TableField("actor")
    private String actor;

    @TableField("actStatus")
    private String actStatus;

    @TableField("repairDes")
    private String repairDes;

    @TableField("repairImgs")
    private String repairImgs;

}
