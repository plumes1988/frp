package com.figure.core.service.signal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.signal.SignalFrequencyInfo;
import com.figure.core.model.signal.SignalFrequencyInfoList;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频率信息 IService
 * </p>
 *
 * @author feather
 * @date 2021-05-25 17:10:38
 */
public interface ISignalFrequencyInfoService extends IService<SignalFrequencyInfo> {

    List<SignalFrequencyInfoList> treelist(QueryWrapper<SignalFrequencyInfo> autoPageWrapper);

    Map<String, SignalFrequencyInfo> getFrequencyByFrequencyIdMap();

    void setFrequencyByFrequencyIdMap();
}