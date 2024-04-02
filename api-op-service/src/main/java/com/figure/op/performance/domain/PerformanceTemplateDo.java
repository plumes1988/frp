package com.figure.op.performance.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 绩效模板对象 performance_template
 *
 * @author ly
 * @date 2023-08-25
 */
@EqualsAndHashCode(callSuper = true)
@TableName("performance_template")
@Data
public class PerformanceTemplateDo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 模板名称
     */
    @TableField("name")
    private String name;

    /**
     * 模板说明
     */
    @TableField("info")
    private String info;

    /**
     * 绩效评价评分
     */
    @TableField("score")
    private Long score;

    /**
     * 状态：0:未删除、1:删除
     */
    @TableLogic
    private Integer isDelete;


}
