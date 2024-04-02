package com.figure.system.test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.figure.core.rocketmq.message.*;
import com.figure.core.util.DateUtils;
import com.figure.core.webSocket.Message;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        //String jsonString = getDeviceStatus();
        //System.out.println(jsonString);

        String msg = "";
        System.out.println("收到数据:"+msg);
        Message message = JSON.parseObject(msg, Message.class);
    }


    // device_status
    public static String getDeviceStatus(){

        DeviceStatus deviceStatus = new DeviceStatus();

        MsgHead head = new MsgHead();

        deviceStatus.setMessageHead(head);

        deviceStatus.setDeviceId(46);

        deviceStatus.setDeviceCode("DC00034");

        deviceStatus.setIndexCount(1);

        deviceStatus.setDataState(1);

        List<DeviceItemInfo> indexInfoArray =  new ArrayList<>();

        deviceStatus.setIndexInfoArray(indexInfoArray);

        DeviceItemInfo deviceItemInfo = new DeviceItemInfo();

        indexInfoArray.add(deviceItemInfo);

        deviceItemInfo.setIndexCode("P097");

        deviceItemInfo.setDeci(1);

        Random random = new Random();
        int randomNumber = random.nextInt(100);

        deviceItemInfo.setIndexData(null);

        deviceItemInfo.setDataType(1);

        List<DeviceAlarmInfo> alarmInfoArray = new ArrayList<>();

        deviceItemInfo.setAlarmInfoArray(alarmInfoArray);

        DeviceAlarmInfo deviceAlarmInfo = new DeviceAlarmInfo();

        //alarmInfoArray.add(deviceAlarmInfo);

        deviceAlarmInfo.setAlarmType(100280);

        deviceAlarmInfo.setAlarmStartTime(DateUtils.dateToStrLong(new Date()));

        deviceAlarmInfo.setAlarmDuration(900000);

        deviceItemInfo.setAlarmCount(alarmInfoArray.size());

        return obj2Str(deviceStatus);
    }

    // device_status
    public static String getDeviceStatus(String deviceCode,String indicatorCode,String indicatorValue){

        DeviceStatus deviceStatus = new DeviceStatus();

        MsgHead head = new MsgHead();

        deviceStatus.setMessageHead(head);

        deviceStatus.setDeviceId(46);

        deviceStatus.setDeviceCode(deviceCode);

        deviceStatus.setIndexCount(1);

        deviceStatus.setDataState(1);

        List<DeviceItemInfo> indexInfoArray =  new ArrayList<>();

        deviceStatus.setIndexInfoArray(indexInfoArray);

        DeviceItemInfo deviceItemInfo = new DeviceItemInfo();

        indexInfoArray.add(deviceItemInfo);

        deviceItemInfo.setIndexCode(indicatorCode);

        deviceItemInfo.setDeci(1);

        Random random = new Random();
        int randomNumber = random.nextInt(100);

        deviceItemInfo.setIndexData(indicatorValue);

        deviceItemInfo.setDataType(1);

        List<DeviceAlarmInfo> alarmInfoArray = new ArrayList<>();

        deviceItemInfo.setAlarmInfoArray(alarmInfoArray);

        DeviceAlarmInfo deviceAlarmInfo = new DeviceAlarmInfo();

        //alarmInfoArray.add(deviceAlarmInfo);

        deviceAlarmInfo.setAlarmType(100280);

        deviceAlarmInfo.setAlarmStartTime(DateUtils.dateToStrLong(new Date()));

        deviceAlarmInfo.setAlarmDuration(900000);

        deviceItemInfo.setAlarmCount(alarmInfoArray.size());

        return obj2Str(deviceStatus);
    }

    // device_alarm
    public static String getDeviceAlarm(){
        DeviceAlarm deviceAlarm = new DeviceAlarm();
        deviceAlarm.setAlarmId(new Date().getTime());
       //deviceAlarm.setAlarmId(1706455490611l);
        deviceAlarm.setDeviceCode("DC00014");
        deviceAlarm.setAlarmType(100280);
        deviceAlarm.setStartTime(DateUtils.dateToStrLong(new Date()));
        deviceAlarm.setDuration((long)10*1000);
        //0报警结束
        //1报警中
        deviceAlarm.setAlarmFlag(1);
        deviceAlarm.setAlarmName("码率过高");
        deviceAlarm.setAlarmContent("码率异常,码率过高");
        deviceAlarm.setDevName("IP切换器102");
        deviceAlarm.setMessageHead(new MsgHead());
        deviceAlarm.setIndicatorCode("P125");
        return obj2Str(deviceAlarm);
    }

    // index_set_response
    public static String getIndexSetResponse(){
        IndexSetResponse indexSetResponse = new IndexSetResponse();
        indexSetResponse.setRequestld("631946ca-c2ef-4b5d-a098-8e452dc2d4d6-1706459496845");
        indexSetResponse.setErrorCode(1);
        indexSetResponse.setErrorDesc("text");
        indexSetResponse.setResponseTime(new Date());
        indexSetResponse.setSetValue("xxx");
        indexSetResponse.setDeviceId(16);
        indexSetResponse.setIndexCode("IP031");
        indexSetResponse.setMessageHead(new MsgHead());

        return obj2Str(indexSetResponse);
    }

    // collect_server_heartbeat
    public static String getCollectServerHeartbeat(){
        CollectServerHeartbeat collectServerHeartbeat =  new  CollectServerHeartbeat();

        collectServerHeartbeat.setServerCode("WG007");

        collectServerHeartbeat.setTimestamp(new Date().getTime());

        return obj2Str(collectServerHeartbeat);
    }

    // device_communication_exception
    public static String getDeviceCommunicationException(){
        DeviceCommunicationException collectServerHeartbeat =  new  DeviceCommunicationException();

        collectServerHeartbeat.setDeviceCode("DC00010");

        collectServerHeartbeat.setTimestamp(new Date().getTime());

        return obj2Str(collectServerHeartbeat);
    }



    public static String obj2Str(Object obj){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(df);
        String jsonString = null;
        try{

            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);

            System.out.println(jsonString);
        }
        catch (JsonParseException e) { e.printStackTrace();}
        catch (JsonMappingException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
        return jsonString;
    }


}
