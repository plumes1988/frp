package com.figure.system.controller.device;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceIndicatorParamRelTemplete;
import com.figure.core.service.device.IDeviceIndicatorParamRelTempleteService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 设备关联指标模版表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-03-22
 */
@RestController
@RequestMapping("/deviceIndicatorParamRelTemplete")
public class DeviceIndicatorParamRelTempleteController extends BaseController {
    @Resource
    private IDeviceIndicatorParamRelTempleteService deviceIndicatorParamRelTempleteService;

    /**
     * 根据id获取关联指标模版
     *
     * @param id 关联指标模版ID
     * @return 关联指标模版
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取关联指标模版" , notes = "根据id获取关联指标模版")
    public DeviceIndicatorParamRelTemplete selectDeviceIndicatorParamRelTempleteById(@PathVariable("id") Long id) {
        return deviceIndicatorParamRelTempleteService.getById(id);
    }

    /**
     * 查询关联指标模版列表
     *
     * @param request
     * @return 关联指标模版集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询关联指标模版列表" , notes = "查询关联指标模版列表")
    public TableDataInfo<DeviceIndicatorParamRelTemplete> selectDeviceIndicatorParamRelTempleteList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceIndicatorParamRelTemplete> pageWrapper =  new PageHelper(DeviceIndicatorParamRelTemplete.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<DeviceIndicatorParamRelTemplete> pages = deviceIndicatorParamRelTempleteService.page(pageWrapper.getPage(),queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存关联指标模版
     *
     * @param deviceIndicatorParamRelTemplete 关联指标模版
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存关联指标模版" , notes = "新增保存关联指标模版")
    public Map<String,Object> insertDeviceIndicatorParamRelTemplete(@RequestBody DeviceIndicatorParamRelTemplete deviceIndicatorParamRelTemplete) {
        String modelIds = deviceIndicatorParamRelTemplete.getModelIds();
        String[] model_Ids = modelIds.split(",");
        for (String modelId : model_Ids) {
            deviceIndicatorParamRelTemplete.setModelId(Integer.parseInt(modelId));
            QueryWrapper<DeviceIndicatorParamRelTemplete> queryWrapper = new QueryWrapper();
            queryWrapper.eq("modelId",modelId);
            queryWrapper.eq("indicatorCode",deviceIndicatorParamRelTemplete.getIndicatorCode());
            deviceIndicatorParamRelTempleteService.remove(queryWrapper);
            deviceIndicatorParamRelTempleteService.save(deviceIndicatorParamRelTemplete);
        }
        return returnMap(true);
    }

    /**
     * 修改保存关联指标模版
     *
     * @param deviceIndicatorParamRelTemplete 关联指标模版
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存关联指标模版" , notes = "修改保存关联指标模版")
    public Map<String,Object> updateDeviceIndicatorParamRelTempleteById(@RequestBody DeviceIndicatorParamRelTemplete deviceIndicatorParamRelTemplete) {
        return returnMap(deviceIndicatorParamRelTempleteService.updateById(deviceIndicatorParamRelTemplete));
    }

    /**
     * 批量删除关联指标模版
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除关联指标模版" , notes = "删除关联指标模版")
    public Map<String,Object> removeDeviceIndicatorParamRelTempleteByIds(String ids) {
        return returnMap(deviceIndicatorParamRelTempleteService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }
}