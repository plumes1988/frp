package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

import java.util.List;

@Data
public class TranscodeTaskControlS2PConsumer extends MessageBase {

    private String serviceCode;

    private Integer isAll;

    private List<Integer> taskIDArray;

    private Integer actionFlag;

    private String alarmContent;
}
