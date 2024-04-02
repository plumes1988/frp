package com.figure.system.scheduled;

import com.figure.core.constant.LogConstants;
import com.figure.core.db.JavaMemoryDb;
import com.figure.core.entity.DeviceIndicatorAlarmStatus;
import com.figure.core.entity.ServiceHealthStatus;
import com.figure.core.model.device.DeviceCollectServer;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.service.device.*;
import com.figure.core.service.log.ILogServiceService;
import com.figure.core.service.others.ICommonService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.util.BusinessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.figure.core.constant.Constants.DEFAULT_COLLECT_SERVICE_CHECK_PORT;
import static com.figure.core.constant.ConstantsForSysPara.*;
import static com.figure.core.db.JavaMemoryDb.DEVICE_CUR_ALARM_STATUS_CACHE;
import static com.figure.core.db.JavaMemoryDb.SERVICE_ONLINE_STATUS;
import static com.figure.core.model.device.DeviceAlarmMessage.MISINTERPRET;
import static com.figure.core.model.device.DeviceAlarmMessage.UN_CONFIRMED;
import static com.figure.core.model.device.DeviceServiceAlarmMessage.ALARMTYPE_OFFLINE;
import static com.figure.core.model.log.LogService.*;

//被监测设备离线监测
@Component
public class DeviceOnlineStatusCheckScheduler {

    @Resource
    ISysParaService sysParaService;

    @Resource
    private IDeviceInfoService deviceInfoService;

    @Resource
    private IDeviceAlarmMessageCacheService deviceAlarmMessageCacheService;

    @Resource
    private IDeviceAlarmMessageService deviceAlarmMessageService;

    @Autowired
    ICommonService commonService;

    @Resource
    private IDeviceIndicatorParamRelService deviceIndicatorParamRelService;

    @Resource
    private IDeviceCollectServerService deviceCollectServerService;

    @Resource
    private ILogServiceService logServiceService;

    @Resource
    private IDeviceServiceAlarmMessageService deviceServiceAlarmMessageService;

    @Scheduled(fixedRate = 5 * 1000, initialDelay = 20 * 1000)//间隔5秒
    private void monitoredDeviceCheck() {

        Map<String, Integer>  deviceCodeOfflineDecisionThresholdMap = deviceInfoService.getDeviceCodeOfflineDecisionThresholdMapFromCache();

        List<DeviceInfo> list =  deviceInfoService.getMonitorDevicesFromCache();

        String offlineJudgmentIntervalOfMonitoredDevice_str = sysParaService.getParamByName(OFFLINE_JUDGMENT_INTERVAL_OF_MONITORED_DEVICE);

        Integer offlineJudgmentIntervalOfMonitoredDevice = Integer.parseInt(offlineJudgmentIntervalOfMonitoredDevice_str);

        String indicatorCode = sysParaService.getParamByName(ONLINE_STATUS_INDICATOR_CODE);
        String alarmTypeId = sysParaService.getParamByName(OFFLINE_ALARM_TYPE_ID);

        for (DeviceInfo deviceInfo:list){
            String deviceCode = deviceInfo.getDeviceCode();
            Integer deviceId = deviceInfo.getDeviceId();

            Integer offlineDecisionThreshold_of_device = deviceCodeOfflineDecisionThresholdMap.get(deviceCode);

            if(offlineDecisionThreshold_of_device!=null){
                offlineJudgmentIntervalOfMonitoredDevice = offlineDecisionThreshold_of_device;
            }

            // String monitorIndicatorCount_str = map.get("monitorIndicatorCount").toString();

            // 检测方法 一
            boolean isOnline = JavaMemoryDb.online(deviceId,offlineJudgmentIntervalOfMonitoredDevice);

            String key = BusinessUtils.getKeyOfDeviceAlarmMessage(deviceCode,indicatorCode,Integer.parseInt(alarmTypeId));

            if(isOnline){
                //end device offline alarm
                deviceAlarmMessageService.endOffLineAlarm(deviceInfo,indicatorCode,alarmTypeId,new Date());
                DEVICE_CUR_ALARM_STATUS_CACHE.remove(key);
            }else{
                //如果设备最新离线告警为误报、未结束，则忽略
                boolean flag = deviceAlarmMessageCacheService.newestAlarmsIsNotEndAndMisinterpret(deviceCode,indicatorCode,alarmTypeId);
                Long nowTimestamp =  new Date().getTime();
                if(!flag){
                    DEVICE_CUR_ALARM_STATUS_CACHE.put(key,new DeviceIndicatorAlarmStatus(nowTimestamp,UN_CONFIRMED));
                    //new device offline alarm
                    boolean isNew =  deviceAlarmMessageService.newOffLineAlarm(deviceInfo,indicatorCode,alarmTypeId,new Date());
                    if(isNew){
                        String message = StringUtils.concat("设备编号为[",deviceCode,"]的设备已经持续",offlineJudgmentIntervalOfMonitoredDevice,"秒未接受到新指标，判定为设备离线！");
                        logServiceService.builderLogServiceAndSave(SOURCE_SERVICE_PLATFORM, LogConstants.ERROR,message,SERVICE_TYPE_PLATFORM);
                    }
                }else{
                    DEVICE_CUR_ALARM_STATUS_CACHE.put(key,new DeviceIndicatorAlarmStatus(nowTimestamp,MISINTERPRET));
                    deviceIndicatorParamRelService.updateDeviceAlarmIndicatorParamRelAndSendSse(deviceCode,true);
                }
            }
            deviceIndicatorParamRelService.updateDeviceAlarmIndicatorParamRelAndSendSse(deviceCode);
        }
    }

