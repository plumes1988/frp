package com.figure.core.rocketmq.struct.producer;

import com.figure.core.model.record.RecordServiceInfo;
import com.figure.core.model.record.RecordTaskInfo;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecordServiceCheckP2SProducer extends MessageBase {

    private String serviceCode;

    private Integer hlsFileTime;

    private Integer fileTime;

    private String savePath;

    private String fileContext;

    private Integer keepDisk;

    private List<com.figure.core.rocketmq.struct.info.RecordTaskInfo> taskInfoArray;

    public RecordServiceCheckP2SProducer(RecordServiceInfo recordServiceInfo, List<RecordTaskInfo> recordTaskInfoList, Long messageID) {
        super(RocketMQConstants.RECORD_SERVICE_CHECK_P2S, RocketMQConstants.ROCKETMQ_ALL_TAGS);
        this.getMessageHead().setMessageID(messageID);
        this.serviceCode = recordServiceInfo.getServiceCode();
        this.hlsFileTime = recordServiceInfo.getHlsFileTime();
        this.fileTime = recordServiceInfo.getFileTime();
        this.savePath = recordServiceInfo.getRecordPath();
        this.fileContext = recordServiceInfo.getUrlContext();
        List<com.figure.core.rocketmq.struct.info.RecordTaskInfo> taskInfoList = new ArrayList<>();
        for (RecordTaskInfo recordTaskInfo : recordTaskInfoList) {
            com.figure.core.rocketmq.struct.info.RecordTaskInfo taskInfo = new com.figure.core.rocketmq.struct.info.RecordTaskInfo(recordTaskInfo);
            taskInfoList.add(taskInfo);
        }
        this.taskInfoArray = taskInfoList;
    }
}
