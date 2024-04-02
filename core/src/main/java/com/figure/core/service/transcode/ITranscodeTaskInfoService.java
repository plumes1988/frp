package com.figure.core.service.transcode;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.transcode.TranscodeTaskInfo;
import com.figure.core.model.transcode.TranscodeTaskInfoList;
import com.figure.core.rocketmq.struct.consumer.TranscodeTaskControlS2PConsumer;
import com.figure.core.rocketmq.struct.consumer.TranscodeTaskSetS2PConsumer;
import com.figure.core.rocketmq.struct.consumer.TranscodeTaskStatusS2PConsumer;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 转码规则管理 IService
 * </p>
 *
 * @author feather
 * @date 2021-09-23 15:43:09
 */
public interface ITranscodeTaskInfoService extends IService<TranscodeTaskInfo> {

    /**
     * 根据前端ID查询可用的转码任务
     *
     * @Description:
     * @Param: [frontId]
     * @return: java.util.List<com.figure.core.model.transcode.TranscodeTaskInfo>
     * @Author: feather
     * @Date: 2021/9/29
     */
    List<TranscodeTaskInfo> queryAvailableTranscodeTaskInfoByFrontId(Integer frontId);

    /**
     * 转码服务ID查询使用的转码任务
     *
     * @Description:
     * @Param: [transcodeServiceId]
     * @return: java.util.List<com.figure.core.model.transcode.TranscodeTaskInfo>
     * @Author: feather
     * @Date: 2021/10/19
     */
    List<Map<String, Object>> queryTranscodeTaskInfoByServiceId(Integer transcodeServiceId);

    List<TranscodeTaskInfo> queryTaskByChannelIdAndTaskIds(String channelId, List<String> transcodeTaskIdsList);

    List<Map<String, Object>> getTranscodeTaskInfoByChannelId(String channelId);

    List<TranscodeTaskInfoList> listByQuery(QueryWrapper<TranscodeTaskInfo> queryWrapper);

    Map<String, Object> delAndSendTranscodeTaskInfo(String transcodeTaskIds);

    void processTaskControl(TranscodeTaskControlS2PConsumer consumer);

    void processTaskSet(TranscodeTaskSetS2PConsumer consumer);

    void processTaskStatus(TranscodeTaskStatusS2PConsumer consumer);

    Map<String, Object> operateTranscodeTaskInfo(TranscodeTaskInfo transcodeTaskInfo);

    Map<String, Object> saveAndSendTranscodeTaskInfo(TranscodeTaskInfo transcodeTaskInfo);
}