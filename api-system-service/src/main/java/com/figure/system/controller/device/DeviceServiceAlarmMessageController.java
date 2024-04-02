package com.figure.system.controller.device;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.model.device.DeviceServiceAlarmMessage;
import com.figure.core.service.device.IDeviceServiceAlarmMessageService;
import com.figure.core.service.device.IDeviceIndicatorParamRelService;
import com.figure.core.service.device.IDeviceIndicatorParamService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.figure.core.constant.ConstantsForSysPara.NUMBER_OF_RECENT_ALARMS;
import static com.figure.core.model.device.DeviceAlarmMessage.*;
import static com.figure.core.model.device.DeviceAlarmMessage.MISINTERPRET;

/**
 * <p>
 * 应用服务报警记录表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2024-03-04
 */
@RestController
@RequestMapping("/deviceServiceAlarmMessage")
public class DeviceServiceAlarmMessageController extends BaseController {

    @Autowired
    ISysParaService sysParaService;

    @Autowired
    IDeviceIndicatorParamService deviceIndicatorParamService;

    @Resource
    private IDeviceServiceAlarmMessageService deviceServiceAlarmMessageService;

    @Resource
    private IDeviceIndicatorParamRelService deviceIndicatorParamRelService;

    /**
     * 根据id获取应用服务报警
     *
     * @param id 应用服务报警ID
     * @return 应用服务报警
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取应用服务报警" , notes = "根据id获取应用服务报警")
    public DeviceServiceAlarmMessage selectDeviceServiceAlarmMessageById(@PathVariable("id") Long id) {
        return deviceServiceAlarmMessageService.getById(id);
    }

    /**
     * 查询应用服务报警列表
     *
     * @param request
     * @return 应用服务报警集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询应用服务报警列表" , notes = "查询应用服务报警列表")
    public Object selectDeviceServiceAlarmMessageList(HttpServletRequest request) throws Exception {

        PageWrapper<DeviceServiceAlarmMessage> pageWrapper =  new PageHelper(DeviceServiceAlarmMessage.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        queryWrapper.orderByDesc("happenTime");

        IPage<DeviceServiceAlarmMessage> pages = deviceServiceAlarmMessageService.page(pageWrapper.getPage(),queryWrapper);

        deviceServiceAlarmMessageService.fillEntityProps(pages.getRecords());

        String type = request.getParameter("type");

        if("export".equals(type)){

            String paraValue_of_uploadPath = sysParaService.getParamByName("uploadPath");

            String uploadPath = paraValue_of_uploadPath+"/export";

            // return DeviceServiceAlarmMessageExporter.export(pages.getRecords(),uploadPath);
        }

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存应用服务报警
     *
     * @param deviceServiceAlarmMessage 应用服务报警
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存应用服务报警" , notes = "新增保存应用服务报警")
    public Map<String,Object> insertDeviceServiceAlarmMessage(@RequestBody DeviceServiceAlarmMessage deviceServiceAlarmMessage) {
        return returnMap(deviceServiceAlarmMessageService.save(deviceServiceAlarmMessage));
    }

    /**
     * 修改保存应用服务报警
     *
     * @param deviceServiceAlarmMessage 应用服务报警
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存应用服务报警" , notes = "修改保存应用服务报警")
    public Map<String,Object> updateDeviceServiceAlarmMessageById(@RequestBody DeviceServiceAlarmMessage deviceServiceAlarmMessage) {
        deviceServiceAlarmMessage.setContinueTime(null);
        boolean result = deviceServiceAlarmMessageService.updateById(deviceServiceAlarmMessage);
        return returnMap(result);
    }

    /**
     * 批量删除应用服务报警
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除应用服务报警" , notes = "删除应用服务报警")
    public Map<String,Object> removeDeviceServiceAlarmMessageByIds(String ids) {
        return returnMap(deviceServiceAlarmMessageService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }


    /**
     * 获取报警size条的报警，未结束优先
     * @return 结果
     */
    @PostMapping("/getServiceAlarmsWithNotEndWithHighPriority")
    @ApiOperation(value = "获取报警size条的报警，未结束优先" , notes = "获取报警size条的报警，未结束优先")
    public List<DeviceServiceAlarmMessage> getServiceAlarmsWithNotEndWithHighPriority() {

        String  numberOfRecentAlarms_str = sysParaService.getParamByName(NUMBER_OF_RECENT_ALARMS);
        Integer size = 30;
        if(numberOfRecentAlarms_str!=null){
            size = Integer.parseInt(numberOfRecentAlarms_str);
        }

        List<DeviceServiceAlarmMessage> result = new ArrayList<>();

        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.eq("status",NOT_END);

        // queryWrapper.ne("confirm",MISINTERPRET);

        // 指定倒序排序的字段
        queryWrapper.orderByDesc("happenTime");
        // 指定限制结果集的数量
        queryWrapper.last("LIMIT "+size);

        List<DeviceServiceAlarmMessage> alarms =  deviceServiceAlarmMessageService.list(queryWrapper);

        result.addAll(alarms);

        if(alarms.size()<size){
            queryWrapper = new QueryWrapper();

            queryWrapper.eq("status",ENDED);

            // queryWrapper.ne("confirm",MISINTERPRET);

            // 指定倒序排序的字段
            queryWrapper.orderByDesc("happenTime");
            // 指定限制结果集的数量
            queryWrapper.last("LIMIT "+(size-alarms.size()));

            List<DeviceServiceAlarmMessage> alarms_ =  deviceServiceAlarmMessageService.list(queryWrapper);

            result.addAll(alarms_);
        }
        return result;
    }

}