package com.figure.op.duty.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 检修计划实体对象
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("repair_plan_info")
public class RepairPlanInfo extends BaseEntity {

    @TableId("repairPlanId")
    private Integer repairPlanId;

    @TableField(value = "repairDeptId")
    private Integer repairDeptId;

    @TableField(value = "repairManagerId")
    private Integer repairManagerId;



    @TableField("repairPlanName")
    private String repairPlanName;

    @TableField("repairType")
    private String repairType;

    @TableField("repairStation")
    private Integer repairStation;


    @TableField("repairStartTime")
    private Date repairStartTime;

    @TableField("repairEndTime")
    private Date repairEndTime;

    @TableField("repairStartDay")
    private Date repairStartDay;

    @TableField("repairEndDay")
    private Date repairEndDay;

    @TableField("repairStart")
    private String repairStart;

    @TableField("repairEnd")
    private String repairEnd;

    @TableField("repairDept")
    private String repairDept;

    @TableField("repairManagerName")
    private String repairManagerName;

    @TableField("phoneNumber")
    private String phoneNumber;

    @TableField("repairContent")
    private String repairContent;

    @TableField("repairTime")
    private Date repairTime;

    @TableField("isRepeat")
    private String isRepeat;

    @TableField("repeatRule")
    private String repeatRule;

    @TableField("repeatTime")
    private String repeatTime;

    @TableField("cancelDay")
    private Date cancelDay;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;

}
