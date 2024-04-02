package com.figure.core.service.transcode.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.model.signal.SignalChannelInfo;
import com.figure.core.model.transcode.TranscodeServiceInfo;
import com.figure.core.model.transcode.TranscodeTaskInfo;
import com.figure.core.model.transcode.TranscodeTaskInfoList;
import com.figure.core.model.transcode.TranscodeTaskStreamRel;
import com.figure.core.query.transcode.TranscodeTaskInfoQuery;
import com.figure.core.repository.transcode.TranscodeServiceInfoMapper;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.RocketMQProducer;
import com.figure.core.rocketmq.struct.consumer.TranscodeServiceCheckS2PConsumer;
import com.figure.core.rocketmq.struct.producer.TranscodeServiceCheckP2SProducer;
import com.figure.core.service.signal.ISignalChannelInfoService;
import com.figure.core.service.transcode.ITranscodeServiceInfoService;
import com.figure.core.service.transcode.ITranscodeTaskInfoService;
import com.figure.core.service.transcode.ITranscodeTaskStreamRelService;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 转码资源管理Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2021-09-23 15:43:09
 */
@Service
public class TranscodeServiceInfoServiceImpl extends ServiceImpl<TranscodeServiceInfoMapper, TranscodeServiceInfo> implements ITranscodeServiceInfoService {
    @Resource
    private ITranscodeTaskInfoService transcodeTaskInfoService;
    @Resource
    private ISignalChannelInfoService signalChannelInfoService;
    @Resource
    private ITranscodeTaskStreamRelService transcodeTaskStreamRelService;

    @Resource
    private RocketMQProducer rocketMQProducer;

