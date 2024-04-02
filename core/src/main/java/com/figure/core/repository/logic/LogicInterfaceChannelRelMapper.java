package com.figure.core.repository.logic;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.logic.LogicInterfaceChannelRel;
import com.figure.core.model.logic.LogicInterfaceChannelRelList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 切换器接口和节目信息关联 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
public interface LogicInterfaceChannelRelMapper extends BaseMapper<LogicInterfaceChannelRel> {

    List<LogicInterfaceChannelRelList> treelist(@Param(Constants.WRAPPER) Wrapper<LogicInterfaceChannelRel> autoPageWrapper);
}