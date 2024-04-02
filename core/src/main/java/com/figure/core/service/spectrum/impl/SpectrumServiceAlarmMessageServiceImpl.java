package com.figure.core.service.spectrum.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.constant.LogConstants;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.device.DeviceAlarmtypeInfo;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.log.LogService;
import com.figure.core.model.spectrum.SpectrumDeviceAlarmKey;
import com.figure.core.model.spectrum.SpectrumServiceAlarmMessage;
import com.figure.core.model.spectrum.SpectrumServiceDeviceRel;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.spectrum.SpectrumServiceAlarmMessageMapper;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.consumer.ServiceStatusS2PConsumer;
import com.figure.core.rocketmq.struct.info.SpectrumServiceAlarmInfo;
import com.figure.core.rocketmq.struct.info.SpectrumServiceTaskInfo;
import com.figure.core.service.device.IDeviceAlarmMessageService;
import com.figure.core.service.device.IDeviceAlarmtypeInfoService;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.device.IDeviceServiceAlarmMessageService;
import com.figure.core.service.log.ILogServiceService;
import com.figure.core.service.spectrum.ISpectrumServiceAlarmMessageService;
import com.figure.core.service.spectrum.ISpectrumServiceDeviceRelService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.util.JSONUtil;
import com.figure.core.webSocket.Message;
import com.figure.core.webSocket.WebSocketServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

import static com.figure.core.constant.ConstantsForSysPara.OFFLINE_ALARM_TYPE_ID;
import static com.figure.core.constant.ConstantsForSysPara.ONLINE_STATUS_INDICATOR_CODE;
import static com.figure.core.model.device.DeviceServiceAlarmMessage.ALARMTYPE_OFFLINE;
import static com.figure.core.model.log.LogService.SERVICE_TYPE_SPECTRUM_MONITOR;

/**
 * <p>
 * 频谱服务报警记录Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2024-03-06 13:22:17
 */
@Service
public class SpectrumServiceAlarmMessageServiceImpl extends ServiceImpl<SpectrumServiceAlarmMessageMapper, SpectrumServiceAlarmMessage> implements ISpectrumServiceAlarmMessageService {

    @Resource
    private WebSocketServer webSocketServer;
    @Resource
    private IRedisTemplateService redisTemplateService;

    @Resource
    private IDeviceAlarmtypeInfoService iDeviceAlarmtypeInfoService;

    @Resource
    private ISpectrumServiceDeviceRelService spectrumServiceDeviceRelService;

