package com.figure.core.service.notice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.model.log.LogDeviceControl;
import com.figure.core.model.log.LogDeviceStateIndicatorChange;
import com.figure.core.model.notice.NoticeAgent;
import com.figure.core.model.notice.NoticeAlarmTriggerRule;
import com.figure.core.model.notice.NoticeMessage;
import com.figure.core.model.notice.NoticeStruct;
import com.figure.core.model.sys.SysUserInfo;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.repository.notice.NoticeMessageMapper;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.notice.INoticeMessageService;
import com.figure.core.service.others.ICommonService;
import com.figure.core.service.sys.ISysUserInfoService;
import com.figure.core.sse.Constants;
import com.figure.core.sse.SseEmitterManager;
import com.figure.core.sse.SseMessage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.figure.core.model.notice.NoticeMessage.WEB;

/**
 * <p>
 * 通知消息表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-12-08
 */
@Service
public class NoticeMessageServiceImpl extends ServiceImpl<NoticeMessageMapper, NoticeMessage> implements INoticeMessageService {

    @Resource
    private IRedisTemplateService redisTemplateService;

    @Resource
    private ISysUserInfoService sysUserInfoService;

    @Resource
    private IDeviceInfoService deviceInfoService;

    @Autowired
    ICommonService commonService;


    @Override
    public void processNoticeMessage(String alarmObjectType, SseMessage message) {
        List<NoticeStruct> noticeStructList = redisTemplateService.getListRedisCache("noticeStruct", NoticeStruct.class);
        if (message instanceof DeviceAlarmMessage) {
            DeviceAlarmMessage deviceAlarmMessage = (DeviceAlarmMessage) message;
            for (NoticeStruct noticeStruct : noticeStructList) {
                MatchResult matchResult = matchCheck(noticeStruct.getSettings(),deviceAlarmMessage);
                if (matchResult.isMatch()) {
                    List<SysUserInfo> sysUserInfoList = noticeStruct.getSysUserInfoList();
                    List<NoticeAgent> noticeStrategyList = noticeStruct.getNoticeAgentList();
                    for (SysUserInfo sysUserInfo : sysUserInfoList) {
                        for (NoticeAgent noticeAgent : noticeStrategyList) {
                            String msg = "设备[" + noticeStruct.getDeviceInfoMap().get(deviceAlarmMessage.getDeviceCode()).getDeviceName() + "]在[" +
                                    DateHelper.format(deviceAlarmMessage.getHappenTime()) + "]发生[" + deviceAlarmMessage.getAlarmTypeId() + "]报警，持续[" +
                                    deviceAlarmMessage.getContinueTime() + "秒],报警状态[" + deviceAlarmMessage.getStatus() + "]";
                            NoticeMessage noticeMessage = new NoticeMessage();
                            noticeMessage.setAgentType(noticeAgent.getType());
                            noticeMessage.setSourceType(alarmObjectType);
                            noticeMessage.setSourceId(deviceAlarmMessage.getAlarmId() + "");
                            noticeMessage.setMsg(msg);
                            noticeMessage.setLevel("1");
                            noticeMessage.setReadStatus("0");
                            noticeMessage.setReceiverUserId(sysUserInfo.getUserId().intValue());
                            noticeMessage.setStatus(1);
                            noticeMessage.setErrorCause("");
                            noticeMessage.setSourceHappendTime(deviceAlarmMessage.getHappenTime());
                            this.saveData(noticeMessage);
                        }
                    }
                }
            }
        } else if (message instanceof  LogDeviceControl) {
            LogDeviceControl logDeviceControl = (LogDeviceControl) message;
            for (NoticeStruct noticeStruct : noticeStructList) {
                MatchResult matchResult = matchCheck(noticeStruct.getSettings(),logDeviceControl);
                if (matchResult.isMatch()) {
                    List<SysUserInfo> sysUserInfoList = noticeStruct.getSysUserInfoList();
                    List<NoticeAgent> noticeStrategyList = noticeStruct.getNoticeAgentList();
                    for (SysUserInfo sysUserInfo : sysUserInfoList) {
                        for (NoticeAgent noticeAgent : noticeStrategyList) {
                            SysUserInfo operatorUser = this.sysUserInfoService.getById(logDeviceControl.getOperatorUserId());
                            String msg = "用户[" + operatorUser.getUserName() + "]于[" + DateHelper.format(logDeviceControl.getRequestTime()) + "]配置设备[" +
                                    noticeStruct.getDeviceInfoMap().get(logDeviceControl.getDeviceCode()).getDeviceName()
                                    + "]的[" + logDeviceControl.getIndicatorCode() + "]值为[" + logDeviceControl.getIndicatorValue() + "],设置状态[" + logDeviceControl.getRequestStatus() + "]";
                            NoticeMessage noticeMessage = new NoticeMessage();
                            noticeMessage.setAgentType(noticeAgent.getType());
                            noticeMessage.setSourceType(alarmObjectType);
                            noticeMessage.setSourceId(logDeviceControl.getId() + "");
                            noticeMessage.setMsg(msg);
                            noticeMessage.setLevel("1");
                            noticeMessage.setReadStatus("0");
                            noticeMessage.setReceiverUserId(sysUserInfo.getUserId().intValue());
                            noticeMessage.setStatus(1);
                            noticeMessage.setErrorCause("");
                            noticeMessage.setSourceHappendTime(logDeviceControl.getRequestTime());
                            this.saveData(noticeMessage);
                        }
                    }
                }
            }
        } else if (message instanceof LogDeviceStateIndicatorChange ) {
            LogDeviceStateIndicatorChange logDeviceStateIndicatorChange = (LogDeviceStateIndicatorChange) message;
            for (NoticeStruct noticeStruct : noticeStructList) {
                MatchResult matchResult = matchCheck(noticeStruct.getSettings(),logDeviceStateIndicatorChange);
                if (matchResult.isMatch()) {
                    List<SysUserInfo> sysUserInfoList = noticeStruct.getSysUserInfoList();
                    List<NoticeAgent> noticeStrategyList = noticeStruct.getNoticeAgentList();
                    for (SysUserInfo sysUserInfo : sysUserInfoList) {
                        for (NoticeAgent noticeAgent : noticeStrategyList) {
                            String msg = "设备[" + noticeStruct.getDeviceInfoMap().get(logDeviceStateIndicatorChange.getDeviceCode()).getDeviceName() + "]在[" +
                                    DateHelper.format(logDeviceStateIndicatorChange.getChangeTime()) + "]时的[" + logDeviceStateIndicatorChange.getIndicatorCode() + "]发生变化，旧值为[" + logDeviceStateIndicatorChange.getOldIndicatorValue()
                                    + "]新值为[" + logDeviceStateIndicatorChange.getNewIndicatorValue() + "]";
                            NoticeMessage noticeMessage = new NoticeMessage();
                            noticeMessage.setAgentType(noticeAgent.getType());
                            noticeMessage.setSourceType(alarmObjectType);
                            noticeMessage.setSourceId(logDeviceStateIndicatorChange.getId() + "");
                            noticeMessage.setMsg(msg);
                            noticeMessage.setLevel("1");
                            noticeMessage.setReadStatus("0");
                            noticeMessage.setReceiverUserId(sysUserInfo.getUserId().intValue());
                            noticeMessage.setStatus(1);
                            noticeMessage.setErrorCause("");
                            noticeMessage.setSourceHappendTime(logDeviceStateIndicatorChange.getChangeTime());
                            this.saveData(noticeMessage);
                        }
                    }
                }
            }
        }
    }


