package com.figure.op.device.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 库存管理实体对象
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("op_product_info")
public class OpProductInfo extends BaseEntity {

    @TableId(value = "opProductId")
    private Integer opProductId;

    @TableField("opProductName")
    private String opProductName;

    @TableField("deviceTypeId")
    private Integer deviceTypeId;

    @TableField("brandId")
    private Integer brandId;

    @TableField("modelId")
    private Integer modelId;

    @TableField("inAllAmount")
    private Integer inAllAmount;

    @TableField("stock")
    private Integer stock;

    @TableField("unit")
    private String unit;

    @TableLogic
    private Integer isDelete;


}