    @Override
    public void processSpectrumServiceAlarmMessage(ServiceStatusS2PConsumer consumer) {
        this.redisTemplateService.setRedisCache(RedisConstants.SERVICE_STATUS_ALARM_LAST_UPDATE_TIME+"_"+consumer.getServiceCode(), DateHelper.format(new Date()));

        List<DeviceAlarmtypeInfo> deviceAlarmtypeInfoList = this.iDeviceAlarmtypeInfoService.list(Wrappers.lambdaQuery(DeviceAlarmtypeInfo.class).eq(DeviceAlarmtypeInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED));
        Map<Integer, String> deviceAlarmTypeMap = new HashMap<>();
        for (DeviceAlarmtypeInfo deviceAlarmtypeInfo : deviceAlarmtypeInfoList) {
            deviceAlarmTypeMap.put(deviceAlarmtypeInfo.getAlarmTypeId(), deviceAlarmtypeInfo.getAlarmTypeName());
        }
        Map<String, Map<String, SpectrumServiceDeviceRel>> spectrumServiceMap = this.spectrumServiceDeviceRelService.getSpectrumServiceData();
        Date now = new Date();
        if (spectrumServiceMap != null) {
            Map<String, SpectrumServiceDeviceRel> spectrumServiceDeviceRelMap = spectrumServiceMap.get(consumer.getServiceCode());
            if (spectrumServiceDeviceRelMap != null) {
                if (redisTemplateService.lock(RedisConstants.SERVICE_STATUS_ALARM_LOCK)) {
                    try {
                        if (consumer.getServiceState().intValue() == Constants.SERVICE_STATUS_ALARM) {
                            List<SpectrumServiceAlarmInfo> spectrumServiceAlarmInfoList = consumer.getAlarmInfoArray();
                            for (SpectrumServiceAlarmInfo spectrumServiceAlarmInfo : spectrumServiceAlarmInfoList) {
                                SpectrumDeviceAlarmKey SpectrumDeviceAlarmKey = new SpectrumDeviceAlarmKey(consumer.getServiceCode(), spectrumServiceAlarmInfo.getAlarmType());
                                SpectrumServiceAlarmMessage oldAlarm = removeAlarmByAlarmKey(SpectrumDeviceAlarmKey);
                                if (oldAlarm == null) {
                                    String serviceName = null;
                                    for (String key : spectrumServiceDeviceRelMap.keySet()) {
                                        serviceName = spectrumServiceDeviceRelMap.get(key).getServiceName();
                                        break;
                                    }
                                    SpectrumServiceAlarmMessage spectrumServiceAlarmMessage = new SpectrumServiceAlarmMessage(consumer, spectrumServiceAlarmInfo, serviceName, deviceAlarmTypeMap.get(spectrumServiceAlarmInfo.getAlarmType()));
                                    this.save(spectrumServiceAlarmMessage);
                                    this.addBeginAlarmByAlarmKey(SpectrumDeviceAlarmKey, spectrumServiceAlarmMessage);
                                } else {
                                    oldAlarm.setDuration(spectrumServiceAlarmInfo.getDuration());
                                    Date startTime = DateHelper.parse(spectrumServiceAlarmInfo.getStartTime(), DateHelper.patterns_masks[1]);
                                    if (startTime.getTime() != oldAlarm.getStartTime().getTime()) {
                                        oldAlarm.setStartTime(new Timestamp(startTime.getTime()));
                                    }
                                    Date endTime = DateHelper.add(startTime, Calendar.SECOND, spectrumServiceAlarmInfo.getDuration());
                                    oldAlarm.setEndTime(new Timestamp(endTime.getTime()));
                                    oldAlarm.setAlarmFlag(spectrumServiceAlarmInfo.getAlarmFlag());

                                    if (oldAlarm.getAlarmFlag().intValue() == Constants.ALARM_ONGOING) {//报警持续中
                                        oldAlarm.setLastUpdateTime(now);
                                        //更新报警缓存
                                        this.addBeginAlarmByAlarmKey(SpectrumDeviceAlarmKey, oldAlarm);
                                    } else if (oldAlarm.getAlarmFlag().intValue() == Constants.ALARM_STOP) {//报警结束
                                        //更新报警
                                        this.updateById(oldAlarm);
                                    }
                                }
                            }
                        }
                        List<SpectrumServiceTaskInfo> spectrumServiceTaskInfoList = consumer.getTaskInfoArray();

                        for (SpectrumServiceTaskInfo spectrumServiceTaskInfo : spectrumServiceTaskInfoList) {
                            List<SpectrumServiceAlarmInfo> spectrumSpectrumServiceAlarmInfoList = spectrumServiceTaskInfo.getAlarmInfoArray();
                            for (SpectrumServiceAlarmInfo spectrumServiceAlarmInfo : spectrumSpectrumServiceAlarmInfoList) {
                                SpectrumDeviceAlarmKey SpectrumServiceAlarmKey = new SpectrumDeviceAlarmKey(spectrumServiceTaskInfo.getObjectID(), spectrumServiceAlarmInfo.getAlarmType());

                                SpectrumServiceAlarmMessage oldAlarm = removeAlarmByAlarmKey(SpectrumServiceAlarmKey);
                                if (oldAlarm == null) {
                                    SpectrumServiceDeviceRel spectrumServiceDeviceRel = spectrumServiceDeviceRelMap.get(spectrumServiceTaskInfo.getObjectID());
                                    String serviceName = spectrumServiceDeviceRel.getServiceName();
                                    String spectrumName = spectrumServiceDeviceRel.getSpectrumName();
                                    SpectrumServiceAlarmMessage spectrumServiceAlarmMessage = new SpectrumServiceAlarmMessage(consumer, spectrumServiceTaskInfo, spectrumServiceAlarmInfo, serviceName, spectrumName, deviceAlarmTypeMap.get(spectrumServiceAlarmInfo.getAlarmType()));
                                    this.save(spectrumServiceAlarmMessage);
                                    this.addBeginAlarmByAlarmKey(SpectrumServiceAlarmKey, spectrumServiceAlarmMessage);
                                } else {
                                    oldAlarm.setDuration(spectrumServiceAlarmInfo.getDuration());
                                    Date startTime = DateHelper.parse(spectrumServiceAlarmInfo.getStartTime(), DateHelper.patterns_masks[1]);
                                    if (startTime.getTime() != oldAlarm.getStartTime().getTime()) {
                                        oldAlarm.setStartTime(new Timestamp(startTime.getTime()));
                                    }
                                    Date endTime = DateHelper.add(startTime, Calendar.SECOND, spectrumServiceAlarmInfo.getDuration());
                                    oldAlarm.setEndTime(new Timestamp(endTime.getTime()));
                                    oldAlarm.setAlarmFlag(spectrumServiceAlarmInfo.getAlarmFlag());

                                    if (oldAlarm.getAlarmFlag().intValue() == Constants.ALARM_ONGOING) {//报警持续中
                                        oldAlarm.setLastUpdateTime(now);
                                        //更新报警缓存
                                        this.addBeginAlarmByAlarmKey(SpectrumServiceAlarmKey, oldAlarm);
                                    } else if (oldAlarm.getAlarmFlag().intValue() == Constants.ALARM_STOP) {//报警结束
                                        //更新报警
                                        this.updateById(oldAlarm);
                                    }
                                }

                            }
                        }
                    } finally {
                        // 释放分布式锁
                        redisTemplateService.unlock(RedisConstants.SERVICE_STATUS_ALARM_LOCK);
                    }
                }
            }
        }
    }

