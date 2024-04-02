package com.figure.core.service.device.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.model.device.DeviceAlarmtypeInfo;
import com.figure.core.repository.device.DeviceAlarmtypeInfoMapper;
import com.figure.core.service.device.IDeviceAlarmtypeInfoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 报警类型表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-12-16
 */
@Service
public class DeviceAlarmtypeInfoServiceImpl extends ServiceImpl<DeviceAlarmtypeInfoMapper, DeviceAlarmtypeInfo> implements IDeviceAlarmtypeInfoService {

}
