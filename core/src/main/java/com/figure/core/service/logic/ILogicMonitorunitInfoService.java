package com.figure.core.service.logic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.logic.LogicMonitorunitInfo;
import com.figure.core.model.logic.LogicMonitorunitInfoList;

import java.util.List;

/**
 * <p>
 * 监控单元信息 IService
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
public interface ILogicMonitorunitInfoService extends IService<LogicMonitorunitInfo> {

    List<LogicMonitorunitInfo> selectMonitorunitByMonitorChannelCode(String monitorChannelCode);

    boolean deleteMonitorunitRel(List<Integer> unitIds);

    List<LogicMonitorunitInfoList> relList(QueryWrapper<LogicMonitorunitInfo> queryWrapper);
}