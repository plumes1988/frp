package com.figure.system.controller.device;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.helper.StringHelper;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceLogicchannelRel;
import com.figure.core.service.device.IDeviceLogicchannelRelService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备 与 逻辑频道（播出系统）关联表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-04-06
 */
@RestController
@RequestMapping("/deviceLogicchannelRel")
public class DeviceLogicchannelRelController extends BaseController {

    @Resource
    private IDeviceLogicchannelRelService deviceLogicchannelRelService;

    /**
     * 查询设备 与 逻辑频道（播出系统）关联信息列表
     *
     * @param request
     * @return 设备 与 逻辑频道（播出系统）关联信息 集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询设备 与 逻辑频道（播出系统）关联信息列表" , notes = "查询设备 与 逻辑频道（播出系统）关联信息列表")
    public TableDataInfo<DeviceLogicchannelRel> selectDeviceLogicchannelRelList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceLogicchannelRel> pageWrapper =  new PageHelper(DeviceLogicchannelRel.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<DeviceLogicchannelRel> pages = deviceLogicchannelRelService.page(pageWrapper.getPage(),queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 保存  设备 与 逻辑频道（播出系统）关联 信息
     *
     * @param deviceLogicchannelRel 保存  设备 与 逻辑频道（播出系统）关联 信息
     * @return 结果
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存  设备 与 逻辑频道（播出系统）关联 信息" , notes = "保存  设备 与 逻辑频道（播出系统）关联 信息")
    public Map<String,Object> insertDeviceLogicchannelRel(@RequestBody DeviceLogicchannelRel deviceLogicchannelRel) {
        String deviceCode = deviceLogicchannelRel.getDeviceCode();

        List<DeviceLogicchannelRel> deviceLogicchannelRels = new ArrayList<>();
        String channelCodes_str = deviceLogicchannelRel.getChannelCodes();
        String[] channelCodes = channelCodes_str.split(",");
        for (String channelCode:channelCodes) {
            DeviceLogicchannelRel entity = new DeviceLogicchannelRel();
            entity.setDeviceCode(deviceCode);
            entity.setChannelCode(channelCode);

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("deviceCode",deviceCode);
            queryWrapper.eq("channelCode",channelCode);
            deviceLogicchannelRelService.remove(queryWrapper);

            deviceLogicchannelRels.add(entity);
        }
        return returnMap(deviceLogicchannelRelService.saveBatch(deviceLogicchannelRels));
    }


    /**
     * 删除  设备 与 逻辑频道（播出系统）关联 信息
     *
     * @param deviceLogicchannelRel 删除  设备 与 逻辑频道（播出系统）关联 信息
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除  设备 与 逻辑频道（播出系统）关联 信息" , notes = "删除  设备 与 逻辑频道（播出系统）关联 信息")
    public Map<String,Object> deleteDeviceLogicchannelRel(@RequestBody DeviceLogicchannelRel deviceLogicchannelRel) {
        String deviceCode = deviceLogicchannelRel.getDeviceCode();
        List<DeviceLogicchannelRel> deviceLogicchannelRels = new ArrayList<>();
        String channelCodes_str = deviceLogicchannelRel.getChannelCodes();
        String[] channelCodes = channelCodes_str.split(",");
        for (String channelCode:channelCodes) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("deviceCode",deviceCode);
            queryWrapper.eq("channelCode",channelCode);
            deviceLogicchannelRelService.remove(queryWrapper);
        }
        return returnMap(deviceLogicchannelRelService.saveBatch(deviceLogicchannelRels));
    }
}
