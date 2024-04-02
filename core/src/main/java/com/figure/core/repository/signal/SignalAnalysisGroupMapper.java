package com.figure.core.repository.signal;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.signal.SignalAnalysisGroup;
import com.figure.core.model.signal.SignalAnalysisGroupList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 特征比对分析组 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2022-11-28 09:58:04
 */
public interface SignalAnalysisGroupMapper extends BaseMapper<SignalAnalysisGroup> {

    List<SignalAnalysisGroupList> listByQuery(@Param(Constants.WRAPPER) Wrapper<SignalAnalysisGroup> queryWrapper);

}