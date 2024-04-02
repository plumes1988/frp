package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_maintain_record")
public class DeviceMaintainRecord implements Serializable {

    /**
     * 设备运维ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("assetNo")
    private String assetNo;

    @TableField("principalId")
    private Integer principalId;

    @TableField("maintainTime")
    private Date maintainTime;

    @TableField("actionCode")
    private String actionCode;

    @TableField("maintainRecord")
    private String maintainRecord;

    @TableField(exist = false)
    private Integer productId;

    @TableField(exist = false)
    private Integer modelId;

    @TableField(exist = false)
    private String deviceType;

    @TableField(exist = false)
    private String deviceSubType;

    @TableField(exist = false)
    private Integer deviceId;

    @TableField(exist = false)
    private String deviceCode;

}
