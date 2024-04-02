package com.figure.op.duty.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 检修计划作废对象
 * @author fsn
 */
@Data
public class RepairPlanInfoCancelBo implements Serializable {

    /**
     * 检修计划ID
     */
    @NotNull(message = "检修计划ID不能为空")
    private Integer planId;

    /**
     * 作废开始日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    private Date cancelStartDate;
}
