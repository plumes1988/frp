package com.figure.core.repository.signal;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.signal.SignalAlarmthresholdRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 信号报警门限规则 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2021-12-01 16:21:59
 */

public interface SignalAlarmthresholdRuleMapper extends BaseMapper<SignalAlarmthresholdRule> {

    List<SignalAlarmthresholdRule> listByQuery(@Param(Constants.WRAPPER) QueryWrapper<SignalAlarmthresholdRule> autoPageWrapper);
}