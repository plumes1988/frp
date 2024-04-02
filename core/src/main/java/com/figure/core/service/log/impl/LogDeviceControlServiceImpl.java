package com.figure.core.service.log.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.log.LogDeviceControl;
import com.figure.core.repository.log.LogDeviceControlMapper;
import com.figure.core.service.log.ILogDeviceControlService;
import com.figure.core.service.others.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备操作日志 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-12-05
 */
@Service
public class LogDeviceControlServiceImpl extends ServiceImpl<LogDeviceControlMapper, LogDeviceControl> implements ILogDeviceControlService {

    @Autowired
    ICommonService commonService;

    @Override
    public void fillEntityProps(List<LogDeviceControl> records) {

        Map<String, Map<String,String>>  map = commonService.get_indicatorCode_value_label_map();

        Map<String,String> deviceCode_deviceName_map = commonService.get_deviceCode_deviceName_map();

        Map<Long,String> userId_userName_map = commonService.get_userId_userName_map();

        Map<String,String> indicatorCode_indicatorName_map = commonService.get_indicatorCode_indicatorName_map();

        Map<Integer, String> frontId_frontName_map = commonService.get_frontId_frontName_map();

        for(LogDeviceControl logDeviceControl:records) {
            String indicatorCode = logDeviceControl.getIndicatorCode();
            String indicatorValue = logDeviceControl.getIndicatorValue();
            if(map.get(indicatorCode)!=null && map.get(indicatorCode).get(indicatorValue)!=null){
                logDeviceControl.setIndicatorValue(map.get(indicatorCode).get(indicatorValue));
            }
            logDeviceControl.setIndicatorName(indicatorCode_indicatorName_map.get(logDeviceControl.getIndicatorCode()));
            Integer operatorUserId = logDeviceControl.getOperatorUserId();
            if(operatorUserId!=null){
                logDeviceControl.setOperatorUserName(userId_userName_map.get(Long.parseLong(operatorUserId.toString())));
            }
            logDeviceControl.setDeviceName(deviceCode_deviceName_map.get(logDeviceControl.getDeviceCode()));
            logDeviceControl.setFrontName(frontId_frontName_map.get(logDeviceControl.getFrontId()));
        }
    }
}
