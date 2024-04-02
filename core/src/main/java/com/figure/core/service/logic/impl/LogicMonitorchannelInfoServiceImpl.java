package com.figure.core.service.logic.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.model.logic.LogicMonitorchannelInfo;
import com.figure.core.model.logic.LogicMonitorchannelInfoList;
import com.figure.core.model.logic.LogicMonitorunitInfo;
import com.figure.core.repository.logic.LogicMonitorchannelInfoMapper;
import com.figure.core.service.logic.ILogicMonitorChannelUnitRelService;
import com.figure.core.service.logic.ILogicMonitorchannelInfoService;
import com.figure.core.service.logic.ILogicMonitorunitInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 监控频道管理信息Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@Service
public class LogicMonitorchannelInfoServiceImpl extends ServiceImpl<LogicMonitorchannelInfoMapper, LogicMonitorchannelInfo> implements ILogicMonitorchannelInfoService {
    @Resource
    ILogicMonitorunitInfoService logicMonitorunitInfoService;

    @Resource
    ILogicMonitorChannelUnitRelService logicMonitorChannelUnitRelService;

    @Override
    public List<LogicMonitorchannelInfoList> listByQuery(QueryWrapper<LogicMonitorchannelInfo> queryWrapper) {
        return this.baseMapper.listByQuery(queryWrapper);
    }

    @Override
    public List<Object> selectMonitorchannelMapList() {
        LambdaQueryWrapper<LogicMonitorchannelInfo> logicMonitorchannelInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        logicMonitorchannelInfoLambdaQueryWrapper.eq(LogicMonitorchannelInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<LogicMonitorchannelInfo> logicMonitorchannelInfoList = this.list(logicMonitorchannelInfoLambdaQueryWrapper);
        List<Object> result = new ArrayList<>();
        for (LogicMonitorchannelInfo logicMonitorchannelInfo : logicMonitorchannelInfoList) {
            logicMonitorchannelInfo.setParentIdString("0");
            result.add(logicMonitorchannelInfo);
            List<LogicMonitorunitInfo> logicMonitorunitInfoList = this.logicMonitorunitInfoService.selectMonitorunitByMonitorChannelCode(logicMonitorchannelInfo.getMonitorChannelCode());
            result.addAll(logicMonitorunitInfoList);
        }
        return result;
    }
}