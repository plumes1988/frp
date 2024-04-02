package com.figure.system.mq;

import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.system.mq.messageListeners.IndicatorMessageForHistoryListener;
import com.figure.system.mq.messageListeners.IndicatorMessageForStateChangeListener;
import com.figure.system.mq.messageListeners.IndicatorMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.figure.system.mq.RQProducerConsumerManager.GROUP_NAME_HYPHEN;

@Component
@Slf4j
public class MessageListenerManager {

    @Autowired
    private ApplicationContext applicationContext;

    public MessageListenerConcurrently getMessageListenerByGroupName(String groupName) {

        MessageListenerConcurrently bean=null;

        if(groupName.startsWith(RocketMQConstants.REAL_TIME_INDICATOR+GROUP_NAME_HYPHEN)){
            bean = applicationContext.getBean("IndicatorMessageListener",IndicatorMessageListener.class);
        }

        if(groupName.startsWith(RocketMQConstants.REAL_TIME_INDICATOR_FOR_HISTORY+GROUP_NAME_HYPHEN)){
            bean = applicationContext.getBean("IndicatorMessageForHistoryListener", IndicatorMessageForHistoryListener.class);
        }

        if(groupName.startsWith(RocketMQConstants.REAL_TIME_INDICATOR_FOR_STATE_CHANGE+GROUP_NAME_HYPHEN)){
            bean = applicationContext.getBean("IndicatorMessageForStateChangeListener", IndicatorMessageForStateChangeListener.class);
        }


        return bean;
    }
}