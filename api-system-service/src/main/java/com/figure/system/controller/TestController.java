package com.figure.system.controller;


import com.figure.core.base.BaseController;
import com.figure.core.model.device.DeviceLocate;
import com.figure.core.service.others.ICommonService;
import com.figure.core.util.StringUtils;
import com.figure.core.webSocket.Message;
import com.figure.core.webSocket.WebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/test")
@Api(value = "测试接口" , tags = "测试用")
public class TestController extends BaseController {

    @Autowired
    WebSocketServer webSocketServer;

    @PostMapping("/sendNotice")
    public Map<String,Object> sendNotice(@RequestBody Message message) {
        webSocketServer.sendInfo(message);
        return returnMap(true,message);
    }

}
