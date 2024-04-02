package com.figure.core.rocketmq.struct.info;

import com.figure.core.helper.DateHelper;
import com.figure.core.model.record.RecordFile;
import lombok.Data;

@Data
public class RecordFileInfo {

    private String fileName;

    private String startTime;

    private String endTime;

    public RecordFileInfo(RecordFile recordFile) {
        this.fileName = recordFile.getFilePath();
        this.startTime = DateHelper.format(recordFile.getStartTime());
        this.endTime = DateHelper.format(recordFile.getEndTime());
    }
}
