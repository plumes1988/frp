package com.figure.core.service.device.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.db.JavaMemoryDb;
import com.figure.core.model.device.DeviceIndicatorParam;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.device.DeviceIndicatorParamMapper;
import com.figure.core.service.device.IDeviceIndicatorParamService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-06-14
 */
@Service
public class DeviceIndicatorParamServiceImpl extends ServiceImpl<DeviceIndicatorParamMapper, DeviceIndicatorParam> implements IDeviceIndicatorParamService {
    @Resource
    private IRedisTemplateService redisTemplateService;

    @Override
    public Map<String, Integer> getIndicatorCodeDataTypeMapFromCache() {
        Map<String, Integer> cache_map = redisTemplateService.getMapRedisByKeyCache(RedisConstants.INDICATORCODE_DATATYPE_MAP, String.class, Integer.class);
        return cache_map;
    }

    @Override
    @PostConstruct
    public void setIndicatorCodeDataTypeMapCache() {
        QueryWrapper<DeviceIndicatorParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("indicatorCode","dataType");
        List<DeviceIndicatorParam> list = this.list(queryWrapper);
        Map<String, Integer> cache_map = new HashMap<>();
        for (DeviceIndicatorParam deviceIndicatorParam:list){
            cache_map.put(deviceIndicatorParam.getIndicatorCode(),deviceIndicatorParam.getDataType());
        }
        redisTemplateService.setRedisCache(RedisConstants.INDICATORCODE_DATATYPE_MAP,cache_map);
    }


    @Override
    @PostConstruct
    public void setIndicatorCodeIsCriticalMapCache() {
        QueryWrapper<DeviceIndicatorParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("indicatorCode","isCritical");
        queryWrapper.eq("isCritical",1);
        List<DeviceIndicatorParam> list = this.list(queryWrapper);
        Map<String, Integer> cache_map = new HashMap<>();
        for (DeviceIndicatorParam deviceIndicatorParam:list){
            JavaMemoryDb.INDICATOR_CODE_IS_CRITICAL.put(deviceIndicatorParam.getIndicatorCode(),deviceIndicatorParam.getIsCritical());
        }
    }

    @Override
    public void updateCache() {
        this.setIndicatorCodeDataTypeMapCache();
        this.setIndicatorCodeIsCriticalMapCache();
    }
}
