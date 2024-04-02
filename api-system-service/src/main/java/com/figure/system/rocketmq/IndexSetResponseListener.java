package com.figure.system.rocketmq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.db.JavaMemoryDb;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.model.log.LogDeviceControl;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.log.ILogDeviceControlService;
import com.figure.core.service.notice.INoticeMessageService;
import com.figure.core.util.BusinessUtils;
import com.figure.core.util.JSONUtil;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.sse.SseEmitterManager;
import com.figure.core.rocketmq.message.IndexSetResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.figure.core.model.notice.NoticeMessage.DEVICE_CONTROL;
import static com.figure.core.sse.Constants.*;

@Slf4j
@Component
@RocketMQMessageListener(topic = RocketMQConstants.INDEX_SET_RESPONSE, consumerGroup = RocketMQConstants.INDEX_SET_RESPONSE_GROUP,consumeMode = ConsumeMode.CONCURRENTLY, consumeThreadMax = 1)
public class IndexSetResponseListener implements RocketMQListener<MessageExt> {

    @Autowired
    private ILogDeviceControlService logDeviceControlService;

    @Autowired
    private INoticeMessageService noticeMessageService;

    @Resource
    private IDeviceInfoService deviceInfoService;

    @Autowired
    private ExecutorService timeoutExecutorService;

    @Override
    public void onMessage(MessageExt message) {

        try{
            long bornTime = message.getBornTimestamp();

            log.info("index_set_response 指标设置【{}】", new String(message.getBody()));
            String message_str = new String(message.getBody());
            IndexSetResponse indexSetResponse = JSONUtil.json2ObjectByT(message_str,
                    IndexSetResponse.class);
            log.info("index_set_response 指标消息【{}】",indexSetResponse);

            QueryWrapper queryWrapper = new QueryWrapper();
            LogDeviceControl logDeviceControl = new LogDeviceControl();
            queryWrapper.eq("requestId", indexSetResponse.getRequestld());

            logDeviceControl.setResponseTime(indexSetResponse.getResponseTime());
            logDeviceControl.setResponseContent(indexSetResponse.getErrorDesc());
            logDeviceControl.setRequestStatus(indexSetResponse.getErrorCode());

            log.info("index_set_response update log");

            logDeviceControlService.update(logDeviceControl,queryWrapper);

            log.info("index_set_response onMessage:"+message_str);

            queryWrapper = new QueryWrapper();

            queryWrapper.eq("requestId", indexSetResponse.getRequestld());

            logDeviceControl = logDeviceControlService.getOne(queryWrapper);

            logDeviceControl.setDeviceId(logDeviceControl.getDeviceId());

            //errorCode 1:成功 2:失败
            if(indexSetResponse.getErrorCode()==1){
                //推送频率非常高，不用往页面推送了
                sendIndicatorToWeb(logDeviceControl,indexSetResponse);
            }

            sendIndexSetResponseToWeb(logDeviceControl);

            //sendMessage(queryWrapper);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void sendIndicatorToWeb(LogDeviceControl logDeviceControl, IndexSetResponse indexSetResponse) {
        // 创建 SimpleDateFormat 对象，指定日期时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 格式化为指定格式的字符串
        String messageTime = dateFormat.format(logDeviceControl.getResponseTime());
        DeviceIndicatorParamRel rel = new DeviceIndicatorParamRel();
        rel.setDeviceCode(logDeviceControl.getDeviceCode());
        Integer deviceId = deviceInfoService.getDeviceIdByDeviceCode(logDeviceControl.getDeviceCode());
        System.out.println("sendIndicatorToWeb-->deviceId:"+deviceId);
        rel.setDeviceId(deviceId);
        rel.setUpdateTime(messageTime);
        rel.setCollectTime(messageTime);
        rel.setIndicatorCode(logDeviceControl.getIndicatorCode());
        //rel.setIndicatorValue(indexSetResponse.getSetValue());
        rel.setIndicatorValue(logDeviceControl.getIndicatorValue());
        //指标实时状态更新方法2
        String key = BusinessUtils.getLiveDeviceIndicatorKey(rel);
        Integer alarmStatus = BusinessUtils.getDeviceIndicatorAlarmStatusByKey(key);
        rel.setAlarmStatus(alarmStatus);
        rel.setIsCritical(JavaMemoryDb.INDICATOR_CODE_IS_CRITICAL.get(rel.getDeviceCode()));
        rel.setTopic(DEVICE_INDICATOR_FOR_DEVICE_CONTROL);
        SseEmitterManager.sendMessageToAllMatchSseEmitter(rel);
    }

    private void sendMessage(QueryWrapper queryWrapper) {
        List<LogDeviceControl> logDeviceControls =  logDeviceControlService.list(queryWrapper);
        for(LogDeviceControl logDeviceControl:logDeviceControls){
            noticeMessageService.processNoticeMessage(DEVICE_CONTROL,logDeviceControl);
        }
    }

    private void sendIndexSetResponseToWeb(LogDeviceControl logDeviceControl) {
        if(logDeviceControl!=null){
            logDeviceControl.setTopic(LOG_DEVICE_CONTROL);
            SseEmitterManager.sendMessageToAllMatchSseEmitter(logDeviceControl);
        }
    }
}
