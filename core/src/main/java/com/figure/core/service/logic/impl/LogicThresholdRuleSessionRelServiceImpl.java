package com.figure.core.service.logic.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.repository.logic.LogicThresholdRuleSessionRelMapper;
import com.figure.core.model.logic.LogicThresholdRuleSessionRel;
import com.figure.core.service.logic.ILogicThresholdRuleSessionRelService;

/**
 * <p>
 * 重点时段和报警门限规则关联Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Service
public class LogicThresholdRuleSessionRelServiceImpl extends ServiceImpl<LogicThresholdRuleSessionRelMapper, LogicThresholdRuleSessionRel> implements ILogicThresholdRuleSessionRelService {

}