    @Override
    public List<TranscodeServiceInfo> queryAvailableServiceByFrontId(Integer frontId) {
        LambdaQueryWrapper<TranscodeServiceInfo> transcodeServiceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        transcodeServiceInfoLambdaQueryWrapper.eq(TranscodeServiceInfo::getFrontId, frontId).eq(TranscodeServiceInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        return this.list(transcodeServiceInfoLambdaQueryWrapper);
    }

    @Override
    public boolean updateBatchSynStatusByFrontId(Integer frontId) {
        LambdaUpdateWrapper<TranscodeServiceInfo> transcodeServiceInfoLambdaQueryWrapper = Wrappers.lambdaUpdate();
        transcodeServiceInfoLambdaQueryWrapper.eq(TranscodeServiceInfo::getFrontId, frontId).eq(TranscodeServiceInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        return this.update(transcodeServiceInfoLambdaQueryWrapper);
    }

    @Override
    public List<TranscodeServiceInfo> queryAvailableService() {
        LambdaQueryWrapper<TranscodeServiceInfo> transcodeServiceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        transcodeServiceInfoLambdaQueryWrapper.eq(TranscodeServiceInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        return this.list(transcodeServiceInfoLambdaQueryWrapper);
    }

    @Override
    public List<AvailableTunnelServiceInfo> getTunnelByServiceIdsList(List<String> transcodeServiceIdsList) {
        List<TranscodeServiceInfo> transcodeServiceInfoList = this.queryAvailableService();
        List<AvailableTunnelServiceInfo> availableTunnelServiceInfoList = new ArrayList<>();
        for (TranscodeServiceInfo transcodeServiceInfo : transcodeServiceInfoList) {
            if (transcodeServiceIdsList.size() == 0 ||
                    transcodeServiceIdsList.contains(transcodeServiceInfo.getTranscodeServiceId().toString())) {
                int availableTunnelCount = this.queryAvailableTunnelNumByObj(transcodeServiceInfo);
                AvailableTunnelServiceInfo availableTunnelServiceInfo = new AvailableTunnelServiceInfo(
                        transcodeServiceInfo.getServiceCode(), availableTunnelCount);
                if (availableTunnelCount >= 16) {
                    availableTunnelServiceInfoList.add(availableTunnelServiceInfo);
                }
                if (availableTunnelCount >= 1) {
                    availableTunnelServiceInfoList.add(availableTunnelServiceInfo);
                }
            }
        }
        return availableTunnelServiceInfoList;
    }

    @Data
    public class AvailableTunnelServiceInfo {
        String serviceCode;
        Integer availableTunnelNum;

        public AvailableTunnelServiceInfo(String transcodeServiceInfoId, Integer availableTunnelCount) {
            super();
            this.serviceCode = transcodeServiceInfoId;
            this.availableTunnelNum = availableTunnelCount;
        }
    }

    @Override
    public int queryAvailableTunnelNumByObj(TranscodeServiceInfo transcodeServiceInfo) {
        return transcodeServiceInfo.getTotalResources().intValue() - this.queryUsedTunnelNumByObj(transcodeServiceInfo);
    }

    @Override
    public int queryUsedTunnelNumByObj(TranscodeServiceInfo transcodeServiceInfo) {
        int usedTunnelNum = 0;
        List<Map<String, Object>> transcodeTaskInfoMapList = this.transcodeTaskInfoService.queryTranscodeTaskInfoByServiceId(transcodeServiceInfo.getTranscodeServiceId());
        for (Map<String, Object> transcodeTaskInfoMap : transcodeTaskInfoMapList) {
            if (transcodeTaskInfoMap.containsKey("isThirdParty")
                    && transcodeTaskInfoMap.get("isThirdParty").equals(1)) {
                usedTunnelNum += 16;
            } else {
                if (transcodeTaskInfoMap.containsKey("videoType")) {
                    Integer videoType = Integer.valueOf(transcodeTaskInfoMap.get("videoType").toString());
                    if (0 == videoType.intValue()) {
                        usedTunnelNum += 1;
                    } else if (1 == videoType.intValue()) {
                        usedTunnelNum += 4;
                    } else if (2 == videoType.intValue()) {
                        usedTunnelNum += 16;
                    }
                }
            }
        }
        return usedTunnelNum;
    }

    @Override
    public String getTranscodeServiceCodeByListAndChannelId(List<AvailableTunnelServiceInfo> availableTunnelServiceInfoList, SignalChannelInfo signalChannelInfo) {
        String result = "0";
        Integer videoType = signalChannelInfo.getVideoType();
        Integer tunnelNum = 1;
        if (videoType.intValue() == 1) {
            tunnelNum = 4;
        } else if (videoType.intValue() == 2) {
            tunnelNum = 16;
        }
        List<AvailableTunnelServiceInfo> baseServiceInfoList = new ArrayList<>();
        for (AvailableTunnelServiceInfo availableTunnelServiceInfo : availableTunnelServiceInfoList) {
            baseServiceInfoList.add(availableTunnelServiceInfo);
        }
        for (AvailableTunnelServiceInfo availableTunnelServiceInfo : baseServiceInfoList) {
            if (availableTunnelServiceInfo.getAvailableTunnelNum() >= tunnelNum) {
                result = availableTunnelServiceInfo.getServiceCode();
                availableTunnelServiceInfo.setAvailableTunnelNum(availableTunnelServiceInfo.getAvailableTunnelNum() - tunnelNum);
                break;
            }
        }
        return result;
    }

    @Override
    public List<TranscodeServiceInfo> selectServiceInfoByClusterId(Integer transcodeClusterId) {
        List<TranscodeServiceInfo> transcodeServiceInfoList = this.baseMapper.selectServiceInfoByClusterId(transcodeClusterId);
        return transcodeServiceInfoList;
    }

    @Override
    public void processServiceCheck(TranscodeServiceCheckS2PConsumer consumer) {
        String serviceCode = consumer.getServiceCode();
        LambdaQueryWrapper<TranscodeServiceInfo> transcodeServiceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        transcodeServiceInfoLambdaQueryWrapper.eq(TranscodeServiceInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED)
                .eq(TranscodeServiceInfo::getServiceCode, serviceCode);
        List<TranscodeServiceInfo> transcodeServiceInfoList = this.list(transcodeServiceInfoLambdaQueryWrapper);

        TranscodeTaskInfoQuery transcodeTaskInfoQuery = new TranscodeTaskInfoQuery();
        transcodeTaskInfoQuery.setServiceCode(serviceCode);
        transcodeTaskInfoQuery.setIsDelete(Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<TranscodeTaskInfoList> transcodeTaskInfoList = this.transcodeTaskInfoService.listByQuery(transcodeTaskInfoQuery.autoPageWrapper());

        for (TranscodeTaskInfo transcodeTaskInfo : transcodeTaskInfoList) {
            LambdaQueryWrapper<TranscodeTaskStreamRel> transcodeTaskStreamRelLambdaQueryWrapper = Wrappers.lambdaQuery();
            transcodeTaskStreamRelLambdaQueryWrapper.eq(TranscodeTaskStreamRel::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED)
                    .eq(TranscodeTaskStreamRel::getTranscodeTaskId, transcodeTaskInfo.getTranscodeTaskId());
            transcodeTaskInfo.setTranscodeTaskStreamRelList(this.transcodeTaskStreamRelService.list(transcodeTaskStreamRelLambdaQueryWrapper));
        }

        TranscodeServiceCheckP2SProducer producer = new TranscodeServiceCheckP2SProducer(serviceCode, transcodeTaskInfoList);
        rocketMQProducer.send(RocketMQConstants.TRANSCODE_SERVICE_CHECK_P2S, RocketMQConstants.ROCKETMQ_ALL_TAGS, producer);
    }
}