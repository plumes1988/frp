package com.figure.core.service.signal;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.signal.SignalChannelInfo;
import com.figure.core.model.signal.SignalChannelInfoList;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频道信息 IService
 * </p>
 *
 * @author feather
 * @date 2021-05-25 17:10:37
 */
public interface ISignalChannelInfoService extends IService<SignalChannelInfo> {

    List<Object> selectSignalChannelInfoTreeList();

    SignalChannelInfoList selectSignalChannelInfoList(String channelIdList);

    void setChannelByChannelIdMap();

    Map<String, SignalChannelInfo> getChannelByChannelIdMap();
}