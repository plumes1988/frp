package com.figure.core.rocketmq.struct.producer;

import com.figure.core.model.transcode.TranscodeTaskInfoList;
import com.figure.core.model.transcode.TranscodeTaskStreamRel;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.MessageBase;
import com.figure.core.rocketmq.struct.info.TranscodeOutPutInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TranscodeTaskSetP2SProducer extends MessageBase {

    List<TranscodeOutPutInfo> outPutInfoArray = new ArrayList<>();
    private String serviceCode;
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

    public TranscodeTaskSetP2SProducer() {
    }

    public TranscodeTaskSetP2SProducer(TranscodeTaskInfoList transcodeTaskInfoList, List<TranscodeTaskStreamRel> transcodeTaskStreamRelList) {
        super(RocketMQConstants.TRANSCODE_TASK_SET_P2S, RocketMQConstants.ROCKETMQ_ALL_TAGS);
        this.serviceCode = transcodeTaskInfoList.getServiceCode();
        this.taskID = transcodeTaskInfoList.getTranscodeTaskId();
        this.signalID = transcodeTaskInfoList.getSignalId();
        this.signalName = transcodeTaskInfoList.getSignalName();
        this.signalType = transcodeTaskInfoList.getSignalType();
        this.signalTypeName = transcodeTaskInfoList.getSignalTypeName();
        this.logicPositionName = transcodeTaskInfoList.getPositionName();
        this.frontStationName = transcodeTaskInfoList.getFrontName();
        this.mediaType = transcodeTaskInfoList.getMediaType();
        this.signalURL = transcodeTaskInfoList.getSourceURL();
        this.receiveIP = transcodeTaskInfoList.getDeviceIP();
        this.transcodeRuleID = transcodeTaskInfoList.getTranscodeRuleId();

        for (TranscodeTaskStreamRel transcodeTaskStreamRel : transcodeTaskStreamRelList) {
            TranscodeOutPutInfo transcodeOutPutInfo = new TranscodeOutPutInfo();
            transcodeOutPutInfo.setOutputType(transcodeTaskStreamRel.getStreamType());
            transcodeOutPutInfo.setOutputURL(transcodeTaskStreamRel.getStreamURL());
            outPutInfoArray.add(transcodeOutPutInfo);
        }
    }
}
