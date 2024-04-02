package com.figure.core.service.record.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.model.record.RecordServiceClusterRel;
import com.figure.core.model.record.RecordServiceInfo;
import com.figure.core.model.record.RecordServiceInfoList;
import com.figure.core.model.record.RecordTaskInfo;
import com.figure.core.repository.record.RecordServiceInfoMapper;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.RocketMQProducer;
import com.figure.core.rocketmq.struct.consumer.RecordServiceCheckS2PConsumer;
import com.figure.core.rocketmq.struct.consumer.RecordServiceSetS2PConsumer;
import com.figure.core.rocketmq.struct.producer.RecordServiceCheckP2SProducer;
import com.figure.core.rocketmq.struct.producer.RecordServiceSetP2SProducer;
import com.figure.core.service.record.IRecordServiceClusterRelService;
import com.figure.core.service.record.IRecordServiceInfoService;
import com.figure.core.service.record.IRecordTaskInfoService;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 收录服务Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2021-10-19 10:36:18
 */
@Service
public class RecordServiceInfoServiceImpl extends ServiceImpl<RecordServiceInfoMapper, RecordServiceInfo> implements IRecordServiceInfoService {
    @Resource
    IRecordTaskInfoService recordTaskInfoService;

    @Resource
    IRecordServiceClusterRelService recordServiceClusterRelService;

    @Resource
    RocketMQProducer rocketMQProducer;

    @Override
    public List<AvailableTunnelServiceInfo> getAvailableTunnelServiceInfoList(List<RecordTaskInfo> recordTaskInfoList) {
        List<String> recordServiceIP = new ArrayList<>();
        for (RecordTaskInfo recordTaskInfo : recordTaskInfoList) {
            recordServiceIP.add(recordTaskInfo.getServiceIP());
        }

        List<RecordServiceInfoServiceImpl.AvailableTunnelServiceInfo> availableTunnelServiceInfoList = new ArrayList<>();
        LambdaQueryWrapper<RecordServiceInfo> recordServiceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        recordServiceInfoLambdaQueryWrapper.eq(RecordServiceInfo::getTaskStatus, Constants.DATATABLE_SYNSTATUS_SYNCED);
        List<RecordServiceInfo> recordServiceInfoList = this.list(recordServiceInfoLambdaQueryWrapper);
        for (RecordServiceInfo recordServiceInfo : recordServiceInfoList) {
            if (recordServiceIP.size() == 0 || recordServiceIP.contains(recordServiceInfo.getRecordServiceIP())) {
                RecordServiceInfoServiceImpl.AvailableTunnelServiceInfo availableTunnelServiceInfo = new AvailableTunnelServiceInfo(recordServiceInfo.getRecordServiceIP(),
                        recordServiceInfo.getTotalBitRate() - this.queryUsedTunnelNumByObj(recordServiceInfo));
                if (availableTunnelServiceInfo.getAvailableTunnelNum() > 0) {
                    availableTunnelServiceInfoList.add(availableTunnelServiceInfo);
                }
            }
        }
        sortResource(availableTunnelServiceInfoList);
        return availableTunnelServiceInfoList;
    }

    private void sortResource(List<AvailableTunnelServiceInfo> list) {
        Collections.sort(list, Comparator.comparingInt(AvailableTunnelServiceInfo::getAvailableTunnelNum));
    }

    @Data
    public class AvailableTunnelServiceInfo {
        String recordServiceIP;
        Integer availableTunnelNum;

        public AvailableTunnelServiceInfo(String recordServiceIP, Integer availableTunnelCount) {
            super();
            this.recordServiceIP = recordServiceIP;
            this.availableTunnelNum = availableTunnelCount;
        }
    }

    @Override
    public int queryUsedTunnelNumByObj(RecordServiceInfo recordServiceInfo) {
        int usedTunnelNum = 0;
        List<Map<String, Object>> recordTaskInfoMapList = this.recordTaskInfoService.queryRecordTaskInfoByServiceId(recordServiceInfo.getRecordServiceId());
        for (Map<String, Object> recordTaskInfoMap : recordTaskInfoMapList) {
            if (recordTaskInfoMap.containsKey("isThirdParty") && recordTaskInfoMap.get("isThirdParty").equals("1")) {
                usedTunnelNum += 16;
            } else {
                if (recordTaskInfoMap.containsKey("videoType")) {
                    int videoType = Integer.parseInt(recordTaskInfoMap.get("videoType").toString());
                    if (0 == videoType) {
                        usedTunnelNum += 1;
                    } else if (1 == videoType) {
                        usedTunnelNum += 4;
                    } else if (2 == videoType) {
                        usedTunnelNum += 16;
                    }
                }
            }
        }
        return usedTunnelNum;
    }

