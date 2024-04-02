package com.figure.system.mq.messageListeners;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.db.JavaMemoryDb;
import com.figure.core.service.device.IDeviceIndicatorParamRelService;
import com.figure.core.service.device.IDeviceIndicatorParamService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.util.BusinessUtils;
import com.figure.core.util.JSONUtil;
import com.figure.core.model.device.DeviceIndicatorParamRel;

import com.figure.core.sse.SseEmitterManager;
import com.figure.core.rocketmq.message.DeviceItemInfo;
import com.figure.core.rocketmq.message.DeviceStatus;
import com.figure.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static com.figure.core.constant.ConstantsForSysPara.NEW_MSG_FROM_MQ_DELAY_THRESHOLD;
import static com.figure.core.sse.Constants.DEVICE_INDICATOR;

/**
 * 消费实时指标数据，忽略历史指标
 */
@Component("IndicatorMessageListener")
@Slf4j
public class IndicatorMessageListener implements MessageListenerConcurrently {

    @Resource
    IDeviceIndicatorParamRelService deviceIndicatorParamRelService;

    @Resource
    IDeviceIndicatorParamService deviceIndicatorParamService;

    @Autowired
    ISysParaService sysParaService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

        Date startDate = new Date();

        Integer newMsgFromMqDelayThreshold = Integer.parseInt(sysParaService.getParamByName(NEW_MSG_FROM_MQ_DELAY_THRESHOLD));

        for (MessageExt msg : list) {

            long messageBornTime = msg.getBornTimestamp();

            long currentTimestamp = System.currentTimeMillis(); // 获取当前时间戳

            long timeDifference = currentTimestamp - messageBornTime; // 计算时间差

            // 判断时间差是否小于阈值，如果小于阈值，则认为是最新数据
            if (timeDifference < newMsgFromMqDelayThreshold*1000) {
                processMessage(msg);
            }

        }

        Date endDate = new Date();

        // log.debug("开始处理实时指标消息：start:{},结束处理实时消息：end:{},耗时：{}毫秒",format.format(startDate),format.format(endDate),(endDate.getTime()-startDate.getTime()));

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    protected void processMessage(MessageExt msg){

        Date  receiveTime = new Date();

        try{

            // log.debug("收到实时指标消息【{}】", new String(msg.getBody()));

            String s = new String(msg.getBody());

            DeviceStatus deviceStatus = JSONUtil.json2ObjectByT(s, DeviceStatus.class);

            String deviceCode = deviceStatus.getDeviceCode();

            Integer deviceId =  deviceStatus.getDeviceId();

            // log.debug("查看消息【{}】",deviceStatus);

            String messageTime =  deviceStatus.getMessageHead().getMessageTime();


            List<DeviceItemInfo> deviceItemInfos = deviceStatus.getIndexInfoArray();

            for (DeviceItemInfo indexInfo:deviceItemInfos){

                String indicatorValue = indexInfo.getIndexData();

                if(!StringUtils.isEmpty(indicatorValue)) {

                    String indicatorCode = indexInfo.getIndexCode();

                    QueryWrapper queryWrapper = new QueryWrapper();

                    queryWrapper.eq("deviceId", deviceId);

                    queryWrapper.eq("indicatorCode", indicatorCode);

                    DeviceIndicatorParamRel rel = new DeviceIndicatorParamRel();

                    rel.setDeviceId(deviceId);

                    rel.setCollectTime(messageTime);

                    rel.setIndicatorValue(indicatorValue);

                    //指标实时状态更新方法2
                    String key = BusinessUtils.getLiveDeviceIndicatorKey(deviceCode, indicatorCode);

                    Integer alarmStatus = BusinessUtils.getDeviceIndicatorAlarmStatusByKey(key);

                    rel.setAlarmStatus(alarmStatus);

                    Date updateTime = new Date();

                    Integer isFromCache = indexInfo.getIsFromCache();


                    if (isFromCache != null && isFromCache == 0 && indexInfo.getDataState() != 2) {
                        JavaMemoryDb.DEVICE_LAST_INDICATOR_VALUE_UPDATE_TIME.put(deviceId, updateTime);
                    }

                    rel.setIndicatorCode(indicatorCode);
                    rel.setDeviceCode(deviceCode);

                    String key_ = BusinessUtils.getLiveDeviceIndicatorKey(rel);

                    JavaMemoryDb.put_into_DEVICE_CUR_INDICATOR_STATUS(key_, rel.getIndicatorValue(), messageTime);

                    rel.setIsCritical(JavaMemoryDb.INDICATOR_CODE_IS_CRITICAL.get(indicatorCode));

                    rel.setTopic(DEVICE_INDICATOR);

                    SseEmitterManager.sendMessageToAllMatchSseEmitter(rel);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
