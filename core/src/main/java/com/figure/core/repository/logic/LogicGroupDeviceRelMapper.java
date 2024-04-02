package com.figure.core.repository.logic;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.logic.LogicGroupDeviceRel;
import com.figure.core.model.logic.LogicGroupDeviceRelList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 逻辑分析组与设备关联 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2023-03-10 16:19:09
 */
public interface LogicGroupDeviceRelMapper extends BaseMapper<LogicGroupDeviceRel> {

    List<LogicGroupDeviceRelList> treelist(@Param(Constants.WRAPPER) Wrapper<LogicGroupDeviceRel> autoPageWrapper);
}