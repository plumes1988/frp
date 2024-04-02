package com.figure.op.duty.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 维修工单 提交状态对象
 * @author fsn
 */
@Data
public class RepairOrderInfoActStatusBo {

    /**
     * 工单ID
     */
    @NotNull
    private Integer repairOrderId;

    /**
     * 执行状态 2完成 3失败 4取消
     */
    @NotBlank
    private String actStatus;

    /**
     * 维修描述
     */
    private String repairDes;

    /**
     * 维修图片
     */
    private String repairImgs;


}
