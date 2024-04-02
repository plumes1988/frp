package com.figure.core.service.logic.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.front.FrontLogicPosition;
import com.figure.core.model.logic.LogicGroupDeviceRel;
import com.figure.core.model.logic.LogicGroupDeviceRelList;
import com.figure.core.model.signal.SignalChannelInfo;
import com.figure.core.repository.logic.LogicGroupDeviceRelMapper;
import com.figure.core.service.front.IFrontLogicPositionService;
import com.figure.core.service.logic.ILogicGroupDeviceRelService;
import com.figure.core.service.signal.ISignalChannelInfoService;
import com.figure.core.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 逻辑分析组与设备关联Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2023-03-10 16:19:09
 */
@Service
public class LogicGroupDeviceRelServiceImpl extends ServiceImpl<LogicGroupDeviceRelMapper, LogicGroupDeviceRel> implements ILogicGroupDeviceRelService {
    @Resource
    private ISignalChannelInfoService signalChannelInfoService;

    @Resource
    private IFrontLogicPositionService frontLogicPositionService;

    @Override
    public List<LogicGroupDeviceRelList> treelist(QueryWrapper<LogicGroupDeviceRel> autoPageWrapper) {
        List<LogicGroupDeviceRelList> list = this.baseMapper.treelist(autoPageWrapper);
        for (LogicGroupDeviceRelList rel : list) {
            if (rel.getChannelIds() != null && rel.getChannelIds().length() > 0) {
                List<String> channelIdList = StringUtils.StringToStringList(rel.getChannelIds());
                List<LogicGroupDeviceRelList.ChannelIdInfo> channelIdInfoList = new ArrayList<>();
                for (String channelId : channelIdList) {
                    LogicGroupDeviceRelList.ChannelIdInfo channelIdInfo = new LogicGroupDeviceRelList.ChannelIdInfo();
                    channelIdInfo.setChannelId(channelId);
                    SignalChannelInfo signalChannelInfo = this.signalChannelInfoService.getById(channelId);
                    channelIdInfo.setChannelName(signalChannelInfo.getChannelName());
                    FrontLogicPosition frontLogicPosition = this.frontLogicPositionService.getById(signalChannelInfo.getLogicPositionId());
                    channelIdInfo.setPositionName(frontLogicPosition.getPositionName());
                    channelIdInfoList.add(channelIdInfo);
                }
                rel.setChannelIdInfoList(channelIdInfoList);
            }
        }
        return list;
    }
}