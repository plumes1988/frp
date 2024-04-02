package com.figure.core.service.device.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.device.DeviceType;
import com.figure.core.repository.device.DeviceTypeMapper;
import com.figure.core.service.device.IDeviceTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-05-26
 */
@Service
public class DeviceTypeServiceImpl extends ServiceImpl<DeviceTypeMapper, DeviceType> implements IDeviceTypeService {

    @Override
    public void clearData() {
        baseMapper.clearData();
    }
}
