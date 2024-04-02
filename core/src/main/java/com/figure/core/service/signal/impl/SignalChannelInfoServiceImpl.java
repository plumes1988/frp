package com.figure.core.service.signal.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.model.front.FrontLogicPosition;
import com.figure.core.model.front.FrontStationInfo;
import com.figure.core.model.signal.SignalChannelInfo;
import com.figure.core.model.signal.SignalChannelInfoList;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.signal.SignalChannelInfoMapper;
import com.figure.core.service.front.impl.FrontLogicPositionServiceImpl;
import com.figure.core.service.front.impl.FrontStationInfoServiceImpl;
import com.figure.core.service.signal.ISignalChannelInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频道信息Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2021-05-25 17:10:37
 */
@Service
public class SignalChannelInfoServiceImpl extends ServiceImpl<SignalChannelInfoMapper, SignalChannelInfo> implements ISignalChannelInfoService {
    @Resource
    FrontStationInfoServiceImpl frontStationInfoService;

    @Resource
    FrontLogicPositionServiceImpl frontLogicPositionService;

    @Resource
    IRedisTemplateService redisTemplateService;

    @Override
    public List<Object> selectSignalChannelInfoTreeList() {
        List<Object> result = new ArrayList<>();

        List<SignalChannelInfo> signalChannelInfoList = this.list();
        LambdaQueryWrapper<FrontStationInfo> frontStationInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        frontStationInfoLambdaQueryWrapper.eq(FrontStationInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED)
                .eq(FrontStationInfo::getIsEnable, Constants.DATATABLE_ISENABLE_ENABLED);
        List<FrontStationInfo> frontStationInfoList = frontStationInfoService.list(frontStationInfoLambdaQueryWrapper);

        LambdaQueryWrapper<FrontLogicPosition> frontLogicPositionLambdaQueryWrapper = Wrappers.lambdaQuery();
        frontLogicPositionLambdaQueryWrapper.eq(FrontLogicPosition::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED)
                .eq(FrontLogicPosition::getIsEnable, Constants.DATATABLE_ISENABLE_ENABLED);
        List<FrontLogicPosition> frontLogicPositionList = frontLogicPositionService.list(frontLogicPositionLambdaQueryWrapper);

//        for (FrontLogicPosition frontLogicPosition : frontLogicPositionList) {
//            List<Object> fromPosition = new ArrayList<Object>();
//            for (SignalChannelInfo signalChannelInfo : signalChannelInfoList) {
//                if (signalChannelInfo.getLogicPositionId() == frontLogicPosition.getPositionId()) {
//                    fromPosition.add(signalChannelInfo);
//                }
//            }
//            frontLogicPosition.setChildren(fromPosition);
//        }
//
//        for (FrontStationInfo frontStationInfo : frontStationInfoList) {
//            List<Object> fromStation = new ArrayList<>();
//            for (FrontLogicPosition frontLogicPosition : frontLogicPositionList) {
//                if (frontLogicPosition.getFrontId() == frontStationInfo.getFrontId()) {
//                    fromStation.add(frontLogicPosition);
//                }
//            }
//            frontStationInfo.setChildren(fromStation);
//            result.add(frontStationInfo);
//        }

        for (FrontStationInfo frontStationInfo : frontStationInfoList) {
            frontStationInfo.setParentId(0);
            result.add(frontStationInfo);
        }

        for (FrontLogicPosition frontLogicPosition : frontLogicPositionList) {
            frontLogicPosition.setParentId(frontLogicPosition.getFrontId());
            result.add(frontLogicPosition);
        }

        for (SignalChannelInfo signalChannelInfo : signalChannelInfoList) {
            signalChannelInfo.setParentId(signalChannelInfo.getLogicPositionId());
            result.add(signalChannelInfo);
        }

        return result;
    }

    @Override
    public SignalChannelInfoList selectSignalChannelInfoList(String channelIdList) {
        return this.baseMapper.selectSignalChannelInfoList(channelIdList);
    }

    @Override
    @PostConstruct
    public void setChannelByChannelIdMap() {
        LambdaQueryWrapper<SignalChannelInfo> signalChannelInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        signalChannelInfoLambdaQueryWrapper.eq(SignalChannelInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<SignalChannelInfo> signalChannelInfoList = this.baseMapper.selectList(signalChannelInfoLambdaQueryWrapper);
        Map<String, SignalChannelInfo> channelByChannelIdMap = new HashMap<>();
        for (SignalChannelInfo signalChannelInfo : signalChannelInfoList) {
            channelByChannelIdMap.put(signalChannelInfo.getChannelId(), signalChannelInfo);
        }
        redisTemplateService.setRedisCache(RedisConstants.CHANNEL_BY_CHANNELID_MAP, channelByChannelIdMap);
    }

    @Override
    public Map<String, SignalChannelInfo> getChannelByChannelIdMap() {
        return redisTemplateService.getMapRedisByKeyCache(RedisConstants.CHANNEL_BY_CHANNELID_MAP, String.class, SignalChannelInfo.class);
    }
}