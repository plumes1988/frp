package com.figure.core.service.front.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.model.front.FrontLogicPosition;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.front.FrontLogicPositionMapper;
import com.figure.core.service.front.IFrontLogicPositionService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采集点管理Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2021-05-19 17:16:25
 */
@Service
public class FrontLogicPositionServiceImpl extends ServiceImpl<FrontLogicPositionMapper, FrontLogicPosition> implements IFrontLogicPositionService {

    @Resource
    IRedisTemplateService redisTemplateService;

    @Override
    @PostConstruct
    public void setFrontLogicPositionListCache() {
        LambdaQueryWrapper<FrontLogicPosition> frontLogicPositionLambdaQueryWrapper = Wrappers.lambdaQuery();
        frontLogicPositionLambdaQueryWrapper.eq(FrontLogicPosition::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<FrontLogicPosition> frontLogicPositionList = this.baseMapper.selectList(frontLogicPositionLambdaQueryWrapper);
        redisTemplateService.setRedisCache(RedisConstants.FRONT_LOGIC_POSITION_LIST, frontLogicPositionList);

        Map<Integer, FrontLogicPosition> positionByFrontIdMap = new HashMap<>();
        for (FrontLogicPosition frontLogicPosition : frontLogicPositionList) {
            positionByFrontIdMap.put(frontLogicPosition.getPositionId(), frontLogicPosition);
        }
        redisTemplateService.setRedisCache(RedisConstants.POSITION_BY_POSITIONID_MAP, positionByFrontIdMap);
    }

    @Override
    public List<FrontLogicPosition> getFrontLogicPositionListCache() {
        return redisTemplateService.getListRedisCache(RedisConstants.FRONT_LOGIC_POSITION_LIST, FrontLogicPosition.class);
    }

    @Override
    public Map<Integer, FrontLogicPosition> getPositionByPositionIdMap() {
        return redisTemplateService.getMapRedisByKeyCache(RedisConstants.POSITION_BY_POSITIONID_MAP, Integer.class, FrontLogicPosition.class);
    }
}