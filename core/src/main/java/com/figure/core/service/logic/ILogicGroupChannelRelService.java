package com.figure.core.service.logic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.logic.LogicGroupChannelRel;
import com.figure.core.model.logic.LogicGroupChannelRelList;

import java.util.List;

/**
 * <p>
 * 逻辑分析组与频道关联 IService
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
public interface ILogicGroupChannelRelService extends IService<LogicGroupChannelRel> {

    List<LogicGroupChannelRelList> treelist(QueryWrapper<LogicGroupChannelRel> autoPageWrapper);

}