package com.figure.op.performance.domain.vo.tempInfo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 绩效模板明细对象 performance_template_info
 *
 * @date 2023-08-25
 */
@Data
public class PerformanceTemplateInfoSecondVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 评分占比
     */
    private Integer scoreRate;

    /**
     * 满分
     */
    private BigDecimal fullScore;

    /**
     * 说明
     */
    private String info;


}
