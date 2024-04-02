package com.figure.core.service.device.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.device.DeviceProductModel;
import com.figure.core.repository.device.DeviceProductModelMapper;
import com.figure.core.service.device.IDeviceProductModelService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 型号管理表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-03-22
 */
@Service
public class DeviceProductModelServiceImpl extends ServiceImpl<DeviceProductModelMapper, DeviceProductModel> implements IDeviceProductModelService {

}
