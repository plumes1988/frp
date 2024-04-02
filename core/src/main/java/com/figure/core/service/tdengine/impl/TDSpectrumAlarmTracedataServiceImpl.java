package com.figure.core.service.tdengine.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumAlarmTracedata;
import com.figure.core.model.spectrum.SpectrumRecordMessage;
import com.figure.core.service.tdengine.TDSpectrumAlarmMessageService;
import com.figure.core.service.tdengine.TDSpectrumAlarmTracedataService;
import com.figure.core.tdrepository.TDSpectrumAlarmMessageMapper;
import com.figure.core.tdrepository.TDSpectrumAlarmTraceDataMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TDSpectrumAlarmTracedataServiceImpl extends ServiceImpl<TDSpectrumAlarmTraceDataMapper, SpectrumAlarmTracedata> implements TDSpectrumAlarmTracedataService {

    @Override
    public int saveEntity(SpectrumAlarmTracedata spectrumAlarmTracedata) {

        return this.baseMapper.saveEntity(spectrumAlarmTracedata);

    }

    @Override
    public List<SpectrumAlarmTracedata> listByWrapper(String tableName, QueryWrapper<SpectrumAlarmTracedata> spectrumAlarmTracedataQueryWrapper) {
        return this.baseMapper.listByWrapper(tableName, spectrumAlarmTracedataQueryWrapper);
    }

    @Override
    public List<SpectrumAlarmTracedata> selectEntityPage(Integer pageNo,Integer pageSize, QueryWrapper<SpectrumAlarmTracedata> queryWrapper) {
        Integer offset = null;
        if(pageNo!=null&&pageSize!=null){
            offset = (pageNo - 1) * pageSize;
        }
        return this.baseMapper.selectEntityPage(offset,pageSize,queryWrapper);
    }

    @Override
    public int selectCount(QueryWrapper<SpectrumAlarmTracedata> queryWrapper) {
        return this.baseMapper.selectCount(queryWrapper);
    }
}
