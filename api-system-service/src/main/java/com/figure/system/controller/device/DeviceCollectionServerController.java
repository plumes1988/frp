package com.figure.system.controller.device;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceCollectServer;
import com.figure.core.service.device.IDeviceCollectServerService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 网管采集服务管理表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-08-24
 */
@RestController
@RequestMapping("/deviceCollectServer")
public class DeviceCollectionServerController extends BaseController {

    @Resource
    private IDeviceCollectServerService deviceCollectServerService;

    /**
     * 根据serverId获取网管采集服务
     *
     * @param serverId 网管采集服务ID
     * @return 网管采集服务
     */
    @GetMapping("/get/{serverId}")
    @ApiOperation(value = "根据serverId获取网管采集服务", notes = "根据serverId获取网管采集服务")
    public DeviceCollectServer selectDeviceCollectServerById(@PathVariable("serverId") Long serverId) {
        return deviceCollectServerService.getById(serverId);
    }

    /**
     * 查询网管采集服务列表
     *
     * @param request
     * @return 网管采集服务集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询网管采集服务列表", notes = "查询网管采集服务列表")
    public TableDataInfo<DeviceCollectServer> selectDeviceCollectServerList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceCollectServer> pageWrapper = new PageHelper(DeviceCollectServer.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<DeviceCollectServer> pages = deviceCollectServerService.page(pageWrapper.getPage(), queryWrapper);

        deviceCollectServerService.fillEntityProps(pages.getRecords());

        return new TableDataInfo(pages);
    }

    /**
     * 查询离线采集服务器
     *
     * @param request
     * @return 离线采集服务集合
     */
    @GetMapping("/getOffLine")
    @ApiOperation(value = "查询离线采集服务器", notes = "查询离线采集服务器")
    public Map<String, Integer> getOffLine(HttpServletRequest request) throws Exception {
        return deviceCollectServerService.getOffLine();
    }



    /**
     * 新增保存网管采集服务
     *
     * @param deviceCollectServer 网管采集服务
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存网管采集服务", notes = "新增保存网管采集服务")
    public Map<String, Object> insertDeviceCollectServer(@RequestBody DeviceCollectServer deviceCollectServer) {
        deviceCollectServer.setHeartbeatTime(new Date());
        deviceCollectServerService.save(deviceCollectServer);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceCollectServer.getServerId());
        return result;
    }

    /**
     * 修改保存网管采集服务
     *
     * @param deviceCollectServer 网管采集服务
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存网管采集服务", notes = "修改保存网管采集服务")
    public Map<String, Object> updateDeviceCollectServerById(@RequestBody DeviceCollectServer deviceCollectServer) {
        return returnMap(deviceCollectServerService.updateById(deviceCollectServer));
    }

    /**
     * 批量删除网管采集服务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除网管采集服务", notes = "删除网管采集服务")
    public Map<String, Object> removeDeviceCollectServerByIds(String ids) {
        return returnMap(deviceCollectServerService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}
