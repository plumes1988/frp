package com.figure.core.service.logic.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.logic.LogicThresholdRuleChannelRel;
import com.figure.core.repository.logic.LogicThresholdRuleChannelRelMapper;
import com.figure.core.service.logic.ILogicThresholdRuleChannelRelService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 信号频道和报警门限规则关联Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2023-03-07 16:00:32
 */
@Service
public class LogicThresholdRuleChannelRelServiceImpl extends ServiceImpl<LogicThresholdRuleChannelRelMapper, LogicThresholdRuleChannelRel> implements ILogicThresholdRuleChannelRelService {

}