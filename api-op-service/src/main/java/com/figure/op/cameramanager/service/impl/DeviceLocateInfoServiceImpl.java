package com.figure.op.cameramanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.op.cameramanager.domain.DeviceLocate;
import com.figure.op.cameramanager.domain.vo.DeviceLocateVo;
import com.figure.op.cameramanager.mapper.DeviceLocateMapper;
import com.figure.op.cameramanager.service.DeviceLocateService;
import com.figure.op.common.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/8 20:42
 * @Version 1.5
 */
@Service
public class DeviceLocateInfoServiceImpl implements DeviceLocateService {

    @Resource
    private DeviceLocateMapper deviceLocateMapper;

    @Override
    public List<DeviceLocateVo> queryDeviceLocateByFrontId(Integer frontId) {
        QueryWrapper<DeviceLocate> wrapper = new QueryWrapper<>();
        wrapper.eq("frontId",frontId);
        List<DeviceLocate> deviceLocates = deviceLocateMapper.selectList(wrapper);
        List<DeviceLocateVo> deviceLocateVos = BeanCopyUtils.copyList(deviceLocates, DeviceLocateVo.class);
        return deviceLocateVos;
    }

    @Override
    public List<DeviceLocateVo> list() {
        QueryWrapper<DeviceLocate> wrapper = new QueryWrapper<>();
        List<DeviceLocate> deviceLocates = deviceLocateMapper.selectList(wrapper);
        List<DeviceLocateVo> deviceLocateVos = BeanCopyUtils.copyList(deviceLocates, DeviceLocateVo.class);
        return deviceLocateVos;
    }
}
