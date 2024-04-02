package com.figure.core.service.record.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.entity.BaseSyncReturn;
import com.figure.core.model.record.*;
import com.figure.core.model.signal.SignalChannelInfo;
import com.figure.core.model.signal.SignalFrequencyInfo;
import com.figure.core.repository.record.RecordTaskInfoMapper;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.RocketMQProducer;
import com.figure.core.rocketmq.struct.consumer.RecordTaskControlS2PConsumer;
import com.figure.core.rocketmq.struct.consumer.RecordTaskSetS2PConsumer;
import com.figure.core.rocketmq.struct.consumer.RecordTaskStatusS2PConsumer;
import com.figure.core.rocketmq.struct.info.RecordTaskStatusInfo;
import com.figure.core.rocketmq.struct.producer.RecordTaskControlP2SProducer;
import com.figure.core.rocketmq.struct.producer.RecordTaskSetP2SProducer;
import com.figure.core.service.device.IDeviceAlarmMessageService;
import com.figure.core.service.record.IRecordServiceInfoService;
import com.figure.core.service.record.IRecordTaskInfoService;
import com.figure.core.service.record.IRecordTimingRuleService;
import com.figure.core.service.signal.ISignalChannelInfoService;
import com.figure.core.service.signal.ISignalFrequencyInfoService;
import com.figure.core.service.table.IOperateTableService;
import com.figure.core.service.transcode.ITranscodeTaskInfoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 录制任务Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2021-10-19 10:36:18
 */
@Data
@Service
public class RecordTaskInfoServiceImpl extends ServiceImpl<RecordTaskInfoMapper, RecordTaskInfo> implements IRecordTaskInfoService {
    @Value("${monitorService_url}")
    protected String monitorService_url;
    @Resource
    protected RestTemplate restTemplate;
    @Resource
    IRecordServiceInfoService recordServiceInfoService;
    @Resource
    ISignalChannelInfoService signalChannelInfoService;
    @Resource
    ISignalFrequencyInfoService signalFrequencyInfoService;
    @Resource
    ITranscodeTaskInfoService transcodeTaskInfoService;
    @Resource
    IRecordTimingRuleService recordTimingRuleService;
    @Resource
    RocketMQProducer rocketMQProducer;
    @Resource
    IDeviceAlarmMessageService deviceAlarmMessageService;
    @Resource
    IOperateTableService operateTableService;

