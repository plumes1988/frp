package com.figure.op.cameramanager.domain.vo;


import lombok.Data;

/**
 * @Author liqiang
 * @Date 2023/9/10 10:24
 * @Version 1.5
 */
@Data
public class CameraInfoVo {
    private Integer cameraId;
    private String cameraCode;
    private String cameraName;
    private String cameraTypeCode;
    private String cameraTypeName;
    private String ip;
    private Integer port;
    private String channelNo;
    private String username;
    private String password;
    private String deviceCode;
    /**
     * 监测设备名称
     */
    private String deviceName;
    /**
     * 监测设备主键
     */
    private Integer deviceId;
    private Integer locateId;
    /**
     * 机房名称
     */
    private String locateName;
    private String remark;
}
