package com.figure.core.rocketmq.struct.producer;

import com.figure.core.model.transcode.TranscodeTaskInfo;
import com.figure.core.rocketmq.struct.MessageBase;
import com.figure.core.util.StringUtils;
import lombok.Data;

import java.util.List;

@Data
public class TranscodeTaskControlP2SProducer extends MessageBase {

    private String serviceCode;

    private Integer isAll;

    private List<Integer> taskIDArray;

    public TranscodeTaskControlP2SProducer() {
    }

    public TranscodeTaskControlP2SProducer(TranscodeTaskInfo transcodeTaskInfo) {
        this.serviceCode = transcodeTaskInfo.getServiceCode();
        this.isAll = transcodeTaskInfo.getIsAll();
        this.taskIDArray = StringUtils.StringToIntList(transcodeTaskInfo.getTranscodeTaskIds());
    }
}
