package com.figure.op.cameramanager.controller;

import com.figure.op.cameramanager.domain.ThermalImageInfo;
import com.figure.op.cameramanager.domain.bo.ThermalImageInfoBo;
import com.figure.op.cameramanager.service.ThermalImageInfoService;
import com.figure.op.common.domain.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author liqiang
 * @Date 2023/9/14 9:29
 * @Version 1.5
 */
@RestController
@RequestMapping("/thermalImageInfoController")
public class ThermalImageInfoController {

    @Resource
    private ThermalImageInfoService thermalImageInfoService;

    /**
     *
     * @return  返回ThermalImageInfo
     */
    @GetMapping("/list/{cameraId}")
    public R<ThermalImageInfo> list(@PathVariable String cameraId){
        return R.ok(thermalImageInfoService.list(cameraId));
    }

    /**
     *
     * @return  返回ThermalImageInfo
     */
    @PostMapping("/update")
    public R<Void> updateInfo(@RequestBody  ThermalImageInfoBo bo){
        return thermalImageInfoService.updateInfo(bo);
    }




}
