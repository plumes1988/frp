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
 * 摄像机信息
 */
@Data
@TableName("camera_info")
public class CameraInfo extends BaseEntity {

    /**
     * 摄像机主键
     */
    @TableField("cameraId")
    @TableId
    private Integer cameraId;

    /**
     * 摄像头编码(设备编号)
     */
    @TableField("cameraCode")
    private String cameraCode;

    /**
     * 摄像机名称
     */
    @TableField("cameraName")
    private String cameraName;

    /**
     * 摄像机类型编号
     */
    @TableField("cameraTypeCode")
    private String cameraTypeCode;

    /**
     * 摄像机ip
     */
    @TableField("ip")
    private String ip;

    /**
     * 摄像机端口
     */
    @TableField("port")
    private Integer port;

    /**
     * 摄像机通道
     */
    @TableField("channelNo")
    private String channelNo;

    /**
     * 摄像机用户名
     */
    @TableField("username")
    private String username;

    /**
     * 摄像机用户名
     */
    @TableField("password")
    private String password;


    /**
     * 拍摄设备编号
     */
    @TableField("deviceCode")
    private String deviceCode;

    /**
     * 机房id
     */
    @TableField("locateId")
    private Integer locateId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;

}
