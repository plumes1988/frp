package com.figure.core.service.logic.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.logic.LogicInterfaceChannelRel;
import com.figure.core.model.logic.LogicInterfaceChannelRelList;
import com.figure.core.repository.logic.LogicInterfaceChannelRelMapper;
import com.figure.core.service.logic.ILogicInterfaceChannelRelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 切换器接口和节目信息关联Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@Service
public class LogicInterfaceChannelRelServiceImpl extends ServiceImpl<LogicInterfaceChannelRelMapper, LogicInterfaceChannelRel> implements ILogicInterfaceChannelRelService {

    @Override
    public List<LogicInterfaceChannelRelList> treelist(QueryWrapper<LogicInterfaceChannelRel> autoPageWrapper) {
        return this.baseMapper.treelist(autoPageWrapper);
    }
}