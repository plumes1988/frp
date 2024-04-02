package com.figure.op.duty.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 维修工单 提交状态对象
 * @author fsn
 */
@Data
public class RepairOrderInfoSubmitStatusBo {

    /**
     * 工单ID
     */
    @NotNull
    private Integer repairOrderId;

    /**
     * 提交状态 0待提交 1已提交
     */
    @NotBlank
    private String submitStatus;

}
