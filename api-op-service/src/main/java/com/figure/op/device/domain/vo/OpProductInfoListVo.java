package com.figure.op.device.domain.vo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class OpProductInfoListVo extends BaseEntity {
    
    private Integer opProductId;

    private String opProductName;

    private String opProductType;

    private String brand;

    private String model;

    private Integer inAllAmount;

    private Integer stock;

    private String unit;

    private Integer isDelete;

}
