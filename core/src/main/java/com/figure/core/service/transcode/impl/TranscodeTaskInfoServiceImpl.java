package com.figure.core.service.transcode.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.entity.BaseSyncReturn;
import com.figure.core.model.signal.SignalChannelInfo;
import com.figure.core.model.transcode.*;
import com.figure.core.query.transcode.TranscodeTaskInfoQuery;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.transcode.TranscodeTaskInfoMapper;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.RocketMQProducer;
import com.figure.core.rocketmq.struct.consumer.TranscodeTaskControlS2PConsumer;
import com.figure.core.rocketmq.struct.consumer.TranscodeTaskSetS2PConsumer;
import com.figure.core.rocketmq.struct.consumer.TranscodeTaskStatusS2PConsumer;
import com.figure.core.rocketmq.struct.info.TranscodeTaskAlarmInfo;
import com.figure.core.rocketmq.struct.producer.TranscodeTaskControlP2SProducer;
import com.figure.core.rocketmq.struct.producer.TranscodeTaskSetP2SProducer;
import com.figure.core.service.signal.ISignalChannelInfoService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.service.transcode.ITranscodeRuleInfoService;
import com.figure.core.service.transcode.ITranscodeServiceInfoService;
import com.figure.core.service.transcode.ITranscodeTaskInfoService;
import com.figure.core.service.transcode.ITranscodeTaskStreamRelService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.text.Convert;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 转码规则管理Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2021-09-23 15:43:09
 */
@Data
@Service
public class TranscodeTaskInfoServiceImpl extends ServiceImpl<TranscodeTaskInfoMapper, TranscodeTaskInfo> implements ITranscodeTaskInfoService {
    @Value("${monitorService_url}")
    protected String monitorService_url;

    @Resource
    private ITranscodeServiceInfoService transcodeServiceInfoService;

    @Resource
    private ITranscodeRuleInfoService transcodeRuleInfoService;

    @Resource
    private ISignalChannelInfoService signalChannelInfoService;

    @Resource
    private ITranscodeTaskStreamRelService transcodeTaskStreamRelService;

    @Resource
    private ISysParaService sysParaService;

    @Resource
    private RocketMQProducer rocketMQProducer;