    @Override
    public Map<String, Object> saveAndSendRecordTask(List<RecordTaskInfo> recordTaskInfoList, List<RecordTimingRule> recordTimingRuleList) {
        Map<String, Object> result = new HashMap<>();

        List<BaseSyncReturn> baseSyncReturnList = new ArrayList<>();
        Map<String, SignalChannelInfo> signalChannelInfoMap = new HashMap<>();
        Map<String, SignalFrequencyInfo> signalFrequencyInfoMap = new HashMap<>();

        for (RecordTaskInfo recordTaskInfo : recordTaskInfoList) {
            BaseSyncReturn baseSyncReturn = new BaseSyncReturn();
            String signalId = recordTaskInfo.getSignalId();

            if (signalId.startsWith("FC")) {
                SignalFrequencyInfo signalFrequencyInfo = this.signalFrequencyInfoService.getById(signalId);
                if (signalFrequencyInfo != null) {
                    signalFrequencyInfoMap.put(signalId, signalFrequencyInfo);
                    recordTaskInfo.setBitRate(signalFrequencyInfo.getBitRate());
                    baseSyncReturn.setResultMsg(signalFrequencyInfo.getFrequencyName());
                    recordTaskInfo.setSignalURL(signalFrequencyInfo.getSignalURL());
                }
            } else if (signalId.startsWith("CC")) {
                SignalChannelInfo signalChannelInfo = this.signalChannelInfoService.getById(signalId);
                if (signalChannelInfo != null) {
                    signalChannelInfoMap.put(signalId, signalChannelInfo);
                    recordTaskInfo.setBitRate(signalChannelInfo.getBitRate());
                    baseSyncReturn.setResultMsg(signalChannelInfo.getChannelName());
                    recordTaskInfo.setSignalURL(signalChannelInfo.getChannelSource());
                }
            }

            if (recordTaskInfo.getRecordTaskId() == null) {
                recordTaskInfo.setTaskStatus(Constants.DATATABLE_SYNSTATUS_NOTSYNCED);
                RecordServiceInfo recordServiceInfo = getRecordServiceInfo(recordTaskInfo, baseSyncReturnList, baseSyncReturn);
                if (recordServiceInfo == null) continue;
                String resultMsg = sendRecordRMQ(recordTaskInfo, recordServiceInfo, null);
                if (resultMsg == null) {
                    baseSyncReturn.setResult(1);
                    baseSyncReturn.setResultMsg(baseSyncReturn.getResultMsg() + ",录制任务下发中");

                    this.save(recordTaskInfo);//保存任务
                    this.recordServiceInfoService.updateById(recordServiceInfo);
                    for (RecordTimingRule recordTimingRule : recordTimingRuleList) {
                        recordTimingRule.setRecordTaskId(recordTaskInfo.getRecordTaskId());
                        recordTimingRuleService.save(recordTimingRule);
                    }

                    String tableName = "record_file_" + recordTaskInfo.getMediaType() + "_" + recordTaskInfo.getSignalId() + "_" + recordTaskInfo.getTranscodeRuleId() + "_" + recordTaskInfo.getIsHLS();

                    if (!this.operateTableService.checkRecordFileTable(tableName)) {
                        CreateRecordFile createRecordFile = new CreateRecordFile(tableName);
                        this.operateTableService.createRecordFileTable(createRecordFile);
                    }
                    this.operateTableService.insertRecordFileNameWithServiceCode(tableName, recordServiceInfo.getServiceCode());
                } else {
                    baseSyncReturn.setResult(0);
                    baseSyncReturn.setResultMsg(resultMsg);
                }

            } else {
                RecordServiceInfo recordServiceInfo = this.recordServiceInfoService.getById(recordTaskInfo.getRecordServiceId());
                String resultMsg = sendRecordRMQ(recordTaskInfo, recordServiceInfo, RocketMQConstants.BUSINESSCODE_EDIT);
                if (resultMsg == null) {
                    this.updateById(recordTaskInfo);
                    recordTaskInfo = this.baseMapper.selectById(recordTaskInfo.getRecordTaskId());
                    LambdaQueryWrapper<RecordTimingRule> recordTimingRuleLambdaQueryWrapper = Wrappers.lambdaQuery();
                    recordTimingRuleLambdaQueryWrapper.eq(RecordTimingRule::getRecordTaskId, recordTaskInfo.getRecordTaskId());
                    recordTimingRuleService.remove(recordTimingRuleLambdaQueryWrapper);
                    for (RecordTimingRule recordTimingRule : recordTimingRuleList) {
                        recordTimingRule.setRecordTaskId(recordTaskInfo.getRecordTaskId());
                        recordTimingRuleService.save(recordTimingRule);
                    }
                    baseSyncReturn.setResult(1);
                    baseSyncReturn.setResultMsg("录制任务下发中");
                } else {
                    baseSyncReturn.setResult(0);
                    baseSyncReturn.setResultMsg(resultMsg);
                }
            }
            baseSyncReturnList.add(baseSyncReturn);
        }

        result.put("return", baseSyncReturnList);
        return result;
    }

    private String sendRecordRMQ(RecordTaskInfo recordTaskInfo, RecordServiceInfo recordServiceInfo, Integer businessCode) {
        RecordTaskSetP2SProducer recordTaskSetP2SProducer = new RecordTaskSetP2SProducer(recordTaskInfo, recordServiceInfo);
        if (businessCode != null) {
            recordTaskSetP2SProducer.getMessageHead().setBusinessCode(businessCode);
        }
        return rocketMQProducer.send(RocketMQConstants.RECORD_TASK_SET_P2S, recordServiceInfo.getServiceCode(), recordTaskSetP2SProducer);
    }

