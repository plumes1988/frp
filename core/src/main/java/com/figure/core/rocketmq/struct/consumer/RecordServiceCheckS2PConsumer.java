package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

@Data
public class RecordServiceCheckS2PConsumer extends MessageBase {

    /**
     * 服务节点编号
     */
    private String serviceCode;

}
