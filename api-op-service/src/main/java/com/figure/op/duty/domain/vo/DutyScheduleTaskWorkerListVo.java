package com.figure.op.duty.domain.vo;

import lombok.Data;

/**
 * 排班任务值班人员关联对象
 * @author fsn
 */
@Data
public class DutyScheduleTaskWorkerListVo {

    /**
     * ID
     */
    private Integer id;

    /**
     * 排班计划ID
     */
    private Integer scheduleId;

    /**
     * 排班任务ID
     */
    private Integer scheduleTaskId;

    /**
     * 值班人员ID
     */
    private Integer workerId;

    /**
     * 值班人员名称
     */
    private String workerName;


}
