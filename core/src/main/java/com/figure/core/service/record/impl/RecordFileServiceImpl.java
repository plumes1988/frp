package com.figure.core.service.record.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.record.CreateRecordFile;
import com.figure.core.model.record.RecordFile;
import com.figure.core.model.record.RecordPlayback;
import com.figure.core.model.record.RecordTaskInfo;
import com.figure.core.model.signal.SignalChannelInfoList;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.record.RecordFileMapper;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.RocketMQProducer;
import com.figure.core.rocketmq.struct.consumer.RecordDataGetS2PConsumer;
import com.figure.core.rocketmq.struct.consumer.RecordDataUpdateS2PConsumer;
import com.figure.core.rocketmq.struct.producer.RecordDataGetP2SProducer;
import com.figure.core.service.record.IRecordFileService;
import com.figure.core.service.record.IRecordTaskInfoService;
import com.figure.core.service.signal.ISignalChannelInfoService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.service.table.IOperateTableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 录制文件Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2023-04-14 14:12:49
 */
@Service
public class RecordFileServiceImpl extends ServiceImpl<RecordFileMapper, RecordFile> implements IRecordFileService {

    private final Map<Long, RecordDataGetS2PConsumer> consumerMap = new ConcurrentHashMap<>();
    @Resource
    RocketMQProducer rocketMQProducer;
    @Resource
    IOperateTableService operateTableService;
    @Resource
    IRecordTaskInfoService recordTaskInfoService;
    @Resource
    ISignalChannelInfoService signalChannelInfoService;
    @Resource
    ISysParaService sysParaService;

    @Override
    public void sendRecordDataGet(RecordDataGetP2SProducer producer) {
        rocketMQProducer.send(RocketMQConstants.RECORD_DATA_GET_P2S, producer.getServiceCode(), producer);
    }

    //record_file_录制类型_源ID_转码规则ID_是否HLS
    @Override
    public void processRecordFileUpdate(RecordDataUpdateS2PConsumer consumer) {
        RecordFile recordFile = new RecordFile(consumer);
        if (!this.operateTableService.checkRecordFileTable(recordFile.getTableName())) {
            CreateRecordFile createRecordFile = new CreateRecordFile(recordFile.getTableName());
            this.operateTableService.createRecordFileTable(createRecordFile);
        }
        this.operateTableService.saveByTableName(recordFile);
    }

