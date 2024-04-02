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
 * @since 2022-12-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_alarmType_map_template")
public class DeviceAlarmtypeMapTemplate implements Serializable {


    /**
     * 模板ID
     */
    @TableId(value = "templateId", type = IdType.AUTO)
    private Integer templateId;

    /**
     * 模板名称
     */
    @TableField("templateName")
    private String templateName;

    /**
     * 设备类型
     */
    @TableField("deviceType")
    private String deviceType;

    /**
     * 设备子类
     */
    @TableField("deviceSubType")
    private String deviceSubType;

    /**
     * 设备上报报警名称
     */
    @TableField("reportAlarmName")
    private String reportAlarmName;

    /**
     * 系统标准报警
     */
    @TableField("alarmId")
    private Integer alarmId;


}
