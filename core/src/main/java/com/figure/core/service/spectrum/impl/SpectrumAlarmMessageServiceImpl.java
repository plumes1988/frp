package com.figure.core.service.spectrum.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.spectrum.*;
import com.figure.core.query.spectrum.SpectrumRecordMessageQuery;
import com.figure.core.query.spectrum.SpectrumRecordTracedataQuery;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.spectrum.SpectrumAlarmMessageMapper;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.consumer.SpectrumAnalysisAlarmS2PConsumer;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.spectrum.ISpectrumAlarmMessageService;
import com.figure.core.service.spectrum.ISpectrumAlarmTracedataService;
import com.figure.core.service.spectrum.ISpectrumRecordMessageService;
import com.figure.core.service.spectrum.ISpectrumRecordTracedataService;
import com.figure.core.service.tdengine.TDSpectrumAlarmMessageService;
import com.figure.core.service.tdengine.TDSpectrumAlarmTracedataService;
import com.figure.core.service.tdengine.TDSpectrumRecordMessageService;
import com.figure.core.service.tdengine.TDSpectrumRecordTraceDataService;
import com.figure.core.util.JSONUtil;
import com.figure.core.webSocket.Message;
import com.figure.core.webSocket.WebSocketServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * <p>
 * 频谱报警记录Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2023-11-24 16:44:13
 */
@Service
public class SpectrumAlarmMessageServiceImpl extends ServiceImpl<SpectrumAlarmMessageMapper, SpectrumAlarmMessage> implements ISpectrumAlarmMessageService {

    @Resource
    private WebSocketServer webSocketServer;
    @Resource
    IDeviceInfoService deviceInfoService;
    @Resource
    IRedisTemplateService redisTemplateService;
    @Resource
    ISpectrumRecordMessageService spectrumRecordMessageService;
    @Resource
    ISpectrumAlarmTracedataService spectrumAlarmTracedataService;
    @Resource
    ISpectrumRecordTracedataService spectrumRecordTracedataService;

    @Resource
    TDSpectrumAlarmMessageService tdSpectrumAlarmMessageService;

    @Resource
    TDSpectrumAlarmTracedataService tdSpectrumAlarmTracedataService;

    @Resource
    TDSpectrumRecordMessageService tdSpectrumRecordMessageService;

    @Resource
    TDSpectrumRecordTraceDataService tdSpectrumRecordTraceDataService;

