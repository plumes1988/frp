package com.figure.core.service.signal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.signal.SignalAnalysisGroup;
import com.figure.core.model.signal.SignalAnalysisGroupList;

import java.util.List;

/**
 * <p>
 * 特征比对分析组 IService
 * </p>
 *
 * @author feather
 * @date 2022-11-28 09:58:04
 */
public interface ISignalAnalysisGroupService extends IService<SignalAnalysisGroup> {

    List<SignalAnalysisGroupList> listByQuery(QueryWrapper<SignalAnalysisGroup> autoPageWrapper);
}