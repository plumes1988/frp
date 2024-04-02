package com.figure.op.device.domain.vo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class OpOutProductListVo extends BaseEntity {
    
    private Integer outId;

    private Integer opProductId;

    private Integer useAmount;

    private String user;

    private Integer userId;

    private Date useTime;

    private Date planUseStart;

    private Date planUseEnd;

    private Date returnTime;

    private Integer returnOperatorId;

    private String status;

    private String returnOperator;

    private Integer isDelete;

    private String opProductName;

    private String opProductType;

    private String brand;

    private String model;

}
