package com.figure.op.cameramanager.service;

import com.figure.op.cameramanager.domain.vo.FrontStationInfoVo;

import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/8 20:41
 * @Version 1.5
 */
public interface FrontStationInfoService {
    List<FrontStationInfoVo> queryFrontStation();
}
