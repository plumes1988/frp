package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2023-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_indicator_record_task_item")
public class DeviceIndicatorRecordTaskItem implements Serializable {


    /**
     * 主键Id
     */
    @TableId(value = "itemId", type = IdType.AUTO)
    private Integer itemId;

    /**
     * 设备Id
     */
    @TableField("deviceId")
    private Integer deviceId;

    /**
     * 设备名称
     */
    @TableField(exist = false)
    private String deviceName;

    /**
     * 指标编号
     */
    @TableField("indicatorCodes")
    private String indicatorCodes;

    /**
     * 任务Id
     */
    @TableField("taskId")
    private Integer taskId;


}