    private MatchResult matchCheck(List<NoticeAlarmTriggerRule.Setting> settings,SseMessage message){

       MatchResult matchResult =  new MatchResult();

       Map<Integer,String> deviceId_deviceCodeMap = deviceInfoService.getDeviceId_deviceCodeMap();
       if(message instanceof DeviceAlarmMessage){
           DeviceAlarmMessage deviceAlarmMessage = (DeviceAlarmMessage) message;
           for (NoticeAlarmTriggerRule.Setting setting:settings) {
               if(setting.getType()==1){
                   for (NoticeAlarmTriggerRule.Param param : setting.getParams()){
                       Integer deviceId= param.getDeviceId();
                       Integer[] alarmTypeIds = param.getAlarmTypeIds();
                       String deviceCode = deviceId_deviceCodeMap.get(deviceId);
                       if(alarmTypeIds!=null && deviceCode!=null){
                           if (deviceAlarmMessage.getDeviceCode().equals(deviceCode)
                                   && Arrays.asList(alarmTypeIds).contains(deviceAlarmMessage.getAlarmTypeId())){
                               matchResult.setMatch(true);
                               matchResult.setNoticeMessageTempleteId(setting.getNoticeMessageTempleteId());
                               return matchResult;
                           }
                       }
                   }
               }
           }
       }
       if(message instanceof LogDeviceControl){
           LogDeviceControl logDeviceControl = (LogDeviceControl) message;
           for (NoticeAlarmTriggerRule.Setting setting:settings) {
               if(setting.getType()==2){
                   for (NoticeAlarmTriggerRule.Param param : setting.getParams()){
                       Integer deviceId= param.getDeviceId();
                       String[] indicatorCodes = param.getIndicatorCodes();
                       String deviceCode = deviceId_deviceCodeMap.get(deviceId);
                       if(indicatorCodes!=null && deviceCode!=null){
                           if (logDeviceControl.getDeviceCode().equals(deviceCode)
                                   && Arrays.asList(indicatorCodes).contains(logDeviceControl.getIndicatorCode())){
                               matchResult.setMatch(true);
                               matchResult.setNoticeMessageTempleteId(setting.getNoticeMessageTempleteId());
                               return matchResult;
                           }
                       }
                   }
               }
           }
       }
       if(message instanceof LogDeviceStateIndicatorChange){
           LogDeviceStateIndicatorChange logDeviceStateIndicatorChange = (LogDeviceStateIndicatorChange) message;
           for (NoticeAlarmTriggerRule.Setting setting:settings) {
               if(setting.getType()==3){
                   for (NoticeAlarmTriggerRule.Param param : setting.getParams()){
                       Integer deviceId= param.getDeviceId();
                       String[] indicatorCodes = param.getIndicatorCodes();
                       String deviceCode = deviceId_deviceCodeMap.get(deviceId);
                       if(indicatorCodes!=null && deviceCode!=null){
                           if (logDeviceStateIndicatorChange.getDeviceCode().equals(deviceCode)
                                   && Arrays.asList(indicatorCodes).contains(logDeviceStateIndicatorChange.getIndicatorCode())){
                               matchResult.setMatch(true);
                               matchResult.setNoticeMessageTempleteId(setting.getNoticeMessageTempleteId());
                               return matchResult;
                           }
                       }
                   }
               }
           }
       }
       return matchResult;
    }

    @Data
    class MatchResult{
        boolean match = false;
        private Integer noticeMessageTempleteId;
    }

    private void saveData(NoticeMessage noticeMessage){
        String agentType = noticeMessage.getAgentType();
        this.save(noticeMessage);
        if(WEB.equals(agentType)){
            noticeMessage.setTopic("notice_message");
            SseEmitterManager.sendMessageToAllMatchSseEmitter(noticeMessage);
        }
    }


    @Override
    public void fillEntityProps(List<NoticeMessage> records) {
        Map<Long, SysUserInfo>  userId_sysUserInfo_map = commonService.get_userId_sysUserInfo_map();

        for (NoticeMessage noticeMessage:records){
            Integer receiverUserId =  noticeMessage.getReceiverUserId();
            if(receiverUserId!=null){
                SysUserInfo sysUserInfo = userId_sysUserInfo_map.get(receiverUserId);
                if(sysUserInfo!=null){
                    noticeMessage.setReceiverUserName(sysUserInfo.getUserName());
                    noticeMessage.setEmail(sysUserInfo.getEmail());
                    noticeMessage.setTelephone(sysUserInfo.getTelephone());
                }
            }
        }
    }
}



