package com.figure.op.cameramanager.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author liqiang
 * @Date 2023/9/9 10:08
 * @Version 1.5
 */
@Data
public class MonitorDeviceInfoVo {
    /**
     * 设备主键
     */
    private Integer deviceId;


    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备编号
     */
    private String deviceCode;

    /**
     * 站点ID
     */
    private Integer frontId;

    /**
     * 站点名称
     */
    private String frontName;

    /**
     * 机房ID
     */
    private Integer locateId;

    /**
     * 机房名称
     */
    private String locateName;

    /**
     * 图片url
     */
    @TableField("imageUrl")
    private String imageUrl;
}
