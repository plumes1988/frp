package com.figure.core.service.spectrum;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.spectrum.SpectrumServiceDeviceRel;
import com.figure.core.query.spectrum.SpectrumServiceDeviceRelQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频谱服务和频谱仪设备关联 IService
 * </p>
 *
 * @author feather
 * @date 2023-11-24 16:44:13
 */
public interface ISpectrumServiceDeviceRelService extends IService<SpectrumServiceDeviceRel> {

    List<HashMap<String, Object>> listRel(SpectrumServiceDeviceRelQuery spectrumServiceDeviceRelQuery);

    boolean spectrumSet(SpectrumServiceDeviceRel spectrumServiceDeviceRel);

    void spectrumServiceDataToRedis();

    Map<String, Map<String, SpectrumServiceDeviceRel>> getSpectrumServiceData();
}