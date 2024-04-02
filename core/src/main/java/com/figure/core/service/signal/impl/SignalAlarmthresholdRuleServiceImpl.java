package com.figure.core.service.signal.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.repository.signal.SignalAlarmthresholdRuleMapper;
import com.figure.core.model.signal.SignalAlarmthresholdRule;
import com.figure.core.service.signal.ISignalAlarmthresholdRuleService;

import java.util.List;

/**
 * <p>
 * 信号报警门限规则Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2021-12-09 15:25:23
 */
@Service
public class SignalAlarmthresholdRuleServiceImpl extends ServiceImpl<SignalAlarmthresholdRuleMapper, SignalAlarmthresholdRule> implements ISignalAlarmthresholdRuleService {

    @Override
    public List<SignalAlarmthresholdRule> listByQuery(QueryWrapper<SignalAlarmthresholdRule> autoPageWrapper) {
        return this.baseMapper.listByQuery(autoPageWrapper);
    }
}