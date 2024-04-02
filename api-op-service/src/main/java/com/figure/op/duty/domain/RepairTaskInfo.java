package com.figure.op.duty.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 检修任务实体对象
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("repair_task_info")
public class RepairTaskInfo extends BaseEntity {

    @TableId(value = "repairTaskId")
    private Integer repairTaskId;

    @TableField("repairPlanId")
    private Integer repairPlanId;

    @TableField("taskName")
    private String taskName;

    @TableField("repairStation")
    private Integer repairStation;

    @TableField("taskDate")
    private Date taskDate;

    @TableField("taskStartTime")
    private Date taskStartTime;

    @TableField("taskEndTime")
    private Date taskEndTime;

    @TableField(value = "taskReviewerId")
    private Integer taskReviewerId;

    @TableField("taskReviewerName")
    private String taskReviewerName;

    @TableField("taskDesc")
    private String taskDesc;

    @TableField("taskDuration")
    private String taskDuration;

    @TableField("taskStatus")
    private String taskStatus;

    @TableField("reason")
    private String reason;
}
