package com.figure.core.service.logic.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.logic.LogicCustomizeInfoPara;
import com.figure.core.model.logic.LogicCustomizeInfoParaList;
import com.figure.core.repository.logic.LogicCustomizeInfoParaMapper;
import com.figure.core.service.logic.ILogicCustomizeInfoParaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 自定义业务逻辑参数Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Service
public class LogicCustomizeInfoParaServiceImpl extends ServiceImpl<LogicCustomizeInfoParaMapper, LogicCustomizeInfoPara> implements ILogicCustomizeInfoParaService {

    @Override
    public List<LogicCustomizeInfoParaList> treelist(QueryWrapper<LogicCustomizeInfoPara> autoPageWrapper) {
        return this.baseMapper.treelist(autoPageWrapper);
    }
}