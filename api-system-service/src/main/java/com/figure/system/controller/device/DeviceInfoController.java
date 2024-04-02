package com.figure.system.controller.device;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.device.DeviceInfoList;
import com.figure.core.model.device.ServiceControlSet;
import com.figure.core.model.signal.SignalInterfaceInfo;
import com.figure.core.query.device.DeviceInfoQuery;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.others.ICommonService;
import com.figure.core.service.signal.ISignalInterfaceInfoService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import com.figure.system.mq.RQProducerConsumerManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.figure.system.mq.RQProducerConsumerManager.GROUP_NAME_HYPHEN;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-06-15
 */
@RestController
@RequestMapping("/deviceInfo")
public class DeviceInfoController extends BaseController {

    @Resource
    private IDeviceInfoService deviceInfoService;

    @Resource
    private ISignalInterfaceInfoService signalInterfaceInfoService;

    @Autowired
    private RQProducerConsumerManager rQProducerConsumerManager;

    @Autowired
    ICommonService commonService;

    /**
     * 根据deviceId获取设备
     *
     * @param deviceId 设备ID
     * @return 设备
     */
    @GetMapping("/get/{deviceId}")
    @ApiOperation(value = "根据deviceId获取设备" , notes = "根据deviceId获取设备")
    public DeviceInfo selectDeviceInfoById(@PathVariable("deviceId") Long deviceId) {
        return deviceInfoService.getById(deviceId);
    }

    /**
     * 查询设备列表
     *
     * @param request
     * @return 设备集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询设备列表" , notes = "查询设备列表")
    public TableDataInfo<DeviceInfo> selectDeviceInfoList(HttpServletRequest request) throws Exception {

        PageWrapper<DeviceInfo> pageWrapper = new PageHelper(DeviceInfo.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        queryWrapper.eq("isDelete", com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED);

        permissionsFilterByFrontIds(request,pageWrapper.getQueryWrapper(),"monitorStationId");

        IPage<DeviceInfo> pages = deviceInfoService.page(pageWrapper.getPage(), queryWrapper);

        for (DeviceInfo deviceInfo : pages.getRecords()) {
            deviceInfo.setFull_deviceSubType(deviceInfo.getDeviceSubType() + "&&" + deviceInfo.getDeviceType());
        }

        return new TableDataInfo(pages);
    }

    /**
     * 查询设备列表
     *
     * @param query
     * @return 设备集合
     */
    @GetMapping("/treelist")
    @ApiOperation(value = "查询设备列表", notes = "查询设备列表")
    public TableDataInfo<DeviceInfoList> selectDeviceInfoTreeList(DeviceInfoQuery query) {
        return toPageResult(deviceInfoService.treelist(query.autoPageWrapper()));
    }

    /**
     * 查询设备列表
     *
     * @param query
     * @return 设备集合
     */
    @GetMapping("/detailList")
    @ApiOperation(value = "查询设备列表", notes = "查询设备列表")
    public TableDataInfo<HashMap<String, Object>> selectDeviceInfoDetailList(DeviceInfoQuery query) {
        return toPageResult(deviceInfoService.detailList(query.autoPageWrapper()));
    }

    /**
     * 查询设备列表
     *
     * @param query
     * @return 设备集合
     */
    @GetMapping("/interbydevice")
    @ApiOperation(value = "查询设备列表", notes = "查询设备列表")
    public TableDataInfo<DeviceInfoList> selectInterfaceByDevice(DeviceInfoQuery query) {
        return toPageResult(deviceInfoService.interbydevice(query.autoPageWrapper()));
    }

