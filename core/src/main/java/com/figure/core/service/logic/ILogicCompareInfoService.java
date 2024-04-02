package com.figure.core.service.logic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.logic.LogicCompareInfo;
import com.figure.core.model.logic.LogicCompareInfoList;

import java.util.List;

/**
 * <p>
 * 异态比对分组配置 IService
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
public interface ILogicCompareInfoService extends IService<LogicCompareInfo> {

    List<LogicCompareInfoList> treelist(QueryWrapper<LogicCompareInfo> autoPageWrapper);
}