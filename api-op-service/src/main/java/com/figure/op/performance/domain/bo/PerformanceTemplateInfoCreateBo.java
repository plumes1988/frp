package com.figure.op.performance.domain.bo;

import lombok.Data;

/**
 * 新增绩效模板明细对象
 *
 * @author fsn
 * @date 2023-08-25
 */
@Data
public class PerformanceTemplateInfoCreateBo {

    private static final long serialVersionUID = 1L;

    /**
     * 模板编号
     */
    private Long performanceTemplateId;

    /**
     * 一级分项编号
     * 如果有上级编号，根据此编号在数据库中查找
     */
    private Long firstTemplateInfoId;

    /**
     * 一级模板名称
     * 如果无上级编号，根据此参数插入数据
     */
    private String firstTemplateInfoTitle;

    /**
     * 一级评分占比
     * 如果无上级编号，根据此参数插入数据
     */
    private Integer firstTemplateInfoScoreRate;

    /**
     * 二级绩效分项标题
     */
    private String secondTemplateInfoTitle;

    /**
     * 二级评分占比
     */
    private Integer secondTemplateInfoScoreRate;

    /**
     * 二级说明
     */
    private String secondTemplateInfo;


}
