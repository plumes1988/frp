package com.figure.core.service.logic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.logic.LogicCustomizeInfoPara;
import com.figure.core.model.logic.LogicCustomizeInfoParaList;

import java.util.List;

/**
 * <p>
 * 自定义业务逻辑参数 IService
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
public interface ILogicCustomizeInfoParaService extends IService<LogicCustomizeInfoPara> {

    List<LogicCustomizeInfoParaList> treelist(QueryWrapper<LogicCustomizeInfoPara> autoPageWrapper);
}