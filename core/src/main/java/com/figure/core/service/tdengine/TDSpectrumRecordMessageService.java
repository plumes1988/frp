package com.figure.core.service.tdengine;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumRecordMessage;
import com.figure.core.query.spectrum.SpectrumRecordMessageQuery;

import java.util.List;

public interface TDSpectrumRecordMessageService extends IService<SpectrumRecordMessage> {

    int saveEntity(SpectrumRecordMessage spectrumRecordMessage);

    List<SpectrumRecordMessage> listByWrapper(String tableName,QueryWrapper<SpectrumRecordMessage> spectrumRecordMessage);

    List<SpectrumRecordMessage> selectEntityPage(Integer pageNo, Integer pageSize, QueryWrapper<SpectrumRecordMessage> spectrumRecordMessageQueryWrapper);

    int selectCount(QueryWrapper<SpectrumRecordMessage> spectrumRecordMessageQueryWrapper);
}
