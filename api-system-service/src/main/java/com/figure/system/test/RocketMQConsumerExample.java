package com.figure.system.test;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class RocketMQConsumerExample {

    public static void main(String[] args){
       test();
    }

    public static void test(){
        try{
            System.out.println("Consumer start....");
            // 创建消费者实例
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("xxxConsumerGroup070800000");

            // 设置NameServer地址
            consumer.setNamesrvAddr("localhost:9876");

            // 订阅要消费的Topic和Tag
            consumer.subscribe("device_status01", "DC00010");

            long currentTimestamp = System.currentTimeMillis();

            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);

            consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(currentTimestamp));

            // 注册消息监听器
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                    for (MessageExt message : messages) {
                        // 处理收到的消息
                        System.out.println("cur thread:"+Thread.currentThread()+"：Received message: " + new String(message.getBody()));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            // 启动消费者
            consumer.start();
            System.out.println("Consumer started.");
        }catch (Exception e){

        }
    }
}
