package com.figure.system.controller.device;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.figure.core.service.device.*;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.sse.SseEmitterManager;
import com.figure.core.util.BusinessUtils;
import com.figure.core.util.DeviceAlarmMessageExporter;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static com.figure.core.constant.ConstantsForSysPara.NUMBER_OF_RECENT_ALARMS;
import static com.figure.core.model.device.DeviceAlarmMessage.*;
import static com.figure.core.service.device.impl.DeviceAlarmMessageServiceCacheImpl.*;
import static com.figure.core.sse.Constants.DEVICE_ALARM;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-07-01
 */
@RestController
@RequestMapping("/deviceAlarmMessage")
public class DeviceAlarmMessageController extends BaseController {

    @Autowired
    ISysParaService sysParaService;

    @Autowired
    IDeviceIndicatorParamService deviceIndicatorParamService;

    @Resource
    private IDeviceAlarmMessageService deviceAlarmMessageService;

    @Resource
    private IDeviceIndicatorParamRelService deviceIndicatorParamRelService;

    @Autowired
    private IDeviceInfoService deviceInfoService;

    @Resource
    private IDeviceAlarmMessageCacheService deviceAlarmMessageCacheService;

    /**
     * 根据id获取指标报警信息
     *
     * @param id 指标报警信息ID
     * @return 指标报警信息
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取指标报警信息" , notes = "根据id获取指标报警信息")
    public DeviceAlarmMessage selectDeviceAlarmMessageById(@PathVariable("id") Long id) {
        return deviceAlarmMessageService.getById(id);
    }

    /**
     * 查询指标报警信息列表
     *
     * @param request
     * @return 指标报警信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询指标报警信息列表" , notes = "查询指标报警信息列表")
    public Object selectDeviceAlarmMessageList(HttpServletRequest request) throws Exception {

        PageWrapper<DeviceAlarmMessage> pageWrapper =  new PageHelper(DeviceAlarmMessage.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        queryWrapper.orderByDesc("happenTime");

        IPage<DeviceAlarmMessage> pages = deviceAlarmMessageService.page(pageWrapper.getPage(),queryWrapper);

        deviceAlarmMessageService.fillEntityProps(pages.getRecords());

        String type = request.getParameter("type");

        if("export".equals(type)){

            String paraValue_of_uploadPath = sysParaService.getParamByName("uploadPath");

            String uploadPath = paraValue_of_uploadPath+"/export";

            return DeviceAlarmMessageExporter.export(pages.getRecords(),uploadPath);
        }

        return new TableDataInfo(pages);
    }

    /**
     * 修改保存指标报警信息
     *
     * @param deviceAlarmMessage 指标报警信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存指标报警信息" , notes = "修改保存指标报警信息")
    public Map<String,Object> updateDeviceAlarmMessageById(@RequestBody DeviceAlarmMessage deviceAlarmMessage) throws IOException, ClassNotFoundException {

        deviceAlarmMessage.setContinueTime(null);
        Long alarmId = deviceAlarmMessage.getAlarmId();
        DeviceAlarmMessage old_deviceAlarmMessage =  deviceAlarmMessageService.getById(alarmId);
        if(ENDED.equals(deviceAlarmMessage.getStatus())&&old_deviceAlarmMessage.getEndTime()==null){
            deviceAlarmMessage.setEndTime(new Date());
        }
        boolean result = deviceAlarmMessageService.updateById(deviceAlarmMessage);

        DeviceAlarmMessage new_deviceAlarmMessage =  deviceAlarmMessageService.getById(alarmId);

        if(BusinessUtils.hasChange(old_deviceAlarmMessage,new_deviceAlarmMessage)){

            //当更新报警信息时，标记为无需通知
            new_deviceAlarmMessage.setNotifyFlag(0);
            new_deviceAlarmMessage.setTopic(DEVICE_ALARM);
            Integer deviceId = deviceInfoService.getDeviceIdByDeviceCode(deviceAlarmMessage.getDeviceCode());
            new_deviceAlarmMessage.setDeviceId(deviceId);
            SseEmitterManager.sendMessageToAllMatchSseEmitter(new_deviceAlarmMessage);

            List<DeviceAlarmMessage>  alarms =new ArrayList<DeviceAlarmMessage>(){{
                add(new_deviceAlarmMessage);
            }};
            if(result){
                Integer updateAlarmType = ALARM_UPDATE_TYPE_UN_KNOW;
                if(NOT_END.equals(new_deviceAlarmMessage.getStatus())){
                    updateAlarmType = ALARM_UPDATE_TYPE_CONFIRM_STATUS;
                }
                if(NOT_END.equals(old_deviceAlarmMessage.getStatus()) && ENDED.equals(new_deviceAlarmMessage.getStatus())){
                    updateAlarmType = ALARM_UPDATE_TYPE_ENDED;
                }
                deviceAlarmMessageCacheService.updateAlarms(alarms,updateAlarmType);
                deviceIndicatorParamRelService.updateDeviceIndicatorParamRelAndSendSseWhenUpdateAlarm(alarms);
            }

        }

        return returnMap(result);
    }

    /**
     * 批量删除指标报警信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除指标报警信息" , notes = "删除指标报警信息")
    public Map<String,Object> removeDeviceAlarmMessageByIds(String ids) throws IOException, ClassNotFoundException {
        List<String> ids_ = Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("alarmId",ids_);
        List<DeviceAlarmMessage> alarms =  deviceAlarmMessageService.list(queryWrapper);
        boolean result = deviceAlarmMessageService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids)));
        if(result){
            deviceIndicatorParamRelService.send2WebForDelete(alarms);
            deviceAlarmMessageCacheService.updateAlarms(alarms,ALARM_UPDATE_TYPE_DELETE);
            deviceIndicatorParamRelService.updateDeviceIndicatorParamRelAndSendSseWhenUpdateAlarm(alarms);
        }
        return returnMap(result);
    }

    /**
     * 获取报警size条的报警，未结束优先
     *
     * @return 结果
     */
    @PostMapping("/getAlarmsWithNotEndWithHighPriority")
    @ApiOperation(value = "获取报警size条的报警，未结束优先" , notes = "获取报警size条的报警，未结束优先")
    public List<DeviceAlarmMessage> getAlarmsWithNotEndWithHighPriority(String deviceCodes) {

        List<DeviceAlarmMessage> result = new ArrayList<>();

        List<String> deviceCodes_ = Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(deviceCodes));

