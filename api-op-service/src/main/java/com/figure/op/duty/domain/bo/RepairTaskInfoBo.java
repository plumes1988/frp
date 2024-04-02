package com.figure.op.duty.domain.bo;

import com.figure.op.common.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 检修任务修改对象
 * @author fsn
 */
@Data
public class RepairTaskInfoBo implements Serializable {

    /**
     * 检修任务ID
     */
    @NotNull(message = "检修任务ID不能为空", groups = { EditGroup.class })
    private Integer repairTaskId;

    /**
     * 任务审核人ID
     */
    @NotNull(message = "任务审核人ID不能为空", groups = { EditGroup.class })
    private Integer taskReviewerId;

    /**
     * 任务描述
     */
    @NotNull(message = "任务描述不能为空", groups = { EditGroup.class })
    private String taskDesc;

}
