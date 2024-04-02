package com.figure.core.redis;

import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.service.device.IDeviceHistoryIndicatorService;
import com.figure.core.util.JSONUtil;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

@Component
public class SaveHistoryIndicatorForAlarmConsumer implements MessageListener {

    @Resource
    private IDeviceHistoryIndicatorService deviceHistoryIndicatorService;

    @Override
    public void onMessage(Message message, byte[] pattern) {

        // 处理接收到的消息
        String queueName = new String(pattern);

        String messageContent = new String(message.getBody());

        messageContent = messageContent.substring(1, messageContent.length() - 1);

        messageContent = messageContent.replace("\\\"", "\"");

        DeviceAlarmMessage deviceAlarmMessage = JSONUtil.json2ObjectByT(messageContent, DeviceAlarmMessage.class);

        Date start = deviceAlarmMessage.getHappenTime();

        Date end = deviceAlarmMessage.getEndTime();

        // 创建 Calendar 实例
        Calendar cal = Calendar.getInstance();

        // 设置开始时间
        cal.setTime(start);
        // 在开始时间上减去 10 秒
        cal.add(Calendar.SECOND, -10);
        Date startTimeWithBuffer = cal.getTime();

        // 设置结束时间
        cal.setTime(end);
        // 在结束时间上增加 10 秒
        cal.add(Calendar.SECOND, 10);
        Date endTimeWithBuffer = cal.getTime();

        deviceHistoryIndicatorService.saveHistoryIndicatorForAlarm(deviceAlarmMessage.getDeviceCode(), deviceAlarmMessage.getIndicatorCode(),startTimeWithBuffer,endTimeWithBuffer);

    }

    public static void main(String[] args) {
        String json_str = "\"{\"alarmId\":1710755219344,\"frontId\":null,\"frontName\":null,\"deviceCode\":\"DC00034\",\"indicatorCode\":\"100001\",\"alarmTypeId\":90001,\"alarmMsg\":\"\",\"happenTime\":\"2024-03-18 17:46:59\",\"endTime\":\"2024-03-19 09:46:17\",\"continueTime\":null,\"status\":\"1\",\"confirm\":\"2\",\"result\":\"\",\"faultType\":3,\"deviceId\":0,\"deviceName\":null,\"indicatorName\":null,\"alarmTypeName\":null,\"alarmType\":null,\"notifyFlag\":1,\"isDelete\":0,\"topic\":null}\"";
        System.out.println(json_str);
        DeviceAlarmMessage deviceAlarmMessage = JSONUtil.json2ObjectByT(json_str, DeviceAlarmMessage.class);
        System.out.println(JSONUtil.Object2JsonStr(deviceAlarmMessage));
    }
}
