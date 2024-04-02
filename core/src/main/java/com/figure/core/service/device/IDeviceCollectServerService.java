package com.figure.core.service.device;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.device.DeviceCollectServer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网管采集服务管理表 服务类
 * </p>
 *
 * @author jobob
 * @since 2022-08-24
 */
public interface IDeviceCollectServerService extends IService<DeviceCollectServer> {

    void fillEntityProps(List<DeviceCollectServer> records);

    Map<String, Integer> getOffLine();
}
