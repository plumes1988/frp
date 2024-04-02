package com.figure.op.cameramanager.service;

import com.figure.op.cameramanager.domain.ThermalImageInfo;
import com.figure.op.cameramanager.domain.bo.ThermalImageInfoBo;
import com.figure.op.common.domain.R;

import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/14 9:31
 * @Version 1.5
 */
public interface ThermalImageInfoService {

    ThermalImageInfo list(String cameraId);

    R updateInfo(ThermalImageInfoBo bo);

    List<ThermalImageInfo> queryMonitorCron();
    Integer getCornByCarameId(Integer id);
    Integer getAnasysCornByCarameId(Integer id);

}
