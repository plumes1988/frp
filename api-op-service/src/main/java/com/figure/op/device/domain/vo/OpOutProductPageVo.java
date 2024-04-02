package com.figure.op.device.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 出库视图对象
 * @author fsn
 */
@Data
public class OpOutProductPageVo {

    private Integer outId;

    private Integer opProductId;

    private Integer useAmount;

    private Integer returnNum;

    private String user;

    private Integer userId;

    private Date useTime;

    private Date planUseStart;

    private Date planUseEnd;

    private Date returnTime;

    private Integer returnOperatorId;

    private String status;

    private String returnOperator;

    private String opProductName;

    private Integer deviceTypeId;

    private String deviceTypeName;

    private Integer brandId;

    private String brandName;

    private Integer modelId;

    private String modelCode;

    private Date createTime;

}
