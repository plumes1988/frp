package com.figure.core.service.logic.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.logic.*;
import com.figure.core.repository.logic.LogicMonitorunitInfoMapper;
import com.figure.core.service.logic.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 监控单元信息Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@Service
public class LogicMonitorunitInfoServiceImpl extends ServiceImpl<LogicMonitorunitInfoMapper, LogicMonitorunitInfo> implements ILogicMonitorunitInfoService {

    @Override
    public List<LogicMonitorunitInfo> selectMonitorunitByMonitorChannelCode(String monitorChannelCode) {
        List<LogicMonitorunitInfo> logicMonitorunitInfoList = this.baseMapper.selectMonitorunitByMonitorChannelCode(monitorChannelCode);
        return logicMonitorunitInfoList;
    }

    @Resource
    private ILogicMonitorunitInterfaceRelService logicMonitorunitInterfaceRelService;

    @Resource
    private ILogicMonitorunitLogicchannelRelService logicMonitorunitLogicchannelRelService;

    @Resource
    private ILogicMonitorunitInterfaceInfoService logicMonitorunitInterfaceInfoService;

    @Resource
    private ILogicInterfaceChannelRelService logicInterfaceChannelRelService;

    @Override
    public boolean deleteMonitorunitRel(List<Integer> unitIds) {
        //List<LogicMonitorunitInfo> logicMonitorunitInfoList = this.baseMapper.selectBatchIds(unitIds);
        for (Integer unitId : unitIds) {
            LambdaQueryWrapper<LogicMonitorunitInterfaceRel> logicMonitorunitInterfaceRelLambdaQueryWrapper = Wrappers.lambdaQuery();
            logicMonitorunitInterfaceRelLambdaQueryWrapper.eq(LogicMonitorunitInterfaceRel::getUnitId, unitId);
            List<LogicMonitorunitInterfaceRel> miRelList = this.logicMonitorunitInterfaceRelService.list(logicMonitorunitInterfaceRelLambdaQueryWrapper);
            List<String> interfaceInfoIds = new ArrayList<>();
            for (LogicMonitorunitInterfaceRel miRel : miRelList) {
                interfaceInfoIds.add(miRel.getInterfaceId());
            }
            this.logicMonitorunitInterfaceInfoService.removeByIds(interfaceInfoIds);
            this.logicMonitorunitInterfaceRelService.remove(logicMonitorunitInterfaceRelLambdaQueryWrapper);

            LambdaQueryWrapper<LogicMonitorunitLogicchannelRel> logicMonitorunitLogicchannelRelLambdaQueryWrapper = Wrappers.lambdaQuery();
            logicMonitorunitLogicchannelRelLambdaQueryWrapper.eq(LogicMonitorunitLogicchannelRel::getMonitorUnitId, unitId);
            this.logicMonitorunitLogicchannelRelService.remove(logicMonitorunitLogicchannelRelLambdaQueryWrapper);

            LambdaQueryWrapper<LogicInterfaceChannelRel> logicInterfaceChannelRelLambdaQueryWrapper = Wrappers.lambdaQuery();
            logicInterfaceChannelRelLambdaQueryWrapper.in(LogicInterfaceChannelRel::getInterfaceId, interfaceInfoIds);
            if (interfaceInfoIds.size() > 0)
                this.logicInterfaceChannelRelService.remove(logicInterfaceChannelRelLambdaQueryWrapper);
        }
        return true;
    }

    @Override
    public List<LogicMonitorunitInfoList> relList(QueryWrapper<LogicMonitorunitInfo> queryWrapper) {
        return this.baseMapper.relList(queryWrapper);
    }
}