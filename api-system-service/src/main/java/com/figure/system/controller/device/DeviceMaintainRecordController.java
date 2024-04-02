package com.figure.system.controller.device;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.audit.AuditAlarmResult;
import com.figure.core.model.device.DeviceMaintainRecord;
import com.figure.core.service.device.IDeviceMaintainRecordService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
 * @since 2022-06-21
 */
@RestController
@RequestMapping("/deviceMaintainRecord")
public class DeviceMaintainRecordController extends BaseController{

    @Resource
    private IDeviceMaintainRecordService deviceMaintainRecordService;

    /**
     * 根据typeId获取设备运维记录
     *
     * @param typeId 设备运维记录ID
     * @return 设备运维记录
     */
    @GetMapping("/get/{typeId}")
    @ApiOperation(value = "根据typeId获取设备运维记录", notes = "根据typeId获取设备运维记录")
    public DeviceMaintainRecord selectDeviceMaintainRecordById(@PathVariable("typeId") Long typeId) {
        return deviceMaintainRecordService.getById(typeId);
    }

    /**
     * 查询设备运维记录列表
     *
     * @param request
     * @return 设备运维记录集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询设备运维记录列表", notes = "查询设备运维记录列表")
    public TableDataInfo<DeviceMaintainRecord> selectDeviceMaintainRecordList(HttpServletRequest request) throws Exception {
        PageWrapper<DeviceMaintainRecord> pageWrapper =  new PageHelper(DeviceMaintainRecord.class, PageHelper.ConditionType.MAP).getPageWrapper(request);
        IPage<DeviceMaintainRecord> pages = deviceMaintainRecordService.selectPage(pageWrapper.getPage(),pageWrapper.getMap());
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存设备运维记录
     *
     * @param deviceMaintainRecord 设备运维记录
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存设备运维记录", notes = "新增保存设备运维记录")
    public Map<String, Object> insertDeviceMaintainRecord(@RequestBody DeviceMaintainRecord deviceMaintainRecord) {
        deviceMaintainRecordService.save(deviceMaintainRecord);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceMaintainRecord.getId());
        return result;
    }

    /**
     * 修改保存设备运维记录
     *
     * @param deviceMaintainRecord 设备运维记录
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存设备运维记录", notes = "修改保存设备运维记录")
    public Map<String, Object> updateDeviceMaintainRecordById(@RequestBody DeviceMaintainRecord deviceMaintainRecord) {
        return returnMap(deviceMaintainRecordService.updateById(deviceMaintainRecord));
    }

    /**
     * 批量删除设备运维记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除设备运维记录", notes = "删除设备运维记录")
    public Map<String, Object> removeDeviceMaintainRecordByIds(String ids) {
        return returnMap(deviceMaintainRecordService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}
