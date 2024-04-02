package com.figure.op.device.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 设备实体对象
 * @author fsn
 */
@Data
@TableName("device_product_model")
public class DeviceProductModel {

    /**
     * 型号ID
     */
    @TableId(value = "modelId")
    private Integer modelId;

    /**
     * 设备类型
     */
    @TableField("deviceTypeCode")
    private String deviceTypeCode;

    /**
     * 设备子类
     */
    @TableField("deviceSubTypeCode")
    private String deviceSubTypeCode;

    /**
     * 所属厂商
     */
    @TableField("productId")
    private Integer productId;

    /**
     * 型号编码
     */
    @TableField("modelCode")
    private String modelCode;


}
