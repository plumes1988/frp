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
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_locate")
public class DeviceLocate implements Serializable {


    /**
     * 机房ID
     */
    @TableId(value = "locateId", type = IdType.AUTO)
    private Integer locateId;

    /**
     * 前端站点
     */
    @TableField("frontId")
    private Integer frontId;

    /**
     * 机房名称
     */
    @TableField("locateName")
    private String locateName;

    /**
     * 机房描述
     */
    @TableField("remark")
    private String remark;


}
