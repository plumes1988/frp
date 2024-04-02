package com.figure.system.controller.device;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceFunction;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.service.device.IDeviceFunctionService;
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
 * 设备功能 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-06-01
 */
@RestController
@RequestMapping("/deviceFunction")
public class DeviceFunctionController extends BaseController {

    @Resource
    private IDeviceFunctionService deviceFunctionService;

    /**
     * 根据pId获取设备功能
     *
     * @param pId 媒介ID
     * @return 设备功能
     */
    @GetMapping("/get/{pId}")
    @ApiOperation(value = "根据pId获取设备功能", notes = "根据pId获取设备功能")
    public DeviceFunction selectDeviceFunctionById(@PathVariable("pId") Long pId) {
        return deviceFunctionService.getById(pId);
    }

    /**
     * 查询设备功能列表
     *
     * @param request
     * @return 设备功能集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询设备功能列表", notes = "查询设备功能列表")
    public TableDataInfo<DeviceFunction> selectDeviceFunctionList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceFunction> pageWrapper = new PageHelper(DeviceFunction.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();
        IPage<DeviceFunction> pages = deviceFunctionService.page(pageWrapper.getPage(), queryWrapper);
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存设备功能
     *
     * @param deviceFunction 设备功能
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存设备功能", notes = "新增保存设备功能")
    public Map<String, Object> insertDeviceFunction(@RequestBody DeviceFunction deviceFunction) {
        deviceFunctionService.save(deviceFunction);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceFunction.getId());
        return result;
    }

    /**
     * 修改保存设备功能
     *
     * @param deviceFunction 设备功能
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存设备功能", notes = "修改保存设备功能")
    public Map<String, Object> updateDeviceFunctionById(@RequestBody DeviceFunction deviceFunction) {
        return returnMap(deviceFunctionService.updateById(deviceFunction));
    }

    /**
     * 批量删除设备功能
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除设备功能", notes = "删除设备功能")
    public Map<String, Object> removeDeviceFunctionByIds(String ids) {
        return returnMap(deviceFunctionService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}