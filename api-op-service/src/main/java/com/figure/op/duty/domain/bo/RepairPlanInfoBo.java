package com.figure.op.duty.domain.bo;

import com.figure.op.common.domain.BaseEntity;
import com.figure.op.common.validate.AddGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 检修计划增改对象
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RepairPlanInfoBo extends BaseEntity {

    /**
     * 检修计划名称
     */
    @NotBlank(message = "检修计划名称不能为空", groups = { AddGroup.class })
    private String repairPlanName;

    /**
     * 检修类型
     */
    @NotBlank(message = "检修类型不能为空", groups = { AddGroup.class })
    private String repairType;

    /**
     * 检修台站
     */
    @NotNull(message = "检修台站不能为空", groups = { AddGroup.class })
    private Integer repairStation;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空", groups = { AddGroup.class })
    private Date repairStartTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空", groups = { AddGroup.class })
    private Date repairEndTime;

    /**
     * 检修部门ID
     */
    @NotNull(message = "检修部门ID不能为空", groups = { AddGroup.class })
    private Integer repairDeptId;

    /**
     * 负责人ID
     */
    @NotNull(message = "负责人ID不能为空", groups = { AddGroup.class })
    private Integer repairManagerId;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空", groups = { AddGroup.class })
    private String phoneNumber;

    /**
     * 是否重复
     */
    @NotBlank(message = "是否重复不能为空", groups = { AddGroup.class })
    private String isRepeat;

    /**
     * 重复规则
     */
    private String repeatRule;

    /**
     * 重复时间
     */
    private String repeatTime;

    /**
     * 检修内容
     */
    @NotBlank(message = "检修内容不能为空", groups = { AddGroup.class })
    private String repairContent;

}
