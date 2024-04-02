package com.figure.op.cameramanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.cameramanager.domain.bo.CameraInfoBo;
import com.figure.op.cameramanager.domain.vo.CameraInfoVo;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;

/**
 * @Author liqiang
 * @Date 2023/9/10 8:58
 * @Version 1.5
 */
public interface CameraInfoService {
    R insertByBo(CameraInfoBo bo);

    Page<CameraInfoVo> list(PageQuery pageQuery,String cameraName);

    R<Void> updateByBo(CameraInfoBo bo);

    Boolean deleteByCameraId(Integer cameraId);

    R getReferencePicture(Integer cameraId);

    R getAnalysisOutImage(Integer cameraId);

    R setReferenceImage(Integer cameraId);
}
