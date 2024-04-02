package com.figure.op.duty.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 排班查询对象
 * @author fsn
 */
@Data
public class DutyScheduleInfoQueryBo {

    /**
     * 排班台站
     */
    private Integer stationId;

    /**
     * 排班计划名称
     */
    private String scheduleName;

    /**
     * 排班任务开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDay;

    /**
     * 排班任务结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDay;

}
