package com.figure.core.service.tdengine;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumAlarmTracedata;
import com.figure.core.model.spectrum.SpectrumRecordMessage;

import java.util.List;

public interface TDSpectrumAlarmTracedataService extends IService<SpectrumAlarmTracedata> {

    int saveEntity(SpectrumAlarmTracedata spectrumAlarmTracedata);

    List<SpectrumAlarmTracedata> listByWrapper(String tableName, QueryWrapper<SpectrumAlarmTracedata> spectrumAlarmTracedataQueryWrapper);

    List<SpectrumAlarmTracedata> selectEntityPage(Integer pageNo, Integer pageSize, QueryWrapper<SpectrumAlarmTracedata> queryWrapper);

    int selectCount(QueryWrapper<SpectrumAlarmTracedata> queryWrapper);
}
