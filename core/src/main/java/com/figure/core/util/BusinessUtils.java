package com.figure.core.util;

import com.figure.core.db.JavaMemoryDb;
import com.figure.core.entity.ServiceHealthStatus;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.figure.core.model.device.DeviceIndicatorParamRel.IN_ALARM;
import static com.figure.core.model.device.DeviceIndicatorParamRel.NOT_IN_ALARM;

public class BusinessUtils {

    public static int connectionTimeout = 500; // 连接超时时间（毫秒）
    public static int requestTimeout = 500;    // 请求超时时间（毫秒）

    public static RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(connectionTimeout)
            .setConnectionRequestTimeout(requestTimeout)
            .build();

    public static boolean isNewMessageFromMq(Date messageTime) {
        return new Date().getTime() - messageTime.getTime() < 3 * 1000;
    }

    public static Double string2Double(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static ServiceHealthStatus checkCollectServerHealth(String ip, Integer port) {

        ServiceHealthStatus serviceHealthStatus = new ServiceHealthStatus();

        serviceHealthStatus.setOK(false);

        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        HttpGet httpGet = new HttpGet("http://" + ip + ":" + port + "/health");

        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            System.out.println("Response Code: " + response.getStatusLine().getStatusCode());
            System.out.println("Response Body: " + responseBody);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                serviceHealthStatus.setOK(true);
            }
        } catch (Exception e) {
            //System.out.println("ip:" + ip + ",port:" + port + " checkCollectServerHealth失败，原因：" + e.getMessage());
            serviceHealthStatus.setOK(false);
            serviceHealthStatus.setDesc(e.getMessage());
        }
        return serviceHealthStatus;
    }

    public static boolean checkServiceOnlineStatusChange(Boolean cur_isOnline, Boolean old_isOnline) {
        if (old_isOnline != null && cur_isOnline == old_isOnline) {
            return false;
        }
        return true;
    }

    public static boolean isRedisOnline(RedisTemplate<String, String> redisTemplate) {
        try {
            String result = redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    // 设置超时时间（单位：秒)
                    return connection.ping();
                }
            });
            // 如果返回结果为 PONG，则说明 Redis 在线
            return "PONG".equals(result);
        } catch (Exception e) {
            // 发生异常，说明 Redis 不在线
            return false;
        }
    }

    public static String getKeyOfDeviceAlarmMessage(DeviceAlarmMessage deviceAlarmMessage){
        return deviceAlarmMessage.getDeviceCode()+"_"+deviceAlarmMessage.getIndicatorCode()+"_"+deviceAlarmMessage.getAlarmTypeId();
    }

    public static String getKeyOfDeviceAlarmMessage(String deviceCode,String indicatorCode,Integer alarmTypeId){
        return deviceCode+"_"+indicatorCode+"_"+alarmTypeId;
    }

    public static String[] parseKey(String key){
        return key.split("_");
    }

    public static String getLiveDeviceIndicatorKey(DeviceIndicatorParamRel rel){
        return rel.getDeviceCode()+"_"+rel.getIndicatorCode();
    }

    public static String getLiveDeviceIndicatorKey( String deviceCode,String indicatorCode){
        return deviceCode+"_"+indicatorCode;
    }

    public static Integer getDeviceIndicatorAlarmStatusByKey(String key){

        Integer alarmStatus = NOT_IN_ALARM;

        if(JavaMemoryDb.deviceCurAlarmCacheHasKeyStartWithAndNotMisinterpret(key)){
            alarmStatus = IN_ALARM;
        }
        return alarmStatus;
    }

    public static Boolean isSameValue(String old_value,String new_value){
        if(old_value==null && new_value==null){
            return true;
        }
        if(old_value!=null && new_value!=null && old_value.equals(new_value)){
            return true;
        }
        return false;
    }

    public static DeviceAlarmMessage buildAlarm(String deviceCode, String indicatorCode, String alarmTypeId) {
        DeviceAlarmMessage deviceAlarmMessage = new DeviceAlarmMessage();
        deviceAlarmMessage.setDeviceCode(deviceCode);
        deviceAlarmMessage.setAlarmTypeId(Integer.parseInt(alarmTypeId));
        deviceAlarmMessage.setIndicatorCode(indicatorCode);
        return deviceAlarmMessage;
    }

    public static void sortAlarmsDesc(List<DeviceAlarmMessage> list){
        if(list!=null){
            Collections.sort(list, Comparator.comparing(DeviceAlarmMessage::getHappenTime).reversed());
        }
    }

    public static boolean hasChange(DeviceAlarmMessage old_deviceAlarmMessage, DeviceAlarmMessage new_deviceAlarmMessage) {
        if(!old_deviceAlarmMessage.getConfirm().equals(new_deviceAlarmMessage.getConfirm())){
            return true;
        }
        if(!old_deviceAlarmMessage.getStatus().equals(new_deviceAlarmMessage.getStatus())){
            return true;
        }
        return false;
    }
}
