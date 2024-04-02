package com.figure.op.duty.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 检修任务状态对象
 * @author fsn
 */
@Data
public class RepairTaskStatusBo implements Serializable {

    /**
     * 检修任务ID
     */
    @NotNull(message = "检修任务ID不能为空")
    private Integer repairTaskId;

    /**
     * 检修任务状态
     */
    @NotNull(message = "检修任务状态不能为空")
    private String taskStatus;

    /**
     * 退回原因
     */
    private String reason;

}
