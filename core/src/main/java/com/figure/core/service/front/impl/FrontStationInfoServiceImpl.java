package com.figure.core.service.front.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.model.front.FrontStationInfo;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.front.FrontStationInfoMapper;
import com.figure.core.service.front.IFrontStationInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-05-20
 */
@Service
public class FrontStationInfoServiceImpl extends ServiceImpl<FrontStationInfoMapper, FrontStationInfo> implements IFrontStationInfoService {

    @Resource
    IRedisTemplateService redisTemplateService;

    @Override
    public List<FrontStationInfo> queryAvailableFrontStationInfo() {
        LambdaQueryWrapper<FrontStationInfo> frontStationInfoWrapper = Wrappers.lambdaQuery();
        frontStationInfoWrapper.eq(FrontStationInfo::getIsEnable, Constants.DATATABLE_ISENABLE_ENABLED)
                .eq(FrontStationInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        return this.list(frontStationInfoWrapper);
    }

    @Override
    @PostConstruct
    public void setFrontStationInfoListCache() {
        LambdaQueryWrapper<FrontStationInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(FrontStationInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<FrontStationInfo> frontStationInfoList = this.baseMapper.selectList(queryWrapper);
        redisTemplateService.setRedisCache(RedisConstants.FRONT_STATION_INFO_LIST, frontStationInfoList);

        Map<Integer, FrontStationInfo> stationByStationIdMap = new HashMap<>();
        for (FrontStationInfo frontStationInfo : frontStationInfoList) {
            stationByStationIdMap.put(frontStationInfo.getFrontId(), frontStationInfo);
        }
        redisTemplateService.setRedisCache(RedisConstants.FRONTSTATION_BY_FRONTID_MAP, stationByStationIdMap);
    }

    @Override
    public List<FrontStationInfo> getFrontStationInfoListCache() {
        return redisTemplateService.getListRedisCache(RedisConstants.FRONT_STATION_INFO_LIST, FrontStationInfo.class);
    }

    @Override
    public Map<Integer, FrontStationInfo> getFrontStationByFrontIdMap() {
        return redisTemplateService.getMapRedisByKeyCache(RedisConstants.FRONTSTATION_BY_FRONTID_MAP, Integer.class, FrontStationInfo.class);
    }
}
