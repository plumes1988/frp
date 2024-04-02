package com.figure.op.performance.domain.bo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 绩效模板对象 performance_template
 *
 * @date 2023-08-25
 */
@Data
public class PerformanceTemplateBo {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板说明
     */
    private String info;

    /**
     * 绩效评价评分
     */
    private Long score;


}
