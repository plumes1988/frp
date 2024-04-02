package com.figure.op.videoplay.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author sdt
 * @version 1.0
 * @description:TODO
 * @date 2023/9/14 11:53
 */
@Data
@TableName("temperature_warning_record")
public class WarningRecord {

    /**
     * 报警键
     */
    @TableField("recordId")
    @TableId
    private Integer recordId;

    /**
     * 摄像机主键
     */
    @TableField("cameraId")
    private Integer cameraId;


    /**
     * 摄像机名称
     */
    @TableField("cameraName")
    private String cameraName;


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
     * 设备名称
     */
    @TableField("deviceName")
    private String deviceName;

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
     * 记录状态: 0(默认)：未处理;1:确认；2：误报
     */
    @TableField("status")
    private float status;

    /**
     * 开始时间
     */
    @TableField("startTime")
    private Date startTime;
    /**
     * 结束时间
     */
    @TableField("endTime")
    private Date endTime;
    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;
}
