package com.figure.core.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.sys.SysPara;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.redis.RedisConstants;
import com.figure.core.repository.sys.SysParaMapper;
import com.figure.core.service.sys.ISysParaService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.figure.core.constant.ConstantsForSysPara.DEFAULT_PARAM_VALUES;
import static com.figure.core.db.JavaMemoryDb.SYS_PARAM_CACHE;

/**
 * <p>
 * 系统参数表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-03-22
 */
@Service
public class SysParaServiceImpl extends ServiceImpl<SysParaMapper, SysPara> implements ISysParaService {

    @Resource
    IRedisTemplateService redisTemplateService;

    @PostConstruct
    public void setAlarmParaCache() {
        setParamCacheByName(RedisConstants.ALARM_PARAM);
    }

    @PostConstruct
    public void setRecordParaCache() {
        setParamCacheByName(RedisConstants.RECORD_PARAM);
    }

    @Override
    public void setParamCacheByName(String paramName) {
        LambdaQueryWrapper<SysPara> sysParaLambdaQueryWrapper = Wrappers.lambdaQuery();
        sysParaLambdaQueryWrapper.eq(SysPara::getParaName, paramName);
        List<SysPara> alarmParamParentList = this.baseMapper.selectList(sysParaLambdaQueryWrapper);

        Map<String, String> alarmParamMap = new HashMap<>();

        if (alarmParamParentList.size() == 1) {
            sysParaLambdaQueryWrapper = Wrappers.lambdaQuery();
            sysParaLambdaQueryWrapper.eq(SysPara::getParentParaId, alarmParamParentList.get(0).getParaId());
            List<SysPara> alarmParamList = this.baseMapper.selectList(sysParaLambdaQueryWrapper);
            for (SysPara sysPara : alarmParamList) {
                alarmParamMap.put(sysPara.getParaName(), sysPara.getParaValue());
            }
        }
        redisTemplateService.setRedisCache(RedisConstants.SYS_PARA + paramName, alarmParamMap);
    }

    @Override
    public Map<String, String> getParamCacheByName(String paramName) {
        return redisTemplateService.getMapRedisByKeyCache(RedisConstants.SYS_PARA + paramName, String.class, String.class);
    }

    @Override
    public String getParamByName(String paramName) {

        String paramValue =  null;

        paramValue = SYS_PARAM_CACHE.get(paramName);

        if(paramValue==null){
            updateCache();
            paramValue = SYS_PARAM_CACHE.get(paramName);
        }

        if(paramValue==null){
          return  DEFAULT_PARAM_VALUES.get(paramName);
        }
        return paramValue;
    }

    @Override
    @PostConstruct
    public void updateCache() {
        LambdaQueryWrapper<SysPara> sysParaLambdaQueryWrapper = Wrappers.lambdaQuery();
        List<SysPara> alarmParamParentList = this.baseMapper.selectList(sysParaLambdaQueryWrapper);
        for (SysPara sysPara : alarmParamParentList) {
            SYS_PARAM_CACHE.put(sysPara.getParaName(), sysPara.getParaValue());
        }
    }


}
