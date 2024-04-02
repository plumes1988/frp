package com.figure.op.duty.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 排班管理增改对象
 * @author fsn
 */
@Data
public class DutyScheduleInfoBo implements Serializable {

    /**
     * 排班计划ID
     */
    @NotNull(message = "排班计划ID不能为空", groups = { EditGroup.class })
    private Integer scheduleId;

    /**
     * 排班计划名称
     */
    @NotBlank(message = "排班计划名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String scheduleName;

    /**
     * 台站ID
     */
    @NotNull(message = "台站ID不能为空", groups = { AddGroup.class })
    private Integer stationId;

    /**
     * 值班任务ID集合
     */
    @NotBlank(message = "值班任务ID集合不能为空", groups = { AddGroup.class, EditGroup.class })
    private String dutyIds;

    /**
     * 班次属性
     */
    @NotBlank(message = "班次属性不能为空", groups = { AddGroup.class, EditGroup.class })
    private String scheduleAttr;

    /**
     * 开始时间
     */
    @NotBlank(message = "开始时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String startTime;

    /**
     * 结束时间
     */
    @NotBlank(message = "结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String endTime;

    /**
     * 开始日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date startDay;

    /**
     * 结束日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "结束日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date endDay;

}
