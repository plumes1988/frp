package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_history_indicator")
public class DeviceHistoryIndicator implements Serializable {


    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备类别
     */
    @TableField("deviceId")
    private Integer deviceId;

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
     * 实时数据
     */
    @TableField("indicatorValue")
    private String indicatorValue;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private Date updateTime;

    /**
     * 指标值的真实采集时间
     */
    @TableField("collectTime")
    private String collectTime;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private String createTime;

    /**
     * 获取实时指标时间
     */
    @TableField("receiveTime")
    private Date receiveTime;

    @TableField(exist = false)
    private Integer alarmStatus;

    /**
     * 设备名称
     */
    @TableField(exist = false)
    private String deviceName;


    /**
     * 指标名称
     */
    @TableField(exist = false)
    private String indicatorName;


}
