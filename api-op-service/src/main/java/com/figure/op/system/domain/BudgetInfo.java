package com.figure.op.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("budget_info")
public class BudgetInfo extends BaseEntity {

    @TableId("budgetId")
    private Integer budgetId;

    @TableField("entryTime")
    private Date entryTime;

    @TableField("source")
    private String source;

    @TableField("organ")
    private String organ;

    @TableField("price")
    private BigDecimal price;

    @TableField("status")
    private String status;

    @TableField("reason")
    private String reason;

    @TableField("reviewId")
    private Integer reviewId;

    @TableField("reviewer")
    private String reviewer;

    @TableField("reviewTime")
    private Date reviewTime;

    @TableField("isPlan")
    private String isPlan;

    @TableField("creator")
    private String creator;

    @TableField("info")
    private String info;

    @TableField("operateTimeline")
    private String operateTimeline;

    @TableField("submitTime")
    private Date submitTime;

    @TableLogic
    private Integer isDelete;

}
