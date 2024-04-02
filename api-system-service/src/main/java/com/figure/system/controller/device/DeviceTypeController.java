package com.figure.system.controller.device;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceType;
import com.figure.core.service.device.IDeviceTypeService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
 * @since 2022-05-26
 */
@RestController
@RequestMapping("/deviceType")
public class DeviceTypeController extends BaseController {

    @Resource
    private IDeviceTypeService deviceTypeService;

    /**
     * 根据typeId获取设备类型
     *
     * @param typeId 设备类型ID
     * @return 设备类型
     */
    @GetMapping("/get/{typeId}")
    @ApiOperation(value = "根据typeId获取设备类型", notes = "根据typeId获取设备类型")
    public DeviceType selectDeviceTypeById(@PathVariable("typeId") Long typeId) {
        return deviceTypeService.getById(typeId);
    }

    /**
     * 查询设备类型列表
     *
     * @param request
     * @return 设备类型集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询设备类型列表", notes = "查询设备类型列表")
    public TableDataInfo<DeviceType> selectDeviceTypeList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceType> pageWrapper = new PageHelper(DeviceType.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<DeviceType> pages = deviceTypeService.page(pageWrapper.getPage(), queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存设备类型
     *
     * @param deviceType 设备类型
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存设备类型", notes = "新增保存设备类型")
    public Map<String, Object> insertDeviceType(@RequestBody DeviceType deviceType) {
        deviceTypeService.save(deviceType);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceType.getTypeId());
        return result;
    }

    /**
     * 修改保存设备类型
     *
     * @param deviceType 设备类型
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存设备类型", notes = "修改保存设备类型")
    public Map<String, Object> updateDeviceTypeById(@RequestBody DeviceType deviceType) {
        if(deviceType.getTypeId()<=0){
            return insertDeviceType(deviceType);
        }
        return returnMap(deviceTypeService.updateById(deviceType),deviceType.getTypeId());
    }

    /**
     * 批量删除设备类型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除设备类型", notes = "删除设备类型")
    public Map<String, Object> removeDeviceTypeByIds(String ids) {
        boolean result = deviceTypeService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids)));
        deviceTypeService.clearData();
        return returnMap(result);
    }
}
