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
public class PerformanceDataTreeVo {

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
     * 评分占比
     */

    private Long scoreRate;

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
     * 层级 first=一级 second=二级
     * PerformanceTemplateLevelEnum
     */

    private String level;

    /**
     * 绩效模板id
     * 关联PerformanceTemplateDo.id
     */

    private Long templateId;

    /**
     * 绩效模板明细id
     * 关联PerformanceTemplateDo.id
     */

    private Long templateInfoId;

    /**
     * 关联绩效管理id
     * 关联PerformanceTemplateDo.id
     */

    private Long manageId;

    /**
     * 子集
     */
    private List<PerformanceDataTreeVo> children;



}
