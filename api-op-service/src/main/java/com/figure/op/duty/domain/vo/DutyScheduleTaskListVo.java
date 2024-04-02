package com.figure.op.duty.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 排班任务列表视图对象
 * @author fsn
 */
@Data
public class DutyScheduleTaskListVo {

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

    /**
     * 值班人员考勤完成度（例如：2/3）
     */
    private String workerPercent;

    /**
     * 值班人员考勤列表（格式：人员-考勤状态）
     */
    private List<DutyScheduleTaskWorkerAttVo> workerAttList;

    /**
     * 值班记录完成度（排班计划自动生成）（例如：3/3）
     */
    private String workerRecordPercent;

    /**
     * 值班记录列表（排班计划自动生成）
     */
    private List<DutyScheduleTaskWorkStatusVo> workerRecordList1;

    /**
     * 值班记录信息条数（手动添加）（例如：2）
     */
    private String workerRecordCount;

    /**
     * 值班记录列表（手动添加）
     */
    private List<DutyScheduleTaskWorkStatusVo> workerRecordList2;

}
