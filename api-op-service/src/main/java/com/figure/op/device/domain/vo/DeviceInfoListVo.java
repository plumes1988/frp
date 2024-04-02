package com.figure.op.device.domain.vo;

import lombok.Data;

/**
 * 设备列表视图对象
 * @author fsn
 */
@Data
public class DeviceInfoListVo {

    private Integer deviceId;

    private String deviceName;

    private String deviceCode;

    private String deviceType;

}
