package com.figure.core.service.signal.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.signal.SignalAnalysisGroup;
import com.figure.core.model.signal.SignalAnalysisGroupList;
import com.figure.core.repository.signal.SignalAnalysisGroupMapper;
import com.figure.core.service.signal.ISignalAnalysisGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 特征比对分析组Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2022-11-28 09:58:04
 */
@Service
public class SignalAnalysisGroupServiceImpl extends ServiceImpl<SignalAnalysisGroupMapper, SignalAnalysisGroup> implements ISignalAnalysisGroupService {

    @Override
    public List<SignalAnalysisGroupList> listByQuery(QueryWrapper<SignalAnalysisGroup> queryWrapper) {
        return this.baseMapper.listByQuery(queryWrapper);
    }
}