package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

import java.util.Date;

@Data
public class DeviceStateIndicatorChangeP2SConsumer extends MessageBase {

    private String deviceCode;

    private String indicatorCode;

    private String oldIndicatorValue;

    private String newIndicatorValue;

    private Date changeTime;

}
