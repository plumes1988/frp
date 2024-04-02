package com.figure.core.model.op;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 交接班
 * </p>
 *
 * @author jobob
 * @since 2023-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("op_schedule_handover")
public class OpScheduleHandover implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 排班Id
     */
    @TableField("schedulerId")
    private Integer schedulerId;

    /**
     * 各项状况
     */
    @TableField("stateChecks")
    private String stateChecks;

    /**
     * 值班日志
     */
    @TableField("log")
    private String log;


    /**
     * 接班排班Id
     */
    @TableField("takeOverSchedulerId")
    private String takeOverSchedulerId;


    /**
     * 接班备注
     */
    @TableField("mark")
    private String mark;

    /**
     * 状态
     */
    @TableField("state")
    private String state;

    /**
     * 交班时间
     */
    @TableField("turnOverDateTime")
    private LocalDateTime turnOverDateTime;


    /**
     * 接班时间
     */
    @TableField("takeOverDateTime")
    private LocalDateTime takeOverDateTime;


    @TableField(exist = false)
    private String operate;


}
