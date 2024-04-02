package com.figure.core.service.signal.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.model.signal.SignalLogicChannel;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.signal.SignalLogicChannelMapper;
import com.figure.core.service.signal.ISignalLogicChannelService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 逻辑频道管理 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-05-19
 */
@Service
public class SignalLogicChannelServiceImpl extends ServiceImpl<SignalLogicChannelMapper, SignalLogicChannel> implements ISignalLogicChannelService {

    @Resource
    IRedisTemplateService redisTemplateService;


    @Override
    @PostConstruct
    public void setSignalLogicChannelListCache() {
        LambdaQueryWrapper<SignalLogicChannel> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SignalLogicChannel::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<SignalLogicChannel> signalLogicChannelList = this.baseMapper.selectList(queryWrapper);
        redisTemplateService.setRedisCache(RedisConstants.SIGNAL_LOGIC_CHANNEL_LIST, signalLogicChannelList);

        Map<String, SignalLogicChannel> logicByChannelCodeMap = new HashMap<>();
        for (SignalLogicChannel signalLogicChannel : signalLogicChannelList) {
            logicByChannelCodeMap.put(signalLogicChannel.getChannelCode(), signalLogicChannel);
        }
        redisTemplateService.setRedisCache(RedisConstants.LOGIC_BY_CHANNELCODE_MAP, logicByChannelCodeMap);
    }

    @Override
    public List<SignalLogicChannel> getSignalLogicChannelListCache() {
        return redisTemplateService.getListRedisCache(RedisConstants.SIGNAL_LOGIC_CHANNEL_LIST, SignalLogicChannel.class);
    }

    @Override
    public Map<String, SignalLogicChannel> getLogicByChannelCodeMap() {
        return redisTemplateService.getMapRedisByKeyCache(RedisConstants.LOGIC_BY_CHANNELCODE_MAP, String.class, SignalLogicChannel.class);
    }


}
