package com.figure.op.cameramanager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author liqiang
 * @Date 2023/9/8 15:55
 * @Version 1.5
 * 设备部件信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("part_info")
public class PartInfo extends BaseEntity {

    /**
     * 部件主键
     */
    @TableField("partId")
    @TableId
    private Integer partId;

    /**
     * 部件名称
     */
    @TableField("partName")
    private String partName;

    /**
     * 部件编号
     */
    @TableField("partCode")
    private String partCode;

    /**
     * 关联设备ID
     */
    @TableField("deviceCode")
    private String deviceCode;


    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;

}
