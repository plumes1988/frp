package com.figure.system.controller.device;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceCabinet;
import com.figure.core.service.device.IDeviceCabinetService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
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
@RequestMapping("/deviceCabinet")
public class DeviceCabinetController  extends BaseController {

    @Resource
    private IDeviceCabinetService deviceCabinetService;

    /**
     * 根据cabinetId获取设备机柜
     *
     * @param cabinetId 设备机柜ID
     * @return 设备机柜
     */
    @GetMapping("/get/{cabinetId}")
    @ApiOperation(value = "根据cabinetId获取设备机柜" , notes = "根据cabinetId获取设备机柜")
    public DeviceCabinet selectDeviceCabinetById(@PathVariable("cabinetId") Long cabinetId) {
        return deviceCabinetService.getById(cabinetId);
    }

    /**
     * 查询设备机柜列表
     *
     * @param request
     * @return 设备机柜集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询设备机柜列表" , notes = "查询设备机柜列表")
    public TableDataInfo<DeviceCabinet> selectDeviceCabinetList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceCabinet> pageWrapper =  new PageHelper(DeviceCabinet.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<DeviceCabinet> pages = deviceCabinetService.page(pageWrapper.getPage(),queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存设备机柜
     *
     * @param deviceCabinet 设备机柜
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存设备机柜" , notes = "新增保存设备机柜")
    public Map<String,Object> insertDeviceCabinet(@RequestBody DeviceCabinet deviceCabinet) {
        return returnMap(deviceCabinetService.save(deviceCabinet));
    }

    /**
     * 修改保存设备机柜
     *
     * @param deviceCabinet 设备机柜
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存设备机柜" , notes = "修改保存设备机柜")
    public Map<String,Object> updateDeviceCabinetById(@RequestBody DeviceCabinet deviceCabinet) {
        return returnMap(deviceCabinetService.updateById(deviceCabinet));
    }

    /**
     * 批量删除设备机柜
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除设备机柜" , notes = "删除设备机柜")
    public Map<String,Object> removeDeviceCabinetByIds(String ids) {
        return returnMap(deviceCabinetService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}
