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
 * 品牌管理表
 * </p>
 *
 * @author jobob
 * @since 2023-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_product_info")
public class DeviceProductInfo implements Serializable {


    /**
     * 厂商ID
     */
    @TableId(value = "productId", type = IdType.AUTO)
    private Integer productId;

    /**
     * 厂商名称
     */
    @TableField("productName")
    private String productName;

    /**
     * 官网地址
     */
    @TableField("website")
    private String website;

    /**
     * 技术联系人电话
     */
    @TableField("tecContactPhone")
    private String tecContactPhone;

    /**
     * 销售联系人电话
     */
    @TableField("salesContactPhone")
    private String salesContactPhone;


}
