package com.figure.core.model.alarm;

import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.rocketmq.struct.consumer.SignalAlarmUpdateS2PConsumer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("alarm_message_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlarmKey", description = "信号报警记录key")
public class AlarmKey {

    private String systemCode;

    private String signalId;

    private Integer alarmType;

    public AlarmKey() {
    }

    public AlarmKey(AlarmMessageInfo alarmMessageInfo) {
        this.systemCode = alarmMessageInfo.getSystemCode();
        this.signalId = alarmMessageInfo.getSignalId();
        this.alarmType = alarmMessageInfo.getAlarmTypeId();
    }

    public AlarmKey(SignalAlarmUpdateS2PConsumer consumer) {
        this.systemCode = consumer.getSystemCode();
        this.signalId = consumer.getSignalID();
        this.alarmType = consumer.getAlarmType();
    }
}
