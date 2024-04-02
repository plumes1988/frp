package com.figure.core.service.device.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.device.DeviceFunction;
import com.figure.core.repository.device.DeviceFunctionMapper;
import com.figure.core.service.device.IDeviceFunctionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备功能 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-06-01
 */
@Service
public class DeviceFunctionServiceImpl extends ServiceImpl<DeviceFunctionMapper, DeviceFunction> implements IDeviceFunctionService {

}
