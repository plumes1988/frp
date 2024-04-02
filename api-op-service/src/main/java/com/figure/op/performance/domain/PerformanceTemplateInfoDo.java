package com.figure.op.performance.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import com.figure.op.common.enums.PerformanceTemplateLevelEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;


/**
 * 绩效模板明细对象 performance_template_info
 *
 * @author 39827
 * @date 2023-08-25
 */
@EqualsAndHashCode(callSuper = true)
@TableName("performance_template_info")
@Data
public class PerformanceTemplateInfoDo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 绩效分项标题
     */
    @TableField("title")
    private String title;

    /**
     * 上级编号
     */
    @TableField("parentId")
    private Long parentId;

    /**
     * 评分占比
     */
    @TableField("scoreRate")
    private Integer scoreRate;

    /**
     * 说明
     */
    @TableField("info")
    private String info;

    /**
     * 层级 first=一级 second=二级
     * PerformanceTemplateLevelEnum
     */
    @TableField("level")
    private String level;

    /**
     * 绩效模板id
     * 关联PerformanceTemplateDo.id
     */
    @TableField("templateId")
    private Long templateId;

    /**
     * 状态：0:未删除、1:删除
     */
    @TableLogic
    private Integer isDelete;



}
