package com.figure.core.service.spectrum.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumRecordMessage;
import com.figure.core.model.spectrum.SpectrumServiceDeviceRel;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.struct.consumer.SpectrumAnalysisDataS2PConsumer;
import com.figure.core.service.spectrum.ISpectrumAnalysisDataService;
import com.figure.core.util.JSONUtil;
import com.figure.core.webSocket.Message;
import com.figure.core.webSocket.WebSocketServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpectrumAnalysisDataServiceImpl implements ISpectrumAnalysisDataService {

    @Resource
    private WebSocketServer webSocketServer;

    @Resource
    private IRedisTemplateService redisTemplateService;

    @Override
    public void acceptData(SpectrumAnalysisDataS2PConsumer consumer) {
        Message message = new Message(RocketMQConstants.SPECTRUM_ANALYSIS_DATA_S2P + "_" + consumer.getSpectrumCode(), JSONUtil.Object2JsonStr(consumer));
        String spectrumServiceMapJson = redisTemplateService.getObjectRedisCache(RedisConstants.CURR_SPECTRUM_DEVICE_MAP,String.class);
        Map<String,Map<String,SpectrumServiceDeviceRel>> spectrumServiceMap = JSON.parseObject(
                spectrumServiceMapJson,
                new TypeReference<Map<String,Map<String,SpectrumServiceDeviceRel>>>(){});
        if(spectrumServiceMap!=null){
            Map<String,SpectrumServiceDeviceRel> spectrumServiceDeviceRelList = spectrumServiceMap.get(consumer.getServiceCode());
            if(spectrumServiceDeviceRelList!=null){
                SpectrumServiceDeviceRel spectrumServiceDeviceRel = spectrumServiceDeviceRelList.get(consumer.getSpectrumCode());
                if(spectrumServiceDeviceRel!=null){
                    SpectrumRecordMessage spectrumRecordMessage = new SpectrumRecordMessage(consumer, spectrumServiceDeviceRel.getServiceName(), spectrumServiceDeviceRel.getSpectrumName());
                    Map<String, SpectrumRecordMessage> spectrumRecordMessageMap = redisTemplateService
                            .getMapRedisByKeyCache(RedisConstants.CURR_SPECTRUM_DATA_MAP, String.class, SpectrumRecordMessage.class);
                    if (spectrumRecordMessageMap == null) {
                        spectrumRecordMessageMap = new HashMap<>();
                    }
                    spectrumRecordMessageMap.put(spectrumRecordMessage.getSpectrumCode(), spectrumRecordMessage);
                    redisTemplateService.setRedisCache(RedisConstants.CURR_SPECTRUM_DATA_MAP, spectrumRecordMessageMap);
                    webSocketServer.sendInfo(message);
                }
            }
        }
    }
}