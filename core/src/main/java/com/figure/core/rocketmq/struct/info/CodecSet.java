package com.figure.core.rocketmq.struct.info;

import com.figure.core.model.transcode.TranscodeRuleInfo;
import lombok.Data;

@Data
public class CodecSet {

    private String videoCodec;

    private Integer videoWidth;

    private Integer videoHeight;

    private Integer videoFramerate;

    private Integer videoBitrate;

    private String audioCodec;

    private Integer audioSample;

    private Integer audioBitrate;

    private String codecMode;

    public CodecSet() {
    }

    public CodecSet(TranscodeRuleInfo transcodeRuleInfo) {
        this.videoCodec = transcodeRuleInfo.getVideoCode();
        this.videoWidth = transcodeRuleInfo.getVideoWidth();
        this.videoHeight = transcodeRuleInfo.getVideoHeight();
        this.videoFramerate = transcodeRuleInfo.getFrameRate();
        this.videoBitrate = transcodeRuleInfo.getVideoCodeRate();

        this.audioCodec = transcodeRuleInfo.getAudioCode();
        this.audioSample = transcodeRuleInfo.getSamplingRate();
        this.audioBitrate = transcodeRuleInfo.getAudioCodeRate();

        switch (transcodeRuleInfo.getCodingMode()) {
            case 2:
                this.codecMode = "medium";
                break;
            case 3:
                this.codecMode = "slower";
                break;
            case 1:
            default:
                this.codecMode = "superfast";
                break;
        }
    }
}
