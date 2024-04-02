package com.figure.op.device.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class OpInProductVo extends BaseEntity {
    
    private Integer inId;

    private Integer opProductId;

    private String opProductName;

    private String opProductType;

    private String brand;

    private String model;

    private Integer amount;

    private String unit;

    private String inType;

    private String operator;

    private Integer isDelete;

}
