package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

@Data
public class TranscodeServiceCheckS2PConsumer extends MessageBase {

    private String serviceCode;
}
