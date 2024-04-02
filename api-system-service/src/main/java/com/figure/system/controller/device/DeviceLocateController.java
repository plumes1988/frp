package com.figure.system.controller.device;


import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceLocate;
import com.figure.core.service.device.IDeviceLocateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-06-11
 */
@RestController
@RequestMapping("/deviceLocate")
public class DeviceLocateController extends BaseController {

    @Autowired
    IDeviceLocateService deviceLocateService;


    /**
     * 查询机房信息列表
     *
     * @param deviceLocate 机房信息
     * @return 机房信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询机房信息列表" , notes = "查询机房信息列表")
    public List<DeviceLocate> selectDeviceLocateList(DeviceLocate deviceLocate, HttpServletRequest request) throws Exception {
        PageWrapper<DeviceLocate> pageWrapper =  new PageHelper(DeviceLocate.class).getPageWrapper(request);
        permissionsFilterByFrontIds(request,pageWrapper.getQueryWrapper());
        return deviceLocateService.list(pageWrapper.getQueryWrapper());
    }


    /**
     * 新增保存机房信息
     *
     * @param deviceLocate 机房信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存机房信息" , notes = "新增保存机房信息")
    public Map<String,Object> insertDeviceLocate(@RequestBody DeviceLocate deviceLocate) {
        deviceLocateService.save(deviceLocate);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceLocate.getLocateId());
        return result;
    }


    /**
     * 修改保存机房信息
     *
     * @param deviceLocate 机房信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存机房信息" , notes = "修改保存机房信息")
    public Map<String,Object> updateDeviceLocateById(@RequestBody DeviceLocate deviceLocate) {
        deviceLocateService.updateById(deviceLocate);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceLocate.getLocateId());
        return result;
    }

    /**
     * 批量删除机房信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除机房信息" , notes = "删除机房信息")
    public boolean removeDeviceLocateByIds(String ids) {
        return deviceLocateService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
    }

}
