package com.figure.core.service.tdengine.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumRecordMessage;
import com.figure.core.query.spectrum.SpectrumRecordMessageQuery;
import com.figure.core.service.tdengine.TDSpectrumRecordMessageService;
import com.figure.core.tdrepository.TDSpectrumRecordMessageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TDSpectrumRecordMessageServiceImpl extends ServiceImpl<TDSpectrumRecordMessageMapper, SpectrumRecordMessage> implements TDSpectrumRecordMessageService {

    @Override
    public int saveEntity(SpectrumRecordMessage spectrumRecordMessage) {
        return this.baseMapper.saveEntity(spectrumRecordMessage);
    }

    @Override
    public List<SpectrumRecordMessage> listByWrapper(String tableName, QueryWrapper<SpectrumRecordMessage> spectrumRecordMessage) {
        return this.baseMapper.listByWrapper(tableName, spectrumRecordMessage);
    }

    @Override
    public List<SpectrumRecordMessage> selectEntityPage(Integer pageNo, Integer pageSize, QueryWrapper<SpectrumRecordMessage> spectrumRecordMessageQueryWrapper) {
        Integer offset = null;
        if(pageNo!=null&&pageSize!=null){
            offset = (pageNo - 1) * pageSize;
        }
        return this.baseMapper.selectEntityPage(offset,pageSize,spectrumRecordMessageQueryWrapper);
    }

    @Override
    public int selectCount(QueryWrapper<SpectrumRecordMessage> spectrumRecordMessageQueryWrapper) {
        return this.baseMapper.selectCount(spectrumRecordMessageQueryWrapper);
    }
}
