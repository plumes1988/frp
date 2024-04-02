package com.figure.core.service.spectrum;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.spectrum.SpectrumServiceInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频谱服务信息 IService
 * </p>
 *
 * @author feather
 * @date 2023-11-24 16:44:13
 */
public interface ISpectrumServiceInfoService extends IService<SpectrumServiceInfo> {

    List<SpectrumServiceInfo> listrel(QueryWrapper<SpectrumServiceInfo> spectrumServiceInfoQueryWrapper);

    Map getDetailById(Integer id);
}