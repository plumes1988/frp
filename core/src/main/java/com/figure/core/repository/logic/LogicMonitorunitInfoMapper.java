package com.figure.core.repository.logic;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.logic.LogicMonitorunitInfo;
import com.figure.core.model.logic.LogicMonitorunitInfoList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 监控单元信息 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
public interface LogicMonitorunitInfoMapper extends BaseMapper<LogicMonitorunitInfo> {

    List<LogicMonitorunitInfo> selectMonitorunitByMonitorChannelCode(String monitorChannelCode);

    List<LogicMonitorunitInfoList> relList(@Param(Constants.WRAPPER) Wrapper<LogicMonitorunitInfo> queryWrapper);
}