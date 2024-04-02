package com.figure.core.tdrepository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumRecordMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TDSpectrumAlarmMessageMapper extends BaseMapper<SpectrumAlarmMessage> {

    int saveEntity(SpectrumAlarmMessage spectrumAlarmMessage);

    List<SpectrumAlarmMessage> listByWrapper(@Param("tableName") String tableName,
                                             @Param(Constants.WRAPPER) QueryWrapper<SpectrumAlarmMessage> spectrumAlarmMessage);

    List<SpectrumAlarmMessage> selectEntityPage(@Param("offset") Integer offset, @Param("size") Integer size,
                                                @Param(Constants.WRAPPER) QueryWrapper<SpectrumAlarmMessage> queryWrapper);

    int selectCount(@Param(Constants.WRAPPER) QueryWrapper<SpectrumAlarmMessage> queryWrapper);
}
