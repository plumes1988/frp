package com.figure.op.cameramanager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.cameramanager.domain.CameraTypeInfo;
import com.figure.op.cameramanager.domain.bo.CameraTypeInfoBo;
import com.figure.op.cameramanager.domain.bo.PartInfoBo;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;

/**
 * @Author liqiang
 * @Date 2023/9/9 21:37
 * @Version 1.5
 */
public interface CameraTypeInfoService {
    R insertByBo(CameraTypeInfoBo bo);

    Page<CameraTypeInfo> list(PageQuery pageQuery);

    Boolean updateByBo(CameraTypeInfoBo bo);

    R deleteByCameraTypeCode(String  cameraTypeCode);
}
