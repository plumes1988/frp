package com.figure.op.duty.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.figure.op.common.validate.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 排班管理考勤状态对象
 * @author fsn
 */
@Data
public class DutyScheduleTaskAttendanceBo implements Serializable {

    /**
     * 排班计划ID
     */
    @NotNull(message = "排班计划ID不能为空", groups = { AddGroup.class })
    private Integer scheduleId;

    /**
     * 值班人员ID
     */
    @NotNull(message = "值班人员ID不能为空", groups = { AddGroup.class })
    private Integer workerId;

    /**
     * 值班状态
     */
    @NotBlank(message = "值班状态不能为空", groups = { AddGroup.class })
    private String dutyStatus;

    /**
     * 旷工日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date absentDate;

    /**
     * 请假开始日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date leaveStartTime;

    /**
     * 请假结束日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date leaveEndTime;

}
