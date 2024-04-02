package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 型号管理表
 * </p>
 *
 * @author jobob
 * @since 2023-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_product_model")
public class DeviceProductModel implements Serializable {


    /**
     * 型号ID
     */
    @TableId(value = "modelId", type = IdType.AUTO)
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

    /**
     * 设备图片
     */
    @TableField("img")
    private String img;

    /**
     * 资料存储地址
     */
    @TableField("files")
    private String files;


}
