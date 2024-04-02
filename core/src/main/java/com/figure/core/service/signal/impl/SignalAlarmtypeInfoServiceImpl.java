package com.figure.core.service.signal.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.model.signal.SignalAlarmtypeInfo;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.signal.SignalAlarmtypeInfoMapper;
import com.figure.core.service.signal.ISignalAlarmtypeInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 默认报警类型信息Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Service
public class SignalAlarmtypeInfoServiceImpl extends ServiceImpl<SignalAlarmtypeInfoMapper, SignalAlarmtypeInfo> implements ISignalAlarmtypeInfoService {

    @Resource
    IRedisTemplateService redisTemplateService;

    @PostConstruct
    @Override
    public void setSignalAlarmtypeListCache() {
        LambdaQueryWrapper<SignalAlarmtypeInfo> signalAlarmtypeInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        signalAlarmtypeInfoLambdaQueryWrapper.eq(SignalAlarmtypeInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<SignalAlarmtypeInfo> signalAlarmtypeInfoList = this.baseMapper.selectList(signalAlarmtypeInfoLambdaQueryWrapper);
        redisTemplateService.setRedisCache(RedisConstants.LOGIC_THRESHOLD_INFO_LIST, signalAlarmtypeInfoList);
    }

    @Override
    public List<SignalAlarmtypeInfo> getSignalAlarmtypeListCache() {
        return redisTemplateService.getListRedisCache(RedisConstants.LOGIC_THRESHOLD_INFO_LIST, SignalAlarmtypeInfo.class);
    }

    @PostConstruct
    @Override
    public void setSignalAlarmtypeByNumberMap() {
        LambdaQueryWrapper<SignalAlarmtypeInfo> signalAlarmtypeInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        signalAlarmtypeInfoLambdaQueryWrapper.eq(SignalAlarmtypeInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<SignalAlarmtypeInfo> signalAlarmtypeInfoList = this.baseMapper.selectList(signalAlarmtypeInfoLambdaQueryWrapper);
        Map<Integer, SignalAlarmtypeInfo> signalAlarmtypeInfoHashMap = new HashMap<>();
        for (SignalAlarmtypeInfo signalAlarmtypeInfo : signalAlarmtypeInfoList) {
            signalAlarmtypeInfoHashMap.put(signalAlarmtypeInfo.getNumber(), signalAlarmtypeInfo);
        }
        redisTemplateService.setRedisCache(RedisConstants.LOGIC_THRESHOLD_BY_NUMBER_MAP, signalAlarmtypeInfoHashMap);
    }

    @Override
    public Map<Integer, SignalAlarmtypeInfo> getSignalAlarmtypeByNumberMap() {
        return redisTemplateService.getMapRedisByKeyCache(RedisConstants.LOGIC_THRESHOLD_BY_NUMBER_MAP, Integer.class, SignalAlarmtypeInfo.class);
    }
}