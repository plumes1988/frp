package com.figure.system.controller;

import com.figure.core.base.BaseController;
import com.figure.core.db.LevelDBManager;
import com.figure.core.helper.SystemStatus;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.others.ICommonService;
import com.figure.core.sse.SseEmitterManager;
import com.figure.core.sse.SseEmitterWrapper;
import com.figure.core.webSocket.WebSocketServer;
import com.figure.system.mq.RQProducerConsumerManager;
import com.zaxxer.hikari.HikariDataSource;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.websocket.Session;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.figure.core.db.JavaMemoryDb.*;
import static com.figure.core.db.JavaMemoryDb.DEVICE_CUR_INDICATOR_STATUS;

@Component
@RestController
@RequestMapping("/system")
@ApiOperation(value = "系统信息接口" , tags = "系统信息")
public class SystemController extends BaseController {

    @Resource
    private IDeviceInfoService deviceInfoService;

    @Resource
    ICommonService commonService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RQProducerConsumerManager rQProducerConsumerManager;

    @Autowired
    private LevelDBManager levelDBManager;

    @GetMapping("/osSystemInfo")
    @ApiOperation(value = "系统状态查询接口", tags = "系统状态查询接口")
    public Map<String, Object> getAllInfo() {
        Map<String, Object> result = new HashMap<>();
        float cpuUsed = SystemStatus.getCpuUsed();
        float[] jvmInfo = SystemStatus.getJVMInfo();
        float[] diskInfo = SystemStatus.getDiskInfo();
        float[] memoryInfo = SystemStatus.getMemoryInfo();
        result.put("cpuUsed", cpuUsed);
        result.put("jvmInfo", jvmInfo);
        result.put("diskInfo", diskInfo);
        result.put("memoryInfo", memoryInfo);
        return result;
    }

    @GetMapping("/getJavaMemoryDbData/{cacheName}")
    @ApiOperation(value = "获取java内存中的缓存的数据", notes = "获取java内存中的缓存的数据")
    public Object getJavaMemoryDbData(@PathVariable("cacheName") String cacheName) {
        if("DEVICE_LAST_INDICATOR_VALUE_UPDATE_TIME".equals(cacheName)){
           return DEVICE_LAST_INDICATOR_VALUE_UPDATE_TIME;
        }
        if("DEVICE_LAST_CHANGE_INDICATOR_VALUE".equals(cacheName)){
            return DEVICE_LAST_CHANGE_INDICATOR_VALUE;
        }
        if("SERVICE_ONLINE_STATUS".equals(cacheName)){
            return SERVICE_ONLINE_STATUS;
        }
        if("SYS_PARAM_CACHE".equals(cacheName)){
            return SYS_PARAM_CACHE;
        }
        if("DEVICE_CUR_ALARM_STATUS_CACHE".equals(cacheName)){
            return DEVICE_CUR_ALARM_STATUS_CACHE;
        }
        if("ONLY_SEND_CHANGE_VALUE_TO_WEB_CACHE".equals(cacheName)){
            return ONLY_SEND_CHANGE_VALUE_TO_WEB_CACHE;
        }
        if("REDIS_ONLINE_STATUS".equals(cacheName)){
            return REDIS_ONLINE_STATUS;
        }
        if("DEVICE_CUR_INDICATOR_STATUS".equals(cacheName)){
            return DEVICE_CUR_INDICATOR_STATUS;
        }
        if("DEVICE_CUR_INDICATOR_VALUE_FOR_HISTORY".equals(cacheName)){
            return DEVICE_CUR_INDICATOR_VALUE_FOR_HISTORY;
        }
        if("DEVICE_HISTORY_INDICATOR_CHANGE_RECORD_MODE_LAST_COLLECT_TIME".equals(cacheName)){
            return DEVICE_HISTORY_INDICATOR_CHANGE_RECORD_MODE_LAST_COLLECT_TIME;
        }
        if("INDICATOR_CODE_IS_CRITICAL".equals(cacheName)){
            return INDICATOR_CODE_IS_CRITICAL;
        }
        return null;
    }

