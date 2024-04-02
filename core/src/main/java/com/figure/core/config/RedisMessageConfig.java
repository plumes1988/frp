package com.figure.core.config;

import com.figure.core.redis.SaveHistoryIndicatorForAlarmConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;


import static com.figure.core.redis.RedisConstants.QUEUE_NAME_SAVE_INDICATOR_HISTORY_FOR_ALARM;

@Configuration
public class RedisMessageConfig {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                       SaveHistoryIndicatorForAlarmConsumer saveHistoryIndicatorForAlarmConsumer) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //container.addMessageListener(saveHistoryIndicatorForAlarmConsumer, new ChannelTopic(QUEUE_NAME_SAVE_INDICATOR_HISTORY_FOR_ALARM));
        return container;
    }
}