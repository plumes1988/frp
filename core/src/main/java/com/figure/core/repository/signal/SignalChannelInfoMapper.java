package com.figure.core.repository.signal;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.figure.core.model.signal.SignalChannelInfo;
import com.figure.core.model.signal.SignalChannelInfoList;
import org.apache.ibatis.annotations.Param;


public interface SignalChannelInfoMapper extends BaseMapper<SignalChannelInfo> {
    SignalChannelInfoList selectSignalChannelInfoList(@Param("channelId") String channelId);
}
