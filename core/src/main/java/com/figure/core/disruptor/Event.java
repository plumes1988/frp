package com.figure.core.disruptor;
import com.figure.core.model.device.DeviceAlarmMessage;
import lombok.Data;

@Data
public class Event {

    private DeviceAlarmMessage deviceAlarmMessage;
}
