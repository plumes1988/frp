package com.figure.core.service.signal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.signal.SignalAlarmthresholdRule;
import com.figure.core.query.signal.SignalAlarmthresholdRuleQuery;

import java.util.List;

/**
 * <p>
 * 信号报警门限规则 IService
 * </p>
 *
 * @author feather
 * @date 2021-12-09 15:25:23
 */
public interface ISignalAlarmthresholdRuleService extends IService<SignalAlarmthresholdRule> {

    List<SignalAlarmthresholdRule> listByQuery(QueryWrapper<SignalAlarmthresholdRule> autoPageWrapper);
}