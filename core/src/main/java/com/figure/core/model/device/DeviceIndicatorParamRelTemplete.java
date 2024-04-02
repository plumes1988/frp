package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 设备关联指标模版表
 * </p>
 *
 * @author jobob
 * @since 2023-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_indicator_param_rel_templete")
public class DeviceIndicatorParamRelTemplete implements Serializable {


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备类型
     */
    @TableField("deviceTypeCode")
    private String deviceTypeCode;

    /**
     * 设备子类
     */
    @TableField("deviceSubTypeCode")
    private String deviceSubTypeCode;

    /**
     * 设备厂商
     */
    @TableField("productId")
    private Integer productId;

    /**
     * 设备型号
     */
    @TableField("modelId")
    private Integer modelId;

    @TableField(exist = false)
    private String modelIds;

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
     * 报警状态
     */
    @TableField("alarmStatus")
    private Integer alarmStatus;

    /**
     * 启用监测
     */
    @TableField("enableMonitor")
    private Integer enableMonitor;

    /**
     * 数据采集频率
     */
    @TableField("monitorInterval")
    private String monitorInterval;

    /**
     * 启用报警
     */
    @TableField("enableAlarm")
    private Integer enableAlarm;

    /**
     * 阈值模板ID
     */
    @TableField("indicatorRuleIds")
    private String indicatorRuleIds;

    /**
     * 交互模式
     */
    @TableField("communicateMode")
    private Integer communicateMode;

    /**
     * 读取交互参数
     */
    @TableField("responseParams")
    private String responseParams;

    /**
     * 读取数据处理
     */
    @TableField("responseParseScript")
    private String responseParseScript;

    /**
     * 启用设置:0否;1是
     */
    @TableField("enableSetting")
    private Integer enableSetting;

    /**
     * 设置交互参数
     */
    @TableField("requestParams")
    private String requestParams;

    /**
     * 设置参数处理
     */
    @TableField("requestParseScript")
    private String requestParseScript;

    /**
     * 校验模式
     */
    @TableField("checkMode")
    private String checkMode;

    /**
     * 历史数据记录 0：不记录 1：实时记录 2：记录变更
     */
    @TableField("recordMode")
    private Integer recordMode;

    /**
     * 数据变化阈值，只在实时数据变化超过该值时记录
     */
    @TableField("changeThreshold")
    private Integer changeThreshold;

    /**
     * 历史数据保存时长，单位：日
     */
    @TableField("retentionTime")
    private Integer retentionTime;

    /**
     * 指标设置执行类
     */
    @TableField("requestClass")
    private String requestClass;

    /**
     * 指标读取执行类
     */
    @TableField("responseClass")
    private String responseClass;

    /**
     * 指标接收、更新时间
     */
    @TableField("updateTime")
    private LocalDateTime updateTime;

    /**
     * 指标采集服务采集时间
     */
    @TableField("collectTime")
    private String collectTime;

    /**
     * 指标数据类型
     */
    @TableField("snmpDataType")
    private String snmpDataType;

}
