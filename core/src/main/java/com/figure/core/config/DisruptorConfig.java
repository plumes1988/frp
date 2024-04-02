package com.figure.core.config;

import com.figure.core.disruptor.Event;
import com.figure.core.disruptor.EventFactory;
import com.figure.core.disruptor.EventProcessor;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class DisruptorConfig {

    @Autowired
    private EventProcessor eventProcessor;


    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public Disruptor<Event> disruptor() {

        int bufferSize = 1024;

        Disruptor<Event> disruptor = new Disruptor(new EventFactory(), bufferSize, Executors.defaultThreadFactory(), ProducerType.MULTI, new YieldingWaitStrategy());

        disruptor.handleEventsWith(eventProcessor); // 关联事件处理器

        return disruptor;
    }
}
