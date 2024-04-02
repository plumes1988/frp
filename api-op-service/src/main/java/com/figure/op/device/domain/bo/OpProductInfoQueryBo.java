package com.figure.op.device.domain.bo;

import lombok.Data;

/**
 * 库存管理查询对象
 * @author fsn
 */
@Data
public class OpProductInfoQueryBo{

    private Integer opProductId;

    private String opProductName;

    private Integer deviceTypeId;

    private Integer brandId;

    private Integer modelId;

    private Integer inAllAmount;

    private Integer stock;

    private String unit;

}
