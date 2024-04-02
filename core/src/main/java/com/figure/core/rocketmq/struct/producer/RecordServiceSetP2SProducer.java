package com.figure.core.rocketmq.struct.producer;

import com.figure.core.model.record.RecordServiceInfo;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

@Data
public class RecordServiceSetP2SProducer extends MessageBase {

    private String serviceCode;

    private Integer hlsFileTime;

    private Integer fileTime;

    private String savePath;

    private String urlContext;

    private Integer keepDisk;


    public RecordServiceSetP2SProducer(RecordServiceInfo recordServiceInfo) {
        super(RocketMQConstants.RECORD_SERVICE_SET_P2S, RocketMQConstants.ROCKETMQ_ALL_TAGS);
        this.serviceCode = recordServiceInfo.getServiceCode();
        this.hlsFileTime = recordServiceInfo.getHlsFileTime();
        this.fileTime = recordServiceInfo.getFileTime();
        this.savePath = recordServiceInfo.getRecordPath();
        this.urlContext = recordServiceInfo.getUrlContext();
        this.keepDisk = recordServiceInfo.getDiskSpaceQuota();
    }
}
