package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_type")
public class DeviceType implements Serializable {


    /**
     * 设备类型ID
     */
    @TableId(value = "typeId", type = IdType.AUTO)
    private Integer typeId;

    /**
     * 类型编号
     */
    @TableField("typeCode")
    private String typeCode;

    /**
     * 父级类型编号
     */
    @TableField("parentTypeCode")
    private String parentTypeCode;

    /**
     * 类型级别
     */
    @TableField("leve")
    private String leve;

    /**
     * 类型名称
     */
    @TableField("typeName")
    private String typeName;

    /**
     * 描述
     */
    @TableField("description")
    private String description;


}
