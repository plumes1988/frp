package com.figure.system.eventBus.subscriber;

import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.service.device.IDeviceIndicatorParamService;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.log.ILogDeviceStateIndicatorChangeService;
import com.figure.core.service.notice.INoticeAlarmTriggerRuleService;
import com.figure.system.eventBus.event.UpdateRedisCacheEvent;
import com.google.common.eventbus.Subscribe;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static com.figure.core.redis.RedisConstants.NOTICE_STRUCT;

@Component
public class EventSubscriber {

    @Resource
    private INoticeAlarmTriggerRuleService noticeAlarmTriggerRuleService;


    // 更新redisCache
    @Subscribe
    public void handleUpdateRedisCacheEvent(UpdateRedisCacheEvent event) {
        String  redisKey =  event.getRedisKey();
        if(NOTICE_STRUCT.equals(redisKey)){
            noticeAlarmTriggerRuleService.updateRuleCache();
        }
    }
}
