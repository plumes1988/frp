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
 * 值班签到表
 * </p>
 *
 * @author jobob
 * @since 2023-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("op_schedule_check")
public class OpScheduleCheck implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户Id
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 签到时间
     */
    @TableField("checkTime")
    private LocalDateTime checkTime;


}
