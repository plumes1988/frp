package com.figure.op.cameramanager.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 * @Author liqiang
 * @Date 2023/9/9 21:28
 * @Version 1.5
 * 摄像机类型
 */
@Data
@TableName("camera_type_info")
public class CameraTypeInfo extends BaseEntity {

    /**
     * 摄像机类型编码
     */
    @TableField("cameraTypeId")
    @TableId
    private Integer cameraTypeId;

    /**
     * 摄像机类型编码
     */
    @TableField("cameraTypeCode")
    private String cameraTypeCode;

    /**
     * 摄像机类型名称
     */
    @TableField("cameraTypeName")
    private String cameraTypeName;

    /**
     * 摄像机类型描述
     */
    @TableField("remark")
    private String remark;


    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;

}
