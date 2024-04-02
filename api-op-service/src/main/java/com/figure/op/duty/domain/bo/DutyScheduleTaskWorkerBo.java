package com.figure.op.duty.domain.bo;

import com.figure.op.common.validate.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 排班任务值班人员对象
 * @author fsn
 */
@Data
public class DutyScheduleTaskWorkerBo implements Serializable {

    /**
     * 排班计划ID
     */
    @NotNull(message = "排班计划ID不能为空", groups = { AddGroup.class })
    private Integer scheduleId;

    /**
     * 排班任务ID
     */
    @NotNull(message = "排班任务ID不能为空", groups = { AddGroup.class })
    private Integer scheduleTaskId;

    /**
     * 值班人员ID集合
     */
    @NotBlank(message = "值班人员ID集合不能为空", groups = { AddGroup.class })
    private String workerIds;

}
