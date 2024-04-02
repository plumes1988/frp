package com.figure.op.duty.domain.bo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 值班记录查询对象
 * @author fsn
 */
@Data
public class DutyWorkRecordQueryBo {

    /**
     * 排班计划ID
     */
    private Integer scheduleId;

    /**
     * 台站ID
     */
    private Integer stationId;

    /**
     * 排班计划名称
     */
    private String scheduleName;

    /**
     * 排班任务ID
     */
    private Integer scheduleTaskId;

    /**
     * 排班任务ID集合
     */
    private String scheduleTaskIds;

    /**
     * 记录人员
     */
    private String workerName;

    /**
     * 记录人员ID
     */
    private Integer workerId;

    /**
     * 执行结果
     */
    private String actResult;

    /**
     * 班次属性
     */
    private String scheduleAttr;

    /**
     * 记录类型( 1排班计划自动生成 2手动添加 ）
     */
    private String cate;

    /**
     * 记录开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDay;

    /**
     * 记录结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDay;

    /**
     * 执行开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date actStartTime;

    /**
     * 执行结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date actEndTime;

}
