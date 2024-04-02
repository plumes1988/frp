package com.figure.core.service.logic.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.logic.LogicCompareInfo;
import com.figure.core.model.logic.LogicCompareInfoList;
import com.figure.core.repository.logic.LogicCompareInfoMapper;
import com.figure.core.service.logic.ILogicCompareInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 异态比对分组配置Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Service
public class LogicCompareInfoServiceImpl extends ServiceImpl<LogicCompareInfoMapper, LogicCompareInfo> implements ILogicCompareInfoService {

    @Override
    public List<LogicCompareInfoList> treelist(QueryWrapper<LogicCompareInfo> autoPageWrapper) {
        return this.baseMapper.treelist(autoPageWrapper);
    }
}