        String  numberOfRecentAlarms_str = sysParaService.getParamByName(NUMBER_OF_RECENT_ALARMS);
        Integer size = 30;
        if(numberOfRecentAlarms_str!=null){
            size = Integer.parseInt(numberOfRecentAlarms_str);
        }

        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.in("faultType", new Integer[]{INDICATOR_ANOMALY, COMMUNICATION_ANOMALY});

        queryWrapper.eq("status",NOT_END);

        queryWrapper.eq("status",NOT_END);

        //queryWrapper.ne("confirm",MISINTERPRET);

        queryWrapper.in("deviceCode",deviceCodes_);

        // 指定倒序排序的字段
        queryWrapper.orderByDesc("happenTime");
        // 指定限制结果集的数量
        queryWrapper.last("LIMIT "+size);

        List<DeviceAlarmMessage> alarms =  deviceAlarmMessageService.list(queryWrapper);

        result.addAll(alarms);

        if(alarms.size()<size){
            queryWrapper = new QueryWrapper();

            queryWrapper.in("faultType", new Integer[]{INDICATOR_ANOMALY, COMMUNICATION_ANOMALY});

            queryWrapper.eq("status",ENDED);

            //queryWrapper.ne("confirm",MISINTERPRET);

            queryWrapper.in("deviceCode",deviceCodes_);

            // 指定倒序排序的字段
            queryWrapper.orderByDesc("happenTime");
            // 指定限制结果集的数量
            queryWrapper.last("LIMIT "+(size-alarms.size()));

            List<DeviceAlarmMessage> alarms_ =  deviceAlarmMessageService.list(queryWrapper);

            result.addAll(alarms_);
        }
        return result;
    }


}
