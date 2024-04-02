package com.figure.core.service.log.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.device.DeviceIndicatorParam;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.log.LogDeviceStateIndicatorChange;
import com.figure.core.repository.log.LogDeviceStateIndicatorChangeMapper;
import com.figure.core.rocketmq.struct.consumer.DeviceStateIndicatorChangeP2SConsumer;
import com.figure.core.service.device.IDeviceIndicatorParamService;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.log.ILogDeviceStateIndicatorChangeService;
import com.figure.core.service.notice.INoticeMessageService;
import com.figure.core.service.others.ICommonService;
import com.figure.core.sse.SseEmitterManager;
import com.figure.core.util.DateUtils;
import com.figure.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED;
import static com.figure.core.model.device.DeviceIndicatorParam.STATUS_VARIABLE;
import static com.figure.core.model.device.DeviceIndicatorParam.UNIT_TAG;
import static com.figure.core.model.notice.NoticeMessage.DEVICE_STATUS_CHANGE;
import static com.figure.core.sse.Constants.LOG_DEVICE_STATE_INDICATOR_CHANGE;

/**
 * <p>
 * 设备状态直播变更日志Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2024-01-17 14:21:47
 */
@Service
public class LogDeviceStateIndicatorChangeServiceImpl extends ServiceImpl<LogDeviceStateIndicatorChangeMapper, LogDeviceStateIndicatorChange> implements ILogDeviceStateIndicatorChangeService {


    @Resource
    private INoticeMessageService noticeMessageService;

    @Resource
    private IDeviceInfoService deviceInfoService;

    public static volatile Map<String,String> DEVICE_CODE_VALUE_MAP =  new HashMap<>();

    @Resource
    private IDeviceIndicatorParamService deviceIndicatorParamService;

    @Autowired
    ICommonService commonService;

    @Override
    public void processDeviceStateIndicatorChange(DeviceStateIndicatorChangeP2SConsumer consumer) {
        LambdaQueryWrapper<DeviceInfo> deviceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        deviceInfoLambdaQueryWrapper.eq(DeviceInfo::getDeviceCode, consumer.getDeviceCode());
        DeviceInfo deviceInfo = this.deviceInfoService.getOne(deviceInfoLambdaQueryWrapper);
        LogDeviceStateIndicatorChange logDeviceStateIndicatorChange = new LogDeviceStateIndicatorChange(consumer, deviceInfo.getMonitorStationId());
        this.save(logDeviceStateIndicatorChange);
        SseEmitterManager.sendMessageToAllMatchSseEmitter(logDeviceStateIndicatorChange);
        noticeMessageService.processNoticeMessage(DEVICE_STATUS_CHANGE, logDeviceStateIndicatorChange);
    }


    @Override
    public void fillEntityProps(List<LogDeviceStateIndicatorChange> records){

        Map<String, Map<String,String>>  indicatorCode_value_label_map = commonService.get_indicatorCode_value_label_map();

        Map<String, String> unit_tag_map = indicatorCode_value_label_map.get(UNIT_TAG);

        Map<String, String> deviceCode_deviceName_map  = commonService.get_deviceCode_deviceName_map();

        Map<Integer, String> frontId_frontName_map  = commonService.get_frontId_frontName_map();

        Map<String, String> indicatorCode_indicatorName_map  = commonService.get_indicatorCode_indicatorName_map();

        for(LogDeviceStateIndicatorChange logDeviceStateIndicatorChange: records) {

            String indicatorCode = logDeviceStateIndicatorChange.getIndicatorCode();

            String indicatorValue = logDeviceStateIndicatorChange.getOldIndicatorValue();

            if(indicatorCode_value_label_map.get(indicatorCode)!=null && indicatorCode_value_label_map.get(indicatorCode).get(indicatorValue)!=null){
                String new_indicatorValue = indicatorCode_value_label_map.get(indicatorCode).get(indicatorValue);
                String unit = unit_tag_map.get(logDeviceStateIndicatorChange.getIndicatorCode());
                if(!StringUtils.isEmpty(unit)){
                    new_indicatorValue += unit;
                }
                logDeviceStateIndicatorChange.setOldIndicatorValue(new_indicatorValue);
            }

            indicatorValue = logDeviceStateIndicatorChange.getNewIndicatorValue();

            if(indicatorCode_value_label_map.get(indicatorCode)!=null && indicatorCode_value_label_map.get(indicatorCode).get(indicatorValue)!=null){
                String new_indicatorValue = indicatorCode_value_label_map.get(indicatorCode).get(indicatorValue);
                String unit = unit_tag_map.get(logDeviceStateIndicatorChange.getIndicatorCode());
                if(!StringUtils.isEmpty(unit)){
                    new_indicatorValue += unit;
                }
                logDeviceStateIndicatorChange.setNewIndicatorValue(new_indicatorValue);
            }

            logDeviceStateIndicatorChange.setFrontName(frontId_frontName_map.get(logDeviceStateIndicatorChange.getFrontId()));

            logDeviceStateIndicatorChange.setDeviceName(deviceCode_deviceName_map.get(logDeviceStateIndicatorChange.getDeviceCode()));

            logDeviceStateIndicatorChange.setIndicatorName(indicatorCode_indicatorName_map.get(logDeviceStateIndicatorChange.getIndicatorCode()));

        }

    }
}