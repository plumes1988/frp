package com.figure.op.duty.domain.bo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RepairPlanInfoQueryBo extends BaseEntity {

    private Integer repairPlanId;

    private Integer repairDeptId;

    /**
     * 检修负责人ID
     */
    private Integer repairManagerId;

    private String repairPlanName;

    private String repairType;

    /**
     * 检修台站
     */
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

    // 查询 检修开始时间

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    // 查询 检修结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

}
