package com.figure.core.rocketmq.struct.producer;

import com.figure.core.helper.DateHelper;
import com.figure.core.model.record.RecordFile;
import com.figure.core.model.record.RecordPlayback;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.MessageBase;
import com.figure.core.rocketmq.struct.info.RecordFileInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecordDataGetP2SProducer extends MessageBase {

    private String serviceCode;

    private String mergeName;

    private String playStartTime;

    private String playEndTime;

    private List<RecordFileInfo> fileInfoArray;

    public RecordDataGetP2SProducer(List<RecordFile> recordFileList, RecordPlayback recordPlayback) {
        super(RocketMQConstants.RECORD_DATA_GET_P2S, RocketMQConstants.ROCKETMQ_ALL_TAGS);
        RecordFile recordFile0 = recordFileList.get(0);
        this.serviceCode = recordFile0.getServiceCode();
        this.mergeName = recordPlayback.getMergeName();
        this.playStartTime = DateHelper.format(recordPlayback.getStartTime());
        this.playEndTime = DateHelper.format(recordPlayback.getEndTime());
        List<RecordFileInfo> recordFileInfoList = new ArrayList<>();
        for (RecordFile recordFile : recordFileList) {
            RecordFileInfo recordFileInfo = new RecordFileInfo(recordFile);
            recordFileInfoList.add(recordFileInfo);
        }
        this.fileInfoArray = recordFileInfoList;
    }
}
