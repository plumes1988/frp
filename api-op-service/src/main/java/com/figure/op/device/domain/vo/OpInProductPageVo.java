package com.figure.op.device.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 入库视图对象
 * @author fsn
 */
@Data
public class OpInProductPageVo {

    private Integer inId;

    private Integer opProductId;

    private String opProductName;

    private Integer deviceTypeId;

    private String deviceTypeName;

    private Integer brandId;

    private String brandName;

    private Integer modelId;

    private String modelCode;

    private Integer amount;

    private String unit;

    private String inType;

    private String operator;

    private Integer operatorId;

    private Date createTime;

}
