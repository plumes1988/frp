package com.figure.system.controller.device;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceAlarmtypeMapTemplate;
import com.figure.core.service.device.IDeviceAlarmtypeMapTemplateService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-12-15
 */
@RestController
@RequestMapping("/deviceAlarmtypeMapTemplate")
public class DeviceAlarmtypeMapTemplateController extends BaseController {

    @Resource
    private IDeviceAlarmtypeMapTemplateService deviceAlarmtypeMapTemplateService;

    /**
     * 根据templateId获取设备报警类型映射
     *
     * @param templateId 设备报警类型映射ID
     * @return 设备报警类型映射
     */
    @GetMapping("/get/{templateId}")
    @ApiOperation(value = "根据templateId获取设备报警类型映射", notes = "根据templateId获取设备报警类型映射")
    public DeviceAlarmtypeMapTemplate selectDeviceAlarmtypeMapTemplateById(@PathVariable("templateId") Long templateId) {
        return deviceAlarmtypeMapTemplateService.getById(templateId);
    }

    /**
     * 查询设备报警类型映射列表
     *
     * @param request
     * @return 设备报警类型映射集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询设备报警类型映射列表", notes = "查询设备报警类型映射列表")
    public TableDataInfo<DeviceAlarmtypeMapTemplate> selectDeviceAlarmtypeMapTemplateList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceAlarmtypeMapTemplate> pageWrapper = new PageHelper(DeviceAlarmtypeMapTemplate.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<DeviceAlarmtypeMapTemplate> pages = deviceAlarmtypeMapTemplateService.page(pageWrapper.getPage(), queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存设备报警类型映射
     *
     * @param deviceAlarmtypeMapTemplate 设备报警类型映射
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存设备报警类型映射", notes = "新增保存设备报警类型映射")
    public Map<String, Object> insertDeviceAlarmtypeMapTemplate(@RequestBody DeviceAlarmtypeMapTemplate deviceAlarmtypeMapTemplate) {
        deviceAlarmtypeMapTemplateService.save(deviceAlarmtypeMapTemplate);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceAlarmtypeMapTemplate.getTemplateId());
        return result;
    }

    /**
     * 修改保存设备报警类型映射
     *
     * @param deviceAlarmtypeMapTemplate 设备报警类型映射
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存设备报警类型映射", notes = "修改保存设备报警类型映射")
    public Map<String, Object> updateDeviceAlarmtypeMapTemplateById(@RequestBody DeviceAlarmtypeMapTemplate deviceAlarmtypeMapTemplate) {
        return returnMap(deviceAlarmtypeMapTemplateService.updateById(deviceAlarmtypeMapTemplate));
    }

    /**
     * 批量删除设备报警类型映射
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除设备报警类型映射", notes = "删除设备报警类型映射")
    public Map<String, Object> removeDeviceAlarmtypeMapTemplateByIds(String ids) {
        return returnMap(deviceAlarmtypeMapTemplateService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}