    @Override
    public Map<SpectrumDeviceAlarmKey, SpectrumServiceAlarmMessage> getBeginAlarmMap() {
        Map<SpectrumDeviceAlarmKey, SpectrumServiceAlarmMessage> alarmMessageInfoMap =
                redisTemplateService.getMapRedisByKeyCache(RedisConstants.SERVICE_STATUS_ALARM_MAP,
                        SpectrumDeviceAlarmKey.class, SpectrumServiceAlarmMessage.class);
        if (alarmMessageInfoMap == null) {
            alarmMessageInfoMap = new HashMap<>();
            setBeginAlarmMap(alarmMessageInfoMap);
        }
        return alarmMessageInfoMap;
    }

    public void setBeginAlarmMap(Map<SpectrumDeviceAlarmKey, SpectrumServiceAlarmMessage> alarmMessageInfoMap) {
        redisTemplateService.setRedisCache(RedisConstants.SERVICE_STATUS_ALARM_MAP, alarmMessageInfoMap);
    }

    public void addBeginAlarmByAlarmKey(SpectrumDeviceAlarmKey SpectrumDeviceAlarmKey, SpectrumServiceAlarmMessage alarmMessageInfo) {
        Map<SpectrumDeviceAlarmKey, SpectrumServiceAlarmMessage> alarmMessageInfoMap = getBeginAlarmMap();
        alarmMessageInfoMap.put(SpectrumDeviceAlarmKey, alarmMessageInfo);
        setBeginAlarmMap(alarmMessageInfoMap);
    }

    @Override
    public SpectrumServiceAlarmMessage removeAlarmByAlarmKey(SpectrumDeviceAlarmKey SpectrumDeviceAlarmKey) {
        Map<SpectrumDeviceAlarmKey, SpectrumServiceAlarmMessage> alarmMessageInfoMap = getBeginAlarmMap();
        SpectrumServiceAlarmMessage alarmMessageInfo = alarmMessageInfoMap.remove(SpectrumDeviceAlarmKey);
        if (alarmMessageInfo != null) {
            setBeginAlarmMap(alarmMessageInfoMap);
        }
        return alarmMessageInfo;
    }
    @Resource
    private ILogServiceService logServiceService;

    @Resource
    private IDeviceServiceAlarmMessageService deviceServiceAlarmMessageService;

    @Resource
    private IDeviceAlarmMessageService deviceAlarmMessageService;

    @Resource
    private ISysParaService sysParaService;