    @Scheduled(fixedRate = 5 * 1000, initialDelay = 20 * 1000)//间隔5秒
    private void collectServerCheck() {

        List<DeviceCollectServer> list = deviceCollectServerService.list();

        for(DeviceCollectServer deviceCollectServer:list){

            String key = SERVICE_TYPE_COLLECT+"_"+deviceCollectServer.getServerCode();

            String ip = deviceCollectServer.getControlIP();
            Integer port = deviceCollectServer.getCheckPort();
            if(port==null){
                port = DEFAULT_COLLECT_SERVICE_CHECK_PORT;
            }
            ServiceHealthStatus serviceHealthStatus =  BusinessUtils.checkCollectServerHealth(ip,port);
            Boolean cur_isOnline = serviceHealthStatus.isOK();

            Boolean old_isOnline =  SERVICE_ONLINE_STATUS.get(key);

            if(BusinessUtils.checkServiceOnlineStatusChange(cur_isOnline,old_isOnline)){
                if(cur_isOnline){
                    // 在线
                    deviceServiceAlarmMessageService.endAlarm("serviceName",deviceCollectServer.getFullName(),SERVICE_TYPE_COLLECT);
                    logServiceService.builderLogServiceAndSave(deviceCollectServer.getFullName(), LogConstants.INFO,"监测到采集服务健康状态OK，采集服务处于在线状态",SERVICE_TYPE_COLLECT);
                }else{
                    // 离线
                    if(deviceServiceAlarmMessageService.isOnline(SERVICE_TYPE_COLLECT,"serviceName",deviceCollectServer.getFullName())){
                        deviceServiceAlarmMessageService.newAlarm(deviceCollectServer.getServerCode(),deviceCollectServer.getFullName(),SERVICE_TYPE_COLLECT,ALARMTYPE_OFFLINE,"监测到采集服务健康状态不OK，采集服务处于离线状态");
                    }
                    logServiceService.builderLogServiceAndSave(deviceCollectServer.getFullName(), LogConstants.ERROR,"监测到采集服务健康状态不OK，采集服务处于离线状态",SERVICE_TYPE_COLLECT);
                }
            }

            SERVICE_ONLINE_STATUS.put(key,cur_isOnline);
        }
    }

    public static void main(String[] args) {
        String sql = "SELECT\n" +
                "\tt0.deviceId,\n" +
                "\tt0.deviceCode,\n" +
                "\tcount( t1.id ) AS monitorIndicatorCount \n" +
                "FROM\n" +
                "\tdevice_info t0\n" +
                "\tLEFT JOIN device_indicator_param_rel t1 ON t0.deviceId = t1.deviceId LEFT JOIN device_collect_server t2 on t0.collectServerCode = t2.serverCode\n" +
                "WHERE\n" +
                "\tt0.isDelete = 0 \n" +
                "\tAND t0.underController = 1 \n" +
                "\tAND t1.enableMonitor = 1 \n" +
//              "\tAND t2.serverId IS NOT NULL\n" +
                "GROUP BY\n" +
                "\tt0.deviceId";
        System.out.println("sql--->"+sql);
    }

}
