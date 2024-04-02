package com.figure.op.duty.domain.bo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 检修事件查询
 * @author fsn
 */
@Data
public class RepairEventInfoQueryBo {

    private Integer repairEventId;

    private Integer repairTaskId;

    private Integer workerId;

    private String worker;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDay;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDay;

    private Integer deviceId;

    private String deviceIds;

    private List<String> deviceIdList;

    private Integer stationId;

    private String device;

    private String station;

    private String repairAct;

    private String confirmStatus;

    private String confirmer;

    private Integer confirmerId;

    private String actStatus;

    private String isDelete;

    private String taskName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date eventDate;

    private String keyword;

    /**
     * 检修事件执行日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date eventExecuteDate;

    /**
     * 检修事件最新执行日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date executeUpdateDate;
}
