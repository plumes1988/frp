package com.figure.op.device.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("device_type_info")
public class DeviceTypeInfo extends BaseEntity {

    @TableId(value = "deviceTypeId")
    private Integer deviceTypeId;

    @TableField("parentId")
    private Integer parentId;

    @TableField("deviceTypeName")
    private String deviceTypeName;

    @TableField("sort")
    private String sort;

    @TableLogic
    private Integer isDelete;

}
