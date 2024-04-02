package com.figure.core.service.signal;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.signal.SignalAlarmtypeInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 默认报警类型信息 IService
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
public interface ISignalAlarmtypeInfoService extends IService<SignalAlarmtypeInfo> {

    void setSignalAlarmtypeListCache();

    List<SignalAlarmtypeInfo> getSignalAlarmtypeListCache();

    void setSignalAlarmtypeByNumberMap();

    Map<Integer, SignalAlarmtypeInfo> getSignalAlarmtypeByNumberMap();

}