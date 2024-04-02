package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 设备 与 逻辑频道（播出系统）关联表
 * </p>
 *
 * @author jobob
 * @since 2023-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_logicchannel_rel")
public class DeviceLogicchannelRel implements Serializable {


    /**
     * 设备编号
     */
    @TableField("deviceCode")
    private String deviceCode;

    /**
     * 逻辑频道（播出系统）编号
     */
    @TableField("channelCode")
    private String channelCode;


    @TableField(exist = false)
    private String channelCodes;
}
