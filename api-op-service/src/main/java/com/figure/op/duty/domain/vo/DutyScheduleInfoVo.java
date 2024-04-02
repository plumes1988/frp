package com.figure.op.duty.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 详情视图对象
 * @author fsn
 */
@Data
public class DutyScheduleInfoVo {

    private Integer scheduleId;

    private String scheduleName;

    private Integer dutyId;

    private String dutyName;

    private String scheduleAttr;

    private String startTime;

    private String endTime;

    private Date startDay;

    private Date endDay;

    private Integer workerId;

    private String creator;

    private String workerName;

}
