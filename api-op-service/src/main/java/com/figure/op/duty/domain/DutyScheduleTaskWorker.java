package com.figure.op.duty.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 排班任务值班人员关联表
 * @author fsn
 */
@Data
@TableName("duty_schedule_task_worker")
public class DutyScheduleTaskWorker {

    /**
     * ID
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * 排班计划ID
     */
    @TableField("scheduleId")
    private Integer scheduleId;

    /**
     * 排班任务ID
     */
    @TableField("scheduleTaskId")
    private Integer scheduleTaskId;

    /**
     * 值班人员ID
     */
    @TableField("workerId")
    private Integer workerId;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;


}
