package com.figure.op.device.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fsn
 */
@RestController
@RequestMapping("/app")
public class AppController {


    @Value("${server.port}")
    private int port;

    @GetMapping("/message")
    public String test() {
        return "Hello JavaInUse Called in cloud-gateway-service & service port :"+ port;
    }
}

