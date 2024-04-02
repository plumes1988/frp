package com.figure.op.device.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("op_in_product")
public class OpInProduct extends BaseEntity {

    @TableId(value = "inId")
    private Integer inId;

    @TableField(value = "opProductId")
    private Integer opProductId;

    @TableField("amount")
    private Integer amount;

    @TableField("unit")
    private String unit;

    @TableField("inType")
    private String inType;

    @TableField("operator")
    private String operator;

    @TableField("operatorId")
    private Integer operatorId;

    @TableLogic
    private Integer isDelete;


}
