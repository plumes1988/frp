package com.figure.core.tdrepository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumRecordMessage;
import com.figure.core.model.spectrum.SpectrumRecordTracedata;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TDSpectrumRecordTraceDataMapper extends BaseMapper<SpectrumRecordTracedata> {

    int saveEntity(SpectrumRecordTracedata spectrumRecordTracedata);

    List<SpectrumRecordTracedata> listByWrapper(@Param("tableName") String tableName,
                                                @Param(Constants.WRAPPER) QueryWrapper<SpectrumRecordTracedata> spectrumRecordTracedata);

    List<SpectrumRecordTracedata> selectEntityPage(@Param("offset") Integer offset,
                                                   @Param("size") Integer size, @Param(Constants.WRAPPER) QueryWrapper<SpectrumRecordTracedata> queryWrapper);

    int selectCount(@Param(Constants.WRAPPER) QueryWrapper<SpectrumRecordTracedata> queryWrappe);
}