    @Resource
    private IDeviceInfoService deviceInfoService;

    @Override
    public boolean save(SpectrumServiceAlarmMessage spectrumServiceAlarmMessage) {
        boolean success = super.save(spectrumServiceAlarmMessage);
//        sendWebsocketMessage(spectrumServiceAlarmMessage);

        String alarmTypeId = sysParaService.getParamByName(OFFLINE_ALARM_TYPE_ID);
        String indicatorCode = sysParaService.getParamByName(ONLINE_STATUS_INDICATOR_CODE);
        //内蒙古501特殊处理
        if(spectrumServiceAlarmMessage.getAlarmClass().intValue()==1){
            String serviceName = "["+spectrumServiceAlarmMessage.getServiceCode()+"]"+spectrumServiceAlarmMessage.getServiceName();
            if(deviceServiceAlarmMessageService.isOnline(SERVICE_TYPE_SPECTRUM_MONITOR,"serviceName",serviceName)){
                deviceServiceAlarmMessageService.newAlarm(serviceName,SERVICE_TYPE_SPECTRUM_MONITOR,ALARMTYPE_OFFLINE,"频谱服务处于离线状态");
            }
            LogService logService = new LogService();
            logService.setTimestamp(new Date());
            logService.setLevel(LogConstants.ERROR);
            logService.setMessage("频谱服务处于离线状态");
            logService.setSourceService(serviceName);
            logService.setServiceType(SERVICE_TYPE_SPECTRUM_MONITOR);
            logServiceService.saveLog(logService);
        }else if(spectrumServiceAlarmMessage.getAlarmClass().intValue()==2){
            spectrumServiceAlarmMessage.getSpectrumCode();
            DeviceInfo deviceInfo = this.deviceInfoService.getDeviceByDeviceCode(spectrumServiceAlarmMessage.getSpectrumCode());
            deviceAlarmMessageService.newOffLineAlarm(deviceInfo,indicatorCode,alarmTypeId,new Date());
        }
        //内蒙古501特殊处理

        return success;
    }

    @Override
    public boolean updateById(SpectrumServiceAlarmMessage spectrumServiceAlarmMessage) {
        boolean success = super.updateById(spectrumServiceAlarmMessage);
//        sendWebsocketMessage(spectrumServiceAlarmMessage);
        String indicatorCode = sysParaService.getParamByName(ONLINE_STATUS_INDICATOR_CODE);
        String alarmTypeId = sysParaService.getParamByName(OFFLINE_ALARM_TYPE_ID);

        //内蒙古501特殊处理
        if(spectrumServiceAlarmMessage.getAlarmClass().intValue()==1) {
            String serviceName = "[" + spectrumServiceAlarmMessage.getServiceCode() + "]" + spectrumServiceAlarmMessage.getServiceName();
            this.deviceServiceAlarmMessageService.endAlarm("serviceName", serviceName, SERVICE_TYPE_SPECTRUM_MONITOR);
            LogService logService = new LogService();
            logService.setTimestamp(new Date());
            logService.setLevel(LogConstants.INFO);
            logService.setMessage("频谱服务处于在线状态");
            logService.setSourceService(serviceName);
            logService.setServiceType(SERVICE_TYPE_SPECTRUM_MONITOR);
            logServiceService.saveLog(logService);
        }else if(spectrumServiceAlarmMessage.getAlarmClass().intValue()==2){
            spectrumServiceAlarmMessage.getSpectrumCode();
            DeviceInfo deviceInfo = this.deviceInfoService.getDeviceByDeviceCode(spectrumServiceAlarmMessage.getSpectrumCode());
            deviceAlarmMessageService.endOffLineAlarm(deviceInfo,indicatorCode,alarmTypeId,new Date());
        }
        //内蒙古501特殊处理

        return success;
    }

    private void sendWebsocketMessage(SpectrumServiceAlarmMessage spectrumServiceAlarmMessage){
        Message message = new Message(RocketMQConstants.SERVICE_STATUS+"_"+spectrumServiceAlarmMessage.getAlarmClass(), JSONUtil.Object2JsonStr(spectrumServiceAlarmMessage));
        this.webSocketServer.sendInfo(message);
    }
}