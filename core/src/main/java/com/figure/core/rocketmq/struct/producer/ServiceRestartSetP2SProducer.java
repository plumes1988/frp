package com.figure.core.rocketmq.struct.producer;

import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceRestartSetP2SProducer extends MessageBase {

    private String serviceCode;

    public ServiceRestartSetP2SProducer(String serviceCode) {
        super(RocketMQConstants.SERVICE_RESTART_SET_P2S, RocketMQConstants.ROCKETMQ_ALL_TAGS);
        this.serviceCode = serviceCode;
    }

}
