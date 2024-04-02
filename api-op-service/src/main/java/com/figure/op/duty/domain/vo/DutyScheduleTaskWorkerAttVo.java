package com.figure.op.duty.domain.vo;

import lombok.Data;

/**
 * 排班任务值班人员状态对象
 * @author fsn
 */
@Data
public class DutyScheduleTaskWorkerAttVo {

    /**
     * 值班人员ID
     */
    private Integer workerId;

    /**
     * 值班人员名称
     */
    private String workerName;

    /**
     * 状态（0到岗 1缺勤）
     */
    private String attStatus;

}
