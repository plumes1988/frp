package com.figure.core.tdrepository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumRecordMessage;
import com.figure.core.model.spectrum.SpectrumRecordTracedata;
import com.figure.core.query.spectrum.SpectrumRecordMessageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TDSpectrumRecordMessageMapper extends BaseMapper<SpectrumRecordMessage> {

    int saveEntity(SpectrumRecordMessage spectrumRecordMessage);

    List<SpectrumRecordMessage> listByWrapper(@Param("tableName") String tableName,
                                              @Param(Constants.WRAPPER) QueryWrapper<SpectrumRecordMessage> spectrumRecordMessage);

    List<SpectrumRecordMessage> selectEntityPage(@Param("offset") Integer offset,
                                                 @Param("size") Integer size, @Param(Constants.WRAPPER) QueryWrapper<SpectrumRecordMessage> queryWrapper);

    int selectCount(@Param(Constants.WRAPPER) QueryWrapper<SpectrumRecordMessage> queryWrapper);
}
