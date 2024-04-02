package com.figure.op.cameramanager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 * @Author liqiang
 * @Date 2023/9/12 13:49
 * @Version 1.5
 */
@Data
@TableName("camera_area_info")
public class CameraAreaInfo extends BaseEntity {
    /**
     * 摄像机区域主键
     */
    @TableField("cameraAreaId")
    @TableId
    private Integer cameraAreaId;

    /**
     * 摄像机主键
     */
    @TableField("cameraId")
    private Integer cameraId;


    /**
     * 摄像机规则区域id
     */
    @TableField("ruleAreaId")
    private Integer ruleAreaId;

    /**
     * 摄像机区域名称
     */
    @TableField("areaName")
    private String areaName;

    /**
     * 绑定部件编码
     */
    @TableField("partCode")
    private String partCode;

    /**
     * 绑定部件名称
     */
    @TableField("partName")
    private String partName;


    /**
     * 设备名称
     */
    @TableField("deviceName")
    private String deviceName;


    /**
     * 报警阈值
     */
    @TableField("alarmThreshold")
    private float alarmThreshold;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;
}
