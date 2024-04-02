package com.figure.op.duty.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 排班管理统计查询对象
 * @author fsn
 */
@Data
public class DutyScheduleInfoStatisticsQueryBo implements Serializable {

    /**
     * 值班人员名称
     */
    private String workerName;

    /**
     * 排班开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date scheduleStartDate;

    /**
     * 排班结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date scheduleEndDate;



}
