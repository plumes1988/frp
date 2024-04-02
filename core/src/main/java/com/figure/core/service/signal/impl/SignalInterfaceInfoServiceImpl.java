package com.figure.core.service.signal.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.signal.SignalInterfaceInfo;
import com.figure.core.repository.signal.SignalInterfaceInfoMapper;
import com.figure.core.service.signal.ISignalInterfaceInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 信号接口信息Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2021-05-25 17:10:38
 */
@Service
public class SignalInterfaceInfoServiceImpl extends ServiceImpl<SignalInterfaceInfoMapper, SignalInterfaceInfo> implements ISignalInterfaceInfoService {

    @Override
    public void bitchInsertOrUpdate(List<SignalInterfaceInfo> signalInterfaceInfos, DeviceInfo deviceInfo) {
        if(deviceInfo.getSignalType()==null){
           return;
        }
        Integer maxSerialNumber = this.getBaseMapper().getMaxSerialNumber(deviceInfo.getMonitorStationId(),deviceInfo.getSignalType(),deviceInfo.getDeviceId());

        if(maxSerialNumber==null){
            maxSerialNumber = 0;
        }

        List<Integer> ids = new ArrayList<Integer>();

        List<SignalInterfaceInfo> new_update_signalInterfaceInfo = new ArrayList<SignalInterfaceInfo>();

        for (int i = 0 ;i<signalInterfaceInfos.size() ;i++) {
            SignalInterfaceInfo signalInterfaceInfo =  signalInterfaceInfos.get(i);
            signalInterfaceInfo.setDeviceId(deviceInfo.getDeviceId());
            signalInterfaceInfo.setLogicPositionId(deviceInfo.getLogicPositionId());
            signalInterfaceInfo.setFrontId(deviceInfo.getMonitorStationId());
            signalInterfaceInfo.setSignalCode(deviceInfo.getSignalType());
            signalInterfaceInfo.setInterfaceIp(deviceInfo.getControlIP());
            signalInterfaceInfo.setSerialNumber(++maxSerialNumber);
            ids.add(signalInterfaceInfo.getInterfaceId());
            new_update_signalInterfaceInfo.add(signalInterfaceInfo);
        }
        if(ids.size()>0){
            QueryWrapper queryWrapper = new QueryWrapper();

            queryWrapper.notIn("interfaceId",ids);
            queryWrapper.eq("deviceId",deviceInfo.getDeviceId());
            this.remove(queryWrapper);
        }

        int start = 1;

        for (SignalInterfaceInfo signalInterfaceInfo : new_update_signalInterfaceInfo) {
            signalInterfaceInfo.setInterfaceNumber(start++);
            this.saveOrUpdate(signalInterfaceInfo);
        }
    }

    @Override
    public void updateInterfaceSourceId(Integer interfaceId, String signalId) {

        LambdaUpdateWrapper<SignalInterfaceInfo> signalInterfaceInfoLambdaUpdateWrapper = Wrappers.lambdaUpdate();

        signalInterfaceInfoLambdaUpdateWrapper.eq(SignalInterfaceInfo::getInterfaceId, interfaceId).set(SignalInterfaceInfo::getSourceId, signalId);

        this.update(signalInterfaceInfoLambdaUpdateWrapper);

    }
}