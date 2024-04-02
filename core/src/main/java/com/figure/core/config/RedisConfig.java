package com.figure.core.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.TimeoutOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        // 创建 RedisStandaloneConfiguration 对象，指定 Redis 服务器的主机和端口
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        // 设置 Redis 服务器的密码
        standaloneConfig.setPassword(redisPassword);

        // 创建 LettuceClientConfigurationBuilder 对象，并设置命令超时时间（单位：毫秒）
        LettuceClientConfiguration.LettuceClientConfigurationBuilder clientConfigBuilder = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(2000));

        // 创建 LettuceClientConfiguration 对象，并设置客户端选项
        LettuceClientConfiguration clientConfig = clientConfigBuilder.build();

        // 创建 LettuceConnectionFactory 对象，并设置 Redis 连接配置和客户端配置
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(standaloneConfig, clientConfig);

        return  connectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
