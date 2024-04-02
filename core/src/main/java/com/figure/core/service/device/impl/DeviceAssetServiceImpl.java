package com.figure.core.service.device.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.device.DeviceAsset;
import com.figure.core.repository.device.DeviceAssetMapper;
import com.figure.core.service.device.IDeviceAssetService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备资产管理表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-12-19
 */
@Service
public class DeviceAssetServiceImpl extends ServiceImpl<DeviceAssetMapper, DeviceAsset> implements IDeviceAssetService {

}
