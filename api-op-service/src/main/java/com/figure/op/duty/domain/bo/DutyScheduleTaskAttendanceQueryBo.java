package com.figure.op.duty.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 排班计划考勤状态查询对象
 * @author fsn
 */
@Data
public class DutyScheduleTaskAttendanceQueryBo implements Serializable {

    /**
     * 排班计划ID
     */
    @NotNull(message = "排班ID不能为空")
    private Integer scheduleId;

    /**
     * 排班任务ID
     */
    private Integer scheduleTaskId;

    /**
     * 排班计划ID集合
     */
    private String scheduleIds;

    /**
     * 排班任务ID集合
     */
    private String scheduleTaskIds;



}
