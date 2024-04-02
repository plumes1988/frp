package com.figure.op.duty.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 维修工单 提交状态对象
 * @author fsn
 */
@Data
public class RepairOrderInfoReviewStatusBo {

    /**
     * 工单ID
     */
    @NotNull
    private Integer repairOrderId;

    /**
     * 审核状态 2通过 3驳回
     */
    @NotBlank
    private String reviewStatus;

    /**
     * 审核原因
     */
    private String reviewReason;

}
