package com.figure.system.controller.device;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceProductModel;
import com.figure.core.service.device.IDeviceProductModelService;
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
 * 型号管理表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-03-22
 */
@RestController
@RequestMapping("/deviceProductModel")
public class DeviceProductModelController extends BaseController {

    @Resource
    private IDeviceProductModelService deviceProductModelService;

    /**
     * 根据modelId获取产品型号
     *
     * @param modelId 产品型号ID
     * @return 产品型号
     */
    @GetMapping("/get/{modelId}")
    @ApiOperation(value = "根据modelId获取产品型号" , notes = "根据modelId获取产品型号")
    public DeviceProductModel selectDeviceProductModelById(@PathVariable("modelId") Long modelId) {
        return deviceProductModelService.getById(modelId);
    }

    /**
     * 查询产品型号列表
     *
     * @param request
     * @return 产品型号集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询产品型号列表" , notes = "查询产品型号列表")
    public TableDataInfo<DeviceProductModel> selectDeviceProductModelList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceProductModel> pageWrapper =  new PageHelper(DeviceProductModel.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<DeviceProductModel> pages = deviceProductModelService.page(pageWrapper.getPage(),queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存产品型号
     *
     * @param deviceProductModel 产品型号
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存产品型号" , notes = "新增保存产品型号")
    public Map<String,Object> insertDeviceProductModel(@RequestBody DeviceProductModel deviceProductModel) {
        deviceProductModelService.save(deviceProductModel);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceProductModel.getModelId());
        return result;
    }

    /**
     * 修改保存产品型号
     *
     * @param deviceProductModel 产品型号
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存产品型号" , notes = "修改保存产品型号")
    public Map<String,Object> updateDeviceProductModelById(@RequestBody DeviceProductModel deviceProductModel) {
        return returnMap(deviceProductModelService.updateById(deviceProductModel));
    }

    /**
     * 批量删除产品型号
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除产品型号" , notes = "删除产品型号")
    public Map<String,Object> removeDeviceProductModelByIds(String ids) {
        return returnMap(deviceProductModelService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}