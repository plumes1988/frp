package com.figure.core.repository.logic;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.logic.LogicGroupChannelRel;
import com.figure.core.model.logic.LogicGroupChannelRelList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 逻辑分析组与频道关联 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */

public interface LogicGroupChannelRelMapper extends BaseMapper<LogicGroupChannelRel> {

    List<LogicGroupChannelRelList> treelist(@Param(Constants.WRAPPER) Wrapper<LogicGroupChannelRel> autoPageWrapper);
}