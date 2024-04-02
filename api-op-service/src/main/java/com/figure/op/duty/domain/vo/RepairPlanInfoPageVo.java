package com.figure.op.duty.domain.vo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class RepairPlanInfoPageVo extends BaseEntity {

    private Integer repairPlanId;

    private Integer repairDeptId;

    private Integer repairManagerId;

    private String repairPlanName;

    private String repairType;

    private Integer repairStation;

    private Date repairStartTime;

    private Date repairEndTime;

    private String repairStart;

    private String repairEnd;

    private String repairDept;

    private String repairManagerName;

    private String phoneNumber;

    private String repairContent;

    private Date repairTime;

    private String isRepeat;

    private String repeatRule;

    private String repairStationName;

    private String repeatTime;

    private Date cancelDay;

}
