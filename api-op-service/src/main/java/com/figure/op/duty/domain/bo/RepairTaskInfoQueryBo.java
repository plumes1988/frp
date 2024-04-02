package com.figure.op.duty.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 检修任务查询对象
 * @author fsn
 */
@Data
public class RepairTaskInfoQueryBo {

    /**
     * 检修任务-任务ID
     */
    private Integer repairTaskId;

    /**
     * 检修任务-任务名称
     */
    private String taskName;

    /**
     * 检修任务-所属计划ID
     */
    private Integer repairPlanId;

    /**
     * 检修任务-审核人ID
     */
    private Integer taskReviewerId;

    /**
     * 检修任务-审核状态
     */
    private String taskStatus;

    /**
     * 检修任务-检修台站ID
     */
    private Integer stationId;

    /**
     * 检修任务-任务日期-开始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startTaskDate;

    /**
     * 检修任务-任务日期-结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endTaskDate;

    /**
     * 检修事件-检修人员ID
     */
    private Integer workerId;

    /**
     * 检修事件-执行情况
     */
    private String actStatus;

    /**
     * 检修事件-检修动作
     */
    private String repairAct;

    /**
     * 检修事件-检修设备
     */
    private Integer deviceId;

}
