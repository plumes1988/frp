package com.figure.op.duty.domain.vo;

import lombok.Data;

/**
 * 排班计划列表视图对象
 * @author fsn
 */
@Data
public class DutyScheduleInfoListVo {

    /**
     * 排班计划ID
     */
    private Integer scheduleId;

    /**
     * 排班计划名称
     */
    private String scheduleName;

}
