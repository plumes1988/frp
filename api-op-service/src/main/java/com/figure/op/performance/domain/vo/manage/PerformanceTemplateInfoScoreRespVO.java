package com.figure.op.performance.domain.vo.manage;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
* 绩效评分分项 得分情况回显
*/
@Data
public class PerformanceTemplateInfoScoreRespVO {

    /**
     * 分项id
     */
    private Long id;

    /**
     * 名称
     */
    private String title;

    /**
     * 得分
     */
    private BigDecimal score;

    /**
     * 上级编号
     */
    private Long parentId;

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
}
