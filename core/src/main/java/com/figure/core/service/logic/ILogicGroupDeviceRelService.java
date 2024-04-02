package com.figure.core.service.logic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.logic.LogicGroupDeviceRel;
import com.figure.core.model.logic.LogicGroupDeviceRelList;

import java.util.List;

/**
 * <p>
 * 逻辑分析组与设备关联 IService
 * </p>
 *
 * @author feather
 * @date 2023-03-10 16:19:09
 */
public interface ILogicGroupDeviceRelService extends IService<LogicGroupDeviceRel> {

    List<LogicGroupDeviceRelList> treelist(QueryWrapper<LogicGroupDeviceRel> autoPageWrapper);
}