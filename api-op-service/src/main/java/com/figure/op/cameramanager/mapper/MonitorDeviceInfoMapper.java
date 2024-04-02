package com.figure.op.cameramanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.cameramanager.domain.MonitorDeviceInfo;
import com.figure.op.cameramanager.domain.vo.MonitorDeviceInfoVo;

import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/8 16:56
 * @Version 1.5
 */
public interface MonitorDeviceInfoMapper extends BaseMapper<MonitorDeviceInfo> {
    Page<MonitorDeviceInfoVo> queryList(Page<MonitorDeviceInfoVo> build,String deviceName);
}
