package com.figure.core.repository.signal;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.figure.core.model.signal.SignalInterfaceInfo;
import org.apache.ibatis.annotations.Select;


public interface SignalInterfaceInfoMapper extends BaseMapper<SignalInterfaceInfo> {
    @Select("select COALESCE(max(serialNumber)) from signal_interface_info where frontId = ${frontId} and signalCode = ${signalCode} and deviceId != ${deviceId}")
    Integer getMaxSerialNumber(Integer frontId,Integer signalCode,Integer deviceId);
}