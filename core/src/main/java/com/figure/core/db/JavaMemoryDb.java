package com.figure.core.db;

import com.figure.core.entity.DeviceCurIndicatorValue;
import com.figure.core.entity.DeviceIndicatorAlarmStatus;
import com.figure.core.entity.DeviceLiveIndicatorSendStatus;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.util.BusinessUtils;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.figure.core.constant.ConstantsForSysPara.LIVE_INDICATOR_SEND_TO_WEB_GAP_THRESHOLD;
import static com.figure.core.model.device.DeviceAlarmMessage.MISINTERPRET;

//java内存当作关键、小体量数据存储、更高效（防止大体量数据，以免造成内存占用过高、内存不足的情况，会影响整体系统的运行状况不佳）
@Slf4j
public class JavaMemoryDb {

    public static final String NULL_VALUE = "NULL_NULL";

    //设备指标最后更新时间缓存,作为设备离线判定依据
    public static volatile Map<Integer, Date>  DEVICE_LAST_INDICATOR_VALUE_UPDATE_TIME = new ConcurrentHashMap<>();

    //设备状态指标值缓存，用于记录变更，作为变更记录入库依据
    public static volatile Map<String,String>  DEVICE_LAST_CHANGE_INDICATOR_VALUE =  Collections.synchronizedMap(new HashMap<>());

    //服务在线状态缓存，作为服务离线、在线是否变更判定,减少频繁的结束、开始、日志入库等动作
    public static volatile Map<String, Boolean>  SERVICE_ONLINE_STATUS  = Collections.synchronizedMap(new HashMap<>());

    //系统参数缓存
    public static volatile Map<String,String>  SYS_PARAM_CACHE  = Collections.synchronizedMap(new HashMap<>());

    //当前设备指标告警，由于报警上报频率较高，放入内存，减少数据库查询
    public static volatile Map<String,DeviceIndicatorAlarmStatus> DEVICE_CUR_ALARM_STATUS_CACHE  = new ConcurrentHashMap<>();

    //只在变更的时候，才推送至web页面
    public static volatile Map<String, DeviceLiveIndicatorSendStatus>  ONLY_SEND_CHANGE_VALUE_TO_WEB_CACHE =  Collections.synchronizedMap(new HashMap<>());

    //redis在线状态，作为redis离线、在线是否变更判定,减少频繁的结束、开始、日志入库等动作
    public static volatile Boolean  REDIS_ONLINE_STATUS  = null;

    //设备指标当前实时值缓存
    public static volatile Map<String, DeviceCurIndicatorValue>  DEVICE_CUR_INDICATOR_STATUS = new ConcurrentHashMap<>();

    //历史消费者设备指标当前实时值缓存，供变更收录模式下日志是否发生变更判定依据
    public static volatile Map<String,String>  DEVICE_CUR_INDICATOR_VALUE_FOR_HISTORY = new ConcurrentHashMap<>();

    //记录变更模式下，最后存入指标时间, 供长时间值未变，导致数据长时间空白，为了减少空白，加了特定采集间隔（即使为变更也入库）
    public static volatile Map<String,Long>  DEVICE_HISTORY_INDICATOR_CHANGE_RECORD_MODE_LAST_COLLECT_TIME = new ConcurrentHashMap<>();

    //指标是否关键指标缓存
    public static volatile Map<String, Integer>  INDICATOR_CODE_IS_CRITICAL  = Collections.synchronizedMap(new HashMap<>());

