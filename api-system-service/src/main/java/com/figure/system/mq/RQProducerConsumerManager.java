package com.figure.system.mq;

import com.figure.core.model.device.DeviceInfo;
import com.figure.core.rocketmq.RocketMQConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
@Slf4j
public class RQProducerConsumerManager  {

    @Resource
    private MessageListenerManager messageListenerManager;

    @Value("${rocketmq.name-server}")
    public String ROCKET_NAME_SERVER;

    public static String GROUP_NAME_HYPHEN = "_v_s_";


    public Map<String, DefaultMQPushConsumer> consumers = new ConcurrentHashMap();

    public Map<String, DefaultMQProducer> producers = new ConcurrentHashMap();


    public DefaultMQProducer createProducer(String groupName) {
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(ROCKET_NAME_SERVER);
        //启动Producer实例
        producer.setRetryTimesWhenSendAsyncFailed(0);
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return producer;
    }

    public DefaultMQProducer getProducer(String groupName) {
        DefaultMQProducer producer = null;
        if (producers.containsKey(groupName)) {
            producer = producers.get(groupName);
        } else {
            producer = createProducer(groupName);
            producers.put(groupName, producer);
        }
        return producer;
    }

    public DefaultMQPushConsumer createConsumer(String topic,String tag,String groupName, MessageListenerConcurrently messageListener) {
        log.debug("createConsumer topic :"+topic+";tag:"+tag);
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(ROCKET_NAME_SERVER);
        // 设置最小和最大消费线程为1
        consumer.setConsumeThreadMin(1);
        consumer.setConsumeThreadMax(1);
        try {
            consumer.subscribe(topic, tag);
            consumer.registerMessageListener(messageListener);
            consumer.start();
            System.out.println("groupName: "+groupName+"; Consumer topic :"+topic+"; tag:"+tag+" started.");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return consumer;
    }


    public DefaultMQPushConsumer getConsumer(String topic,String tag, String groupName) {
        DefaultMQPushConsumer consumer = null;
        if (consumers.containsKey(groupName)) {
            consumer = consumers.get(groupName);
        } else {
            consumer = createConsumer(topic,tag, groupName, messageListenerManager.getMessageListenerByGroupName(groupName));
            consumers.put(groupName, consumer);
        }
        log.info("CONSUMENR");
        return consumer;
    }

    public void createRealRimeIndicatorConsumer(DeviceInfo deviceInfo){
        String topic = RocketMQConstants.REAL_TIME_INDICATOR ;
        String tag = deviceInfo.getDeviceCode();
        getConsumer(topic,tag, RocketMQConstants.REAL_TIME_INDICATOR+GROUP_NAME_HYPHEN+tag);
    }

    public void createRealRimeIndicatorConsumerForHistory(DeviceInfo deviceInfo){
        String topic = RocketMQConstants.REAL_TIME_INDICATOR ;
        String tag = deviceInfo.getDeviceCode();
        getConsumer(topic,tag, RocketMQConstants.REAL_TIME_INDICATOR_FOR_HISTORY+GROUP_NAME_HYPHEN+tag);
    }

    public void createRealRimeIndicatorConsumerForStateChange(DeviceInfo deviceInfo){
        String topic = RocketMQConstants.REAL_TIME_INDICATOR ;
        String tag = deviceInfo.getDeviceCode();
        getConsumer(topic,tag, RocketMQConstants.REAL_TIME_INDICATOR_FOR_STATE_CHANGE+GROUP_NAME_HYPHEN+tag);
    }

    public void removeConsumer(String key){
        log.debug("removeConsumer key:"+key);
        DefaultMQPushConsumer consumer = consumers.get(key);
        if(consumer!=null){
            consumer.shutdown();
            consumers.remove(key);
        }
    }
}
