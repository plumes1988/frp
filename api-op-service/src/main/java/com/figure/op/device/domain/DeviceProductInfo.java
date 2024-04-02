package com.figure.op.device.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备实体对象
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("device_product_info")
public class DeviceProductInfo extends BaseEntity {

    /**
     * 厂商ID
     */
    @TableId(value = "productId")
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

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;


}
