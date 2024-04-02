package com.figure.op.cameramanager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author liqiang
 * @Date 2023/9/8 17:08
 * @Version 1.5
 * 机房信息
 */
@Data
@TableName("device_locate")
public class DeviceLocate{
    /**
     * 机房ID
     */
    @TableField("locateId")
    private Integer locateId;


    /**
     * 站点Id
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
