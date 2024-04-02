package com.figure.core.repository.signal;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.signal.SignalAlarmthresholdStreamRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 报警对象与报警门限规则关联 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2021-12-01 16:21:59
 */

public interface SignalAlarmthresholdStreamRelMapper extends BaseMapper<SignalAlarmthresholdStreamRel> {

    List<SignalAlarmthresholdStreamRel> listByQuery(@Param(Constants.WRAPPER) QueryWrapper<SignalAlarmthresholdStreamRel> autoPageWrapper);
}