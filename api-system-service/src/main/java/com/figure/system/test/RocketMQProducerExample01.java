package com.figure.system.test;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RocketMQProducerExample01 {

    public static void main(String[] args) throws MQClientException, InterruptedException {


        ExecutorService executorService = Executors.newFixedThreadPool(10); // 创建线程池

        for (int i = 1; i < 26; i++) {
            if(i%5==0){
                test2();
            }else{
                test();
            }


            Thread.sleep(1000);
        }

        executorService.shutdown();
    }

    public static void test() {
        try {
            // 创建一个默认的生产者
            DefaultMQProducer producer = new DefaultMQProducer("your_producer_group");
            // 指定RocketMQ的命名服务器地址
            producer.setNamesrvAddr("127.0.0.1:9876");
            // 启动生产者
            producer.start();

            String jsonString = "{\"alarmFlag\":0,\"alarmId\":1711615231631,\"alarmType\":90005,\"devName\":\"3KW功放4\",\"deviceCode\":\"DC00043\",\"duration\":0,\"indicatorCode\":\"KC012\",\"messageHead\":{\"businessCode\":\"devicealarm_report\",\"downloadFileURL\":\"\",\"fromWhere\":\"WG007\",\"instructType\":0,\"isDownloadFile\":0,\"messageID\":1711615231631,\"messageTime\":\"2024-03-28 16:40:31\",\"version\":1},\"startTime\":\"2024-03-28 16:40:31\"}";

            try {
                // 创建消息对象，指定消息的主题、标签和内容
                // device_status
                Message message = new Message(
                        "device_alarm",  // 主题
                        "DC00043",    // 标签
                        (jsonString).getBytes(RemotingHelper.DEFAULT_CHARSET)  // 内容
                );

                // 发送消息并获取发送结果
                SendResult sendResult = producer.send(message);

                // 打印发送结果
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 关闭生产者
            producer.shutdown();
        } catch (Exception e) {

        }
    }

    public static void test2() {
        try {
            // 创建一个默认的生产者
            DefaultMQProducer producer = new DefaultMQProducer("your_producer_group");
            // 指定RocketMQ的命名服务器地址
            producer.setNamesrvAddr("127.0.0.1:9876");
            // 启动生产者
            producer.start();

            String jsonString = "{\"alarmFlag\":1,\"alarmId\":1711615231631,\"alarmType\":90005,\"devName\":\"3KW功放4\",\"deviceCode\":\"DC00043\",\"duration\":0,\"indicatorCode\":\"KC012\",\"messageHead\":{\"businessCode\":\"devicealarm_report\",\"downloadFileURL\":\"\",\"fromWhere\":\"WG007\",\"instructType\":0,\"isDownloadFile\":0,\"messageID\":1711615231631,\"messageTime\":\"2024-03-28 16:59:31\",\"version\":1},\"startTime\":\"2024-03-28 16:40:31\"}";

            try {
                // 创建消息对象，指定消息的主题、标签和内容
                // device_status
                Message message = new Message(
                        "device_alarm",  // 主题
                        "DC00043",    // 标签
                        (jsonString).getBytes(RemotingHelper.DEFAULT_CHARSET)  // 内容
                );

                // 发送消息并获取发送结果
                SendResult sendResult = producer.send(message);

                // 打印发送结果
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 关闭生产者
            producer.shutdown();
        } catch (Exception e) {

        }
    }
}
