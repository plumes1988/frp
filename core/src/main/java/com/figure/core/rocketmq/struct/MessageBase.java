package com.figure.core.rocketmq.struct;

import lombok.Data;

@Data
public class MessageBase {

    private MessageHead messageHead;

    private String rocketmqTopic;

    private String rocketmqTag;

    public MessageBase(String rocketmqTopic, String rocketmqTag) {
        this.messageHead = new MessageHead();
        this.rocketmqTopic = rocketmqTopic;
        this.rocketmqTag = rocketmqTag;
    }

    public MessageBase() {
        this.messageHead = new MessageHead();
    }
}
