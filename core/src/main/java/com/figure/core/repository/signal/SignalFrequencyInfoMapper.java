package com.figure.core.repository.signal;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.signal.SignalFrequencyInfo;
import com.figure.core.model.signal.SignalFrequencyInfoList;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SignalFrequencyInfoMapper extends BaseMapper<SignalFrequencyInfo> {

    List<SignalFrequencyInfoList> treelist(@Param(Constants.WRAPPER) Wrapper<SignalFrequencyInfo> autoPageWrapper);
}
