package com.figure.core.service.alarm.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.alarm.AlarmKey;
import com.figure.core.model.alarm.AlarmMessageInfo;
import com.figure.core.model.front.FrontLogicPosition;
import com.figure.core.model.front.FrontStationInfo;
import com.figure.core.model.signal.SignalAlarmtypeInfo;
import com.figure.core.model.signal.SignalChannelInfo;
import com.figure.core.model.signal.SignalFrequencyInfo;
import com.figure.core.model.signal.SignalLogicChannel;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.alarm.AlarmMessageInfoMapper;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.RocketMQProducer;
import com.figure.core.rocketmq.struct.consumer.SignalAlarmCheckS2PConsumer;
import com.figure.core.rocketmq.struct.consumer.SignalAlarmUpdateS2PConsumer;
import com.figure.core.rocketmq.struct.producer.SignalAlarmCheckP2SProducer;
import com.figure.core.rocketmq.struct.producer.SignalAlarmUpdateP2SProducer;
import com.figure.core.service.alarm.IAlarmEventInfoService;
import com.figure.core.service.alarm.IAlarmMessageInfoService;
import com.figure.core.service.front.IFrontLogicPositionService;
import com.figure.core.service.front.IFrontStationInfoService;
import com.figure.core.service.signal.ISignalAlarmtypeInfoService;
import com.figure.core.service.signal.ISignalChannelInfoService;
import com.figure.core.service.signal.ISignalFrequencyInfoService;
import com.figure.core.service.signal.ISignalLogicChannelService;
import com.figure.core.webSocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * <p>
 * 信号报警记录Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2023-03-22 10:53:57
 */
@Service
public class AlarmMessageInfoServiceImpl extends ServiceImpl<AlarmMessageInfoMapper, AlarmMessageInfo> implements IAlarmMessageInfoService {

    @Resource
    IAlarmMessageInfoService alarmMessageInfoService;

    @Resource
    ISignalLogicChannelService signalLogicChannelService;

    @Resource
    IFrontStationInfoService frontStationInfoService;

    @Resource
    IFrontLogicPositionService frontLogicPositionService;

    @Resource
    ISignalChannelInfoService signalChannelInfoService;

    @Resource
    ISignalFrequencyInfoService signalFrequencyInfoService;

    @Resource
    ISignalAlarmtypeInfoService logicThresholdInfoService;

    @Resource
    IRedisTemplateService redisTemplateService;

    @Resource
    IAlarmEventInfoService alarmEventInfoService;

    @Resource
    private RocketMQProducer rocketMQSender;

    private Map<Integer, SignalAlarmtypeInfo> logicThresholdInfoMap;

    private Map<String, SignalLogicChannel> logicByChannelCodeMap;

    private Map<Integer, FrontStationInfo> frontStationByFrontIdMap;
    private Map<Integer, FrontLogicPosition> positionByPositionIdMap;

    private Map<String, SignalChannelInfo> channelBychannelIdMap;

    private Map<String, SignalFrequencyInfo> frequencyByFrequencyIdMap;

    @Autowired
    WebSocketServer webSocketServer;

