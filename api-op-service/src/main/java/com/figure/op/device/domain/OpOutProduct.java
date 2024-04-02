package com.figure.op.device.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("op_out_product")
public class OpOutProduct extends BaseEntity {

    @TableId(value = "outId")
    private Integer outId;

    @TableField(value = "opProductId")
    private Integer opProductId;

    @TableField(value = "useAmount")
    private Integer useAmount;

    @TableField(value = "returnNum")
    private Integer returnNum;

    @TableField("user")
    private String user;

    @TableField("userId")
    private Integer userId;

    @TableField("useTime")
    private Date useTime;

    @TableField("planUseStart")
    private Date planUseStart;

    @TableField("planUseEnd")
    private Date planUseEnd;

    @TableField("returnTime")
    private Date returnTime;

    @TableField("returnOperatorId")
    private Integer returnOperatorId;

    @TableField("status")
    private String status;

    @TableField("returnOperator")
    private String returnOperator;

    @TableField("returnTimeline")
    private String returnTimeline;

    @TableLogic
    private Integer isDelete;


}
