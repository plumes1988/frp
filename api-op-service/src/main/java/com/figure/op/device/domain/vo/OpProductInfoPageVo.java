package com.figure.op.device.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 库存管理实体对象
 * @author fsn
 */
@Data
public class OpProductInfoPageVo {

    private Integer opProductId;

    private String opProductName;

    private Integer deviceTypeId;

    private String deviceTypeName;

    private Integer brandId;

    private String brandName;

    private Integer modelId;

    private String modelCode;

    private Integer inAllAmount;

    private Integer stock;

    private String unit;

    private Date createTime;

}
