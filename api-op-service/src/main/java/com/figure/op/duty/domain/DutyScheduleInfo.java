package com.figure.op.duty.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 排班计划
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("duty_schedule_info")
public class DutyScheduleInfo extends BaseEntity {

    /**
     * 排班计划ID
     */
    @TableId(value = "scheduleId")
    private Integer scheduleId;

    /**
     * 排班计划名称
     */
    @TableField("scheduleName")
    private String scheduleName;

    /**
     * 台站ID
     */
    @TableField("stationId")
    private Integer stationId;

    /**
     * 值班任务ID集合
     */
    @TableField("dutyIds")
    private String dutyIds;

    /**
     * 班次属性
     */
    @TableField("scheduleAttr")
    private String scheduleAttr;

    /**
     * 开始时间
     */
    @TableField("startTime")
    private String startTime;

    /**
     * 结束时间
     */
    @TableField("endTime")
    private String endTime;

    /**
     * 开始日期
     */
    @TableField("startDay")
    private Date startDay;

    /**
     * 结束日期
     */
    @TableField("endDay")
    private Date endDay;

    /**
     * 单日时长
     */
    @TableField("time")
    private Integer time;

    /**
     * 排班总时长
     */
    @TableField("totalTime")
    private Integer totalTime;

    /**
     * 排班总天数
     */
    @TableField("totalDay")
    private Integer totalDay;

    /**
     * 作废日期
     */
    @TableField("cancelDay")
    private Date cancelDay;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;


}