    private RecordServiceInfo getRecordServiceInfo(RecordTaskInfo recordTaskInfo, List<BaseSyncReturn> baseSyncReturnList, BaseSyncReturn baseSyncReturn) {
        RecordServiceInfo recordServiceInfo = null;
        if (recordTaskInfo.getServiceCode() == null && recordTaskInfo.getRecordServiceId() == null) {
            LambdaQueryWrapper<RecordTaskInfo> checkChannelExists = Wrappers.lambdaQuery();
            checkChannelExists.eq(RecordTaskInfo::getSignalId, recordTaskInfo.getSignalId()).eq(RecordTaskInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
            List<RecordTaskInfo> channelExistsTaskList = this.list(checkChannelExists);
            if (channelExistsTaskList.size() > 0) {
                recordServiceInfo = this.recordServiceInfoService.getRecordServiceInfoByServiceCode(channelExistsTaskList.get(0).getServiceCode());
            } else {
                recordServiceInfo = this.recordServiceInfoService.getAvailableRecordServiceInfoByClusterId(recordTaskInfo.getRecordClusterId());
            }
        } else {
            if (recordTaskInfo.getServiceCode() != null) {
                recordServiceInfo = this.recordServiceInfoService.getRecordServiceInfoByServiceCode(recordTaskInfo.getServiceCode());
            }
            if (recordTaskInfo.getRecordServiceId() != null) {
                recordServiceInfo = this.recordServiceInfoService.getById(recordTaskInfo.getRecordServiceId());
            }
        }

        if (recordServiceInfo == null) {
            baseSyncReturn.setResult(0);
            baseSyncReturn.setResultMsg(baseSyncReturn.getResultMsg() + ",录制资源不足");
            baseSyncReturnList.add(baseSyncReturn);
            return null;
        }

        //码率录制时，转码规则为0
        if (recordTaskInfo.getMediaType() == 2) {
            recordTaskInfo.setTranscodeRuleId(0);
        }

        if (recordServiceInfo.getTotalBitRate() - recordServiceInfo.getUsedBitRate() < recordTaskInfo.getBitRate()) {
            baseSyncReturn.setResult(0);
            baseSyncReturn.setResultMsg(baseSyncReturn.getResultMsg() + ",录制资源不足");
            baseSyncReturnList.add(baseSyncReturn);
            return null;
        }

        recordTaskInfo.setServiceCode(recordServiceInfo.getServiceCode());
        recordTaskInfo.setServiceName(recordServiceInfo.getServiceName());
        recordTaskInfo.setServiceIP(recordServiceInfo.getRecordServiceIP());
        recordTaskInfo.setInputIP(recordServiceInfo.getRecordServiceIP());
        recordTaskInfo.setTaskStatus(Constants.TASK_WAIT);

        recordServiceInfo.setUsedBitRate(recordServiceInfo.getUsedBitRate() + recordTaskInfo.getBitRate());
        return recordServiceInfo;
    }

    @Override
    public Map<String, Object> delAndSendRecordTask(List<Integer> ids) {
        Map<String, Object> result = new HashMap<>();
        List<RecordTaskInfo> recordTaskInfoList = this.listByIds(ids);
        List<BaseSyncReturn> baseSyncReturnList = new ArrayList<>();
        for (RecordTaskInfo recordTaskInfo : recordTaskInfoList) {
            BaseSyncReturn baseSyncReturn = new BaseSyncReturn();
            RecordServiceInfo recordServiceInfo = this.recordServiceInfoService.getRecordServiceInfoByServiceCode(recordTaskInfo.getServiceCode());
            String resultMsg = sendRecordRMQ(recordTaskInfo, recordServiceInfo, RocketMQConstants.BUSINESSCODE_DEL);
            if (resultMsg == null) {
                baseSyncReturn.setResult(1);
                baseSyncReturn.setResultMsg("录制任务删除中");
                this.removeByIds(ids);
            } else {
                baseSyncReturn.setResult(0);
                baseSyncReturn.setResultMsg(resultMsg);
            }
            baseSyncReturnList.add(baseSyncReturn);
        }
        result.put("return", baseSyncReturnList);
        return result;
    }

    @Override
    public List<Map<String, Object>> queryRecordTaskInfoByServiceId(Integer recordServiceId) {
        return this.baseMapper.queryRecordTaskInfoByServiceId(recordServiceId);
    }

    @Override
    public List<RecordTaskInfoList> listByQuery(QueryWrapper<RecordTaskInfo> queryWrapper) {
        return this.baseMapper.listByQuery(queryWrapper);
    }

    @Override
    public void sendTaskControl(Integer instruct, String serviceCode, List<RecordTaskInfo> recordTaskInfoList) {
        RecordTaskControlP2SProducer producer = null;
        if (recordTaskInfoList == null) {
            producer = new RecordTaskControlP2SProducer(instruct, serviceCode, RocketMQConstants.IS_ALL_YES, null);
        } else {
            List<Integer> taskID = new ArrayList<>();
            for (RecordTaskInfo recordTaskInfo : recordTaskInfoList) {
                taskID.add(recordTaskInfo.getRecordTaskId());
            }
            producer = new RecordTaskControlP2SProducer(instruct, serviceCode, RocketMQConstants.IS_ALL_NO, null);
        }
        this.rocketMQProducer.send(RocketMQConstants.RECORD_TASK_CONTROL_P2S, serviceCode, producer);
    }

    @Override
    public void processTaskControl(RecordTaskControlS2PConsumer consumer) {
        LambdaUpdateWrapper<RecordTaskInfo> recordTaskInfoLambdaUpdateWrapper = Wrappers.lambdaUpdate();
        if (Objects.equals(consumer.getIsAll(), RocketMQConstants.IS_ALL_NO)) {
            recordTaskInfoLambdaUpdateWrapper.in(RecordTaskInfo::getRecordTaskId, consumer.getTaskIDArray());
        } else if (Objects.equals(consumer.getIsAll(), RocketMQConstants.IS_ALL_YES)) {
            recordTaskInfoLambdaUpdateWrapper.eq(RecordTaskInfo::getServiceCode, consumer.getServiceCode());
        }

        if (Objects.equals(consumer.getActionFlag(), RocketMQConstants.ACTION_FLAG_NORMAL)) {
            if (Objects.equals(consumer.getMessageHead().getBusinessCode(), RocketMQConstants.RECORD_CONTROL_START)
                    || Objects.equals(consumer.getMessageHead().getBusinessCode(), RocketMQConstants.RECORD_CONTROL_RESTART)) {
                recordTaskInfoLambdaUpdateWrapper.set(RecordTaskInfo::getTaskStatus, Constants.TASK_NORMAL);
            } else if (consumer.getMessageHead().getBusinessCode().equals(RocketMQConstants.RECORD_CONTROL_STOP)) {
                recordTaskInfoLambdaUpdateWrapper.set(RecordTaskInfo::getTaskStatus, Constants.TASK_MANUAL_INTERRUPTION);
            }
        } else if (Objects.equals(consumer.getActionFlag(), RocketMQConstants.ACTION_FLAG_ERROR)) {
            recordTaskInfoLambdaUpdateWrapper.set(RecordTaskInfo::getTaskStatus, Constants.TASK_ERROR_INTERRUPTION).set(RecordTaskInfo::getErrorInfo, consumer.getAlarmContent());
        }
        this.update(recordTaskInfoLambdaUpdateWrapper);
    }

    @Override
    public void processTaskSet(RecordTaskSetS2PConsumer consumer) {
        LambdaUpdateWrapper<RecordTaskInfo> recordTaskInfoLambdaUpdateWrapper = Wrappers.lambdaUpdate();
        recordTaskInfoLambdaUpdateWrapper.eq(RecordTaskInfo::getRecordTaskId, consumer.getTaskID()).eq(RecordTaskInfo::getServiceCode, consumer.getServiceCode());
        if (Objects.equals(consumer.getActionFlag(), RocketMQConstants.ACTION_FLAG_NORMAL)) {
            if (Objects.equals(consumer.getMessageHead().getBusinessCode(), RocketMQConstants.RECORD_CONTROL_START)
                    || Objects.equals(consumer.getMessageHead().getBusinessCode(), RocketMQConstants.RECORD_CONTROL_RESTART)) {
                recordTaskInfoLambdaUpdateWrapper.set(RecordTaskInfo::getTaskStatus, Constants.TASK_NORMAL);
            } else if (Objects.equals(consumer.getMessageHead().getBusinessCode(), RocketMQConstants.RECORD_CONTROL_STOP)) {
                recordTaskInfoLambdaUpdateWrapper.set(RecordTaskInfo::getTaskStatus, Constants.TASK_MANUAL_INTERRUPTION);
            }
        } else if (consumer.getActionFlag() == RocketMQConstants.ACTION_FLAG_ERROR) {
            recordTaskInfoLambdaUpdateWrapper.set(RecordTaskInfo::getTaskStatus, Constants.TASK_ERROR_INTERRUPTION).set(RecordTaskInfo::getErrorInfo, consumer.getAlarmContent());
        }
        this.update(recordTaskInfoLambdaUpdateWrapper);
    }

    @Override
    public void processTaskStatus(RecordTaskStatusS2PConsumer consumer) {
        //处理设备报警
//        List<RecordAlarmInfo> recordAlarmInfoList = consumer.getAlarmInfoArray();
//        for(RecordAlarmInfo recordAlarmInfo:recordAlarmInfoList){
//            DeviceAlarmMessage deviceAlarmMessage = new DeviceAlarmMessage(recordAlarmInfo);
//        }

        //处理录制任务状态
        List<RecordTaskStatusInfo> recordTaskStatusInfoList = consumer.getTaskInfoArray();
        for (RecordTaskStatusInfo recordTaskStatusInfo : recordTaskStatusInfoList) {
            LambdaUpdateWrapper<RecordTaskInfo> recordTaskInfoLambdaUpdateWrapper = Wrappers.lambdaUpdate();
            recordTaskInfoLambdaUpdateWrapper.eq(RecordTaskInfo::getRecordTaskId, recordTaskStatusInfo.getTaskID())
                    .set(RecordTaskInfo::getTaskStatus, recordTaskStatusInfo.getTaskState() == 4 ? 1 : recordTaskStatusInfo.getTaskState())
                    .set(RecordTaskInfo::getErrorInfo, recordTaskStatusInfo.getAlarmContent());
            this.update(recordTaskInfoLambdaUpdateWrapper);
        }
    }

    @Override
    public Map<String, Object> operateRecordTask(Integer operation, List<Integer> ids) {
        Map<String, Object> result = new HashMap<>();
        List<RecordTaskInfo> recordTaskInfoList = this.listByIds(ids);
        List<BaseSyncReturn> baseSyncReturnList = new ArrayList<>();
        for (RecordTaskInfo recordTaskInfo : recordTaskInfoList) {
            BaseSyncReturn baseSyncReturn = new BaseSyncReturn();
            RecordServiceInfo recordServiceInfo = this.recordServiceInfoService.getRecordServiceInfoByServiceCode(recordTaskInfo.getServiceCode());
            RecordTaskControlP2SProducer recordTaskControlP2SProducer = new RecordTaskControlP2SProducer(operation, recordServiceInfo.getServiceCode(), RocketMQConstants.IS_ALL_NO, ids);
            String resultMsg = rocketMQProducer.send(RocketMQConstants.RECORD_TASK_CONTROL_P2S, recordServiceInfo.getServiceCode(), recordTaskControlP2SProducer);
            if (resultMsg == null) {
                baseSyncReturn.setResult(1);
                switch (operation) {
                    case 1:
                        baseSyncReturn.setResultMsg("录制任务启动中");
                    case 3:
                        baseSyncReturn.setResultMsg("录制任务停止中");
                    case 4:
                        baseSyncReturn.setResultMsg("录制任务重启中");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + operation);
                }

            } else {
                baseSyncReturn.setResult(0);
                baseSyncReturn.setResultMsg(resultMsg);
            }
            baseSyncReturnList.add(baseSyncReturn);
        }
        result.put("return", baseSyncReturnList);
        return result;
    }

