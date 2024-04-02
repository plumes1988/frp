package com.figure.core.service.signal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.signal.SignalAlarmthresholdRulePara;
import com.figure.core.query.signal.SignalAlarmthresholdRuleParaQuery;

import java.util.List;

/**
 * <p>
 * 信号报警门限规则参数 IService
 * </p>
 *
 * @author feather
 * @date 2021-12-09 15:25:23
 */
public interface ISignalAlarmthresholdRuleParaService extends IService<SignalAlarmthresholdRulePara> {

    List<SignalAlarmthresholdRulePara> listByQuery(QueryWrapper<SignalAlarmthresholdRulePara> autoPageWrapper);
}