package com.figure.op.cameramanager.service;

import com.figure.op.cameramanager.domain.CameraAreaInfo;
import com.figure.op.cameramanager.domain.bo.CameraAreInfoBo;
import com.figure.op.cameramanager.domain.vo.RuleInfoVo;
import com.figure.op.common.domain.R;

import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/12 13:55
 * @Version 1.5
 */

public interface CameraAreService {
    List<RuleInfoVo> listAreaName(Integer cameraId);

    R addCameraInfo(CameraAreInfoBo bo);

    Boolean deleteByCameraAreaId(Integer cameraAreaId);

    R<Void> updateByBo(CameraAreInfoBo bo);

    List<CameraAreaInfo> listCameraAreaInfo(Integer cameraId);
}