    @Override
    public List<TranscodeTaskInfo> queryAvailableTranscodeTaskInfoByFrontId(Integer frontId) {
        LambdaQueryWrapper<TranscodeTaskInfo> taskInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        taskInfoLambdaQueryWrapper.eq(TranscodeTaskInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        return this.list(taskInfoLambdaQueryWrapper);
    }

    @Override
    public List<Map<String, Object>> queryTranscodeTaskInfoByServiceId(Integer transcodeServiceId) {
        return this.baseMapper.queryTranscodeTaskInfoByServiceId(transcodeServiceId);
    }

    @Override
    public List<TranscodeTaskInfo> queryTaskByChannelIdAndTaskIds(String channelId, List<String> transcodeTaskIdsList) {
        LambdaQueryWrapper<TranscodeTaskInfo> taskInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        taskInfoLambdaQueryWrapper.eq(TranscodeTaskInfo::getSignalId, channelId).in(TranscodeTaskInfo::getTranscodeTaskId, transcodeTaskIdsList);
        return this.list(taskInfoLambdaQueryWrapper);
    }

    @Override
    public Map<String, Object> saveAndSendTranscodeTaskInfo(TranscodeTaskInfo transcodeTaskInfoRequest) {
        Map<String, Object> result = new HashMap<>();
        List<BaseSyncReturn> baseSyncReturnList = new ArrayList<>();

        String channelIds = transcodeTaskInfoRequest.getChannelIds();
        String transcodeRuleIds = transcodeTaskInfoRequest.getTranscodeRuleIds();
        String transcodeServiceIds = transcodeTaskInfoRequest.getTranscodeServiceIds();
        Integer transcodeTaskId = transcodeTaskInfoRequest.getTranscodeTaskId();
        List<TranscodeTaskInfo> transcodeTaskInfoList = new ArrayList<>();

        boolean isEdit = false;

        if (transcodeTaskId == null) {
            List<String> transcodeServiceIdsList = StringUtils.StringToStringList(transcodeServiceIds);

            List<TranscodeServiceInfoServiceImpl.AvailableTunnelServiceInfo> availableTunnelServiceInfoList =
                    this.transcodeServiceInfoService.getTunnelByServiceIdsList(transcodeServiceIdsList);

            if (availableTunnelServiceInfoList.size() == 0) {
                BaseSyncReturn baseSyncReturn = new BaseSyncReturn();
                baseSyncReturn.setResult(0);
                baseSyncReturn.setResultMsg("转码资源不足。");
                baseSyncReturnList.add(baseSyncReturn);
                result.put("return", baseSyncReturnList);
                return result;
            }

            List<String> channelIdsList = StringUtils.StringToStringList(channelIds);
            List<String> transcodeRuleIdsList = StringUtils.StringToStringList(transcodeRuleIds);
            for (String channelId : channelIdsList) {
                for (String transcodeRuleId : transcodeRuleIdsList) {
                    Integer transcodeRuleIdInteger = Integer.valueOf(transcodeRuleId);

                    TranscodeTaskInfo transcodeTaskInfo = new TranscodeTaskInfo();
                    transcodeTaskInfo.setTranscodeRuleId(transcodeRuleIdInteger);

                    TranscodeRuleInfo transcodeRuleInfo = this.transcodeRuleInfoService.getById(transcodeTaskInfo.getTranscodeRuleId());

                    transcodeTaskInfo.setTranscodeRuleInfo(transcodeRuleInfo);
                    transcodeTaskInfo.setSignalId(channelId);

                    SignalChannelInfo signalChannelInfo = this.signalChannelInfoService.getById(channelId);
                    transcodeTaskInfo.setTranscodeTaskName("频道:" + signalChannelInfo.getChannelName() + "，规则:" + transcodeRuleInfo.getRuleName());
                    if (transcodeTaskInfo.getSourceURL() == null) {
                        transcodeTaskInfo.setSourceURL(signalChannelInfo.getChannelSource());
                    }

                    transcodeTaskInfo.setIsDelete(Constants.DATATABLE_ISDELETE_NOTDELETED);
                    transcodeTaskInfo.setCreateTime(new Date());

                    String transcodeServiceCode = this.transcodeServiceInfoService.getTranscodeServiceCodeByListAndChannelId(availableTunnelServiceInfoList, signalChannelInfo);
                    LambdaQueryWrapper<TranscodeServiceInfo> transcodeServiceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
                    transcodeServiceInfoLambdaQueryWrapper.eq(TranscodeServiceInfo::getServiceCode, transcodeServiceCode).eq(TranscodeServiceInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
                    List<TranscodeServiceInfo> transcodeServiceInfoList = this.transcodeServiceInfoService.list(transcodeServiceInfoLambdaQueryWrapper);
                    String controlIP = transcodeServiceInfoList.get(0).getTranscodeServiceIP();
                    transcodeTaskInfo.setServiceCode(transcodeServiceCode);
                    transcodeTaskInfo.setOsdSwitch(transcodeRuleInfo.getIsOSD().toString());
                    transcodeTaskInfo.setTaskStatus(Constants.TASK_WAIT);
                    transcodeTaskInfo.setTaskMode((transcodeTaskInfoRequest.getTaskMode() == null || transcodeTaskInfoRequest.getTaskMode() == 1) ? 0 : 1);

                    this.save(transcodeTaskInfo);
                    List<TranscodeTaskStreamRel> transcodeTaskStreamRelList = getTranscodeTaskStreamRelList(transcodeRuleIdInteger, transcodeTaskInfo, transcodeRuleInfo, signalChannelInfo, controlIP);
                    transcodeTaskInfo.setTranscodeTaskStreamRelList(transcodeTaskStreamRelList);
                    transcodeTaskInfoList.add(transcodeTaskInfo);
                }
            }
        } else {
            isEdit = true;
            TranscodeTaskInfo transcodeTaskInfo = this.getById(transcodeTaskInfoRequest.getTranscodeTaskId());
            TranscodeServiceInfo transcodeServiceInfo = this.transcodeServiceInfoService.getById(Integer.valueOf(transcodeTaskInfoRequest.getTranscodeServiceIds()));
            transcodeTaskInfo.setServiceCode(transcodeServiceInfo.getServiceCode());
            String controlIP = transcodeServiceInfo.getTranscodeServiceIP();

            if (transcodeRuleIds.equals(transcodeTaskInfo.getTranscodeRuleId().toString())) {
                LambdaQueryWrapper<TranscodeTaskStreamRel> transcodeTaskStreamRelLambdaQueryWrapper = Wrappers.lambdaQuery();
                transcodeTaskStreamRelLambdaQueryWrapper.eq(TranscodeTaskStreamRel::getTranscodeTaskId, transcodeTaskInfo.getTranscodeTaskIds());
                List<TranscodeTaskStreamRel> transcodeTaskStreamRelList = this.transcodeTaskStreamRelService.list(transcodeTaskStreamRelLambdaQueryWrapper);
                transcodeTaskInfo.setTranscodeTaskStreamRelList(transcodeTaskStreamRelList);
            } else {
                TranscodeRuleInfo transcodeRuleInfo = this.transcodeRuleInfoService.getById(transcodeRuleIds);
                transcodeTaskInfo.setTranscodeRuleInfo(transcodeRuleInfo);
                transcodeTaskInfo.setTranscodeRuleId(transcodeRuleInfo.getRuleId());
                SignalChannelInfo signalChannelInfo = this.signalChannelInfoService.getById(transcodeTaskInfo.getSignalId());
                transcodeTaskInfo.setTranscodeTaskName("频道:" + signalChannelInfo.getChannelName() + "，规则:" + transcodeRuleInfo.getRuleName());
                QueryWrapper<TranscodeTaskStreamRel> transcodeTaskStreamRelQuery = new QueryWrapper<>();
                transcodeTaskStreamRelQuery.eq("transcodeTaskId", transcodeTaskInfo.getTranscodeTaskId());
                this.transcodeTaskStreamRelService.remove(transcodeTaskStreamRelQuery);

                List<TranscodeTaskStreamRel> transcodeTaskStreamRelList = getTranscodeTaskStreamRelList(transcodeRuleInfo.getRuleId(), transcodeTaskInfo, transcodeRuleInfo, signalChannelInfo, controlIP);
                transcodeTaskInfo.setTranscodeTaskStreamRelList(transcodeTaskStreamRelList);
            }
            SignalChannelInfo signalChannelInfo = this.signalChannelInfoService.getById(channelIds);
            transcodeTaskInfo.setSourceURL(signalChannelInfo.getChannelSource());

            this.updateById(transcodeTaskInfo);
            transcodeTaskInfoList.add(transcodeTaskInfo);
        }
        if (transcodeTaskInfoList.size() > 0) {
            for (TranscodeTaskInfo transcodeTaskInfo : transcodeTaskInfoList) {
                BaseSyncReturn baseSyncReturn = new BaseSyncReturn();
                baseSyncReturn.setResultMsg(transcodeTaskInfo.getTranscodeTaskName());
                TranscodeTaskInfoQuery transcodeTaskInfoQuery = new TranscodeTaskInfoQuery();
                transcodeTaskInfoQuery.setTranscodeTaskId(transcodeTaskInfo.getTranscodeTaskId());
                List<TranscodeTaskInfoList> transcodeTaskInfoListList = this.baseMapper.listByQuery(transcodeTaskInfoQuery.autoPageWrapper());
                TranscodeTaskSetP2SProducer transcodeTaskSetP2SProducer = new TranscodeTaskSetP2SProducer(transcodeTaskInfoListList.get(0), transcodeTaskInfo.getTranscodeTaskStreamRelList());
                if (isEdit) {
                    transcodeTaskSetP2SProducer.getMessageHead().setBusinessCode(RocketMQConstants.BUSINESSCODE_EDIT);
                }
                rocketMQProducer.send(RocketMQConstants.TRANSCODE_TASK_SET_P2S, transcodeTaskInfo.getServiceCode(), transcodeTaskSetP2SProducer);
                baseSyncReturn.setResult(1);
                baseSyncReturn.setResultMsg(baseSyncReturn.getResultMsg() + ",转码任务下发中");
                baseSyncReturnList.add(baseSyncReturn);
            }
        } else {
            BaseSyncReturn baseSyncReturn = new BaseSyncReturn();
            baseSyncReturn.setResult(0);
            baseSyncReturn.setResultMsg("");
            baseSyncReturnList.add(baseSyncReturn);
        }


        result.put("return", baseSyncReturnList);
        return result;
    }

    private List<TranscodeTaskStreamRel> getTranscodeTaskStreamRelList(Integer transcodeRuleId, TranscodeTaskInfo transcodeTaskInfo, TranscodeRuleInfo transcodeRuleInfo, SignalChannelInfo signalChannelInfo, String controlIP) {
        List<TranscodeTaskStreamRel> transcodeTaskStreamRelList = new ArrayList<>();
        if (transcodeRuleInfo.getShowUDP() == 1) {
            TranscodeTaskStreamRel transcodeTaskStreamRel = new TranscodeTaskStreamRel();
            transcodeTaskStreamRel.setTranscodeTaskId(transcodeTaskInfo.getTranscodeTaskId());
            transcodeTaskStreamRel.setStreamType(Constants.TRANSCODE_UDP);
            Map<String, String> recordParamMap = sysParaService.getParamCacheByName(RedisConstants.RECORD_PARAM);
            String localIP = recordParamMap.get(Constants.LOCAL_IP);
            transcodeTaskStreamRel.setStreamURL("udp://235." + localIP.substring(0, localIP.lastIndexOf("."))
                    + ":" + (15000 + signalChannelInfo.getServiceId() + transcodeRuleId));
            this.transcodeTaskStreamRelService.save(transcodeTaskStreamRel);
            transcodeTaskStreamRelList.add(transcodeTaskStreamRel);
        }
        if (transcodeRuleInfo.getShowHLS() == 1) {
            TranscodeTaskStreamRel transcodeTaskStreamRel = new TranscodeTaskStreamRel();
            transcodeTaskStreamRel.setTranscodeTaskId(transcodeTaskInfo.getTranscodeTaskId());
            transcodeTaskStreamRel.setStreamType(Constants.TRANSCODE_HLS);
            transcodeTaskStreamRel.setStreamURL("http://" + controlIP + ":9800/live/" + signalChannelInfo.getChannelId()
                    + "_" + transcodeRuleInfo.getVideoWidth() + "_" + transcodeRuleInfo.getVideoHeight()
                    + "_" + transcodeRuleInfo.getVideoCodeRate() + "_" + transcodeRuleInfo.getIsOSD() + ".m3u8");
            this.transcodeTaskStreamRelService.save(transcodeTaskStreamRel);
            transcodeTaskStreamRelList.add(transcodeTaskStreamRel);
        }
        if (transcodeRuleInfo.getShowRTMP() == 1) {
            TranscodeTaskStreamRel transcodeTaskStreamRel = new TranscodeTaskStreamRel();
            transcodeTaskStreamRel.setTranscodeTaskId(transcodeTaskInfo.getTranscodeTaskId());
            transcodeTaskStreamRel.setStreamType(Constants.TRANSCODE_RTMP);
            transcodeTaskStreamRel.setStreamURL("rtmp://" + controlIP + ":1935/live/" + signalChannelInfo.getChannelId()
                    + "_" + transcodeRuleInfo.getVideoWidth() + "_" + transcodeRuleInfo.getVideoHeight()
                    + "_" + transcodeRuleInfo.getVideoCodeRate() + "_" + transcodeRuleInfo.getIsOSD());
            this.transcodeTaskStreamRelService.save(transcodeTaskStreamRel);
            transcodeTaskStreamRelList.add(transcodeTaskStreamRel);
        }
        if (transcodeRuleInfo.getShowHTTP_FLV() == 1) {
            TranscodeTaskStreamRel transcodeTaskStreamRel = new TranscodeTaskStreamRel();
            transcodeTaskStreamRel.setTranscodeTaskId(transcodeTaskInfo.getTranscodeTaskId());
            transcodeTaskStreamRel.setStreamType(Constants.TRANSCODE_HTTP_FLV);
            transcodeTaskStreamRel.setStreamURL("http://" + controlIP + ":9800/flv?port=1935&app=live&stream=" + signalChannelInfo.getChannelId()
                    + "_" + transcodeRuleInfo.getVideoWidth() + "_" + transcodeRuleInfo.getVideoHeight()
                    + "_" + transcodeRuleInfo.getVideoCodeRate() + "_" + transcodeRuleInfo.getIsOSD());
            this.transcodeTaskStreamRelService.save(transcodeTaskStreamRel);
            transcodeTaskStreamRelList.add(transcodeTaskStreamRel);
        }
        return transcodeTaskStreamRelList;
    }

    @Override
    public List<Map<String, Object>> getTranscodeTaskInfoByChannelId(String channelId) {
        if (StringUtils.isNotBlank(channelId)) {
            List<Map<String, Object>> transcodeTaskInfoList = this.baseMapper.getByChannelId(Arrays.asList(Convert.toStrArray(channelId)));
            for (Map<String, Object> info : transcodeTaskInfoList) {
                Integer transcodeTaskId = Integer.valueOf(info.get("transcodeTaskId").toString());
                LambdaQueryWrapper<TranscodeTaskStreamRel> transcodeTaskStreamRelLambdaQueryWrapper = Wrappers.lambdaQuery();
                transcodeTaskStreamRelLambdaQueryWrapper.eq(TranscodeTaskStreamRel::getTranscodeTaskId, transcodeTaskId);
                List<TranscodeTaskStreamRel> transcodeTaskStreamRelList = transcodeTaskStreamRelService.list(transcodeTaskStreamRelLambdaQueryWrapper);
                info.put("stream", transcodeTaskStreamRelList);
            }
            return transcodeTaskInfoList;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<TranscodeTaskInfoList> listByQuery(QueryWrapper<TranscodeTaskInfo> queryWrapper) {
        return this.baseMapper.listByQuery(queryWrapper);
    }

    @Override
    public Map<String, Object> delAndSendTranscodeTaskInfo(String transcodeTaskIds) {
        Map<String, Object> result = new HashMap<>();
        List<BaseSyncReturn> baseSyncReturnList = new ArrayList<>();
        if (StringUtils.isNotBlank(transcodeTaskIds)) {
            List<Integer> transcodeTaskIdList = StringUtils.StringToIntList(transcodeTaskIds);
            for (Integer transcodeTaskId : transcodeTaskIdList) {
                BaseSyncReturn baseSyncReturn = new BaseSyncReturn();
                TranscodeTaskInfoQuery transcodeTaskInfoQuery = new TranscodeTaskInfoQuery();
                transcodeTaskInfoQuery.setTranscodeTaskId(transcodeTaskId);
                List<TranscodeTaskInfoList> transcodeTaskInfoListList = this.baseMapper.listByQuery(transcodeTaskInfoQuery.autoPageWrapper());
                LambdaQueryWrapper<TranscodeTaskStreamRel> transcodeTaskStreamRelLambdaQueryWrapper = Wrappers.lambdaQuery();
                transcodeTaskStreamRelLambdaQueryWrapper.eq(TranscodeTaskStreamRel::getTranscodeTaskId, transcodeTaskId)
                        .eq(TranscodeTaskStreamRel::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
                List<TranscodeTaskStreamRel> transcodeTaskStreamRelLIst = this.transcodeTaskStreamRelService.list(transcodeTaskStreamRelLambdaQueryWrapper);

                TranscodeTaskInfo transcodeTaskInfo = transcodeTaskInfoListList.get(0);
                transcodeTaskInfo.setTaskStatus(Constants.TASK_WAIT);
                TranscodeTaskSetP2SProducer transcodeTaskSetP2SProducer = new TranscodeTaskSetP2SProducer(transcodeTaskInfoListList.get(0), transcodeTaskStreamRelLIst);
                transcodeTaskSetP2SProducer.getMessageHead().setBusinessCode(RocketMQConstants.BUSINESSCODE_DEL);
                rocketMQProducer.send(RocketMQConstants.TRANSCODE_TASK_SET_P2S, transcodeTaskInfoListList.get(0).getServiceCode(), transcodeTaskSetP2SProducer);

                this.save(transcodeTaskInfo);
                baseSyncReturn.setResult(1);
                baseSyncReturn.setResultMsg(baseSyncReturn.getResultMsg() + ",录制任务删除中");
                baseSyncReturnList.add(baseSyncReturn);
            }
        } else {
            BaseSyncReturn baseSyncReturn = new BaseSyncReturn();
            baseSyncReturn.setResult(0);
            baseSyncReturn.setResultMsg("未找到对应的转码任务。");
        }
        result.put("return", baseSyncReturnList);
        return result;
    }

    @Override
    public void processTaskControl(TranscodeTaskControlS2PConsumer consumer) {
        if (consumer.getActionFlag() == RocketMQConstants.ACTION_FLAG_NORMAL) {
            List<Integer> taskIdArray = consumer.getTaskIDArray();
            LambdaUpdateWrapper<TranscodeTaskInfo> transcodeTaskInfoLambdaUpdateWrapper = Wrappers.lambdaUpdate();
            if (consumer.getIsAll() == RocketMQConstants.IS_ALL_YES) {
                transcodeTaskInfoLambdaUpdateWrapper.eq(TranscodeTaskInfo::getServiceCode, consumer.getServiceCode())
                        .eq(TranscodeTaskInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED)
                        .set(TranscodeTaskInfo::getTaskStatus, consumer.getMessageHead().getBusinessCode());
            } else {
                transcodeTaskInfoLambdaUpdateWrapper.eq(TranscodeTaskInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED)
                        .in(TranscodeTaskInfo::getTranscodeTaskId, taskIdArray)
                        .set(TranscodeTaskInfo::getTaskStatus, consumer.getMessageHead().getBusinessCode());
            }
            this.update(transcodeTaskInfoLambdaUpdateWrapper);
        }
    }

    @Override
    public void processTaskSet(TranscodeTaskSetS2PConsumer consumer) {
        if (consumer.getActionFlag() == RocketMQConstants.ACTION_FLAG_NORMAL) {
            LambdaUpdateWrapper<TranscodeTaskInfo> transcodeTaskInfoLambdaUpdateWrapper = Wrappers.lambdaUpdate();
            transcodeTaskInfoLambdaUpdateWrapper.eq(TranscodeTaskInfo::getTranscodeTaskId, consumer.getTaskID());
            if (consumer.getMessageHead().getBusinessCode() == 2) {
                transcodeTaskInfoLambdaUpdateWrapper.set(TranscodeTaskInfo::getIsDelete, Constants.DATATABLE_ISDELETE_DELETED);
            }
            this.update(transcodeTaskInfoLambdaUpdateWrapper);
        }
    }

    /**
     * 只做了更新任务状态，报警功能未完成
     *
     * @param consumer
     */
    @Override
    public void processTaskStatus(TranscodeTaskStatusS2PConsumer consumer) {
        if (consumer.getServiceState() == 1) {
            List<TranscodeTaskAlarmInfo> alarmInfoList = consumer.getTaskInfoArray();
            for (TranscodeTaskAlarmInfo alarmInfo : alarmInfoList) {
                LambdaUpdateWrapper<TranscodeTaskInfo> transcodeTaskInfoLambdaUpdateWrapper = Wrappers.lambdaUpdate();
                transcodeTaskInfoLambdaUpdateWrapper.eq(TranscodeTaskInfo::getTranscodeTaskId, alarmInfo.getTaskID())
                        .set(TranscodeTaskInfo::getTaskStatus, alarmInfo.getState());
                this.update(transcodeTaskInfoLambdaUpdateWrapper);
            }
        }
    }

    @Override
    public Map<String, Object> operateTranscodeTaskInfo(TranscodeTaskInfo transcodeTaskInfo) {
        Map<String, Object> result = new HashMap<>();
        List<BaseSyncReturn> baseSyncReturnList = new ArrayList<>();
        TranscodeTaskControlP2SProducer producer = new TranscodeTaskControlP2SProducer(transcodeTaskInfo);
        producer.getMessageHead().setBusinessCode(transcodeTaskInfo.getOperation());
        rocketMQProducer.send(RocketMQConstants.TRANSCODE_TASK_CONTROL_P2S, producer.getServiceCode(), producer);
        BaseSyncReturn baseSyncReturn = new BaseSyncReturn();
        String operation = "";
        switch (transcodeTaskInfo.getOperation()) {
            case 2:
                operation = "停止";
            case 3:
                operation = "重启";
            case 1:
            default:
                operation = "启动";
        }
        baseSyncReturn.setResultMsg("转码任务正在" + operation);
        baseSyncReturn.setResult(1);
        baseSyncReturnList.add(baseSyncReturn);
        result.put("return", baseSyncReturnList);
        return result;
    }


}