    @Override
    public void processAlarmUpdate(SignalAlarmUpdateS2PConsumer consumer) {
        AlarmKey alarmKey = new AlarmKey(consumer);
        if (redisTemplateService.lock(RedisConstants.CURR_ALARM_MAP_LOCK)) {
            try {
                //获取缓存报警
                AlarmMessageInfo oldAlarm = removeAlarmByAlarmKey(alarmKey);
                //报警过滤

                //已经存在报警
                if (oldAlarm == null) {
                    //报警入库
                    AlarmMessageInfo alarmMessageInfo = insertAlarmConsumer(consumer);
                    if (alarmMessageInfo == null) {
                        return;
                    }
                    //新增报警缓存
                    this.addBeginAlarmByAlarmKey(alarmKey, alarmMessageInfo);
                    //报警事件处理
                    this.alarmEventInfoService.processEventInfo(alarmMessageInfo);
                } else {
                    oldAlarm.setDuration(consumer.getDuration());
                    Date startTime = DateHelper.parse(consumer.getStartTime(), DateHelper.patterns_masks[1]);
                    if (startTime.getTime() != oldAlarm.getStartTime().getTime()) {
                        oldAlarm.setStartTime(new Timestamp(startTime.getTime()));
                    }
                    Date endTime = DateHelper.add(startTime, Calendar.MILLISECOND, consumer.getDuration());
                    oldAlarm.setEndTime(new Timestamp(endTime.getTime()));
                    oldAlarm.setAlarmFlag(consumer.getAlarmFlag());

                    if (oldAlarm.getAlarmFlag().intValue() == Constants.ALARM_ONGOING) {//报警持续中
                        oldAlarm.setLastUpdateTime(new Date());
                        //更新报警缓存
                        this.addBeginAlarmByAlarmKey(alarmKey, oldAlarm);
                    } else if (oldAlarm.getAlarmFlag().intValue() == Constants.ALARM_STOP) {//报警结束
                        //更新报警
                        this.alarmMessageInfoService.updateById(oldAlarm);
                        //报警广播
                        this.sendAlarmUpdate(oldAlarm);
                        //报警事件处理
                        this.alarmEventInfoService.processEventInfo(oldAlarm);
                    }
                }
            } finally {
                // 释放分布式锁
                redisTemplateService.unlock(RedisConstants.CURR_ALARM_MAP_LOCK);
            }
        } else {
            //未获取到锁的处理
            System.out.println("---no lock obtained---");
        }
    }

    @Override
    public Map<AlarmKey, AlarmMessageInfo> getBeginAlarmMap() {
        Map<AlarmKey, AlarmMessageInfo> alarmMessageInfoMap = redisTemplateService.getMapRedisByKeyCache(RedisConstants.CURR_ALARM_MAP, AlarmKey.class, AlarmMessageInfo.class);
        if (alarmMessageInfoMap == null) {
            alarmMessageInfoMap = new HashMap<>();
            setBeginAlarmMap(alarmMessageInfoMap);
        }
        return alarmMessageInfoMap;
    }

    @Override
    public void setBeginAlarmMap(Map<AlarmKey, AlarmMessageInfo> alarmMessageInfoMap) {
        redisTemplateService.setRedisCache(RedisConstants.CURR_ALARM_MAP, alarmMessageInfoMap);
    }

    @Override
    public void addBeginAlarmByAlarmKey(AlarmKey alarmKey, AlarmMessageInfo alarmMessageInfo) {
        Map<AlarmKey, AlarmMessageInfo> alarmMessageInfoMap = getBeginAlarmMap();
        alarmMessageInfoMap.put(alarmKey, alarmMessageInfo);
        setBeginAlarmMap(alarmMessageInfoMap);
    }

    @Override
    public AlarmMessageInfo removeAlarmByAlarmKey(AlarmKey alarmKey) {
        Map<AlarmKey, AlarmMessageInfo> alarmMessageInfoMap = getBeginAlarmMap();
        AlarmMessageInfo alarmMessageInfo = alarmMessageInfoMap.remove(alarmKey);
        if (alarmMessageInfo != null) {
            setBeginAlarmMap(alarmMessageInfoMap);
        }
        return alarmMessageInfo;
    }

    @Override
    public void sendAlarmUpdate(AlarmMessageInfo alarmMessageInfo) {
        SignalAlarmUpdateP2SProducer signalAlarmUpdateP2SProducer = new SignalAlarmUpdateP2SProducer(alarmMessageInfo);
        rocketMQSender.send(RocketMQConstants.SIGNAL_ALARM_UPDATE_P2S, null, signalAlarmUpdateP2SProducer);
    }

    @Override
    public void sendAlarmListUpdate(List<AlarmMessageInfo> alarmMessageInfoList, String serviceCode) {
        SignalAlarmCheckP2SProducer signalAlarmCheckP2SProducer = new SignalAlarmCheckP2SProducer(alarmMessageInfoList);
        rocketMQSender.send(RocketMQConstants.SIGNAL_ALARM_CHECK_P2S, serviceCode, signalAlarmCheckP2SProducer);
    }

