package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

@Data
public class TranscodeTaskSetS2PConsumer extends MessageBase {

    private String serviceCode;

    private Integer taskID;

    private Integer actionFlag;

    private String alarmContent;
}
