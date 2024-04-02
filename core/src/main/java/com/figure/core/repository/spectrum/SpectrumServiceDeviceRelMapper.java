package com.figure.core.repository.spectrum;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.spectrum.SpectrumServiceDeviceRel;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 频谱服务和频谱仪设备关联 BaseMapper
 * </p>
 *
 * @author feather
 * @date 2023-11-24 16:44:13
 */
public interface SpectrumServiceDeviceRelMapper extends BaseMapper<SpectrumServiceDeviceRel> {

    List<HashMap<String, Object>> listRel(@Param(Constants.WRAPPER) Wrapper<SpectrumServiceDeviceRel> autoPageWrapper);
}