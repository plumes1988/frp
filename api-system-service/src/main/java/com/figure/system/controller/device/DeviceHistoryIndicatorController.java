package com.figure.system.controller.device;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceHistoryIndicator;
import com.figure.core.service.device.IDeviceHistoryIndicatorService;
import com.figure.core.service.device.IDeviceIndicatorParamService;
import com.figure.core.service.others.ICommonService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/deviceHistoryIndicator")
public class DeviceHistoryIndicatorController extends BaseController {

    @Resource
    private IDeviceHistoryIndicatorService deviceHistoryIndicatorService;

    @Autowired
    IDeviceIndicatorParamService deviceIndicatorParamService;

    @Resource
    private ICommonService commonService;

    /**
     * 根据id获取指标历史记录
     *
     * @param id 指标历史记录ID
     * @return 指标历史记录
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取指标历史记录" , notes = "根据id获取指标历史记录")
    public DeviceHistoryIndicator selectDeviceHistoryIndicatorById(@PathVariable("id") Long id) {
        return deviceHistoryIndicatorService.getById(id);
    }

    /**
     * 查询指标历史记录列表
     *
     * @param request
     * @return 指标历史记录集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询指标历史记录列表" , notes = "查询指标历史记录列表")
    public TableDataInfo<DeviceHistoryIndicator> selectDeviceHistoryIndicatorList(HttpServletRequest request) throws Exception {

        PageWrapper<DeviceHistoryIndicator> pageWrapper =  new PageHelper(DeviceHistoryIndicator.class,PageHelper.ConditionType.MAP).getPageWrapper(request);

        Map<String, String> condition_map = pageWrapper.getMap();

        String deviceCode = condition_map.get("deviceCode");

        condition_map.put("tableName","device_history_indicator_"+deviceCode);

        IPage<DeviceHistoryIndicator> pages = deviceHistoryIndicatorService.selectPage(pageWrapper.getPage(),condition_map);


        deviceHistoryIndicatorService.fillEntityProps(pages.getRecords());

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存指标历史记录
     *
     * @param deviceHistoryIndicator 指标历史记录
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存指标历史记录" , notes = "新增保存指标历史记录")
    public Map<String,Object> insertDeviceHistoryIndicator(@RequestBody DeviceHistoryIndicator deviceHistoryIndicator) {
        deviceHistoryIndicatorService.saveHistoryIndicator(deviceHistoryIndicator.getDeviceCode(),deviceHistoryIndicator);
        return returnMap(true);
    }

    /**
     * 修改保存指标历史记录
     *
     * @param deviceHistoryIndicator 指标历史记录
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存指标历史记录" , notes = "修改保存指标历史记录")
    public Map<String,Object> updateDeviceHistoryIndicatorById(@RequestBody DeviceHistoryIndicator deviceHistoryIndicator) {
        return returnMap(deviceHistoryIndicatorService.updateById(deviceHistoryIndicator));
    }

    /**
     * 批量删除指标历史记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除指标历史记录" , notes = "删除指标历史记录")
    public Map<String,Object> removeDeviceHistoryIndicatorByIds(String ids) {
        return returnMap(deviceHistoryIndicatorService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}
