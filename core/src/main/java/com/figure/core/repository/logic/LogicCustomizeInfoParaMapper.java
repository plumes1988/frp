package com.figure.core.repository.logic;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.logic.LogicCustomizeInfoPara;
import com.figure.core.model.logic.LogicCustomizeInfoParaList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 自定义业务逻辑参数 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */

public interface LogicCustomizeInfoParaMapper extends BaseMapper<LogicCustomizeInfoPara> {

    List<LogicCustomizeInfoParaList> treelist(@Param(Constants.WRAPPER) Wrapper<LogicCustomizeInfoPara> autoPageWrapper);
}