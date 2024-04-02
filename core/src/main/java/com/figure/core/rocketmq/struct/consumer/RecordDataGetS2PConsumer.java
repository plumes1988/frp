package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

@Data
public class RecordDataGetS2PConsumer extends MessageBase {

    private String serviceCode;

    private String mediaURL;

    /**
     * 执行状态 0正常执行，1执行故障
     */
    private Integer actionFlag;

    /**
     * 故障原因
     */
    private String alarmContent;

    public RecordDataGetS2PConsumer(String serviceCode, String mediaURL, Integer actionFlag, String alarmContent) {
        this.serviceCode = serviceCode;
        this.mediaURL = mediaURL;
        this.actionFlag = actionFlag;
        this.alarmContent = alarmContent;
    }
}
