package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author jobob
 * @since 2022-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_indicator_param")
public class DeviceIndicatorParam implements Serializable {

    public static Integer SWITCH_VARIABLE =  0;

    public static Integer NUMBER_VARIABLE =  1;

    public static Integer STATUS_VARIABLE =  2;

    public static Integer TEXT_VARIABLE =  3;

    public static String UNIT_TAG =  "unit_tag";

    public static Integer IS_CRITICAL_YES = 1;

    public static Integer NOT_CRITICAL = 0;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备类别
     */
    @TableField("deviceType")
    private String deviceType;

    /**
     * 设备子类
     */
    @TableField("deviceSubType")
    private String deviceSubType;

    /**
     * 指标名称
     */
    @TableField("indicatorName")
    private String indicatorName;

    /**
     * 指标标签
     */
    @TableField("indicatorTag")
    private String indicatorTag;

    /**
     * 指标编号
     */
    @TableField("indicatorCode")
    private String indicatorCode;

    /**
     * 指标单位
     */
    @TableField("unit")
    private String unit;

    /**
     * 数据类型(0开关量、1模拟量、2状态值、3文本信息描述)
     */
    @TableField("dataType")
    private Integer dataType;

    /**
     * 模拟量精度
     */
    @TableField("accuracy")
    private Integer accuracy;

    /**
     * 是否启用报警
     */
    @TableField("enableAlarm")
    private Integer enableAlarm;

    /**
     * 是否关键指标 0:否；1:是
     */
    @TableField("isCritical")
    private Integer isCritical;

    /**
     * 状态值关联数据字典参数类型
     */
    @TableField("statusParaType")
    private String statusParaType;

    /**
     * 值越高、级别越高、排序越靠前
     */
    @TableField("level")
    private Integer level;

    /**
     * 描述
     */
    @TableField("remark")
    private String remark;

    @TableField(exist = false)
    private List<DeviceIndicatorParamRule> rules =  new ArrayList<>();

    @TableField(exist = false)
    private List<DeviceDataAnalysisStrategy> deviceDataAnalysisStrategys =  new ArrayList<>();

}

