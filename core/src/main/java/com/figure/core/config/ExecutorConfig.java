package com.figure.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

    @Bean
    public ExecutorService timeoutExecutorService() {
        int timeoutThreadPoolSize = 10; // 超时线程池大小，根据需求进行调整
        return Executors.newFixedThreadPool(timeoutThreadPoolSize);
    }
}