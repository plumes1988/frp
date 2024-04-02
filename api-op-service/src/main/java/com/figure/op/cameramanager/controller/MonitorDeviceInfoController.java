package com.figure.op.cameramanager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.cameramanager.domain.bo.MonitorDeviceInfoBo;
import com.figure.op.cameramanager.domain.vo.MonitorDeviceInfoVo;
import com.figure.op.cameramanager.service.MonitorDeviceInfoService;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.validate.AddGroup;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author liqiang
 * @Date 2023/9/8 16:52
 * @Version 1.5
 * 监测设备管理
 */
@RestController
@RequestMapping("/monitorDeviceController")
public class MonitorDeviceInfoController {

    @Resource
    private MonitorDeviceInfoService monitorDeviceService;

    /**
     * 新增设备
     */
    @PostMapping("/add")
    public R add(@Validated(AddGroup.class)  MonitorDeviceInfoBo bo,MultipartFile image){
        return monitorDeviceService.insertByBo(bo,image);
    }


    /**
     *查询监测设备列表
     */
    @GetMapping("/list")
    public R<Page<MonitorDeviceInfoVo>> list(PageQuery pageQuery, String deviceName) {
        return R.ok(monitorDeviceService.queryList(pageQuery,deviceName));
    }


    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(MonitorDeviceInfoBo bo,MultipartFile image) {
        return monitorDeviceService.updateByBo(bo,image);
    }


    /**
     * 删除用设备编号
     */
    @DeleteMapping("/delete/{deviceCode}")
    public R delete(@PathVariable String deviceCode) {
        return monitorDeviceService.deleteByDeviceCode(deviceCode);
    }


}
