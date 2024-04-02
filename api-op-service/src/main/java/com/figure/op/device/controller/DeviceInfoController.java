package com.figure.op.device.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.device.domain.bo.DeviceInfoQueryBo;
import com.figure.op.device.domain.vo.*;
import com.figure.op.device.service.IDeviceInfoService;
import com.figure.op.duty.domain.bo.DutyScheduleInfoQueryBo;
import com.figure.op.duty.domain.vo.DutyScheduleInfoPageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设备控制器
 * @author fsn
 */
@RestController
@RequestMapping("/deviceInfo")
public class DeviceInfoController {

    @Resource
    private IDeviceInfoService deviceProductInfoService;

    /**
     * 设备全部列表
     */
    @GetMapping("/list")
    public R<List<DeviceInfoListVo>> deviceList() {
        return R.ok(deviceProductInfoService.queryDeviceList());
    }

    /**
     * 设备-分页列表
     */
    @GetMapping("/page")
    public TableDataInfo<DeviceInfoPageVo> page(DeviceInfoQueryBo queryBo, PageQuery pageQuery) {
        return deviceProductInfoService.queryDevicePage(queryBo, pageQuery);
    }

    /**
     * 采集站列表
     */
    @GetMapping("/monitorStationList")
    public R<List<MonitorStationVo>> monitorStationList() {
        return R.ok(deviceProductInfoService.queryMonitorStationList());
    }

    /**
     * 采集点列表
     */
    @GetMapping("/logicPositionList")
    public R<List<LogicPositionVo>> logicPositionList() {
        return R.ok(deviceProductInfoService.queryLogicPositionList());
    }

    /**
     * 设备类型列表
     */
    @GetMapping("/deviceTypeList")
    public R<List<String>> deviceTypeList() {
        return R.ok(deviceProductInfoService.queryDeviceTypeList());
    }

    /**
     * 设备子类列表
     */
    @GetMapping("/deviceSubTypeList")
    public R<List<String>> deviceSubTypeList() {
        return R.ok(deviceProductInfoService.queryDeviceSubTypeList());
    }

    /**
     * 设备型号列表
     */
    @GetMapping("/modelList")
    public R<List<ModelVo>> modelList(@RequestParam(value = "brandId", required = false) Integer brandId) {
        return R.ok(deviceProductInfoService.queryModelList(brandId));
    }

    /**
     * 设备厂商列表
     */
    @GetMapping("/productInfoList")
    public R<List<DeviceProductInfoVo>> productInfoList() {
        return R.ok(deviceProductInfoService.queryProductInfoList());
    }
}
