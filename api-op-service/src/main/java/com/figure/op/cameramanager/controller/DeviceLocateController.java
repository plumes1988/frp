package com.figure.op.cameramanager.controller;

import com.figure.op.cameramanager.domain.vo.DeviceLocateVo;
import com.figure.op.cameramanager.service.DeviceLocateService;
import com.figure.op.common.domain.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/8 19:56
 * @Version 1.5
 * 机房信息
 */
@RestController
@RequestMapping("/deviceLocateController")
public class DeviceLocateController {

    @Resource
    private DeviceLocateService deviceLocateService;

    /**
     * 拿到站点id,name
     */
    @GetMapping("/queryDeviceLocateByFrontId/{frontId}")
    public R<List<DeviceLocateVo>> queryDeviceLocateByFrontId(@PathVariable("frontId") Integer frontId ) {
        return R.ok(deviceLocateService.queryDeviceLocateByFrontId(frontId));
    }

    /**
     * 拿到站点id,name
     */
    @GetMapping("/list")
    public R<List<DeviceLocateVo>> list() {
        return R.ok(deviceLocateService.list());
    }
}
