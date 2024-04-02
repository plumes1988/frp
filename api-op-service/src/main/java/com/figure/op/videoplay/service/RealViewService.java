package com.figure.op.videoplay.service;

import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.cameramanager.domain.bo.CameraInfoBo;
import com.figure.op.cameramanager.domain.vo.CameraInfoVo;
import com.figure.op.common.domain.R;
import com.figure.op.videoplay.domain.vo.RealPartThermometry;

import java.util.List;

public interface RealViewService {
    List<CameraInfo> getCameraList();
    CameraInfo getCameraInfoById(Integer id);
    String getRealPlayUrl(CameraInfoBo bo);

    R stopStream(String cameraId);

    List<RealPartThermometry> getRealThermometry(CameraInfoBo bo);

    R getRealExceedCamera();
}
