package com.figure.system.controller.device;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceAlarmtypeInfo;
import com.figure.core.service.device.IDeviceAlarmtypeInfoService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 报警类型表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-12-16
 */
@RestController
@RequestMapping("/deviceAlarmtypeInfo")
public class DeviceAlarmtypeInfoController extends BaseController {

    @Resource
    private IDeviceAlarmtypeInfoService deviceAlarmtypeInfoService;

    /**
     * 根据alarmTypeId获取报警类型
     *
     * @param alarmTypeId 报警类型ID
     * @return 报警类型
     */
    @GetMapping("/get/{alarmTypeId}")
    @ApiOperation(value = "根据alarmTypeId获取报警类型", notes = "根据alarmTypeId获取报警类型")
    public DeviceAlarmtypeInfo selectDeviceAlarmtypeInfoById(@PathVariable("alarmTypeId") Long alarmTypeId) {
        return deviceAlarmtypeInfoService.getById(alarmTypeId);
    }

    /**
     * 查询报警类型列表
     *
     * @param request
     * @return 报警类型集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询报警类型列表", notes = "查询报警类型列表")
    public TableDataInfo<DeviceAlarmtypeInfo> selectDeviceAlarmtypeInfoList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceAlarmtypeInfo> pageWrapper = new PageHelper(DeviceAlarmtypeInfo.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<DeviceAlarmtypeInfo> pages = deviceAlarmtypeInfoService.page(pageWrapper.getPage(), queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存报警类型
     *
     * @param deviceAlarmtypeInfo 报警类型
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存报警类型", notes = "新增保存报警类型")
    public Map<String, Object> insertDeviceAlarmtypeInfo(@RequestBody DeviceAlarmtypeInfo deviceAlarmtypeInfo) {
        deviceAlarmtypeInfoService.save(deviceAlarmtypeInfo);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceAlarmtypeInfo.getAlarmTypeId());
        return result;
    }

    /**
     * 修改保存报警类型
     *
     * @param deviceAlarmtypeInfo 报警类型
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存报警类型", notes = "修改保存报警类型")
    public Map<String, Object> updateDeviceAlarmtypeInfoById(@RequestBody DeviceAlarmtypeInfo deviceAlarmtypeInfo) {
        QueryWrapper queryWrapper  = new QueryWrapper();
        queryWrapper.eq("alarmTypeId",deviceAlarmtypeInfo.getOldAlarmTypeId());
        return returnMap(deviceAlarmtypeInfoService.update(deviceAlarmtypeInfo,queryWrapper));
    }

    /**
     * 批量删除报警类型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除报警类型", notes = "删除报警类型")
    public Map<String, Object> removeDeviceAlarmtypeInfoByIds(String ids) {
        return returnMap(deviceAlarmtypeInfoService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}