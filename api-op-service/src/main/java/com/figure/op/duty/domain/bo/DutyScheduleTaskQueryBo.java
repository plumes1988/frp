package com.figure.op.duty.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 排班任务查询对象
 * @author fsn
 */
@Data
public class DutyScheduleTaskQueryBo implements Serializable {

    /**
     * 排班台站
     */
    private Integer stationId;

    /**
     * 排班计划名称
     */
    private String scheduleName;

    /**
     * 开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDay;

    /**
     * 结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDay;


}
