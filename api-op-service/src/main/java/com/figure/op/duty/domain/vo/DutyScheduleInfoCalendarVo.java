package com.figure.op.duty.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 排班管理日历视图对象
 * @author fsn
 */
@Data
public class DutyScheduleInfoCalendarVo {

    /**
     * 排班任务ID
     */
    private Integer scheduleId;

    /**
     * 排班任务名称
     */
    private String scheduleName;

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
    private List<String> workerAttList;

    /**
     * 值班任务完成度（例如：3/3）
     */
    private String dutyPercent;

    /**
     * 值班任务状态列表（格式：机房巡检-已完成）
     */
    private List<String> dutyStatusList;

    /**
     * 值班记录信息条数（例如：2）
     */
    private Integer workerRecordDetailCount;

    /**
     * 值班记录信息列表
     */
    private List<String> workerRecordDetailList;

}
