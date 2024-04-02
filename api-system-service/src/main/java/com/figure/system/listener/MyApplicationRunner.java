package com.figure.system.listener;

import com.figure.core.db.CallBack;
import com.figure.core.db.MysqlMonitor;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.service.device.IDeviceAlarmMessageCacheService;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.sys.ISysRoleLogicChannelRelService;
import com.figure.core.util.JSONUtil;
import com.figure.core.util.StringUtils;
import com.figure.core.webSocket.Message;
import com.figure.core.webSocket.WebSocketServer;
import com.figure.system.mq.RQProducerConsumerManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.figure.core.sse.Constants.TABLE_SYS_ROLE_LOGIC_CHANNEL_REL_CHANGE;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Resource
    private IDeviceInfoService deviceInfoService;

    @Resource
    private RQProducerConsumerManager rQProducerConsumerManager;

    @Resource
    WebSocketServer webSocketServer;

    @Resource
    private ISysRoleLogicChannelRelService sysRoleLogicChannelRelService;

    @Resource
    private IDeviceAlarmMessageCacheService deviceAlarmMessageCacheService;

    @Value("${spring.datasource01.jdbc-url}")
    private String url;
    @Value("${spring.datasource01.username}")
    private String username;
    @Value("${spring.datasource01.password}")
    private String password;

    @Value("${mysql.binlog}")
    private Integer binlog;

    @Override
    public void run(ApplicationArguments args)  {
        // 执行其他初始化操作
        System.out.println("执行其他初始化操作-------------------------------------------------------------start");

        try {

            // deviceIndicatorParamRelService.loadAllDeviceCurIndicatorValueIntoJavaMemoryDb();

            deviceAlarmMessageCacheService.loadAlarmingAlarmsFromDb();

            int NUM_CONSUMERS = 20; // 要创建的消费者数量

            ExecutorService executorService = Executors.newFixedThreadPool(NUM_CONSUMERS);


            for (DeviceInfo deviceInfo : deviceInfoService.list()) {
                if(!deviceInfo.getIsDelete()&& !StringUtils.isEmpty(deviceInfo.getCollectServerCode())){
                    executorService.execute(() -> {
                        rQProducerConsumerManager.createRealRimeIndicatorConsumer(deviceInfo);
                    });
                    executorService.execute(() -> {
                        rQProducerConsumerManager.createRealRimeIndicatorConsumerForHistory(deviceInfo);
                    });
                    executorService.execute(() -> {
                        rQProducerConsumerManager.createRealRimeIndicatorConsumerForStateChange(deviceInfo);
                    });
                }
            }

            executorService.shutdown(); // 关闭线程池
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // 等待任务执行完成


            if(binlog==1){
                URI uri = new URI(url.replace("jdbc:mysql","mysql"));
                System.out.println("Port = " + uri.getPort());
                System.out.println("Host = " + uri.getHost());
                System.out.println("Path = " + uri.getPath().replace("/",""));
                MysqlMonitor mysqlMonitor = new  MysqlMonitor(uri.getHost(),
                        uri.getPort(),
                        username,
                        password,
                        uri.getPath().replace("/",""),
                        "sys_role_logic_channel_rel",
                        "id");
                mysqlMonitor.startMonitor(new CallBack() {
                    @Override
                    public void call() {
                        Message message = new Message();
                        String topic = TABLE_SYS_ROLE_LOGIC_CHANNEL_REL_CHANGE;
                        message.setTopic(topic);
                        message.setBody(JSONUtil.getGson().toJson(sysRoleLogicChannelRelService.list()));
                        webSocketServer.sendInfo(message);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("执行其他初始化操作-------------------------------------------------------------end");
    }
}
