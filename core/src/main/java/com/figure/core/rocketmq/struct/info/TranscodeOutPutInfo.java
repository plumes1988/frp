package com.figure.core.rocketmq.struct.info;

import com.figure.core.model.transcode.TranscodeTaskStreamRel;
import lombok.Data;

@Data
public class TranscodeOutPutInfo {

    private Integer outputType;

    private String outputURL;

    public TranscodeOutPutInfo() {
    }

    public TranscodeOutPutInfo(TranscodeTaskStreamRel transcodeTaskStreamRel) {
        this.outputType = transcodeTaskStreamRel.getStreamType();
        this.outputURL = transcodeTaskStreamRel.getStreamURL();
    }
}
