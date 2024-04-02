package com.figure.core.repository.spectrum;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.spectrum.SpectrumServiceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 频谱服务信息 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2023-11-24 16:44:13
 */
public interface SpectrumServiceInfoMapper extends BaseMapper<SpectrumServiceInfo> {

    List<SpectrumServiceInfo> listrel(@Param(Constants.WRAPPER) Wrapper<SpectrumServiceInfo> autoPageWrapper);

    HashMap getDetailById(Integer id);
}