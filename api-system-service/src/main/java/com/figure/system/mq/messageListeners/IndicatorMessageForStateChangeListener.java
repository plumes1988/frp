package com.figure.system.mq.messageListeners;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.db.JavaMemoryDb;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.rocketmq.message.DeviceAlarmInfo;
import com.figure.core.rocketmq.message.DeviceItemInfo;
import com.figure.core.rocketmq.message.DeviceStatus;
import com.figure.core.service.device.IDeviceIndicatorParamRelService;
import com.figure.core.service.others.IDenseDataService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.sse.SseEmitterManager;
import com.figure.core.util.BusinessUtils;
import com.figure.core.util.JSONUtil;
import com.figure.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.figure.core.constant.ConstantsForSysPara.NEW_MSG_FROM_MQ_DELAY_THRESHOLD;

/**
 * 消费实时指标数据，忽略历史指标
 */
@Component("IndicatorMessageForStateChangeListener")
@Slf4j
public class IndicatorMessageForStateChangeListener implements MessageListenerConcurrently {

    @Resource
    IDeviceIndicatorParamRelService deviceIndicatorParamRelService;

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


            for (DeviceItemInfo indexInfo:deviceItemInfos){

                String indicatorValue = indexInfo.getIndexData();

                if(!StringUtils.isEmpty(indicatorValue)) {

                    String indicatorCode = indexInfo.getIndexCode();

                    queryWrapper.eq("indicatorCode", indicatorCode);


                    DeviceIndicatorParamRel rel = new DeviceIndicatorParamRel();

                    rel.setCollectTime(messageTime);

                    rel.setIndicatorValue(indicatorValue);

                    rel.setAlarmStatus(indexInfo.getAlarmCount() > 0 ? 1 : 0);

                    rel.setDeviceId(deviceStatus.getDeviceId());

                    rel.setDeviceCode(deviceCode);

                    rel.setIndicatorCode(indicatorCode);

                    denseDataService.saveDeviceStateIndicatorChange(rel);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        Date endDate = new Date();

        // log.debug("开始监测收录实时状态指标改变消息：start:{},结束处理实时消息：end:{},耗时：{}毫秒",format.format(startDate),format.format(endDate),(endDate.getTime()-startDate.getTime()));

    }

}
