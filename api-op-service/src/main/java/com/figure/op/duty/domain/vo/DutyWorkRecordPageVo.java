package com.figure.op.duty.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 值班记录分页视图对象
 * @author fsn
 */
@Data
public class DutyWorkRecordPageVo {

    /**
     * 值班记录ID
     */
    private Integer workRecordId;

    /**
     * 排班计划ID
     */
    private Integer scheduleId;

    /**
     * 排班计划名称
     */
    private String scheduleName;

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
     * 班次属性
     */
    private String scheduleAttr;

    /**
     * 班次属性名称
     */
    private String scheduleAttrName;

    /**
     * 值班任务ID
     */
    private Integer dutyId;

    /**
     * 值班任务名称
     */
    private String dutyName;

    /**
     * 值班任务类型
     */
    private String dutyType;

    /**
     * 记录人员ID集合
     */
    private String workerIds;

    /**
     * 记录人员名称集合
     */
    private String workerNames;

    /**
     * 记录时间
     */
    private Date recordTime;

    /**
     * 记录信息
     */
    private String recordDesc;

    /**
     * 资料上传路径
     */
    private String attachPath;

    /**
     * 执行人员ID
     */
    private Integer actWorkerId;

    /**
     * 执行人员名称
     */
    private String actWorkerName;

    /**
     * 执行结果（0未完成 1已完成）
     */
    private String actResult;

    /**
     * 执行时间
     */
    private Date actTime;

    /**
     * 详细描述
     */
    private String detailDesc;

    /**
     * 值班记录标题
     */
    private String workTitle;

    /**
     * 记录类型( 1排班计划自动生成 2手动添加）
     */
    private String cate;
}
