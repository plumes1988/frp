package com.figure.core.repository.signal;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.signal.SignalAlarmthresholdRulePara;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 信号报警门限规则参数 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2021-12-01 16:21:59
 */

public interface SignalAlarmthresholdRuleParaMapper extends BaseMapper<SignalAlarmthresholdRulePara> {

    List<SignalAlarmthresholdRulePara> listByQuery(@Param(Constants.WRAPPER) QueryWrapper<SignalAlarmthresholdRulePara> autoPageWrapper);
}