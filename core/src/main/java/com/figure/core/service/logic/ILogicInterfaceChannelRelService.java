package com.figure.core.service.logic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.logic.LogicInterfaceChannelRel;
import com.figure.core.model.logic.LogicInterfaceChannelRelList;

import java.util.List;

/**
 * <p>
 * 切换器接口和节目信息关联 IService
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
public interface ILogicInterfaceChannelRelService extends IService<LogicInterfaceChannelRel> {

    List<LogicInterfaceChannelRelList> treelist(QueryWrapper<LogicInterfaceChannelRel> autoPageWrapper);
}