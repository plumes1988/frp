package com.figure.op.device.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备实体对象
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("device_info")
public class DeviceInfo extends BaseEntity {

    /**
     * 设备ID
     */
    @TableId(value = "deviceId")
    private Integer deviceId;

    /**
     * 设备名称
     */
    @TableField("deviceName")
    private String deviceName;

    /**
     * 设备编号
     */
    @TableField("deviceCode")
    private String deviceCode;

    /**
     * 设备归类
     */
    @TableField("deviceType")
    private String deviceType;

    /**
     * 设备子类
     */
    @TableField("deviceSubType")
    private String deviceSubType;

    /**
     * 所属监测站ID
     */
    @TableField("monitorStationId")
    private Integer monitorStationId;

    /**
     * 关联采集点
     */
    @TableField("logicPositionId")
    private Integer logicPositionId;

    /**
     * 关联采集点
     */
    @TableField("modelId")
    private Integer modelId;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;


}