    @Override
    public Map<String, Object> recordPlayback(RecordPlayback recordPlayback) {
        Map<String, Object> result = new HashMap<>();

        String signalId = recordPlayback.getSignalId();
        LambdaQueryWrapper<RecordTaskInfo> recordTaskInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        if (recordPlayback.getRecordTaskId() == null) {
            recordTaskInfoLambdaQueryWrapper.eq(RecordTaskInfo::getSignalId, signalId).eq(RecordTaskInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        } else {
            recordTaskInfoLambdaQueryWrapper.eq(RecordTaskInfo::getRecordTaskId, recordPlayback.getRecordTaskId());
        }
        List<RecordTaskInfo> recordTaskInfoList = this.recordTaskInfoService.list(recordTaskInfoLambdaQueryWrapper);

        Integer transcodeRuleId = 0;
        Integer mediaType = 0;
        Integer isHLS;

        if (recordTaskInfoList.isEmpty()) {
            result.put("success", 0);
            result.put("msg", "找不到对应录制任务");
            return result;
        } else {
            transcodeRuleId = recordTaskInfoList.get(0).getTranscodeRuleId();
            mediaType = recordTaskInfoList.get(0).getMediaType();
            isHLS = recordTaskInfoList.get(0).getIsHLS();
        }
        String tableName = "record_file_" + mediaType + "_" + signalId + "_" + transcodeRuleId + "_" + isHLS;
        List<RecordFile> recordFileList = new ArrayList<>();

        if (recordPlayback.getIsAlarm() == 1) {
            int alarmRecordFileTime = Integer.parseInt(sysParaService.getParamCacheByName(RedisConstants.ALARM_PARAM).get(("alarmRecordFileTime")));
            recordPlayback.setStartTime(DateHelper.add(recordPlayback.getStartTime(), Calendar.SECOND, -alarmRecordFileTime));
            recordPlayback.setEndTime(DateHelper.add(recordPlayback.getEndTime(), Calendar.SECOND, alarmRecordFileTime));
        }

        LambdaQueryWrapper<RecordFile> recordFileLambdaQueryWrapper = Wrappers.lambdaQuery();
        recordFileLambdaQueryWrapper.between(RecordFile::getStartTime, recordPlayback.getStartTime(), recordPlayback.getEndTime())
                .or().between(RecordFile::getEndTime, recordPlayback.getStartTime(), recordPlayback.getEndTime())
                .or().apply("'" + DateHelper.format(recordPlayback.getStartTime()) + "' BETWEEN startTime and endTime")
                .or().apply("'" + DateHelper.format(recordPlayback.getEndTime()) + "' BETWEEN startTime and endTime");

        if (this.operateTableService.checkRecordFileTable(tableName)) {
            recordFileList = this.operateTableService.selectByTableName(tableName, recordFileLambdaQueryWrapper);
        }
        if (!recordFileList.isEmpty()) {
            RecordDataGetS2PConsumer consumer = null;

            long filePlayTime = DateHelper.diffMillis(recordPlayback.getEndTime(), recordPlayback.getStartTime()) / 1000;
            SignalChannelInfoList signalChannelInfo = this.signalChannelInfoService.selectSignalChannelInfoList(signalId);
            recordPlayback.setMergeName(DateHelper.format(new Date(), DateHelper.patterns_masks[10])
                    + "_" + signalChannelInfo.getFrontName()
                    + "_" + signalChannelInfo.getLogicPositionName()
                    + "_" + signalChannelInfo.getChannelName()
                    + "_" + filePlayTime + "s.ts"
            );
            if (recordPlayback.getType() == 0) {
                //下载
                consumer = requestMergeServer(recordPlayback, recordFileList);
                if (consumer == null) {
                    result.put("success", 0);
                    result.put("msg", "找不到对应下载拼接服务");
                } else {
                    if (consumer.getActionFlag() == 1) {
                        result.put("success", 0);
                        result.put("msg", consumer.getAlarmContent());
                    } else {
                        result.put("success", 1);
                        result.put("msg", "返回下载URL");
                        result.put("url", consumer.getMediaURL());
                    }
                }
            } else {
                //回看
                if (isHLS == 1) {
                    result = this.createHLSFile(recordFileList);
                } else {
                    consumer = requestMergeServer(recordPlayback, recordFileList);
                    if (consumer == null) {
                        result.put("success", 0);
                        result.put("msg", "找不到对应播放拼接服务");
                    } else {
                        if (consumer.getActionFlag() == 1) {
                            result.put("success", 0);
                            result.put("msg", consumer.getAlarmContent());
                        } else {
                            result.put("success", 1);
                            result.put("msg", "返回播放URL");
                            result.put("url", consumer.getMediaURL());
                        }
                    }
                }
            }
        } else {
            result.put("success", 0);
            result.put("msg", "找不到对应录制文件");
        }
        return result;
    }

    private RecordDataGetS2PConsumer requestMergeServer(RecordPlayback recordPlayback, List<RecordFile> recordFileList) {
        RecordDataGetP2SProducer producer = new RecordDataGetP2SProducer(recordFileList, recordPlayback);
        this.sendRecordDataGet(producer);
        long timeout = 10000;
        Long messageID = producer.getMessageHead().getMessageID();
        Date date = new Date();
        synchronized (this) {
            while (System.currentTimeMillis() - date.getTime() < timeout) {
                if (consumerMap.containsKey(messageID)) {
                    return consumerMap.remove(messageID);
                } else {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void processRecordDataGet(RecordDataGetS2PConsumer consumer) {
        Long messageID = consumer.getMessageHead().getMessageID();
        consumerMap.put(messageID, consumer);
    }

    @Override
    public Map<String, Object> createHLSFile(List<RecordFile> recordFileList) {
        Map<String, Object> result = new HashMap<>();
        Map<String, String> recordParamMap = sysParaService.getParamCacheByName(RedisConstants.RECORD_PARAM);
        String localIP = recordParamMap.get(Constants.LOCAL_IP);
        String hlsFilePath = "/figure/data/recordback/";

        StringBuilder m3u8Text = new StringBuilder("#EXTM3U\n#EXT-X-TARGETDURATION:" + recordFileList.get(0).getFileTime() + "\n#EXT-X-MEDIA-SEQUENCE:1\n");

        for (RecordFile recordFile : recordFileList) {
            m3u8Text.append("#EXTINF:").append(recordFile.getFileTime() - 1).append(",\n");
            m3u8Text.append(recordFile.getFileUrl()).append("\n");
        }
        m3u8Text.append("#EXT-X-ENDLIST\n");
        // 定义输出文件路径
        String fileName = recordFileList.get(0).getSignalId() + "_" + DateHelper.format(new Date(), DateHelper.patterns_masks[16]) + ".m3u8";

        Path outputPath = Paths.get(hlsFilePath, fileName);
        try {
            Files.createDirectories(outputPath.getParent());
            Files.createFile(outputPath);
        } catch (IOException e) {
            result.put("success", 0);
            result.put("msg", "无法创建M3U8文件：" + e.getMessage());
        }

        // 将 M3U8 文本写入文件
        try {
            FileWriter writer = new FileWriter(hlsFilePath + fileName);
            writer.write(m3u8Text.toString());
            writer.close();
            result.put("success", 1);
            result.put("msg", "M3U8文件已生成");
            result.put("url", "http://" + localIP + ":9800/recordback/" + fileName);
        } catch (IOException e) {
            result.put("success", 0);
            result.put("msg", "写入文件时发生错误：" + e.getMessage());
        }
        return result;
    }

}