package com.figure.system.test;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.figure.system.test.Test.getDeviceStatus;


public class RocketMQProducerExample {

    public static void main(String[] args) throws MQClientException, InterruptedException {


        ExecutorService executorService = Executors.newFixedThreadPool(10); // 创建线程池

        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                test();
            });
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

            String jsonString = getDeviceStatus("DC00034","P097","1");

            try {
                // 创建消息对象，指定消息的主题、标签和内容
                // device_status
                Message message = new Message(
                        "device_status",  // 主题
                        "DC00034",    // 标签
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
