package com.figure.core.service.tdengine;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumAlarmTracedata;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface TDSpectrumAlarmMessageService extends IService<SpectrumAlarmMessage> {

    int saveEntity(SpectrumAlarmMessage spectrumAlarmMessage);

    List<SpectrumAlarmMessage> listByWrapper(String tableName, QueryWrapper<SpectrumAlarmMessage> spectrumAlarmMessageQuery);

    List<SpectrumAlarmMessage> selectEntityPage(Integer pageNo,Integer pageSize, QueryWrapper<SpectrumAlarmMessage> spectrumAlarmMessageQueryWrapper);

    int selectCount(QueryWrapper<SpectrumAlarmMessage> spectrumAlarmMessageQueryWrapper);
}
