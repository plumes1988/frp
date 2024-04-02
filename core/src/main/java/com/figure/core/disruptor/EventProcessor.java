package com.figure.core.disruptor;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.service.device.IDeviceAlarmMessageService;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import static com.figure.core.model.device.DeviceAlarmMessage.ENDED;
import static com.figure.core.model.device.DeviceAlarmMessage.NOT_END;
import static com.figure.core.service.device.impl.DeviceAlarmMessageServiceCacheImpl.ALARM_UPDATE_TYPE_END_ALL_BY_KEY;
import static com.figure.core.service.device.impl.DeviceAlarmMessageServiceCacheImpl.ALARM_UPDATE_TYPE_NEW;

@Component
public class EventProcessor implements com.lmax.disruptor.EventHandler<Event> {

    @Resource
    IDeviceAlarmMessageService deviceAlarmMessageService;

    @Override
    public void onEvent(Event event, long sequence, boolean endOfBatch) {

        DeviceAlarmMessage deviceAlarmMessage = event.getDeviceAlarmMessage();

        if(ALARM_UPDATE_TYPE_NEW==deviceAlarmMessage.getAlarmUpdateType()){
            deviceAlarmMessageService.newAlarm(deviceAlarmMessage);
        }

        if(ALARM_UPDATE_TYPE_END_ALL_BY_KEY==deviceAlarmMessage.getAlarmUpdateType()){

            UpdateWrapper updateWrapper = new UpdateWrapper();

            updateWrapper.eq("deviceCode",deviceAlarmMessage.getDeviceCode());

            updateWrapper.eq("indicatorCode",deviceAlarmMessage.getIndicatorCode());

            updateWrapper.eq("alarmTypeId",deviceAlarmMessage.getAlarmTypeId());

            updateWrapper.eq("status",NOT_END);

            updateWrapper.set("status",ENDED);

            updateWrapper.set("endTime",deviceAlarmMessage.getEndTime());

            deviceAlarmMessageService.update(updateWrapper);

        }
    }
}
