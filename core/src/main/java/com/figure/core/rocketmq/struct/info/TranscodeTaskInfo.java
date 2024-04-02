package com.figure.core.rocketmq.struct.info;

import com.figure.core.model.transcode.TranscodeTaskInfoList;
import com.figure.core.model.transcode.TranscodeTaskStreamRel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TranscodeTaskInfo {

    List<TranscodeOutPutInfo> outPutInfoArray = new ArrayList<>();
    private Integer taskID;
    private String signalID;
    private String signalName;
    private Integer signalType;
    private String signalTypeName;
    private String logicPositionName;
    private String frontStationName;
    private Integer mediaType;
    private String signalURL;
    private String receiveIP;
    private Integer transcodeRuleID;

    public TranscodeTaskInfo(TranscodeTaskInfoList taskInfo) {
        this.taskID = taskInfo.getTranscodeTaskId();
        this.signalID = taskInfo.getSignalId();
        this.signalName = taskInfo.getSignalName();
        this.signalType = taskInfo.getSignalType();
        this.signalTypeName = taskInfo.getSignalTypeName();
        this.logicPositionName = taskInfo.getPositionName();
        this.frontStationName = taskInfo.getFrontName();
        this.mediaType = taskInfo.getMediaType();
        this.signalURL = taskInfo.getSourceURL();
        this.receiveIP = taskInfo.getDeviceIP();
        this.transcodeRuleID = taskInfo.getTranscodeRuleId();
        List<TranscodeTaskStreamRel> transcodeTaskStreamRelList = taskInfo.getTranscodeTaskStreamRelList();
        for (TranscodeTaskStreamRel transcodeTaskStreamRel : transcodeTaskStreamRelList) {
            TranscodeOutPutInfo transcodeOutPutInfo = new TranscodeOutPutInfo(transcodeTaskStreamRel);
            this.outPutInfoArray.add(transcodeOutPutInfo);
        }
    }
}
