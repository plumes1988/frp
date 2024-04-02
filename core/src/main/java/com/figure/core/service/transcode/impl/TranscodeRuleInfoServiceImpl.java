package com.figure.core.service.transcode.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.model.transcode.TranscodeRuleInfo;
import com.figure.core.repository.transcode.TranscodeRuleInfoMapper;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.RocketMQProducer;
import com.figure.core.rocketmq.struct.consumer.TranscodeRuleSetS2PConsumer;
import com.figure.core.rocketmq.struct.producer.TranscodeRuleSetP2SProducer;
import com.figure.core.service.transcode.ITranscodeRuleInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 转码规则管理Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2021-09-23 15:43:09
 */
@Service
public class TranscodeRuleInfoServiceImpl extends ServiceImpl<TranscodeRuleInfoMapper, TranscodeRuleInfo> implements ITranscodeRuleInfoService {

    private final Map<Long, List<TranscodeRuleInfo>> producerMap = new ConcurrentHashMap<>();
    @Resource
    private RocketMQProducer rocketMQProducer;

    @Override
    public Map<String, Object> sync() {
        Map<String, Object> result = new HashMap<>();
        LambdaQueryWrapper<TranscodeRuleInfo> transcodeRuleInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        transcodeRuleInfoLambdaQueryWrapper.eq(TranscodeRuleInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<TranscodeRuleInfo> transcodeRuleInfoList = this.list(transcodeRuleInfoLambdaQueryWrapper);
        TranscodeRuleSetP2SProducer producer = new TranscodeRuleSetP2SProducer(transcodeRuleInfoList);
        rocketMQProducer.send(RocketMQConstants.TRANSCODE_RULE_SET_P2S, RocketMQConstants.ROCKETMQ_ALL_TAGS, producer);
        producerMap.put(producer.getMessageHead().getMessageID(), transcodeRuleInfoList);
        result.put("status", 1);
        result.put("msg", "下发转码规则中");
        return result;
    }

    @Override
    public void processRuleSet(TranscodeRuleSetS2PConsumer consumer) {
        List<TranscodeRuleInfo> transcodeRuleInfoList = producerMap.remove(consumer.getMessageHead().getMessageID());
        if (transcodeRuleInfoList != null && consumer.getActionFlag() == RocketMQConstants.ACTION_FLAG_NORMAL) {
            LambdaUpdateWrapper<TranscodeRuleInfo> transcodeRuleInfoUpdateWrapper = Wrappers.lambdaUpdate();
            transcodeRuleInfoUpdateWrapper.set(TranscodeRuleInfo::getSyncStatus, Constants.TASK_NORMAL);
            this.update(transcodeRuleInfoUpdateWrapper);
        }
    }
}