package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 巡机抄录任务
 * </p>
 *
 * @author jobob
 * @since 2023-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_indicator_record_task")
public class DeviceIndicatorRecordTask extends BaseModel implements Serializable {


    /**
     * 任务Id
     */
    @TableId(value = "taskId", type = IdType.AUTO)
    private Integer taskId;

    /**
     * 任务名称
     */
    @TableField("taskName")
    private String taskName;

    /**
     * 开始日期
     */
    @TableField("startDate")
    private LocalDate startDate;

    /**
     * 结束日期
     */
    @TableField("endDate")
    private LocalDate endDate;

    /**
     * 执行时间点
     */
    @TableField("excuteTimes")
    private String excuteTimes;

    /**
     * 模式: 1 天 2 周 3 月
     */
    @TableField("mode")
    private String mode;

    /**
     * 周几
     */
    @TableField("weeks")
    private String weeks;

    /**
     * 几号
     */
    @TableField("days")
    private String days;


    @TableField(exist = false)
    private List items;

}
