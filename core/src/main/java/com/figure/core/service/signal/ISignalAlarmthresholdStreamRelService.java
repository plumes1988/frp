package com.figure.core.service.signal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.signal.SignalAlarmthresholdStreamRel;
import com.figure.core.query.signal.SignalAlarmthresholdStreamRelQuery;

import java.util.List;

/**
 * <p>
 * 报警对象与报警门限规则关联 IService
 * </p>
 *
 * @author feather
 * @date 2021-12-09 15:25:23
 */
public interface ISignalAlarmthresholdStreamRelService extends IService<SignalAlarmthresholdStreamRel> {

    List<SignalAlarmthresholdStreamRel> listByQuery(QueryWrapper<SignalAlarmthresholdStreamRel> autoPageWrapper);
}