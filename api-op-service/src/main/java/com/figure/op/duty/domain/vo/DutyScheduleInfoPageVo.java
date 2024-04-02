package com.figure.op.duty.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 排班计划分页视图对象
 * @author fsn
 */
@Data
public class DutyScheduleInfoPageVo {

    /**
     * 排班计划ID
     */
    private Integer scheduleId;

    /**
     * 排班计划名称
     */
    private String scheduleName;

    /**
     * 值班任务ID集合
     */
    private String dutyIds;

    /**
     * 值班任务名称集合
     */
    private String dutyNames;

    /**
     * 班次属性
     */
    private String scheduleAttr;

    /**
     * 班次属性名称
     */
    private String scheduleAttrName;

    /**
     * 排班台站名称
     */
    private String frontName;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 开始日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDay;

    /**
     * 结束日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDay;

    /**
     * 作废时间
     */
    private Date cancelDay;

    /**
     * 创建人名称
     */
    private String createUsername;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