    // delay 单位秒
    public static boolean online(Integer deviceId,Integer delay){
        try{
            Date last_update_time =  DEVICE_LAST_INDICATOR_VALUE_UPDATE_TIME.get(deviceId);

            if(last_update_time!=null && (new Date().getTime() - last_update_time.getTime())<delay*1000){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean canSend(String key, String curIndicatorValue, Object onlyChange, Long now_st, Integer isCritical, Object pushInterval) {

        DeviceLiveIndicatorSendStatus deviceLiveIndicatorSendStatus =  ONLY_SEND_CHANGE_VALUE_TO_WEB_CACHE.get(key);
        if(deviceLiveIndicatorSendStatus==null){
            return true;
        }
        String prev_indicatorValue =  deviceLiveIndicatorSendStatus.getIndicatorValue();
        Long lastSendTime =  deviceLiveIndicatorSendStatus.getLastSendTime();

        if(onlyChange!=null && "1".equals(onlyChange.toString())){
            if(!BusinessUtils.isSameValue(prev_indicatorValue,curIndicatorValue)){
                return true;
            }
        }else{
           /*if(isCritical!=null && isCritical ==1){
                return true;
            }*/
            if(!BusinessUtils.isSameValue(prev_indicatorValue,curIndicatorValue)){
                return true;
            }
            String liveIndicatorSendToWebGapThreshold_str = null;
            if(pushInterval!=null){
                liveIndicatorSendToWebGapThreshold_str = pushInterval.toString();
            }
            if(liveIndicatorSendToWebGapThreshold_str==null){
                liveIndicatorSendToWebGapThreshold_str = SYS_PARAM_CACHE.get(LIVE_INDICATOR_SEND_TO_WEB_GAP_THRESHOLD);
            }
            if(liveIndicatorSendToWebGapThreshold_str!=null) {
                Integer liveIndicatorSendToWebGapThreshold = Integer.parseInt(liveIndicatorSendToWebGapThreshold_str);
                if(now_st-lastSendTime >= liveIndicatorSendToWebGapThreshold*1000){
                    return true;
                }
            }
        }
        return false;
    }

    public static void put_into_DEVICE_LAST_CHANGE_INDICATOR_VALUE(String key,String value){
        if(value==null){
            DEVICE_LAST_CHANGE_INDICATOR_VALUE.put(key,NULL_VALUE);
        }else{
            DEVICE_LAST_CHANGE_INDICATOR_VALUE.put(key,value);
        }
    }

    public static String get_from_DEVICE_LAST_CHANGE_INDICATOR_VALUE(String key) {
        String value =  DEVICE_LAST_CHANGE_INDICATOR_VALUE.get(key);
        if(NULL_VALUE.equals(value)){
            return null;
        }
        return value;
    }

    //返回true，即判定报警中
    public static boolean deviceCurAlarmCacheHasKeyStartWithAndNotMisinterpret(String prefixToSearch) {
        boolean found = DEVICE_CUR_ALARM_STATUS_CACHE.entrySet().stream()
                .anyMatch(entry -> entry.getKey().toString().startsWith(prefixToSearch) && !MISINTERPRET.equals(entry.getValue().getConfirm()));
        return found;
    }

    public static void put_into_DEVICE_CUR_INDICATOR_STATUS(String key,String value,String collectTime){
        DeviceCurIndicatorValue deviceCurIndicatorValue = new DeviceCurIndicatorValue(value,collectTime, DateHelper.dateToStr(new Date()));
        DEVICE_CUR_INDICATOR_STATUS.put(key,deviceCurIndicatorValue);
    }

    public static String get_value_from_DEVICE_CUR_INDICATOR_STATUS(String key) {
        DeviceCurIndicatorValue deviceCurIndicatorValue =  DEVICE_CUR_INDICATOR_STATUS.get(key);
       if(deviceCurIndicatorValue==null){
           return null;
       }
       return deviceCurIndicatorValue.getIndicatorValue();
    }

    public static void put_into_DEVICE_CUR_INDICATOR_VALUE_FOR_HISTORY(String key,String value){
        if(value==null){
            DEVICE_CUR_INDICATOR_VALUE_FOR_HISTORY.put(key,NULL_VALUE);
        }else{
            DEVICE_CUR_INDICATOR_VALUE_FOR_HISTORY.put(key,value);
        }
    }

    public static String get_from_DEVICE_CUR_INDICATOR_VALUE_FOR_HISTORY(String key) {
        String value =  DEVICE_CUR_INDICATOR_VALUE_FOR_HISTORY.get(key);
        if(NULL_VALUE.equals(value)){
            return null;
        }
        return value;

    }

    public static void clear_ONLY_SEND_CHANGE_VALUE_TO_WEB_CACHE(String uuid) {
        // 遍历键集合并移除以 "aaa" 开头的键
        ONLY_SEND_CHANGE_VALUE_TO_WEB_CACHE.entrySet().removeIf(entry -> entry.getKey().startsWith(uuid+"_"));
    }

    public static void main(String[] args) {
        Boolean mark = null;
        if(mark!=null && mark){

        }
    }
}