    @Override
    public Map<String, Object> moveAndSendRecordTask(List<Integer> ids, Integer recordServiceId) {
        Map<String, Object> result = new HashMap<>();
        List<BaseSyncReturn> baseSyncReturnList = new ArrayList<>();
        for (Integer id : ids) {
            BaseSyncReturn baseSyncReturn = new BaseSyncReturn();
            RecordTaskInfo recordTaskInfo = this.getById(id);
            String originServiceCode = recordTaskInfo.getServiceCode();
            recordTaskInfo.setRecordServiceId(recordServiceId);
            RecordServiceInfo recordServiceInfo = getRecordServiceInfo(recordTaskInfo, baseSyncReturnList, baseSyncReturn);
            if (recordServiceInfo == null) continue;
            recordTaskInfo.setServiceCode(recordServiceInfo.getServiceCode());
            recordTaskInfo.setServiceName(recordServiceInfo.getServiceName());

            String resultMsg = sendRecordRMQ(recordTaskInfo, recordServiceInfo, RocketMQConstants.BUSINESSCODE_EDIT);
            if (resultMsg == null) {
                baseSyncReturn.setResultMsg(recordTaskInfo.getRecordTaskName());
                baseSyncReturn.setResult(1);
                baseSyncReturn.setResultMsg(baseSyncReturn.getResultMsg() + ",录制任务迁移中");
//                this.removeByIds(ids);
                this.updateById(recordTaskInfo);//保存任务
                this.recordServiceInfoService.updateById(recordServiceInfo);

                //保存迁移前服务的使用码率
                RecordServiceInfo originRecordServiceInfo = this.recordServiceInfoService.getRecordServiceInfoByServiceCode(originServiceCode);
                originRecordServiceInfo.setUsedBitRate(recordServiceInfo.getUsedBitRate() - recordTaskInfo.getBitRate());
                this.recordServiceInfoService.updateById(originRecordServiceInfo);
            } else {
                baseSyncReturn.setResult(0);
                baseSyncReturn.setResultMsg(resultMsg);
            }
            baseSyncReturnList.add(baseSyncReturn);
        }
        result.put("return", baseSyncReturnList);
        return result;
    }

}