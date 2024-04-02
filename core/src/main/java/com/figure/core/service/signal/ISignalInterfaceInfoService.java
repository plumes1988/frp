package com.figure.core.service.signal;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.signal.SignalInterfaceInfo;

import java.util.List;

/**
 * <p>
 * 信号接口信息 IService
 * </p>
 *
 * @author feather
 * @date 2021-05-25 17:10:38
 */
public interface ISignalInterfaceInfoService extends IService<SignalInterfaceInfo> {

    void bitchInsertOrUpdate(List<SignalInterfaceInfo> signalInterfaceInfos, DeviceInfo deviceInfo);

    void updateInterfaceSourceId(Integer interfaceId, String signalId);
}