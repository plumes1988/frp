package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 设备资产管理表
 * </p>
 *
 * @author jobob
 * @since 2022-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_asset")
public class DeviceAsset implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 资产编号
     */
    @TableField("assetNo")
    private String assetNo;


    @TableField(exist = false)
    private String oldAssetNo;

    /**
     * 设备序列号
     */
    @TableField("serialNo")
    private String serialNo;

    /**
     * 设备归类：信源监测、广电专业、通用服务、传感设备
     */
    @TableField("deviceType")
    private String deviceType;

    /**
     * 设备子类：监测板卡、多画面、编码器。。。。
     */
    @TableField("deviceSubType")
    private String deviceSubType;

    /**
     * 生产厂商
     */
    @TableField("productId")
    private Integer productId;

    /**
     * 设备型号
     */
    @TableField("modelId")
    private Integer modelId;

    /**
     * 购买日期
     */
    @TableField("buyDate")
    private Date buyDate;

    /**
     * 上线日期
     */
    @TableField("useDate")
    private Date useDate;

    /**
     * 最近维护时间
     */
    @TableField("lastMaintainTime")
    private Date lastMaintainTime;

    /**
     * 运维状态 0：使用中 1：维护中 2：停用. 3：备件 4：报废
     */
    @TableField("status")
    private Integer status;

    /**
     * 设备图片
     */
    @TableField("imgURL")
    private String imgURL;

    /**
     * 额定功率
     */
    @TableField("ratedPower")
    private String ratedPower;

    /**
     * 重量
     */
    @TableField("weight")
    private Double weight;

    /**
     * 3D建模ID
     */
    @TableField("threeDModelId")
    private String threeDModelId;

    /**
     * 备注信息
     */
    @TableField("remark")
    private String remark;

    /**
     * 购买价格
     */
    @TableField("purchasePrice")
    private Double purchasePrice;


}
