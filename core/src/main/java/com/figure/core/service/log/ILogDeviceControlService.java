package com.figure.core.service.log;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.log.LogDeviceControl;

import java.util.List;

/**
 * <p>
 * 设备操作日志 服务类
 * </p>
 *
 * @author jobob
 * @since 2023-12-05
 */
public interface ILogDeviceControlService extends IService<LogDeviceControl> {

    void fillEntityProps(List<LogDeviceControl> records);
}
