package com.figure.system.controller.device;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceIndicatorRecordTask;
import com.figure.core.model.device.DeviceIndicatorRecordTaskItem;
import com.figure.core.service.device.IDeviceIndicatorRecordTaskItemService;
import com.figure.core.service.device.IDeviceIndicatorRecordTaskService;
import com.figure.core.service.others.ICommonService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 巡机抄录任务 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-06-05
 */
@RestController
@RequestMapping("/deviceIndicatorRecordTask")
public class DeviceIndicatorRecordTaskController extends BaseController {

    @Resource
    private IDeviceIndicatorRecordTaskService deviceIndicatorRecordTaskService;

    @Resource
    private IDeviceIndicatorRecordTaskItemService deviceIndicatorRecordTaskItemService;


    @Autowired
    ICommonService commonService;

    /**
     * 根据taskId获取巡机抄录任务
     *
     * @param taskId 媒介ID
     * @return 巡机抄录任务
     */
    @GetMapping("/get/{taskId}")
    @ApiOperation(value = "根据taskId获取巡机抄录任务", notes = "根据taskId获取巡机抄录任务")
    public DeviceIndicatorRecordTask selectDeviceIndicatorRecordTaskById(@PathVariable("taskId") Long taskId) {
        return deviceIndicatorRecordTaskService.getById(taskId);
    }

    /**
     * 查询巡机抄录任务列表
     *
     * @param request
     * @return 巡机抄录任务集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询巡机抄录任务列表", notes = "查询巡机抄录任务列表")
    public TableDataInfo<DeviceIndicatorRecordTask> selectDeviceIndicatorRecordTaskList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceIndicatorRecordTask> pageWrapper = new PageHelper(DeviceIndicatorRecordTask.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();
        IPage<DeviceIndicatorRecordTask> pages = deviceIndicatorRecordTaskService.page(pageWrapper.getPage(), queryWrapper);
        for(DeviceIndicatorRecordTask task:pages.getRecords()){
            List<Map<String,Object>> items = commonService.queryTable("t1.*,t2.deviceName","device_indicator_record_task_item t1 left join device_info t2 on t1.deviceId = t2.deviceId","t1.taskId = "+task.getTaskId());
            task.setItems(items);
        }
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存巡机抄录任务
     *
     * @param deviceIndicatorRecordTask 巡机抄录任务
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存巡机抄录任务", notes = "新增保存巡机抄录任务")
    public Map<String, Object> insertDeviceIndicatorRecordTask(@RequestBody DeviceIndicatorRecordTask deviceIndicatorRecordTask) {
        initCreateProps(deviceIndicatorRecordTask);
        deviceIndicatorRecordTaskService.save(deviceIndicatorRecordTask);
        List<DeviceIndicatorRecordTaskItem> items = deviceIndicatorRecordTask.getItems();
        for(DeviceIndicatorRecordTaskItem item:items){
            item.setTaskId(deviceIndicatorRecordTask.getTaskId());
            deviceIndicatorRecordTaskItemService.save(item);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceIndicatorRecordTask.getTaskId());
        return result;
    }

    /**
     * 修改保存巡机抄录任务
     *
     * @param deviceIndicatorRecordTask 巡机抄录任务
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存巡机抄录任务", notes = "修改保存巡机抄录任务")
    public Map<String, Object> updateDeviceIndicatorRecordTaskById(@RequestBody DeviceIndicatorRecordTask deviceIndicatorRecordTask) {
        return returnMap(deviceIndicatorRecordTaskService.updateById(deviceIndicatorRecordTask));
    }

    /**
     * 批量删除巡机抄录任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除巡机抄录任务", notes = "删除巡机抄录任务")
    public Map<String, Object> removeDeviceIndicatorRecordTaskByIds(String ids) {
        return returnMap(deviceIndicatorRecordTaskService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}