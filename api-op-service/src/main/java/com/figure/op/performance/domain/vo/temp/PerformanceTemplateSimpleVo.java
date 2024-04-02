package com.figure.op.performance.domain.vo.temp;

import lombok.Data;

/**
 * 绩效模板简化对象 performance_template
 *
 * @date 2023-08-25
 */
@Data
public class PerformanceTemplateSimpleVo {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 模板名称
     */
    private String name;

}
