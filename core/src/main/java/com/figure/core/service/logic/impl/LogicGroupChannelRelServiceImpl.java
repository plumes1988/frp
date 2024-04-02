package com.figure.core.service.logic.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.logic.LogicGroupChannelRel;
import com.figure.core.model.logic.LogicGroupChannelRelList;
import com.figure.core.repository.logic.LogicGroupChannelRelMapper;
import com.figure.core.service.logic.ILogicGroupChannelRelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 逻辑分析组与频道关联Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Service
public class LogicGroupChannelRelServiceImpl extends ServiceImpl<LogicGroupChannelRelMapper, LogicGroupChannelRel> implements ILogicGroupChannelRelService {

    @Override
    public List<LogicGroupChannelRelList> treelist(QueryWrapper<LogicGroupChannelRel> autoPageWrapper) {
        return this.baseMapper.treelist(autoPageWrapper);
    }
}