package com.figure.op.cameramanager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author liqiang
 * @Date 2023/9/8 16:48
 * @Version 1.5
 * 部件信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("monitor_device_info")
public class MonitorDeviceInfo extends BaseEntity {

    /**
     * 部件主键
     */
    @TableField("deviceId")
    @TableId
    private Integer deviceId;

    /**
     * 设备编号
     */
    @TableField("deviceCode")
    private String deviceCode;

    /**
     * 设备名称
     */
    @TableField("deviceName")
    private String deviceName;

    /**
     * 站点ID
     */
    @TableField("frontId")
    private Integer frontId;

    /**
     * 机房ID
     */
    @TableField("locateId")
    private Integer locateId;

    /**
     * 图片url
     */
    @TableField("imageUrl")
    private String imageUrl;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;
}
