package com.figure.core.service.record;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.record.RecordFile;
import com.figure.core.model.record.RecordPlayback;
import com.figure.core.rocketmq.struct.consumer.RecordDataGetS2PConsumer;
import com.figure.core.rocketmq.struct.consumer.RecordDataUpdateS2PConsumer;
import com.figure.core.rocketmq.struct.producer.RecordDataGetP2SProducer;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 录制文件 IService
 * </p>
 *
 * @author feather
 * @date 2023-04-14 14:12:49
 */
public interface IRecordFileService extends IService<RecordFile> {

    void sendRecordDataGet(RecordDataGetP2SProducer producer);

    void processRecordFileUpdate(RecordDataUpdateS2PConsumer consumer);

    Map<String, Object> recordPlayback(RecordPlayback recordPlayback);

    void processRecordDataGet(RecordDataGetS2PConsumer consumer);

    Map<String, Object> createHLSFile(List<RecordFile> recordFileList);
}