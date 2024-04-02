package com.figure.system.controller.device;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceAsset;
import com.figure.core.service.device.IDeviceAssetService;
import com.figure.core.service.others.IDenseDataService;
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
 * 设备资产管理表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-12-19
 */
@RestController
@RequestMapping("/deviceAsset")
public class DeviceAssetController extends BaseController {

    @Resource
    private IDeviceAssetService deviceAssetService;

    @Resource
    private IDenseDataService denseDataServiceImpl;



    /**
     * 根据assetNo获取设备资产
     *
     * @param assetNo 设备资产ID
     * @return 设备资产
     */
    @GetMapping("/get/{assetNo}")
    @ApiOperation(value = "根据assetNo获取设备资产", notes = "根据assetNo获取设备资产")
    public DeviceAsset selectDeviceAssetById(@PathVariable("assetNo") Long assetNo) {
        return deviceAssetService.getById(assetNo);
    }

    /**
     * 查询设备资产列表
     *
     * @param request
     * @return 设备资产集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询设备资产列表", notes = "查询设备资产列表")
    public TableDataInfo<DeviceAsset> selectDeviceAssetList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceAsset> pageWrapper = new PageHelper(DeviceAsset.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<DeviceAsset> pages = deviceAssetService.page(pageWrapper.getPage(), queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存设备资产
     *
     * @param deviceAsset 设备资产
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存设备资产", notes = "新增保存设备资产")
    public Map<String, Object> insertDeviceAsset(@RequestBody DeviceAsset deviceAsset) {
        deviceAssetService.save(deviceAsset);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceAsset.getId());
        return result;
    }

    /**
     * 修改保存设备资产
     *
     * @param deviceAsset 设备资产
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存设备资产", notes = "修改保存设备资产")
    public Map<String, Object> updateDeviceAssetById(@RequestBody DeviceAsset deviceAsset) {
        return returnMap(deviceAssetService.updateById(deviceAsset));
    }

    /**
     * 批量删除设备资产
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除设备资产", notes = "删除设备资产")
    public Map<String, Object> removeDeviceAssetByIds(String ids) {
        return returnMap(deviceAssetService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}