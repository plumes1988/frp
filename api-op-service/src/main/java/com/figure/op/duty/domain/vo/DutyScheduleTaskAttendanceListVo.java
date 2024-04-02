package com.figure.op.duty.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 排班计划考勤列表对象
 * @author fsn
 */
@Data
public class DutyScheduleTaskAttendanceListVo {

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

    /**
     * 值班状态
     */
    private String dutyStatus;

    /**
     * 值班状态名称
     */
    private String dutyStatusName;

    /**
     * 开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;


}
