package com.figure.system.controller.device;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.service.device.IDeviceIndicatorParamRelService;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-06-28
 */
@RestController
@RequestMapping("/deviceIndicatorParamRel")
public class DeviceIndicatorParamRelController extends BaseController {

    @Resource
    private IDeviceIndicatorParamRelService deviceIndicatorParamRelService;

    @Resource
    private IDeviceInfoService deviceInfoService;

    /**
     * 根据id获取关联指标
     *
     * @param id 关联指标ID
     * @return 关联指标
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取关联指标" , notes = "根据id获取关联指标")
    public DeviceIndicatorParamRel selectDeviceIndicatorParamRelById(@PathVariable("id") Long id) {
        return deviceIndicatorParamRelService.getById(id);
    }

    /**
     * 查询关联指标列表
     *
     * @param request
     * @return 关联指标集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询关联指标列表" , notes = "查询关联指标列表")
    public TableDataInfo<DeviceIndicatorParamRel> selectDeviceIndicatorParamRelList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceIndicatorParamRel> pageWrapper =  new PageHelper(DeviceIndicatorParamRel.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<DeviceIndicatorParamRel> pages = deviceIndicatorParamRelService.page(pageWrapper.getPage(),queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存关联指标
     *
     * @param deviceIndicatorParamRel 关联指标
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存关联指标" , notes = "新增保存关联指标")
    public Map<String,Object> insertDeviceIndicatorParamRel(@RequestBody DeviceIndicatorParamRel deviceIndicatorParamRel) {
        String deviceIds = deviceIndicatorParamRel.getDeviceIds();
        String[] device_Ids = deviceIds.split(",");
        for (String deviceId_str : device_Ids) {
            Integer deviceId = Integer.parseInt(deviceId_str);
            DeviceInfo deviceInfo = deviceInfoService.getById(deviceId);
            deviceIndicatorParamRel.setDeviceId(deviceId);
            deviceIndicatorParamRel.setDeviceCode(deviceInfo.getDeviceCode());
            QueryWrapper<DeviceIndicatorParamRel> queryWrapper = new QueryWrapper();
            queryWrapper.eq("deviceId",deviceId);
            queryWrapper.eq("indicatorCode",deviceIndicatorParamRel.getIndicatorCode());
            deviceIndicatorParamRelService.saveOrUpdate(deviceIndicatorParamRel,queryWrapper);
        }
        deviceIndicatorParamRelService.updateCache();
        return returnMap(true);
    }

    /**
     * 修改保存关联指标
     *
     * @param deviceIndicatorParamRel 关联指标
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存关联指标" , notes = "修改保存关联指标")
    public Map<String,Object> updateDeviceIndicatorParamRelById(@RequestBody DeviceIndicatorParamRel deviceIndicatorParamRel) {
        Integer deviceId = deviceIndicatorParamRel.getDeviceId();
        DeviceInfo deviceInfo = deviceInfoService.getById(deviceId);
        deviceIndicatorParamRel.setDeviceCode(deviceInfo.getDeviceCode());
        deviceIndicatorParamRelService.updateCache();
        return returnMap(deviceIndicatorParamRelService.updateById(deviceIndicatorParamRel));
    }

    /**
     * 批量删除关联指标
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除关联指标" , notes = "删除关联指标")
    public Map<String,Object> removeDeviceIndicatorParamRelByIds(String ids) {
        deviceIndicatorParamRelService.updateCache();
        return returnMap(deviceIndicatorParamRelService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

    /**
     * 将配置参数应用至同厂商、同型号的设备
     *
     * @param deviceInfo 设备信息
     * @return 结果
     */
    @PostMapping("/applyParamsSetting2SameProductAndModelDevices")
    @ApiOperation(value = "将配置参数应用至同厂商、同型号的设备" , notes = "将配置参数应用至同厂商、同型号的设备")
    public Map<String,Object> applyParamsSetting2SameProductAndModelDevices(@RequestBody DeviceInfo deviceInfo) {
        Integer deviceId = deviceInfo.getDeviceId();
        deviceIndicatorParamRelService.applyParamsSetting2SameProductAndModelDevices(deviceId);
        return returnMap(true);
    }

}