    @Override
    public RecordServiceInfo getAvailableRecordServiceInfoByClusterId(Integer recordClusterId) {
        List<RecordServiceInfoList> transcodeServiceInfoListList = this.baseMapper.selectRecordServiceInfoByClusterId(recordClusterId);
        sortResources(transcodeServiceInfoListList);
        if (transcodeServiceInfoListList.size() > 0) {
            return this.baseMapper.selectById(transcodeServiceInfoListList.get(0).getRecordServiceId());
        } else {
            return null;
        }
    }

    @Override
    public RecordServiceInfo getRecordServiceInfoByServiceCode(String serviceCode) {
        LambdaQueryWrapper<RecordServiceInfo> recordServiceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        recordServiceInfoLambdaQueryWrapper.eq(RecordServiceInfo::getServiceCode, serviceCode);
        List<RecordServiceInfo> recordServiceInfoList = this.list(recordServiceInfoLambdaQueryWrapper);
        if (recordServiceInfoList.size() == 1) {
            return recordServiceInfoList.get(0);
        }
        return null;
    }

    @Override
    public void processRecordServiceCheck(RecordServiceCheckS2PConsumer consumer) {
        String serviceCode = consumer.getServiceCode();
        RecordServiceInfo recordServiceInfo = this.getRecordServiceInfoByServiceCode(serviceCode);
        if (recordServiceInfo != null) {
            LambdaQueryWrapper<RecordTaskInfo> recordTaskInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
            recordTaskInfoLambdaQueryWrapper.eq(RecordTaskInfo::getServiceCode, serviceCode).eq(RecordTaskInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
            List<RecordTaskInfo> recordTaskInfoList = this.recordTaskInfoService.list(recordTaskInfoLambdaQueryWrapper);
            RecordServiceCheckP2SProducer producer = new RecordServiceCheckP2SProducer(recordServiceInfo, recordTaskInfoList, consumer.getMessageHead().getMessageID());
            this.rocketMQProducer.send(RocketMQConstants.RECORD_SERVICE_CHECK_P2S, recordServiceInfo.getServiceCode(), producer);
        }
    }

    @Override
    public void sendRecordServiceSet(RecordServiceInfo recordServiceInfo) {
        RecordServiceSetP2SProducer producer = new RecordServiceSetP2SProducer(recordServiceInfo);
        this.rocketMQProducer.send(RocketMQConstants.RECORD_SERVICE_SET_P2S, producer.getServiceCode(), producer);
    }

    @Override
    public void processRecordServiceSet(RecordServiceSetS2PConsumer consumer) {
        String serviceCode = consumer.getServiceCode();
        RecordServiceInfo recordServiceInfo = this.getRecordServiceInfoByServiceCode(serviceCode);
        if (recordServiceInfo != null) {
            LambdaUpdateWrapper<RecordServiceInfo> recordServiceInfoLambdaUpdateWrapper = Wrappers.lambdaUpdate();
            recordServiceInfoLambdaUpdateWrapper.eq(RecordServiceInfo::getServiceCode, serviceCode);
            if (consumer.getActionFlag() == RocketMQConstants.ACTION_FLAG_NORMAL) {
                recordServiceInfoLambdaUpdateWrapper.set(RecordServiceInfo::getTaskStatus, Constants.TASK_NORMAL);
            } else {
                recordServiceInfoLambdaUpdateWrapper.set(RecordServiceInfo::getTaskStatus, Constants.TASK_ERROR_INTERRUPTION)
                        .set(RecordServiceInfo::getErrorInfo, consumer.getAlarmContent());
            }
            this.update(recordServiceInfoLambdaUpdateWrapper);
        }
    }

    @Override
    public List<RecordServiceInfoList> selectServiceInfoByClusterId(Integer recordClusterId) {
        return this.baseMapper.selectRecordServiceInfoByClusterId(recordClusterId);
    }

    @Override
    public List<RecordServiceInfo> selectServiceInfoNotWithCluster() {
        LambdaQueryWrapper<RecordServiceClusterRel> recordServiceClusterRelLambdaQueryWrapper = Wrappers.lambdaQuery();
        recordServiceClusterRelLambdaQueryWrapper.eq(RecordServiceClusterRel::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<RecordServiceClusterRel> recordServiceClusterRelList = this.recordServiceClusterRelService.list(recordServiceClusterRelLambdaQueryWrapper);
        List<Integer> recordServiceIdList = new ArrayList<>();
        for (RecordServiceClusterRel recordServiceClusterRel : recordServiceClusterRelList) {
            recordServiceIdList.add(recordServiceClusterRel.getRecordServiceId());
        }
        LambdaQueryWrapper<RecordServiceInfo> recordServiceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        recordServiceInfoLambdaQueryWrapper.eq(RecordServiceInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED)
                .notIn(RecordServiceInfo::getRecordServiceId, recordServiceIdList);
        return this.list(recordServiceInfoLambdaQueryWrapper);
    }

    private void sortResources(List<RecordServiceInfoList> recordServiceInfoList) {
        recordServiceInfoList.sort(Comparator.comparingInt(o -> (o.getTotalBitRate() - o.getUsedBitRate())));
    }
}