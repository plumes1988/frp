package com.figure.core.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.sys.SysPara;

import java.util.Map;

/**
 * <p>
 * 系统参数表 服务类
 * </p>
 *
 * @author jobob
 * @since 2021-03-22
 */
public interface ISysParaService extends IService<SysPara> {

    void setParamCacheByName(String paramName);

    Map<String, String> getParamCacheByName(String paramName);

    void setAlarmParaCache();

    void setRecordParaCache();

    String getParamByName(String paramName);

    void updateCache();

}
