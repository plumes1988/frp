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
 * 排班管理考勤状态
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("duty_schedule_task_attendance")
public class DutyScheduleTaskAttendance extends BaseEntity {

    /**
     * 考勤ID
     */
    @TableId(value = "attendanceId")
    private Integer attendanceId;

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
     * 排班日期
     */
    @TableField("scheduleDate")
    private Date scheduleDate;

    /**
     * 值班人员ID
     */
    @TableField("workerId")
    private Integer workerId;

    /**
     * 值班状态
     */
    @TableField("dutyStatus")
    private String dutyStatus;

    /**
     * 请假日期
     */
    @TableField("leaveDate")
    private Date leaveDate;

    /**
     * 请假-开始时间
     */
    @TableField("leaveStartTime")
    private Date leaveStartTime;

    /**
     * 请假-结束时间
     */
    @TableField("leaveEndTime")
    private Date leaveEndTime;

    /**
     * 请假时长
     */
    @TableField("leaveTime")
    private Integer leaveTime;

    /**
     * 请假天数
     */
    @TableField("leaveDay")
    private Integer leaveDay;

    /**
     * 旷工日期
     */
    @TableField("absentDate")
    private Date absentDate;


    /**
     * 旷工-开始时间
     */
    @TableField("absentStartTime")
    private Date absentStartTime;

    /**
     * 旷工-结束时间
     */
    @TableField("absentEndTime")
    private Date absentEndTime;

    /**
     * 旷工时长
     */
    @TableField("absentTime")
    private Integer absentTime;

    /**
     * 旷工天数
     */
    @TableField("absentDay")
    private Integer absentDay;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;



}
