package com.figure.op.performance.domain.vo.manage;

import lombok.*;

import javax.validation.constraints.*;

/**
 * 绩效评分管理更新
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PerformanceManageUpdateReqVO extends PerformanceManageBaseVO {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

}
