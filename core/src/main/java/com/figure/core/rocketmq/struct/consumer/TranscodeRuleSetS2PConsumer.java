package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

@Data
public class TranscodeRuleSetS2PConsumer extends MessageBase {

    private String serviceCode;

    private Integer actionFlag;

    private String alarmContent;

}
