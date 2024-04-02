package com.figure.op.cameramanager.service;

import com.figure.op.cameramanager.domain.vo.DeviceLocateVo;

import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/8 20:41
 * @Version 1.5
 */
public interface DeviceLocateService {
    List<DeviceLocateVo> queryDeviceLocateByFrontId(Integer frontId);

    List<DeviceLocateVo> list();
}
