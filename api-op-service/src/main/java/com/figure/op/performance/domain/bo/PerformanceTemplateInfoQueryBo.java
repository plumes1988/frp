package com.figure.op.performance.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

/**
 * 绩效模板明细对象 performance_template_info
 *
 * @author fsn
 * @date 2023-08-25
 */
@Data
public class PerformanceTemplateInfoQueryBo {

    private static final long serialVersionUID = 1L;

    /**
     * 模板ID
     */
    @NotNull(message = "模板ID不能为空")
    private Long templateId;

    /**
     * 绩效分项标题
     */
    private String title;

    /**
     * 上级模板明细名称
     */
    private String parentTemplateTitle;

}
