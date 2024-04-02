package com.figure.core.service.tdengine.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumRecordMessage;
import com.figure.core.service.tdengine.TDSpectrumAlarmMessageService;
import com.figure.core.tdrepository.TDSpectrumAlarmMessageMapper;
import com.figure.core.util.copycat.annotaion.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TDSpectrumAlarmMessageServiceImpl extends ServiceImpl<TDSpectrumAlarmMessageMapper, SpectrumAlarmMessage> implements TDSpectrumAlarmMessageService {

    @Override
    public int saveEntity(SpectrumAlarmMessage spectrumAlarmMessage) {

        return this.baseMapper.saveEntity(spectrumAlarmMessage);

    }

    @Override
    public List<SpectrumAlarmMessage> listByWrapper(String tableName, QueryWrapper<SpectrumAlarmMessage> spectrumAlarmMessageQueryWrapper) {
        return this.baseMapper.listByWrapper(tableName, spectrumAlarmMessageQueryWrapper);
    }

    @Override
    public List<SpectrumAlarmMessage> selectEntityPage(Integer pageNo,Integer pageSize, QueryWrapper<SpectrumAlarmMessage> spectrumAlarmMessageQueryWrapper) {
        Integer offset = null;
        if(pageNo!=null&&pageSize!=null){
            offset = (pageNo - 1) * pageSize;
        }
        return this.baseMapper.selectEntityPage(offset,pageSize,spectrumAlarmMessageQueryWrapper);
    }

    @Override
    public int selectCount(QueryWrapper<SpectrumAlarmMessage> spectrumAlarmMessageQueryWrapper) {
        return this.baseMapper.selectCount(spectrumAlarmMessageQueryWrapper);
    }
}
