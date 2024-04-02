package com.figure.op.performance.domain;

import com.figure.op.common.domain.BaseEntity;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;

/**
 * 绩效评分管理 DO
 *
 * @author 管理员
 */
@TableName("performance_manage")
@Data
public class PerformanceManageDO extends BaseEntity {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 机构id
     */
    @TableField("agencyId")
    private Integer agencyId;
    /**
     * 模板id
     */
    @TableField("templateId")
    private Long templateId;
    /**
     * 绩效评价评分
     */
    @TableField("score")
    private BigDecimal score;

    /**
     * 状态：0:未删除、1:删除
     */
    @TableLogic
    private Boolean isDelete;

}
