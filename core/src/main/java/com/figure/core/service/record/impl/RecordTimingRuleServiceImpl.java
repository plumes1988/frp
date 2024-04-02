package com.figure.core.service.record.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.repository.record.RecordTimingRuleMapper;
import com.figure.core.model.record.RecordTimingRule;
import com.figure.core.service.record.IRecordTimingRuleService;

/**
 * <p>
 * 定时录制时间规则Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2021-10-19 10:36:18
 */
@Service
public class RecordTimingRuleServiceImpl extends ServiceImpl<RecordTimingRuleMapper, RecordTimingRule> implements IRecordTimingRuleService {

}