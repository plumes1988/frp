package com.figure.core.service.device.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.device.DeviceProductInfo;
import com.figure.core.repository.device.DeviceProductInfoMapper;
import com.figure.core.service.device.IDeviceProductInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌管理表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-03-21
 */
@Service
public class DeviceProductInfoServiceImpl extends ServiceImpl<DeviceProductInfoMapper, DeviceProductInfo> implements IDeviceProductInfoService {

}
