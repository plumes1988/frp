package com.figure.core.model.op;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 排班管理
 * </p>
 *
 * @author jobob
 * @since 2023-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("op_schedule_scheduler")
public class OpScheduleScheduler implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 值班员Id
     */
    @TableField("userId")
    private String userId;

    /**
     * 日期
     */
    @TableField("day")
    private LocalDate day;

    /**
     * 班次设置Id
     */
    @TableField("pid")
    private Integer pid;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;


}
