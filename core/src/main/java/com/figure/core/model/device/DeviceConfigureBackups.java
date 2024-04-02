package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_configure_backups")
public class DeviceConfigureBackups implements Serializable {


    /**
     * 服务Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备编号
     */
    @TableField("deviceCode")
    private String deviceCode;

    /**
     * 备份名称
     */
    @TableField("backupsName")
    private String backupsName;

    /**
     * 备份日期
     */
    @TableField("backupsTime")
    private LocalDateTime backupsTime;

    /**
     * 备份方式：0记录配置  1文件下载
     */
    @TableField("dataType")
    private Integer dataType;

    /**
     * 备份数据  1:备份方式是记录配置时，直接存取配置信息 2:备份方式为文件下载时，存取文件保存url
     */
    @TableField("backupsData")
    private String backupsData;


}