    @Override
    public void processAlarmCheck(SignalAlarmCheckS2PConsumer consumer) {
        LambdaQueryWrapper<AlarmMessageInfo> alarmMessageInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        if (consumer.getIsAllFront().intValue() == 0) {
            List<Integer> frontIdArray = consumer.getFrontIDArray();
            alarmMessageInfoLambdaQueryWrapper.in(AlarmMessageInfo::getFrontId, frontIdArray);
        }
        if (consumer.getIsAllSystem().intValue() == 0) {
            List<String> systemCodeArray = consumer.getSystemCodeArray();
            alarmMessageInfoLambdaQueryWrapper.in(AlarmMessageInfo::getSystemCode, systemCodeArray);
        }
        List<AlarmMessageInfo> alarmMessageInfoList = this.alarmMessageInfoService.list(alarmMessageInfoLambdaQueryWrapper);
        sendAlarmListUpdate(alarmMessageInfoList, consumer.getServiceCode());
    }

    @Override
    @PostConstruct
    public void initCache() {
        logicThresholdInfoMap = this.logicThresholdInfoService.getSignalAlarmtypeByNumberMap();

        logicByChannelCodeMap = this.signalLogicChannelService.getLogicByChannelCodeMap();

        frontStationByFrontIdMap = this.frontStationInfoService.getFrontStationByFrontIdMap();

        positionByPositionIdMap = this.frontLogicPositionService.getPositionByPositionIdMap();

        channelBychannelIdMap = this.signalChannelInfoService.getChannelByChannelIdMap();

        frequencyByFrequencyIdMap = this.signalFrequencyInfoService.getFrequencyByFrequencyIdMap();
    }

