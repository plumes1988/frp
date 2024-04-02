package com.figure.op.duty.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 品牌增改对象
 * @author fsn
 */
@Data
public class RepairTaskInfoListVo implements Serializable {

    private Integer repairTaskId;

    private Integer repairPlanId;

    private String taskName;

    private Integer repairStation;

    private Date taskStartTime;

    private Date taskEndTime;

    private Integer taskReviewerId;

    private String taskReviewerName;

    private String taskDesc;

    private String taskDuration;

    private String taskStatus;

    private String reason;

    private String repairStationName;

}
