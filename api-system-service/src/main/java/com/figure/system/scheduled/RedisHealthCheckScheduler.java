package com.figure.system.scheduled;

import com.figure.core.constant.LogConstants;
import com.figure.core.model.log.LogService;
import com.figure.core.service.device.IDeviceServiceAlarmMessageService;
import com.figure.core.service.log.ILogServiceService;
import com.figure.core.util.BusinessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

import static com.figure.core.db.JavaMemoryDb.REDIS_ONLINE_STATUS;
import static com.figure.core.model.device.DeviceServiceAlarmMessage.ALARMTYPE_OFFLINE;
import static com.figure.core.model.log.LogService.*;

@Component
public class RedisHealthCheckScheduler {


    @Resource
    private ILogServiceService logServiceService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private IDeviceServiceAlarmMessageService deviceServiceAlarmMessageService;

    @Scheduled(fixedDelay = 5000, initialDelay = 20 * 1000) // 每隔5秒执行一次任务
    public void checkRedisHealth() {
        boolean isOnline = BusinessUtils.isRedisOnline(redisTemplate);
        Boolean old_status = REDIS_ONLINE_STATUS;

        Boolean hasChange = false;

        if(old_status==null){
            hasChange = true;
        }else{
            hasChange = isOnline != old_status;
        }

        if(hasChange){
            if(isOnline){
                deviceServiceAlarmMessageService.endAlarm("serviceName",SOURCE_SERVICE_REIDS,SERVICE_TYPE_REDIS);
                logServiceService.builderLogServiceAndSave(SOURCE_SERVICE_REIDS, LogConstants.INFO,"ping redis服务成功，判定redis服务处于在线状态",SERVICE_TYPE_REDIS);
            }else{
                if(deviceServiceAlarmMessageService.isOnline(SERVICE_TYPE_REDIS,"serviceName",SOURCE_SERVICE_REIDS)){
                    deviceServiceAlarmMessageService.newAlarm(SOURCE_SERVICE_REIDS,SERVICE_TYPE_REDIS,ALARMTYPE_OFFLINE,"ping redis服务失败，判定redis服务处于离线状态");
                }
                logServiceService.builderLogServiceAndSave(SOURCE_SERVICE_REIDS, LogConstants.ERROR,"ping redis服务失败，判定redis服务处于离线状态",SERVICE_TYPE_REDIS);
            }
        }
        REDIS_ONLINE_STATUS = isOnline;
    }
}