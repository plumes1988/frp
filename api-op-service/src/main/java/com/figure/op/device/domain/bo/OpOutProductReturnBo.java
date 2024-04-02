package com.figure.op.device.domain.bo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 出库管理归还对象
 * @author fsn
 */
@Data
public class OpOutProductReturnBo {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Integer outId;

    /**
     * 归还数量
     */
    @NotNull(message = "归还数量不能为空")
    @Min(value = 1, message = "归还数量最小是1")
    private Integer returnNum;

}
