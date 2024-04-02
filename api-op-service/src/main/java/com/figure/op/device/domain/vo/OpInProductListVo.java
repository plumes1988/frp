package com.figure.op.device.domain.vo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class OpInProductListVo extends BaseEntity {
    
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
