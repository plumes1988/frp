package com.figure.core.tdrepository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumAlarmTracedata;
import com.figure.core.model.spectrum.SpectrumRecordMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TDSpectrumAlarmTraceDataMapper extends BaseMapper<SpectrumAlarmTracedata> {

    int saveEntity(SpectrumAlarmTracedata spectrumAlarmTracedata);

    List<SpectrumAlarmTracedata> listByWrapper(@Param("tableName") String tableName,
                                               @Param(Constants.WRAPPER) QueryWrapper<SpectrumAlarmTracedata> spectrumRecordMessage);

    List<SpectrumAlarmTracedata> selectEntityPage(@Param("offset") Integer offset, @Param("size") Integer size,
                                                  @Param(Constants.WRAPPER) QueryWrapper<SpectrumAlarmTracedata> queryWrapper);

    int selectCount(@Param(Constants.WRAPPER) QueryWrapper<SpectrumAlarmTracedata> queryWrapper);
}
