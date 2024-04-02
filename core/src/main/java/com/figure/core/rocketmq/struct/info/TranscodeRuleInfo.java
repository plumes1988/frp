package com.figure.core.rocketmq.struct.info;

import lombok.Data;

@Data
public class TranscodeRuleInfo {

    private Integer ruleID;

    private Integer mediaType;

    private Integer enableCodec;

    private Integer isDefault;

    private CodecSet codecSet = new CodecSet();

    private OSDSet OSDSet = new OSDSet();

    private Integer outputInfo;

    public TranscodeRuleInfo() {

    }

    public TranscodeRuleInfo(com.figure.core.model.transcode.TranscodeRuleInfo transcodeRuleInfo) {

        this.ruleID = transcodeRuleInfo.getRuleId();

        this.mediaType = transcodeRuleInfo.getMediaType();

        this.enableCodec = transcodeRuleInfo.getWorkMode();

        this.isDefault = transcodeRuleInfo.getIsDefault();

        this.codecSet = new CodecSet(transcodeRuleInfo);

        this.OSDSet = new OSDSet(transcodeRuleInfo);

        this.outputInfo = transcodeRuleInfo.getShowUDP() | (transcodeRuleInfo.getShowHLS() << 1)
                | (transcodeRuleInfo.getShowRTMP() << 2) | (transcodeRuleInfo.getShowHTTP_FLV() << 3);
    }
}
