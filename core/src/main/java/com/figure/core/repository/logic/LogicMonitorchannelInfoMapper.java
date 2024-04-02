package com.figure.core.repository.logic;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.logic.LogicMonitorchannelInfo;
import com.figure.core.model.logic.LogicMonitorchannelInfoList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 监控频道管理信息 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
public interface LogicMonitorchannelInfoMapper extends BaseMapper<LogicMonitorchannelInfo> {

    List<LogicMonitorchannelInfoList> listByQuery(@Param(Constants.WRAPPER) Wrapper<LogicMonitorchannelInfo> queryWrapper);

}