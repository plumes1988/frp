package com.figure.core.rocketmq.struct.producer;

import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

import java.util.List;

@Data
public class RecordTaskControlP2SProducer extends MessageBase {

    private String serviceCode;

    private Integer isAll;

    private List<Integer> taskIDArray;

    public RecordTaskControlP2SProducer(Integer businessCode, String serviceCode, Integer isAll, List<Integer> taskIDArray) {
        super(RocketMQConstants.RECORD_TASK_CONTROL_P2S, RocketMQConstants.ROCKETMQ_ALL_TAGS);
        this.getMessageHead().setBusinessCode(businessCode);
        this.serviceCode = serviceCode;
        this.isAll = isAll;
        this.taskIDArray = taskIDArray;
    }
}
