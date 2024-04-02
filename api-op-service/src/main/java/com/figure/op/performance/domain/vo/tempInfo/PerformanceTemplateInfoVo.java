package com.figure.op.performance.domain.vo.tempInfo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Set;

/**
 * 绩效模板明细对象 performance_template_info
 *
 * @date 2023-08-25
 */
@Data
public class PerformanceTemplateInfoVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 绩效分项标题
     */
    private String title;

    /**
     * 上级编号
     */
    private Long parentId;


    /**
     * 上级编号串
     */
    private Set<Long> parentIds;

    /**
     * 上级模板明细名称
     */
    private String parentTemplateTitle;

    /**
     * 评分占比
     */
    private Integer scoreRate;

    /**
     * 得分-自动计算
     * （根据最终评分*单项占比）/ 100
     */
    private BigDecimal score;

    /**
     * 说明
     */
    private String info;

    /**
     * 绩效模板id
     * 关联PerformanceTemplateDo.id
     */
    private Long templateId;


}
