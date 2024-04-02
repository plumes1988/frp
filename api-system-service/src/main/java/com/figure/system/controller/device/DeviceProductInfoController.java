package com.figure.system.controller.device;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.base.BaseDataBackupTask;
import com.figure.core.model.device.DeviceProductInfo;
import com.figure.core.service.device.IDeviceProductInfoService;
import com.figure.core.service.device.IDeviceProductModelService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌管理表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-03-21
 */
@RestController
@RequestMapping("/deviceProductInfo")
public class DeviceProductInfoController extends BaseController {

    @Resource
    private IDeviceProductInfoService deviceProductInfoService;

    @Resource
    private IDeviceProductModelService deviceProductModelService;

    /**
     * 根据productId获取品牌信息
     *
     * @param productId 品牌信息ID
     * @return 品牌信息
     */
    @GetMapping("/get/{productId}")
    @ApiOperation(value = "根据productId获取品牌信息" , notes = "根据productId获取品牌信息")
    public DeviceProductInfo selectDeviceProductInfoById(@PathVariable("productId") Long productId) {
        return deviceProductInfoService.getById(productId);
    }

    /**
     * 查询品牌信息列表
     *
     * @param request
     * @return 品牌信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询品牌信息列表" , notes = "查询品牌信息列表")
    public TableDataInfo<DeviceProductInfo> selectDeviceProductInfoList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceProductInfo> pageWrapper =  new PageHelper(DeviceProductInfo.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<DeviceProductInfo> pages = deviceProductInfoService.page(pageWrapper.getPage(),queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存品牌信息
     *
     * @param deviceProductInfo 品牌信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存品牌信息" , notes = "新增保存品牌信息")
    public Map<String,Object> insertDeviceProductInfo(@RequestBody DeviceProductInfo deviceProductInfo) {
        deviceProductInfoService.save(deviceProductInfo);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceProductInfo.getProductId());
        return result;
    }

    /**
     * 修改保存品牌信息
     *
     * @param deviceProductInfo 品牌信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存品牌信息" , notes = "修改保存品牌信息")
    public Map<String,Object> updateDeviceProductInfoById(@RequestBody DeviceProductInfo deviceProductInfo) {
        return returnMap(deviceProductInfoService.updateById(deviceProductInfo));
    }

    /**
     * 批量删除品牌信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除品牌信息" , notes = "删除品牌信息")
    public Map<String,Object> removeDeviceProductInfoByIds(String ids) {
        List<String> idList = Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("productId",idList);
        deviceProductModelService.remove(queryWrapper);
        return returnMap(deviceProductInfoService.removeByIds(idList));
    }

}