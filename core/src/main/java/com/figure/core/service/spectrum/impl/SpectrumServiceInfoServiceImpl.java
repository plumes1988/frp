package com.figure.core.service.spectrum.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.spectrum.SpectrumServiceInfo;
import com.figure.core.repository.spectrum.SpectrumServiceInfoMapper;
import com.figure.core.service.spectrum.ISpectrumServiceInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频谱服务信息Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2023-11-24 16:44:13
 */
@Service
public class SpectrumServiceInfoServiceImpl extends ServiceImpl<SpectrumServiceInfoMapper, SpectrumServiceInfo> implements ISpectrumServiceInfoService {

    @Override
    public List<SpectrumServiceInfo> listrel(QueryWrapper<SpectrumServiceInfo> spectrumServiceInfoQueryWrapper) {
        return this.baseMapper.listrel(spectrumServiceInfoQueryWrapper);
    }

    @Override
    public Map getDetailById(Integer id) {
        return this.baseMapper.getDetailById(id);
    }
}