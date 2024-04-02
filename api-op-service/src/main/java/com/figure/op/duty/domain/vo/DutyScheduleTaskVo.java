package com.figure.op.duty.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 排班任务视图对象
 * @author fsn
 */
@Data
public class DutyScheduleTaskVo {

    /**
     * 台站ID
     */
    private Integer stationId;

    /**
     * 台站名称
     */
    private String stationName;

    /**
     * 排班任务ID
     */
    private Integer scheduleTaskId;

    /**
     * 排班计划ID
     */
    private Integer scheduleId;

    /**
     * 排班计划名称
     */
    private String scheduleName;

    /**
     * 排班计划-班次属性
     */
    private String scheduleAttr;

    /**
     * 排班计划-班次属性名称
     */
    private String scheduleAttrName;

    /**
     * 排班日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date scheduleDate;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

}
