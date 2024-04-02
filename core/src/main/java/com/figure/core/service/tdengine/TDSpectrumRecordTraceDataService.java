package com.figure.core.service.tdengine;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.spectrum.SpectrumAlarmTracedata;
import com.figure.core.model.spectrum.SpectrumRecordMessage;
import com.figure.core.model.spectrum.SpectrumRecordTracedata;

import java.util.List;

public interface TDSpectrumRecordTraceDataService extends IService<SpectrumRecordTracedata> {

    int  saveEntity(SpectrumRecordTracedata spectrumRecordTracedata);

    List<SpectrumRecordTracedata> listByWrapper(String tableName, QueryWrapper<SpectrumRecordTracedata> spectrumRecordTracedata);

    List<SpectrumRecordTracedata> selectEntityPage(Integer pageNo, Integer pageSize, QueryWrapper<SpectrumRecordTracedata> queryWrapper);

    int selectCount(QueryWrapper<SpectrumRecordTracedata> queryWrapper);

}
