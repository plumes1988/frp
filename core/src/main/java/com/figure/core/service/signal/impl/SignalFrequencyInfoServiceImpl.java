package com.figure.core.service.signal.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.model.signal.SignalFrequencyInfo;
import com.figure.core.model.signal.SignalFrequencyInfoList;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.signal.SignalFrequencyInfoMapper;
import com.figure.core.service.signal.ISignalFrequencyInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频率信息Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2021-05-25 17:10:38
 */
@Service
public class SignalFrequencyInfoServiceImpl extends ServiceImpl<SignalFrequencyInfoMapper, SignalFrequencyInfo> implements ISignalFrequencyInfoService {

    @Resource
    private IRedisTemplateService redisTemplateService;

    @Override
    public List<SignalFrequencyInfoList> treelist(QueryWrapper<SignalFrequencyInfo> autoPageWrapper) {
        return this.baseMapper.treelist(autoPageWrapper);
    }

    @Override
    public Map<String, SignalFrequencyInfo> getFrequencyByFrequencyIdMap() {
        return redisTemplateService.getMapRedisByKeyCache(RedisConstants.FREQUENCY_BY_FREQUENCYID_MAP, String.class, SignalFrequencyInfo.class);
    }

    @Override
    @PostConstruct
    public void setFrequencyByFrequencyIdMap() {
        LambdaQueryWrapper<SignalFrequencyInfo> signalFrequencyInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        signalFrequencyInfoLambdaQueryWrapper.eq(SignalFrequencyInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<SignalFrequencyInfo> signalFrequencyInfoList = this.baseMapper.selectList(signalFrequencyInfoLambdaQueryWrapper);
        Map<String, SignalFrequencyInfo> frequencyByFrequencyIdMap = new HashMap<>();
        for (SignalFrequencyInfo signalFrequencyInfo : signalFrequencyInfoList) {
            frequencyByFrequencyIdMap.put(signalFrequencyInfo.getFrequencyId(), signalFrequencyInfo);
        }
        redisTemplateService.setRedisCache(RedisConstants.FREQUENCY_BY_FREQUENCYID_MAP, frequencyByFrequencyIdMap);
    }
}