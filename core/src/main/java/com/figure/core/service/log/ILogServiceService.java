package com.figure.core.service.log;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.device.DeviceCollectServer;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.log.LogService;

import java.util.List;

/**
 * <p>
 * 服务日志 服务类
 * </p>
 *
 * @author jobob
 * @since 2024-03-02
 */
public interface ILogServiceService extends IService<LogService> {

    void saveLog(LogService logService);

    LogService builderLogService(String serviceName,String level,String message,Integer serviceType);

    void builderLogServiceAndSave(String serviceName,String level,String message,Integer serviceType);
}
