package com.figure.core.service.tdengine.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumRecordMessage;
import com.figure.core.model.spectrum.SpectrumRecordTracedata;
import com.figure.core.tdrepository.TDSpectrumRecordTraceDataMapper;
import com.figure.core.service.tdengine.TDSpectrumRecordTraceDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TDSpectrumRecordTraceDataServiceImpl extends ServiceImpl<TDSpectrumRecordTraceDataMapper, SpectrumRecordTracedata> implements TDSpectrumRecordTraceDataService {

    @Override
    public int saveEntity(SpectrumRecordTracedata spectrumRecordTracedata) {
        return this.baseMapper.saveEntity(spectrumRecordTracedata);
    }

    @Override
    public List<SpectrumRecordTracedata> listByWrapper(String tableName, QueryWrapper<SpectrumRecordTracedata> spectrumRecordTracedata) {
        return this.baseMapper.listByWrapper(tableName, spectrumRecordTracedata);
    }

    @Override
    public List<SpectrumRecordTracedata> selectEntityPage(Integer pageNo, Integer pageSize, QueryWrapper<SpectrumRecordTracedata> queryWrapper) {
        Integer offset = null;
        if(pageNo!=null&&pageSize!=null){
            offset = (pageNo - 1) * pageSize;
        }
        return this.baseMapper.selectEntityPage(offset,pageSize,queryWrapper);
    }

    @Override
    public int selectCount(QueryWrapper<SpectrumRecordTracedata> queryWrapper) {
        return this.baseMapper.selectCount(queryWrapper);
    }
}