package com.figure.core.service.device.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.device.DeviceServiceAlarmMessage;
import com.figure.core.repository.device.DeviceServiceAlarmMessageMapper;
import com.figure.core.service.device.IDeviceServiceAlarmMessageService;
import com.figure.core.sse.SseEmitterManager;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.figure.core.model.device.DeviceServiceAlarmMessage.*;
import static com.figure.core.sse.Constants.DEVICE_SERVICE_ALARM;

/**
 * <p>
 * 应用服务报警记录表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2024-03-04
 */
@Service
public class DeviceServiceAlarmMessageServiceImpl extends ServiceImpl<DeviceServiceAlarmMessageMapper, DeviceServiceAlarmMessage> implements IDeviceServiceAlarmMessageService {


    @Override
    public void fillEntityProps(List<DeviceServiceAlarmMessage> records) {

        for (DeviceServiceAlarmMessage deviceServiceAlarmMessage:records){
            Integer continueTime =  deviceServiceAlarmMessage.getContinueTime();
            if(continueTime==null){
                Date endTime = deviceServiceAlarmMessage.getEndTime();
                if(endTime==null){
                    endTime = new Date();
                }
                continueTime =  (int)(endTime.getTime() -  deviceServiceAlarmMessage.getHappenTime().getTime());
            }
            deviceServiceAlarmMessage.setContinueTime(continueTime);
        }

    }

    @Override
    public boolean isOnline(Integer serviceType, String keyName,String keyValue) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("serviceType",serviceType);
        queryWrapper.eq(keyName,keyValue);
        queryWrapper.eq("alarmType",ALARMTYPE_OFFLINE);
        queryWrapper.eq("status",NOT_END);
        queryWrapper.in("confirm", Arrays.asList(new String[]{UN_CONFIRMED,CONFIRMED}));
        return this.count(queryWrapper)==0;
    }

    @Override
    public void newAlarm(String serviceName, Integer serviceType, Integer alarmType,String alarmMsg) {
        this.newAlarm(null,serviceName, serviceType,alarmType,alarmMsg);
    }

    @Override
    public void newAlarm(String serviceCode, String serviceName, Integer serviceType, Integer alarmType,String alarmMsg) {

        Date now = new Date();

        DeviceServiceAlarmMessage deviceServiceAlarmMessage = new DeviceServiceAlarmMessage();

        deviceServiceAlarmMessage.setServiceCode(serviceCode);

        deviceServiceAlarmMessage.setAlarmId(now.getTime());

        deviceServiceAlarmMessage.setAlarmType(alarmType);

        deviceServiceAlarmMessage.setServiceType(serviceType);

        deviceServiceAlarmMessage.setHappenTime(now);

        deviceServiceAlarmMessage.setStatus(NOT_END);

        deviceServiceAlarmMessage.setConfirm(UN_CONFIRMED);

        deviceServiceAlarmMessage.setAlarmMsg(alarmMsg);

        deviceServiceAlarmMessage.setServiceName(serviceName);

        this.save(deviceServiceAlarmMessage);

        deviceServiceAlarmMessage.setTopic(DEVICE_SERVICE_ALARM);
        SseEmitterManager.sendMessageToAllMatchSseEmitter(deviceServiceAlarmMessage);
    }

    @Override
    public void endAlarm(String keyName,String keyValue,Integer serviceType) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("serviceType",serviceType);
        queryWrapper.eq(keyName,keyValue);
        queryWrapper.eq("alarmType",ALARMTYPE_OFFLINE);
        queryWrapper.eq("status",NOT_END);
        queryWrapper.in("confirm",Arrays.asList(new String[]{UN_CONFIRMED,CONFIRMED}));

        List<DeviceServiceAlarmMessage> deviceServiceAlarmMessages =   this.list(queryWrapper);
        for(DeviceServiceAlarmMessage deviceServiceAlarmMessage:deviceServiceAlarmMessages){
            deviceServiceAlarmMessage.setEndTime(new Date());
            deviceServiceAlarmMessage.setStatus(ENDED);
            this.updateById(deviceServiceAlarmMessage);
            //todo
            deviceServiceAlarmMessage.setTopic(DEVICE_SERVICE_ALARM);
            SseEmitterManager.sendMessageToAllMatchSseEmitter(deviceServiceAlarmMessage);
        }
    }
}
