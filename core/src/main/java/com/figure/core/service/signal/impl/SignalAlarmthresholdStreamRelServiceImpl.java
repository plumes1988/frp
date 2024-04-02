package com.figure.core.service.signal.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.repository.signal.SignalAlarmthresholdStreamRelMapper;
import com.figure.core.model.signal.SignalAlarmthresholdStreamRel;
import com.figure.core.service.signal.ISignalAlarmthresholdStreamRelService;

import java.util.List;

/**
 * <p>
 * 报警对象与报警门限规则关联Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2021-12-09 15:25:23
 */
@Service
public class SignalAlarmthresholdStreamRelServiceImpl extends ServiceImpl<SignalAlarmthresholdStreamRelMapper, SignalAlarmthresholdStreamRel> implements ISignalAlarmthresholdStreamRelService {

    @Override
    public List<SignalAlarmthresholdStreamRel> listByQuery(QueryWrapper<SignalAlarmthresholdStreamRel> autoPageWrapper) {
        return this.baseMapper.listByQuery(autoPageWrapper);
    }
}