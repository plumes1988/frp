package com.figure.core.service.logic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.logic.LogicMonitorchannelInfo;
import com.figure.core.model.logic.LogicMonitorchannelInfoList;

import java.util.List;

/**
 * <p>
 * 监控频道管理信息 IService
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
public interface ILogicMonitorchannelInfoService extends IService<LogicMonitorchannelInfo> {

    List<LogicMonitorchannelInfoList> listByQuery(QueryWrapper<LogicMonitorchannelInfo> autoPageWrapper);

    List<Object> selectMonitorchannelMapList();

}