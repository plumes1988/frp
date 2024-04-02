package com.figure.op.cameramanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.cameramanager.domain.vo.CameraInfoVo;

/**
 * @Author liqiang
 * @Date 2023/9/10 8:59
 * @Version 1.5
 */
public interface CameraInfoMapper extends BaseMapper<CameraInfo> {
    Page<CameraInfoVo> queryList(Page<Object> build, String cameraName);
}
