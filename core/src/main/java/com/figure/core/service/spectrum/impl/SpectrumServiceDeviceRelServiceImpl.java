package com.figure.core.service.spectrum.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.spectrum.SpectrumServiceDeviceRel;
import com.figure.core.model.spectrum.SpectrumServiceInfo;
import com.figure.core.query.spectrum.SpectrumServiceDeviceRelQuery;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.spectrum.SpectrumServiceDeviceRelMapper;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.RocketMQProducer;
import com.figure.core.rocketmq.struct.producer.SpectrumAnalysisSetP2SProducer;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.spectrum.ISpectrumServiceDeviceRelService;
import com.figure.core.service.spectrum.ISpectrumServiceInfoService;
import com.figure.core.util.JSONUtil;
import com.figure.core.webSocket.Message;
import com.figure.core.webSocket.WebSocketServer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频谱服务和频谱仪设备关联Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2023-11-24 16:44:13
 */
@Service
public class SpectrumServiceDeviceRelServiceImpl extends ServiceImpl<SpectrumServiceDeviceRelMapper,
        SpectrumServiceDeviceRel> implements ISpectrumServiceDeviceRelService {

    @Resource
    private RocketMQProducer rocketMQProducer;

    @Resource
    private WebSocketServer webSocketServer;

    @Resource
    private IDeviceInfoService deviceInfoService;

    @Resource
    private IRedisTemplateService redisTemplateService;

    @Resource
    private ISpectrumServiceInfoService spectrumServiceInfoService;

    @Override
    public List<HashMap<String, Object>> listRel(SpectrumServiceDeviceRelQuery spectrumServiceDeviceRelQuery) {
        return this.baseMapper.listRel(spectrumServiceDeviceRelQuery.autoPageWrapper());
    }

    @Override
    public boolean spectrumSet(SpectrumServiceDeviceRel spectrumServiceDeviceRel) {
        SpectrumAnalysisSetP2SProducer producer = new SpectrumAnalysisSetP2SProducer(spectrumServiceDeviceRel);
        String result = rocketMQProducer.send(producer.getRocketmqTopic(), producer.getRocketmqTag(), producer);
        if (result == null) {
            Message message = new Message(RocketMQConstants.SPECTRUM_ANALYSIS_SET_P2S + "_" + spectrumServiceDeviceRel.getSpectrumCode(),
                    JSONUtil.Object2JsonStr(spectrumServiceDeviceRel));
            webSocketServer.sendInfo(message);
            return true;
        } else {
            return false;
        }
    }

    @PostConstruct
    @Override
    public void spectrumServiceDataToRedis() {
        LambdaQueryWrapper<SpectrumServiceInfo> serviceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        serviceInfoLambdaQueryWrapper.eq(SpectrumServiceInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<SpectrumServiceInfo> spectrumServiceInfoList = this.spectrumServiceInfoService.list(serviceInfoLambdaQueryWrapper);

        Map<String, Map<String, SpectrumServiceDeviceRel>> spectrumServiceInfoMap = new HashMap<>();
        for (SpectrumServiceInfo spectrumServiceInfo : spectrumServiceInfoList) {
            LambdaQueryWrapper<SpectrumServiceDeviceRel> relLambdaQueryWrapper = Wrappers.lambdaQuery();
            relLambdaQueryWrapper.eq(SpectrumServiceDeviceRel::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED)
                    .eq(SpectrumServiceDeviceRel::getServiceCode, spectrumServiceInfo.getServiceCode());
            List<SpectrumServiceDeviceRel> spectrumServiceDeviceRelList = this.list(relLambdaQueryWrapper);
            Map<String, SpectrumServiceDeviceRel> serviceDeviceRelMap = new HashMap<>();
            for (SpectrumServiceDeviceRel spectrumServiceDeviceRel : spectrumServiceDeviceRelList) {
                spectrumServiceDeviceRel.setServiceName(spectrumServiceInfo.getServiceName());

                LambdaQueryWrapper<DeviceInfo> deviceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
                deviceInfoLambdaQueryWrapper.eq(DeviceInfo::getDeviceCode, spectrumServiceDeviceRel.getSpectrumCode());
                DeviceInfo spectrumInfo = this.deviceInfoService.getOne(deviceInfoLambdaQueryWrapper);
                spectrumServiceDeviceRel.setSpectrumName(spectrumInfo.getDeviceName());

                serviceDeviceRelMap.put(spectrumServiceDeviceRel.getSpectrumCode(), spectrumServiceDeviceRel);
            }
            spectrumServiceInfoMap.put(spectrumServiceInfo.getServiceCode(), serviceDeviceRelMap);
        }
        redisTemplateService.setRedisCache(RedisConstants.CURR_SPECTRUM_DEVICE_MAP, JSON.toJSON(spectrumServiceInfoMap));
    }

    @Override
    public Map<String, Map<String, SpectrumServiceDeviceRel>> getSpectrumServiceData() {
        String spectrumServiceMapJson = redisTemplateService.getObjectRedisCache(RedisConstants.CURR_SPECTRUM_DEVICE_MAP, String.class);
        Map<String, Map<String, SpectrumServiceDeviceRel>> spectrumServiceMap =
                JSON.parseObject(spectrumServiceMapJson, new TypeReference<Map<String, Map<String, SpectrumServiceDeviceRel>>>() {
                });
        return spectrumServiceMap;
    }
}