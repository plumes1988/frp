package com.figure.op.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 通用配置
 * 
 * @author ruoyi
 */
@Configuration
@Component
public class ResourcesConfig implements WebMvcConfigurer
{

    @Value("${file.prefix}")
    private String prefix;

    @Value("${file.path}")
    private String path;

    private static  final Logger logger = LoggerFactory.getLogger(ResourcesConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        logger.info("加载静态文件路径映射");
        /** 本地文件上传路径 */
        registry.addResourceHandler(prefix + "/**")
                .addResourceLocations("file:" + path + "/")
                .addResourceLocations("classpath:/static/");

    }

}