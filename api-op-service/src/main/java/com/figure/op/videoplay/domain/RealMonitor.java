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
 * @description:摄像头实时监测对象
 * @date 2023/9/14 11:25
 */

@Data
@TableName("real_monitor_temperature")
public class RealMonitor {

    /**
     * 监测id
     */
    @TableField("monitorId")
    @TableId
    private Integer monitorId;

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
     * 部件名称
     */
    @TableField("partName")
    private String partName;

    /**
     * 插入时间
     */
    @TableField("insertTime")
    private Date insertTime;

    /**
     * 温度
     */

    @TableField("temperature")
    private Float temperature;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;
}
