package com.figure.core.repository.logic;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.logic.LogicCompareInfo;
import com.figure.core.model.logic.LogicCompareInfoList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 异态比对分组配置 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */

public interface LogicCompareInfoMapper extends BaseMapper<LogicCompareInfo> {

    List<LogicCompareInfoList> treelist(@Param(Constants.WRAPPER) Wrapper<LogicCompareInfo> autoPageWrapper);
}