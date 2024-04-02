package com.figure.op.cameramanager.service;

import com.figure.op.cameramanager.domain.bo.PartInfoBo;
import com.figure.op.cameramanager.domain.vo.PartInfoVo;
import com.figure.op.common.domain.R;

import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/8 16:38
 * @Version 1.5
 */
public interface PartInfoService {

    R insertByBo(PartInfoBo partInfoBo);

    List<PartInfoVo> list(String deviceCode);

    R updateByBo(PartInfoBo bo);

    Boolean deleteByPartCode(Integer partId);
}
