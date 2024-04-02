package com.figure.core.service.log.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.LogConstants;
import com.figure.core.model.log.LogService;
import com.figure.core.repository.log.LogServiceMapper;
import com.figure.core.service.log.ILogServiceService;
import com.figure.core.sse.SseEmitterManager;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.figure.core.model.log.LogService.SERVICE_TYPE_COLLECT;
import static com.figure.core.sse.Constants.LOG_SERVICE;

/**
 * <p>
 * 服务日志 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2024-03-02
 */
@Service
@DS("frp01")
public class LogServiceServiceImpl extends ServiceImpl<LogServiceMapper, LogService> implements ILogServiceService {

    @Override
    public void saveLog(LogService logService) {
        logService.setTopic(LOG_SERVICE);
        this.save(logService);
        SseEmitterManager.sendMessageToAllMatchSseEmitter(logService);
    }

    @Override
    public LogService builderLogService(String serviceName, String level, String message, Integer serviceType) {
        LogService logService = new LogService();
        logService.setTimestamp(new Date());
        logService.setLevel(level);
        logService.setMessage(message);
        logService.setSourceService(serviceName);
        logService.setServiceType(serviceType);
        return logService;
    }

    @Override
    public void builderLogServiceAndSave(String serviceName, String level, String message, Integer serviceType) {
        LogService logService = builderLogService(serviceName,level, message,serviceType);
        this.saveLog(logService);
    }
}
