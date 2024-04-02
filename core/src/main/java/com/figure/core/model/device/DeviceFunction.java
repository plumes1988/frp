package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 设备功能
 * </p>
 *
 * @author jobob
 * @since 2023-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_function")
public class DeviceFunction implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 功能名
     */
    @TableField("name")
    private String name;

    /**
     * 设备编号
     */
    @TableField("deviceCode")
    private String deviceCode;

    /**
     * 指标编号
     */
    @TableField("indicatorCode")
    private String indicatorCode;

    /**
     * 参数模版
     */
    @TableField("indicatorValue")
    private String indicatorValue;


}
