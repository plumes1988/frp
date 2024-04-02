package com.figure.core.service.device;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.device.DeviceIndicatorParam;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2022-06-14
 */
public interface IDeviceIndicatorParamService extends IService<DeviceIndicatorParam> {

    Map<String, Integer> getIndicatorCodeDataTypeMapFromCache();

    void setIndicatorCodeDataTypeMapCache();

    void setIndicatorCodeIsCriticalMapCache();

    void updateCache();
}
