package com.figure.core.service.device.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.entity.BaseSyncReturn;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.device.DeviceInfoList;
import com.figure.core.model.device.ServiceControlSet;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.device.DeviceInfoMapper;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.rocketmq.RocketMQProducer;
import com.figure.core.rocketmq.struct.producer.ServiceControlSetProducer;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.others.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-06-15
 */
@Service
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoMapper, DeviceInfo> implements IDeviceInfoService {

    @Resource
    private RocketMQProducer rocketMQProducer;

    @Resource
    private IRedisTemplateService redisTemplateService;

    @Autowired
    ICommonService commonService;

    @Override
    public List<DeviceInfo> queryAvailableDeviceInfoByFrontId(Integer frontId) {
        LambdaQueryWrapper<DeviceInfo> deviceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        deviceInfoLambdaQueryWrapper.eq(DeviceInfo::getMonitorStationId, frontId)
                .eq(DeviceInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        return this.list(deviceInfoLambdaQueryWrapper);
    }

    @Override
    public boolean updateBatchSynStatusByFrontId(Integer frontId) {
        LambdaUpdateWrapper<DeviceInfo> deviceInfoLambdaUpdateWrapper = Wrappers.lambdaUpdate();
        deviceInfoLambdaUpdateWrapper.eq(DeviceInfo::getMonitorStationId, frontId)
                .eq(DeviceInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED)
                .set(DeviceInfo::getSyncStatus, Constants.DATATABLE_SYNSTATUS_SYNCED);
        return this.update(deviceInfoLambdaUpdateWrapper);
    }

    @Override
    public List<DeviceInfoList> treelist(QueryWrapper<DeviceInfo> autoPageWrapper) {
        return this.baseMapper.treelist(autoPageWrapper);
    }

    @Override
    public List<DeviceInfoList> interbydevice(QueryWrapper<DeviceInfo> autoPageWrapper) {
        return this.baseMapper.interbydevice(autoPageWrapper);
    }

    @Override
    public Integer getDeviceIdByDeviceCode(String deviceCode) {
        Map<String,Integer> map =  getDeviceCode_deviceIdMap();
        return map.get(deviceCode);
    }

    @Override
    public String getDeviceCodeByDeviceId(Integer deviceId) {
        Map<Integer,String> map =   getDeviceId_deviceCodeMap();
        return map.get(deviceId);
    }

    @Override
    public DeviceInfo getDeviceByDeviceCode(String deviceCode){
        QueryWrapper<DeviceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deviceCode", deviceCode);
        queryWrapper.eq("isDelete", 0);
        List<DeviceInfo> list = this.baseMapper.selectList(queryWrapper);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Map<String, Object> restartServiceByCode(ServiceControlSet serviceControlSet) {
        Map<String, Object> result = new HashMap<>();
        List<BaseSyncReturn> baseSyncReturnList = new ArrayList<>();
        for (String serviceCode : serviceControlSet.getServiceCodeArray()) {
            BaseSyncReturn baseSyncReturn = new BaseSyncReturn();
            ServiceControlSetProducer producer = new ServiceControlSetProducer(serviceCode, serviceControlSet.getControlMode());
            String resultMsg = rocketMQProducer.send(RocketMQConstants.SERVICE_CONTROL_SET, "", producer);
            if (resultMsg == null) {
                LambdaQueryWrapper<DeviceInfo> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(DeviceInfo::getDeviceCode, serviceCode).eq(DeviceInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
                DeviceInfo deviceInfo = this.baseMapper.selectOne(wrapper);
                baseSyncReturn.setResult(1);
                baseSyncReturn.setResultMsg("服务(" + deviceInfo.getDeviceName() + ")重启成功");
            } else {
                baseSyncReturn.setResult(0);
                baseSyncReturn.setResultMsg(resultMsg);
            }
            baseSyncReturnList.add(baseSyncReturn);
        }
        result.put("return", baseSyncReturnList);
        return result;
    }

    @Override
    public Map<Integer, String> getDeviceId_deviceCodeMap() {
        Map<Integer, String>  cache_map = redisTemplateService.getMapRedisByKeyCache(RedisConstants.DEVICEID_DEVICECODE_MAP, Integer.class, String.class);
        return cache_map;
    }

    @Override
    @PostConstruct
    public void setDeviceId_deviceCodeEachMapCache() {
        Map<Integer, String>  cache_map = new HashMap<>();
        Map<String, Integer>  cache_map_ = new HashMap<>();
        QueryWrapper<DeviceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("deviceId", "deviceCode"); // 指定要查询的列
        List<DeviceInfo> deviceInfos = baseMapper.selectList(queryWrapper);
        for(DeviceInfo deviceInfo:deviceInfos){
            cache_map.put(deviceInfo.getDeviceId(),deviceInfo.getDeviceCode());
            cache_map_.put(deviceInfo.getDeviceCode(),deviceInfo.getDeviceId());
        }
        redisTemplateService.setRedisCache(RedisConstants.DEVICEID_DEVICECODE_MAP,cache_map);
        redisTemplateService.setRedisCache(RedisConstants.DEVICECODE_DEVICEID_MAP,cache_map_);
    }

    @Override
    public Map<String,Integer> getDeviceCode_deviceIdMap() {
        Map<String, Integer>  cache_map = redisTemplateService.getMapRedisByKeyCache(RedisConstants.DEVICECODE_DEVICEID_MAP, String.class, Integer.class);
        return cache_map;
    }

    @Override
    public Map<String, Integer> getDeviceCodeFrontIdMapFromCache() {
        Map<String, Integer> cache_map = redisTemplateService.getMapRedisByKeyCache(RedisConstants.DEVICECODE_MONITORSTATIONID_MAP, String.class, Integer.class);
        return cache_map;
    }

    @Override
    @PostConstruct
    public void setDeviceCodeFrontIdMapCache() {
        QueryWrapper<DeviceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("deviceCode","monitorStationId");
        queryWrapper.eq("isDelete",com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<DeviceInfo> list = this.list(queryWrapper);
        Map<String, Integer> cache_map = new HashMap<>();
        for (DeviceInfo deviceInfo:list){
            cache_map.put(deviceInfo.getDeviceCode(), deviceInfo.getMonitorStationId());
        }
        redisTemplateService.setRedisCache(RedisConstants.DEVICECODE_MONITORSTATIONID_MAP, cache_map);
    }

    @Override
    public Map<String, Integer> getDeviceCodeOfflineDecisionThresholdMapFromCache() {
        Map<String, Integer> cache_map = redisTemplateService.getMapRedisByKeyCache(RedisConstants.DEVICECODE_OFFLINEDECISIONTHRESHOLD_MAP, String.class, Integer.class);
        return cache_map;
    }

    @Override
    @PostConstruct
    public void setDeviceCodeOfflineDecisionThresholdMapCache() {
        QueryWrapper<DeviceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("deviceCode","offlineDecisionThreshold");
        queryWrapper.isNotNull("offlineDecisionThreshold");
        queryWrapper.eq("isDelete",com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<DeviceInfo> list = this.list(queryWrapper);
        Map<String, Integer> cache_map = new HashMap<>();
        for (DeviceInfo deviceInfo:list){
            cache_map.put(deviceInfo.getDeviceCode(), deviceInfo.getOfflineDecisionThreshold());
        }
        redisTemplateService.setRedisCache(RedisConstants.DEVICECODE_OFFLINEDECISIONTHRESHOLD_MAP, cache_map);
    }

    @Override
    public Map<String, DeviceInfo> getDeviceCodeDeviceInfoMapFromCache() {
        Map<String, DeviceInfo> cache_map = redisTemplateService.getMapRedisByKeyCache(RedisConstants.DEVICECODE_DEVICEINFO_MAP, String.class, DeviceInfo.class);
        return cache_map;
    }

    @Override
    @PostConstruct
    public void setDeviceCodeDeviceInfoMapCache() {
        QueryWrapper<DeviceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDelete",com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<DeviceInfo> list = this.list(queryWrapper);
        Map<String, DeviceInfo> cache_map = new HashMap<>();
        for (DeviceInfo deviceInfo:list){
            cache_map.put(deviceInfo.getDeviceCode(), deviceInfo);
        }
        redisTemplateService.setRedisCache(RedisConstants.DEVICECODE_DEVICEINFO_MAP, cache_map);
    }

    @Override
    public void updateCache() {
        setDeviceId_deviceCodeEachMapCache();
        setDeviceCodeFrontIdMapCache();
        setDeviceCodeOfflineDecisionThresholdMapCache();
        setDeviceCodeDeviceInfoMapCache();
        setMonitorDevicesFromCache();
    }

    @Override
    public List<HashMap<String, Object>> detailList(QueryWrapper<DeviceInfo> deviceInfoQueryWrapper) {
        return this.baseMapper.detailList(deviceInfoQueryWrapper);
    }

    @Override
    public List<DeviceInfo> getMonitorDevicesFromCache() {
        List<DeviceInfo> cache_list = redisTemplateService.getListRedisCache(RedisConstants.MONITOR_DEVICES, DeviceInfo.class);
        return cache_list;
    }

    @Override
    @PostConstruct
    public void setMonitorDevicesFromCache() {

        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.eq("isDelete",com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED);

        queryWrapper.eq("underController",1);

        List<DeviceInfo> list_cache  = list(queryWrapper);

        redisTemplateService.setRedisCache(RedisConstants.MONITOR_DEVICES, list_cache);

    }
}


