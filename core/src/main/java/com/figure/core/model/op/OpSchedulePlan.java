package com.figure.core.model.op;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * <p>
 * 班次设置
 * </p>
 *
 * @author jobob
 * @since 2023-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("op_schedule_plan")
public class OpSchedulePlan implements Serializable {


    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    /**
     * 班次名称
     */
    @TableField("planName")
    private String planName;

    /**
     * 排班颜色
     */
    @TableField("color")
    private String color;

    /**
     * 班次类型
     */
    @TableField("planType")
    private String planType;

    /**
     * 开始时间
     */
    @TableField("startTime")
    private LocalTime startTime;

    /**
     * 结束时间
     */
    @TableField("endTime")
    private LocalTime endTime;


}
