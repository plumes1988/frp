package com.figure.op.duty.domain.vo;

import lombok.Data;

/**
 *
 * 排班管理统计对象
 * @author fsn
 */
@Data
public class DutyScheduleStatisticsPageVo {

    /**
     * 值班人员ID
     */
    private Integer workerId;

    /**
     * 值班人员
     */
    private String workerName;

    /**
     * 部门
     */
    private String deptName;

    /**
     * 计划值班天数
     */
    private Integer planDays;

    /**
     * 计划值班时长
     */
    private Integer planTimes;

    /**
     * 实际值班天数
     */
    private Integer realDays;

    /**
     * 实际值班时长
     */
    private Integer realTimes;

    /**
     * 异常值班天数
     */
    private Integer expDays;

    /**
     * 异常值班时长
     */
    private Integer expTimes;

    /**
     * 请假时长
     */
    private Integer leaveTimes;

    /**
     * 旷工时长
     */
    private Integer absentTimes;
}
