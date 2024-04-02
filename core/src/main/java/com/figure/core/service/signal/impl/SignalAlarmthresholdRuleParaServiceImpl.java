package com.figure.core.service.signal.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.repository.signal.SignalAlarmthresholdRuleParaMapper;
import com.figure.core.model.signal.SignalAlarmthresholdRulePara;
import com.figure.core.service.signal.ISignalAlarmthresholdRuleParaService;

import java.util.List;

/**
 * <p>
 * 信号报警门限规则参数Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2021-12-09 15:25:23
 */
@Service
public class SignalAlarmthresholdRuleParaServiceImpl extends ServiceImpl<SignalAlarmthresholdRuleParaMapper, SignalAlarmthresholdRulePara> implements ISignalAlarmthresholdRuleParaService {

    @Override
    public List<SignalAlarmthresholdRulePara> listByQuery(QueryWrapper<SignalAlarmthresholdRulePara> autoPageWrapper) {
        return this.baseMapper.listByQuery(autoPageWrapper);
    }
}