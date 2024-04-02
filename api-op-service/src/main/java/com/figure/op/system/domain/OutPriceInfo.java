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
@TableName("out_price_info")
public class OutPriceInfo extends BaseEntity {

    @TableId("outPriceId")
    private Integer outPriceId;

    @TableField("useInfo")
    private String useInfo;

    @TableField("source")
    private String source;

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

    @TableField("path")
    private String path;

    @TableField("applier")
    private String applier;

    @TableField("applierId")
    private Integer applierId;

    @TableField("applyTime")
    private Date applyTime;

    @TableLogic
    private Integer isDelete;

    @TableField("organ")
    private String organ;

    @TableField("purchase")
    private String purchase;

    @TableField("operateTimeline")
    private String operateTimeline;

    @TableField("paymentTime")
    private Date paymentTime;

    @TableField("submitTime")
    private Date submitTime;

}
