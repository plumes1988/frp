package com.figure.core.rocketmq.struct.producer;

import com.figure.core.model.transcode.TranscodeTaskInfoList;
import com.figure.core.rocketmq.struct.MessageBase;
import com.figure.core.rocketmq.struct.info.TranscodeTaskInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TranscodeServiceCheckP2SProducer extends MessageBase {

    private String serviceCode;

    private List<TranscodeTaskInfo> taskInfoArray = new ArrayList<>();

    public TranscodeServiceCheckP2SProducer(String serviceCode, List<TranscodeTaskInfoList> transcodeTaskInfoList) {
        this.serviceCode = serviceCode;
        for (TranscodeTaskInfoList taskInfo : transcodeTaskInfoList) {
            TranscodeTaskInfo transcodeTaskInfo = new TranscodeTaskInfo(taskInfo);
            this.taskInfoArray.add(transcodeTaskInfo);
        }
    }
}
