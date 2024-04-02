package com.figure.core.model.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.figure.core.base.BaseModel;
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
@TableName("device_cabinet")
public class DeviceCabinet  implements Serializable{


    /**
     * 机柜ID
     */
    @TableId(value = "cabinetId", type = IdType.AUTO)
    private Integer cabinetId;

    /**
     * 机柜名称/编号
     */
    @TableField("cabinetName")
    private String cabinetName;

    /**
     * 机柜总空间
     */
    @TableField("storage")
    private String storage;

    /**
     * 机柜占用空间
     */
    @TableField("restStorage")
    private String restStorage;

    /**
     * 所属机房ID
     */
    @TableField("locateId")
    private Integer locateId;

    /**
     * 机柜描述
     */
    @TableField("description")
    private String description;


}
