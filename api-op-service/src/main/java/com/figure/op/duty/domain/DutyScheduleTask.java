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
 * 排班任务
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("duty_schedule_task")
public class DutyScheduleTask extends BaseEntity {

    /**
     * 排班任务ID
     */
    @TableId(value = "scheduleTaskId")
    private Integer scheduleTaskId;

    /**
     * 排班计划ID
     */
    @TableField(value = "scheduleId")
    private Integer scheduleId;

    /**
     * 排班日期
     */
    @TableField(value = "scheduleDate")
    private Date scheduleDate;


    /**
     * 值班人员ID集合
     */
    @TableField(value = "workerIds")
    private String workerIds;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;


}
