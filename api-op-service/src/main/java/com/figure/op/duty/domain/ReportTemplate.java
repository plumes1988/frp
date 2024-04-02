package com.figure.op.duty.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 报表模板实体对象
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("report_template")
public class ReportTemplate extends BaseEntity {

    /**
     * 值班任务ID
     */
    @TableId(value = "templateId")
    private Integer templateId;

    /**
     * 任务名称
     */
    @TableField("templateName")
    private String templateName;

    /**
     * 任务类型
     */
    @TableField("templateType")
    private String templateType;

    @TableField("reportType")
    private String reportType;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;


}
