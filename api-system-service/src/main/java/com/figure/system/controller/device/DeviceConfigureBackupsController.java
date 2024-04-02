package com.figure.system.controller.device;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceConfigureBackups;
import com.figure.core.service.device.IDeviceConfigureBackupsService;
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
 * @since 2022-12-16
 */
@RestController
@RequestMapping("/deviceConfigureBackups")
public class DeviceConfigureBackupsController extends BaseController {

    @Resource
    private IDeviceConfigureBackupsService deviceConfigureBackupsService;

    /**
     * 根据id获取设备配置备份管理
     *
     * @param id 设备配置备份管理ID
     * @return 设备配置备份管理
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取设备配置备份管理", notes = "根据id获取设备配置备份管理")
    public DeviceConfigureBackups selectDeviceConfigureBackupsById(@PathVariable("id") Long id) {
        return deviceConfigureBackupsService.getById(id);
    }

    /**
     * 查询设备配置备份管理列表
     *
     * @param request
     * @return 设备配置备份管理集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询设备配置备份管理列表", notes = "查询设备配置备份管理列表")
    public TableDataInfo<DeviceConfigureBackups> selectDeviceConfigureBackupsList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceConfigureBackups> pageWrapper = new PageHelper(DeviceConfigureBackups.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<DeviceConfigureBackups> pages = deviceConfigureBackupsService.page(pageWrapper.getPage(), queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存设备配置备份管理
     *
     * @param deviceConfigureBackups 设备配置备份管理
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存设备配置备份管理", notes = "新增保存设备配置备份管理")
    public Map<String, Object> insertDeviceConfigureBackups(@RequestBody DeviceConfigureBackups deviceConfigureBackups) {
        deviceConfigureBackupsService.save(deviceConfigureBackups);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceConfigureBackups.getId());
        return result;
    }

    /**
     * 修改保存设备配置备份管理
     *
     * @param deviceConfigureBackups 设备配置备份管理
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存设备配置备份管理", notes = "修改保存设备配置备份管理")
    public Map<String, Object> updateDeviceConfigureBackupsById(@RequestBody DeviceConfigureBackups deviceConfigureBackups) {
        return returnMap(deviceConfigureBackupsService.updateById(deviceConfigureBackups));
    }

    /**
     * 批量删除设备配置备份管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除设备配置备份管理", notes = "删除设备配置备份管理")
    public Map<String, Object> removeDeviceConfigureBackupsByIds(String ids) {
        return returnMap(deviceConfigureBackupsService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}
