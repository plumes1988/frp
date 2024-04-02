package com.figure.core.rocketmq.struct.producer;

import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

@Data
public class ServiceControlSetProducer extends MessageBase {

    private String serviceCode;

    private Integer controlMode;


    public ServiceControlSetProducer(String serviceCode, Integer controlMode) {
        super(RocketMQConstants.SERVICE_CONTROL_SET, RocketMQConstants.ROCKETMQ_ALL_TAGS);
        this.serviceCode = serviceCode;
        this.controlMode = controlMode;
    }
}
