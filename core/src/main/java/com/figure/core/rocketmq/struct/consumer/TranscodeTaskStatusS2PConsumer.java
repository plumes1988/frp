package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import com.figure.core.rocketmq.struct.info.TransCodeAlarmInfo;
import com.figure.core.rocketmq.struct.info.TranscodeTaskAlarmInfo;
import lombok.Data;

import java.util.List;

@Data
public class TranscodeTaskStatusS2PConsumer extends MessageBase {

    private String serviceCode;

    private Integer serviceState;

    private List<TransCodeAlarmInfo> alarmInfoArray;

    private List<TranscodeTaskAlarmInfo> taskInfoArray;

}
