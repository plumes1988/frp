package com.figure.op.performance.domain.vo.date;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


/**
 * 绩效模板明细对象 performance_template_info
 *
 * @date 2023-08-25
 */
@Data
public class PerformanceDataExcelVo {

    /**
     * id
     */
    private Long id;

    /**
     * 上级编号
     */
    private Long parentId;

    /**
     *父级标题
     */
    private String parentTitle;

    /**
     * 父级评分占比
     */
    private Integer parentScoreRate;

    /**
     * 父级满分
     */
    private BigDecimal parentFullScore;

    /**
     * 父级实际分值
     */
    private BigDecimal parentScore;

    /**
     * 子级标题
     */
    private String title;

    /**
     * 子级说明
     */
    private String info;

    /**
     * 子级评分占比
     */
    private Integer scoreRate;

    /**
     * 子级满分
     */
    private BigDecimal fullScore;

    /**
     * 子级实际分值
     */
    private BigDecimal score;

    /**
     * 子级打分
     */
    private Integer mark;

    /**
     * 层级 first=一级 second=二级
     * PerformanceTemplateLevelEnum
     */
    private String level;



}
