package com.figure.core.service.signal;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.signal.SignalLogicChannel;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 逻辑频道管理 服务类
 * </p>
 *
 * @author jobob
 * @since 2021-05-19
 */
public interface ISignalLogicChannelService extends IService<SignalLogicChannel> {

    void setSignalLogicChannelListCache();

    List<SignalLogicChannel> getSignalLogicChannelListCache();

    Map<String, SignalLogicChannel> getLogicByChannelCodeMap();
}