    @GetMapping("/createAllDeviceHistoryIndicatorTable")
    @ApiOperation(value = "一次性生成所有指标历史表", notes = "一次性生成所有指标历史表")
    public Map<String, Object> createAllDeviceHistoryIndicatorTable() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<DeviceInfo> deviceInfos = deviceInfoService.list();
            for (DeviceInfo deviceInfo : deviceInfos) {
                if (!deviceInfo.getIsDelete()) {
                    commonService.createDeviceHistoryIndicatorTable(deviceInfo.getDeviceCode());
                }
            }
            result.put("isOK", true);
            result.put("msg", "操作成功！");
        } catch (Exception e) {
            result.put("isOK", false);
            result.put("msg", "失败原因：" + e.getMessage());
        }

        return result;
    }

    @GetMapping("/getHikariConnectionPoolInfo")
    @ApiOperation(value = "获取Hikari连接池状态", notes = "获取Hikari连接池状态")
    public Map<String, Map<String,Object>> getHikariConnectionPoolInfo() {
        Map<String, Map<String,Object>> result = new HashMap<>();

        Map<String, DataSource> dataSourceBeans = applicationContext.getBeansOfType(DataSource.class);

        for (Map.Entry<String, DataSource> entry : dataSourceBeans.entrySet()) {
            String beanName = entry.getKey();
            DataSource dataSource = entry.getValue();
            result.put(beanName,getDataSourceInfo(dataSource));
        }

        return result;

    }

    private Map<String,Object> getDataSourceInfo(DataSource dataSource) {
        Map<String,Object> map_ = new HashMap<>();

        if (dataSource instanceof HikariDataSource) {

            HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
            map_.put("Connection Pool Name",hikariDataSource.getPoolName());
            if(hikariDataSource.getHikariPoolMXBean()!=null){
                map_.put("Total Connections" , hikariDataSource.getHikariPoolMXBean().getTotalConnections());
                map_.put("Active Connections",hikariDataSource.getHikariPoolMXBean().getActiveConnections());
                map_.put("Idle Connections" , hikariDataSource.getHikariPoolMXBean().getIdleConnections());
                map_.put("ThreadsAwaitingConnection" , hikariDataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());
            }
        }

        return map_;
    }


    @RequestMapping("/getWebSocketSessionInfos")
    @ApiOperation(value = "获取WebSocket会话信息", notes = "获取WebSocket会话信息")
    public Map<String, Object> getWebSocketSessionInfos() {

        Map<String, Object> map_ = new HashMap<>();

        map_.put("在线人数",WebSocketServer.onlineNum);

        Map<String, Session> map = WebSocketServer.sessionPools;

        List<Map<String ,Object>> list = new ArrayList();

        map_.put("WebSocketServer.sessionPools",list);

        for (String key:map.keySet()){
            Map<String,Object> map01 =  new HashMap();
            Session session = map.get(key);
            map01.put("sessionId",session.getId().toString());
            map01.put("isOpen",session.isOpen());
            list.add(map01);
        }

        Set<String> keys = SseEmitterManager.sseEmitterWrappers.keySet();
        for(String topic:keys){
            ConcurrentLinkedQueue<SseEmitterWrapper> topic_sseEmitterWrappers = SseEmitterManager.sseEmitterWrappers.get(topic);
            list = new ArrayList();
            map_.put("SseEmitterManager.sseEmitterWrappers_topic_["+topic+"]",list);
            for (SseEmitterWrapper sseEmitterWrapper:topic_sseEmitterWrappers){
                Session session = sseEmitterWrapper.getSession();
                if(session!=null){
                    Map<String,Object> map01 =  new HashMap();
                    map01.put("sessionId",session.getId().toString());
                    map01.put("isOpen",session.isOpen());
                    list.add(map01);
                }
            }
        }
        return map_;
    }

    @RequestMapping("/getRQProducerConsumerManagerInfo")
    @ApiOperation(value = "获取自定义消费者、生产者管理器信息", notes = "获取自定义消费者、生产者管理器信息")
    public Map<String, Object> getRQProducerConsumerManagerInfo() {
        Map<String, Object> map_ = new HashMap<>();
        map_.put("consumers",rQProducerConsumerManager.consumers.keySet());
        map_.put("producers",rQProducerConsumerManager.producers.keySet());
        return map_;
    }

    @RequestMapping("/getDataFromLeveLDB")
    @ApiOperation(value = "获取LevelDB信息", notes = "获取LevelDB信息")
    public Map<String,Object> getDataFromLeveLDB() throws IOException {
        Map<String, Object> map_ = new HashMap<>();
        try{
            String[] keys =  levelDBManager.getAllKeys();
            map_.put("allKeys",keys);
            for(String key:keys){
                map_.put(key,levelDBManager.getObjectList(key));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map_;
    }

}