    /**
     * 新增保存设备
     *
     * @param deviceInfo 设备
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存设备", notes = "新增保存设备")
    public Map<String, Object> insertDeviceInfo(@RequestBody DeviceInfo deviceInfo) {
        initCreateProps(deviceInfo);

        String getBoardInterfaces_str = deviceInfo.getBoardInterfaces_str();
        List<SignalInterfaceInfo> signalInterfaceInfos = new Gson().fromJson(getBoardInterfaces_str, new TypeToken<List<SignalInterfaceInfo>>() {
        }.getType());

        boolean result = deviceInfoService.save(deviceInfo);

        signalInterfaceInfoService.bitchInsertOrUpdate(signalInterfaceInfos, deviceInfo);

        rQProducerConsumerManager.createRealRimeIndicatorConsumer(deviceInfo);
        rQProducerConsumerManager.createRealRimeIndicatorConsumerForHistory(deviceInfo);
        rQProducerConsumerManager.createRealRimeIndicatorConsumerForStateChange(deviceInfo);

        deviceInfoService.updateCache();

        return returnMap(result,deviceInfo.getDeviceId());
    }

    /**
     * 修改保存设备
     *
     * @param deviceInfo 设备
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存设备" , notes = "修改保存设备")
    public Map<String,Object> updateDeviceInfoById(@RequestBody DeviceInfo deviceInfo) {

        DeviceInfo old_deviceInfo = deviceInfoService.getById(deviceInfo.getDeviceId());

        initUpdateProps(deviceInfo);
        String getBoardInterfaces_str = deviceInfo.getBoardInterfaces_str();
        List<SignalInterfaceInfo> signalInterfaceInfos = new Gson().fromJson(getBoardInterfaces_str, new TypeToken<List<SignalInterfaceInfo>>() {
        }.getType());

        signalInterfaceInfoService.bitchInsertOrUpdate(signalInterfaceInfos,deviceInfo);

        DeviceInfo new_deviceInfo = deviceInfoService.getById(deviceInfo.getDeviceId());

        boolean codeChange = false;

        if(old_deviceInfo!=null&&new_deviceInfo!=null){

             if(!old_deviceInfo.getDeviceCode().equals(new_deviceInfo.getDeviceCode())){

                 String key = RocketMQConstants.REAL_TIME_INDICATOR + GROUP_NAME_HYPHEN + old_deviceInfo.getDeviceCode();
                 rQProducerConsumerManager.removeConsumer(key);

                 key = RocketMQConstants.REAL_TIME_INDICATOR_FOR_HISTORY + GROUP_NAME_HYPHEN + old_deviceInfo.getDeviceCode();
                 rQProducerConsumerManager.removeConsumer(key);

                 key = RocketMQConstants.REAL_TIME_INDICATOR_FOR_STATE_CHANGE + GROUP_NAME_HYPHEN + old_deviceInfo.getDeviceCode();
                 rQProducerConsumerManager.removeConsumer(key);

                 codeChange = true;
             }

        }
        boolean result = deviceInfoService.updateById(deviceInfo);


        // 如果字段的值为null，则设置为空
        if (deviceInfo.getOfflineDecisionThreshold() == null) {
            UpdateWrapper<DeviceInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("deviceId", deviceInfo.getDeviceId());
            updateWrapper.set("offlineDecisionThreshold", null);
            deviceInfoService.update(updateWrapper);
        }

        deviceInfoService.updateCache();

        deviceInfo = deviceInfoService.getById(deviceInfo.getDeviceId());

        if(codeChange && !StringUtils.isEmpty(deviceInfo.getCollectServerCode())){
            rQProducerConsumerManager.createRealRimeIndicatorConsumer(deviceInfo);
            rQProducerConsumerManager.createRealRimeIndicatorConsumerForHistory(deviceInfo);
            rQProducerConsumerManager.createRealRimeIndicatorConsumerForStateChange(deviceInfo);
        }
        return returnMap(result,deviceInfo.getDeviceId());
    }

    /**
     * 批量删除设备
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除设备" , notes = "删除设备")
    public Map<String,Object> removeDeviceInfoByIds(String ids) {

        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.in("deviceId", Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids)));

        List<DeviceInfo> deviceInfos = deviceInfoService.list(queryWrapper);

        for (DeviceInfo deviceInfo : deviceInfos) {

            String key = RocketMQConstants.REAL_TIME_INDICATOR + GROUP_NAME_HYPHEN + deviceInfo.getDeviceCode();
            rQProducerConsumerManager.removeConsumer(key);

            key = RocketMQConstants.REAL_TIME_INDICATOR_FOR_HISTORY + GROUP_NAME_HYPHEN + deviceInfo.getDeviceCode();
            rQProducerConsumerManager.removeConsumer(key);

            key = RocketMQConstants.REAL_TIME_INDICATOR_FOR_STATE_CHANGE + GROUP_NAME_HYPHEN + deviceInfo.getDeviceCode();
            rQProducerConsumerManager.removeConsumer(key);

        }
        deviceInfoService.updateCache();
        return returnMap(deviceInfoService.update(getSoftDeleteUpdateWrapperByIds("deviceId", ids, 1)));
    }

    /**
     * 根据serviceCode重启服务
     *
     * @param serviceControlSet 根据serviceCode重启服务
     * @return 结果
     */
    @PostMapping("/serviceControlByMode")
    @ApiOperation(value = "根据serviceCode重启服务", notes = "根据serviceCode重启服务")
    public Map<String, Object> serviceControlByMode(@RequestBody ServiceControlSet serviceControlSet) {
        return deviceInfoService.restartServiceByCode(serviceControlSet);
    }
}
