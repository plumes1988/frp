package com.figure.core.rocketmq.struct.producer;

import com.figure.core.rocketmq.struct.MessageBase;
import com.figure.core.rocketmq.struct.info.TranscodeRuleInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TranscodeRuleSetP2SProducer extends MessageBase {

    private List<TranscodeRuleInfo> ruleInfoArray = new ArrayList<>();

    public TranscodeRuleSetP2SProducer() {
    }

    public TranscodeRuleSetP2SProducer(List<com.figure.core.model.transcode.TranscodeRuleInfo> transcodeRuleInfoList) {
        for (com.figure.core.model.transcode.TranscodeRuleInfo transcodeRuleInfo : transcodeRuleInfoList) {
            TranscodeRuleInfo transcodeRuleInfoItem = new TranscodeRuleInfo(transcodeRuleInfo);
            ruleInfoArray.add(transcodeRuleInfoItem);
        }
    }
}
