package com.figure.system.controller;

import com.figure.core.base.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RestTemplateController extends BaseController {


     @GetMapping("/remoteMonitorService")
     public String remoteMonitorService(String msg){
         String object = getRestTemplate().getForObject(monitorService_url+"/app?msg=hello", String.class);
         return object;
     }
}