    @Override
    public void acceptAlarm(SpectrumAnalysisAlarmS2PConsumer consumer) {
        SpectrumAnalysisAlarmKey spectrumAnalysisAlarmKey = new SpectrumAnalysisAlarmKey(consumer);
        if (redisTemplateService.lock(RedisConstants.CURR_SPECTRUM_ALARM_MAP_LOCK)) {
            try {
                SpectrumAlarmMessage oldAlarm = removeAlarmByAlarmKey(spectrumAnalysisAlarmKey);
                if (oldAlarm == null) {
                    SpectrumAlarmMessage spectrumAlarmMessage = insertAlarmConsumer(consumer);

                    this.addBeginAlarmByAlarmKey(spectrumAnalysisAlarmKey, spectrumAlarmMessage);
                } else {
                    oldAlarm.setAlarmDuration(consumer.getAlarmDuration());
                    Date startTime = DateHelper.parse(consumer.getStartTime(), DateHelper.patterns_masks[1]);
                    if (startTime.getTime() != oldAlarm.getStartTime().getTime()) {
                        oldAlarm.setStartTime(new Timestamp(startTime.getTime()));
                    }
                    Date endTime = DateHelper.add(startTime, Calendar.SECOND, consumer.getAlarmDuration());
                    oldAlarm.setEndTime(new Timestamp(endTime.getTime()));
                    oldAlarm.setAlarmFlag(consumer.getAlarmFlag());

                    if (oldAlarm.getAlarmFlag().intValue() == Constants.ALARM_ONGOING) {//报警持续中
                        oldAlarm.setLastUpdateTime(new Date());
                        //更新报警缓存
                        this.addBeginAlarmByAlarmKey(spectrumAnalysisAlarmKey, oldAlarm);
                    } else if (oldAlarm.getAlarmFlag().intValue() == Constants.ALARM_STOP) {//报警结束
                        //更新报警
                        //this.updateById(oldAlarm);
                        this.save(oldAlarm);
                        //停止报警频谱数据保存
                        //this.saveTraceData(oldAlarm);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                // 释放分布式锁
                redisTemplateService.unlock(RedisConstants.CURR_SPECTRUM_ALARM_MAP_LOCK);
            }
        }
    }

    private SpectrumAlarmMessage insertAlarmConsumer(SpectrumAnalysisAlarmS2PConsumer consumer) {
        String spectrumServiceMapJson = redisTemplateService.getObjectRedisCache(RedisConstants.CURR_SPECTRUM_DEVICE_MAP,String.class);
        Map<String,Map<String,SpectrumServiceDeviceRel>> spectrumServiceMap = JSON.parseObject(
                spectrumServiceMapJson,
                new TypeReference<Map<String,Map<String,SpectrumServiceDeviceRel>>>(){});
        Map<String,SpectrumServiceDeviceRel> spectrumServiceDeviceRelList = spectrumServiceMap.get(consumer.getServiceCode());
        SpectrumServiceDeviceRel spectrumServiceDeviceRel = spectrumServiceDeviceRelList.get(consumer.getSpectrumCode());
        SpectrumAlarmMessage spectrumAlarmMessage = new SpectrumAlarmMessage(consumer, spectrumServiceDeviceRel.getServiceName(), spectrumServiceDeviceRel.getSpectrumName());
        this.save(spectrumAlarmMessage);
        return spectrumAlarmMessage;
    }

    @Override
    public Map<SpectrumAnalysisAlarmKey, SpectrumAlarmMessage> getBeginAlarmMap() {
        Map<SpectrumAnalysisAlarmKey, SpectrumAlarmMessage> alarmMessageInfoMap = redisTemplateService.getMapRedisByKeyCache(RedisConstants.CURR_SPECTRUM_ALARM_MAP, SpectrumAnalysisAlarmKey.class, SpectrumAlarmMessage.class);
        if (alarmMessageInfoMap == null) {
            alarmMessageInfoMap = new HashMap<>();
            setBeginAlarmMap(alarmMessageInfoMap);
        }
        return alarmMessageInfoMap;
    }

    @Override
    public void setBeginAlarmMap(Map<SpectrumAnalysisAlarmKey, SpectrumAlarmMessage> alarmMessageInfoMap) {
        redisTemplateService.setRedisCache(RedisConstants.CURR_SPECTRUM_ALARM_MAP, alarmMessageInfoMap);
    }

    @Override
    public void addBeginAlarmByAlarmKey(SpectrumAnalysisAlarmKey spectrumAnalysisAlarmKey, SpectrumAlarmMessage alarmMessageInfo) {
        Map<SpectrumAnalysisAlarmKey, SpectrumAlarmMessage> alarmMessageInfoMap = getBeginAlarmMap();
        alarmMessageInfoMap.put(spectrumAnalysisAlarmKey, alarmMessageInfo);
        setBeginAlarmMap(alarmMessageInfoMap);
    }

    @Override
    public SpectrumAlarmMessage removeAlarmByAlarmKey(SpectrumAnalysisAlarmKey spectrumAnalysisAlarmKey) {
        Map<SpectrumAnalysisAlarmKey, SpectrumAlarmMessage> alarmMessageInfoMap = getBeginAlarmMap();
        SpectrumAlarmMessage alarmMessageInfo = alarmMessageInfoMap.remove(spectrumAnalysisAlarmKey);
        if (alarmMessageInfo != null) {
            setBeginAlarmMap(alarmMessageInfoMap);
        }
        return alarmMessageInfo;
    }

    @Override
    public void saveTraceData(SpectrumAlarmMessage spectrumAlarmMessage) {
        int alarmTimeout = 10;

//        LambdaQueryWrapper<SpectrumRecordMessage> spectrumRecordMessageLambdaQueryWrapper = Wrappers.lambdaQuery();
//        spectrumRecordMessageLambdaQueryWrapper.eq(SpectrumRecordMessage::getServiceCode, spectrumAlarmMessage.getServiceCode())
//                .eq(SpectrumRecordMessage::getSpectrumCode, spectrumAlarmMessage.getSpectrumCode())
//                .between(SpectrumRecordMessage::getCreateTime, DateHelper.add(spectrumAlarmMessage.getStartTime(), Calendar.SECOND, -alarmTimeout),
//                        DateHelper.add(spectrumAlarmMessage.getEndTime(), Calendar.SECOND, alarmTimeout));
        SpectrumRecordMessageQuery recordMessageQuery = new SpectrumRecordMessageQuery();
//        recordMessageQuery.setSpectrumCode( spectrumAlarmMessage.getSpectrumCode());
//        recordMessageQuery.setServiceCode(spectrumAlarmMessage.getServiceCode());
        recordMessageQuery.setCreateStart(DateHelper.add(spectrumAlarmMessage.getStartTime(), Calendar.SECOND, -alarmTimeout));
        recordMessageQuery.setCreateEnd(DateHelper.add(spectrumAlarmMessage.getEndTime(), Calendar.SECOND, alarmTimeout));

//        List<SpectrumRecordMessage> spectrumRecordMessagesList = this.spectrumRecordMessageService.list(spectrumRecordMessageLambdaQueryWrapper);
        List<SpectrumRecordMessage> spectrumRecordMessagesList = this.tdSpectrumRecordMessageService.listByWrapper("_"+spectrumAlarmMessage.getSpectrumCode(),recordMessageQuery.autoWrapper());

        for (SpectrumRecordMessage spectrumRecordMessage : spectrumRecordMessagesList) {
            SpectrumRecordTracedataQuery recordTracedataQuery = new SpectrumRecordTracedataQuery();
            recordTracedataQuery.setCreateTime(spectrumRecordMessage.getCreateTime());
            List<SpectrumRecordTracedata> spectrumRecordTracedata = this.tdSpectrumRecordTraceDataService.listByWrapper("_"+spectrumRecordMessage.getSpectrumCode(),recordTracedataQuery.autoWrapper());
            if(spectrumRecordTracedata.size()>0){
                SpectrumAlarmTracedata spectrumAlarmTracedata = new SpectrumAlarmTracedata(spectrumAlarmMessage, spectrumRecordTracedata.get(0));
//            this.spectrumAlarmTracedataService.save(spectrumAlarmTracedata);
                this.tdSpectrumAlarmTracedataService.saveEntity(spectrumAlarmTracedata);
            }
        }
    }

    @Override
    public boolean save(SpectrumAlarmMessage spectrumAlarmMessage) {
//        boolean success = super.save(spectrumAlarmMessage);
        //写入TDengine
        boolean success = this.tdSpectrumAlarmMessageService.saveEntity(spectrumAlarmMessage)>0?true:false;
        sendWebsocketMessage(spectrumAlarmMessage);

        return success;
    }

    @Override
    public boolean updateById(SpectrumAlarmMessage spectrumAlarmMessage) {
        boolean success = super.updateById(spectrumAlarmMessage);
        sendWebsocketMessage(spectrumAlarmMessage);

        return success;
    }

    private void sendWebsocketMessage(SpectrumAlarmMessage spectrumAlarmMessage){
        Message message = new Message(RocketMQConstants.SPECTRUM_ANALYSIS_ALARM_S2P, JSON.toJSONString(spectrumAlarmMessage));
        this.webSocketServer.sendInfo(message);
    }
}