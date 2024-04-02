package com.figure.system.mq.messageListeners;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.rocketmq.message.DeviceItemInfo;
import com.figure.core.rocketmq.message.DeviceStatus;
import com.figure.core.service.others.IDenseDataService;
import com.figure.core.util.JSONUtil;
import com.figure.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.figure.core.model.device.DeviceIndicatorParamRel.IN_ALARM;
import static com.figure.core.model.device.DeviceIndicatorParamRel.NOT_IN_ALARM;

/**
 * 消费实时指标数据，忽略历史指标
 */
@Component("IndicatorMessageForHistoryListener")
@Slf4j
public class IndicatorMessageForHistoryListener implements MessageListenerConcurrently {

    @Resource
    IDenseDataService denseDataService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

        Date startDate = new Date();

        for (MessageExt msg : list) {

            processMessage(msg);

        }

        Date endDate = new Date();

        // log.debug("开始处理实时指标消息：start:{},结束处理实时消息：end:{},耗时：{}毫秒",format.format(startDate),format.format(endDate),(endDate.getTime()-startDate.getTime()));

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    protected void processMessage(MessageExt message){

        Date startDate = new Date();

        try{

            String message_str = new String(message.getBody());

            DeviceStatus deviceStatus  = JSONUtil.json2ObjectByT(message_str,
                    DeviceStatus.class);

            String deviceCode = deviceStatus.getDeviceCode();

            // log.debug("查看消息【{}】",deviceStatus);

            String messageTime =  deviceStatus.getMessageHead().getMessageTime();

            QueryWrapper queryWrapper = new QueryWrapper();

            queryWrapper.eq("deviceId",deviceStatus.getDeviceId());

            List<DeviceItemInfo> deviceItemInfos = deviceStatus.getIndexInfoArray();

            for (DeviceItemInfo indexInfo:deviceItemInfos) {

                String indicatorValue = indexInfo.getIndexData();

                if(!StringUtils.isEmpty(indicatorValue)){
                    String indicatorCode = indexInfo.getIndexCode();

                    queryWrapper.eq("indicatorCode", indicatorCode);

                    DeviceIndicatorParamRel rel = new DeviceIndicatorParamRel();

                    rel.setCollectTime(messageTime);



                    rel.setIndicatorValue(indexInfo.getIndexData());
                    rel.setAlarmStatus(indexInfo.getAlarmCount() > 0 ? 1 : 0);

                    Integer alarmStatus = indexInfo.getAlarmCount()>0?IN_ALARM:NOT_IN_ALARM;

                    denseDataService.saveHistoryIndicator(deviceStatus, indexInfo, messageTime, alarmStatus);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        Date endDate = new Date();

        // log.debug("开始收录实时指标消息：start:{},结束处理实时消息：end:{},耗时：{}毫秒",format.format(startDate),format.format(endDate),(endDate.getTime()-startDate.getTime()));
    }

}