    private AlarmMessageInfo insertAlarmConsumer(SignalAlarmUpdateS2PConsumer consumer) {
        AlarmMessageInfo alarmMessageInfo = new AlarmMessageInfo();
        alarmMessageInfo.setMediaType(consumer.getMediaType());
        alarmMessageInfo.setSystemCode(consumer.getSystemCode());
        alarmMessageInfo.setSignalId(consumer.getSignalID());
        alarmMessageInfo.setRefSignalId(consumer.getSignalID_Ref());
        alarmMessageInfo.setAlarmTypeId(consumer.getAlarmType());


        SignalAlarmtypeInfo signalAlarmtypeInfo = logicThresholdInfoMap.get(consumer.getAlarmType());
        if (signalAlarmtypeInfo == null) {
            return null;
        } else {
            alarmMessageInfo.setAlarmTypeName(signalAlarmtypeInfo.getAlarmName());
        }

        Date startTime = DateHelper.parse(consumer.getStartTime(), DateHelper.patterns_masks[1]);
        Date endTime = DateHelper.add(startTime, Calendar.MILLISECOND, consumer.getDuration());
        alarmMessageInfo.setStartTime(new Timestamp(startTime.getTime()));
        alarmMessageInfo.setEndTime(new Timestamp(endTime.getTime()));
        alarmMessageInfo.setDuration(consumer.getDuration());
        alarmMessageInfo.setAlarmFlag(consumer.getAlarmFlag());
        alarmMessageInfo.setCommitFlag(Constants.COMMITFLAG_UNCONFIRM);

        alarmMessageInfo.setSystemName(logicByChannelCodeMap.get(consumer.getSystemCode()).getChannelName());

        if (consumer.getSignalID().startsWith("FC")) {
            SignalFrequencyInfo signalFrequencyInfo = frequencyByFrequencyIdMap.get(consumer.getSignalID());
            if (signalFrequencyInfo != null) {
                alarmMessageInfo.setSignalName(signalFrequencyInfo.getFrequencyName());

                alarmMessageInfo.setFrontId(signalFrequencyInfo.getFrontId());
                FrontStationInfo frontStationInfo = frontStationByFrontIdMap.get(signalFrequencyInfo.getFrontId());
                if (frontStationInfo != null) {
                    alarmMessageInfo.setFrontName(frontStationInfo.getFrontName());
                }

                alarmMessageInfo.setLogicPositionId(signalFrequencyInfo.getLogicPositionId());
                FrontLogicPosition frontLogicPosition = positionByPositionIdMap.get(signalFrequencyInfo.getLogicPositionId());
                if (frontLogicPosition != null) {
                    alarmMessageInfo.setLogicPositionName(frontLogicPosition.getPositionName());
                }
            }

            if (consumer.getSignalID_Ref() != null) {
                SignalFrequencyInfo signalFrequencyInfoRef = frequencyByFrequencyIdMap.get(consumer.getSignalID_Ref());
                if (signalFrequencyInfoRef != null) {
                    alarmMessageInfo.setRefSignalName(signalFrequencyInfoRef.getFrequencyName());

                    alarmMessageInfo.setRefFrontId(signalFrequencyInfoRef.getFrontId());
                    FrontStationInfo refFrontStationInfo = frontStationByFrontIdMap.get(signalFrequencyInfoRef.getFrontId());
                    if (refFrontStationInfo != null) {
                        alarmMessageInfo.setRefFrontName(refFrontStationInfo.getFrontName());
                    }

                    alarmMessageInfo.setRefLogicPositionId(signalFrequencyInfoRef.getLogicPositionId());
                    FrontLogicPosition refFrontLogicPosition = positionByPositionIdMap.get(signalFrequencyInfo.getLogicPositionId());
                    if (refFrontLogicPosition != null) {
                        alarmMessageInfo.setRefLogicPositionName(refFrontLogicPosition.getPositionName());
                    }
                }
            }
        } else if (consumer.getSignalID().startsWith("CC")) {
            SignalChannelInfo signalChannelInfo = channelBychannelIdMap.get(consumer.getSignalID());
            if (signalChannelInfo != null) {
                alarmMessageInfo.setSignalName(signalChannelInfo.getChannelName());
                alarmMessageInfo.setFrontId(signalChannelInfo.getFrontId());
                FrontStationInfo frontStationInfo = frontStationByFrontIdMap.get(signalChannelInfo.getFrontId());
                if (frontStationInfo != null) {
                    alarmMessageInfo.setFrontName(frontStationInfo.getFrontName());
                }
                alarmMessageInfo.setLogicPositionId(signalChannelInfo.getLogicPositionId());
                FrontLogicPosition frontLogicPosition = positionByPositionIdMap.get(signalChannelInfo.getLogicPositionId());
                if (frontLogicPosition != null) {
                    alarmMessageInfo.setLogicPositionName(frontLogicPosition.getPositionName());
                }
            }

            if (consumer.getSignalID_Ref() != null) {
                SignalChannelInfo signalChannelInfoRef = channelBychannelIdMap.get(consumer.getSignalID_Ref());
                if (signalChannelInfoRef != null) {
                    alarmMessageInfo.setRefSignalName(signalChannelInfoRef.getChannelName());

                    alarmMessageInfo.setRefFrontId(signalChannelInfoRef.getFrontId());
                    FrontStationInfo refFrontStationInfo = frontStationByFrontIdMap.get(signalChannelInfoRef.getFrontId());
                    if (refFrontStationInfo != null) {
                        alarmMessageInfo.setRefFrontName(refFrontStationInfo.getFrontName());
                    }

                    alarmMessageInfo.setRefLogicPositionId(signalChannelInfoRef.getLogicPositionId());
                    FrontLogicPosition refFrontLogicPosition = positionByPositionIdMap.get(signalChannelInfoRef.getLogicPositionId());
                    if (refFrontLogicPosition != null) {
                        alarmMessageInfo.setRefLogicPositionName(refFrontLogicPosition.getPositionName());
                    }

                    alarmMessageInfo.setRefSystemCode(signalChannelInfoRef.getLogicChannelCode());
                }
            }

        }
        this.alarmMessageInfoService.save(alarmMessageInfo);
        alarmMessageInfo.setLastUpdateTime(new Date());
        //报警广播
        this.sendAlarmUpdate(alarmMessageInfo);
        return alarmMessageInfo;
    }

    @Override
    public boolean updateById(AlarmMessageInfo entity){
        boolean bool = super.updateById(entity);
        webSocketServer.sendAlarm(entity);
        return bool;
    }

    @Override
    public boolean save(AlarmMessageInfo entity){
        boolean bool = super.save(entity);
        webSocketServer.sendAlarm(entity);
        return bool;
    }

}