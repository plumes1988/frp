package com.figure;

import com.figure.core.base.BaseApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(
        basePackages = {
                "com.figure.system",
                "com.figure.system.controller",
                "com.figure.multiview",
                "com.figure.multiview.controller",
                "com.figure.audit",
                "com.figure.audit.controller",
                "com.figure.transcode",
                "com.figure.transcode.controller",
                "com.figure.visual",
                "com.figure.visual.controller",
                "com.figure.core",
                "com.figure.core.exception.handler",
                "com.figure.core.aop",
                "com.figure.op",
                "com.figure.config",
        }
)

public class Application extends BaseApplication{

    public static void main(String[] args) {
        System.out.println("Get Java Home Directory = " + System.getProperty("java.home"));
        System.out.print("Java Specification Version: ");
        System.out.println(System.getProperty("java.specification.version"));
        System.out.print("java Runtime Environment (JRE) version: ");
        System.out.println(System.getProperty("java.version"));
        System.out.println("Get Java User Home = " + System.getProperty("user.home"));
        SpringApplication.run(Application.class, args);
    }

}
