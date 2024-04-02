package com.figure.system.controller.device;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.base.BaseController;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.log.LogDeviceControl;
import com.figure.core.service.device.IDeviceIndicatorParamRelService;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.log.ILogDeviceControlService;
import com.figure.core.service.notice.INoticeMessageService;
import com.figure.core.sse.Constants;
import com.figure.core.sse.SseEmitterManager;
import com.figure.system.mq.RocketMqDeviceIndicatorParamRel;
import com.figure.core.rocketmq.message.DeviceIndexSet;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.figure.core.constant.Constants.*;
import static com.figure.core.model.device.DeviceIndicatorParamRel.ENABLESETTING_YES;
import static com.figure.core.model.log.LogDeviceControl.*;
import static com.figure.core.model.notice.NoticeMessage.DEVICE_CONTROL;
import static com.figure.core.sse.Constants.LOG_DEVICE_CONTROL;


@Component
@RestController
@Api("发送测试")
@Slf4j
@RequestMapping("/indicator")
public class DeviceIndicatorParamSetController extends BaseController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private IDeviceInfoService  deviceInfoService;

    @Autowired
    private ILogDeviceControlService logDeviceControlService;

    @Autowired
    private INoticeMessageService noticeMessageService;

    @Resource
    private IDeviceIndicatorParamRelService deviceIndicatorParamRelService;


    @PostMapping("/producerIndicatorSet")
    public Map<String, Object> producerIndicatorMessage(@RequestBody DeviceIndexSet deviceIndexSet) {

        String indicatorCode =  deviceIndexSet.getIndexCode();
        String deviceCode =  deviceIndexSet.getDeviceCode();

        LogDeviceControl logDeviceControl = new LogDeviceControl();
        String requestId = this.getRequestId();

        logDeviceControl.setRequestId(requestId);
        logDeviceControl.setRequestTime(new Date());
        logDeviceControl.setIndicatorCode(indicatorCode);
        logDeviceControl.setIndicatorValue(deviceIndexSet.getSetData());
        logDeviceControl.setOperatorUserId(deviceIndexSet.getUserId());
        logDeviceControl.setDeviceCode(deviceCode);

        String msg = "发送成功";

        boolean result = true;

        if(deviceIndicatorParamRelService.indicatorSetCheckSetValueIsSameWithCurValue(deviceIndexSet)){
            msg = SAME_VALUE_NOT_NEED_TO_SET;
            logDeviceControl.setRequestStatus(PARAMETER_EXCEPTION);
            result = false;
        }

        if(deviceIndicatorParamRelService.dataOutOfBounds(deviceIndexSet)){
            msg = DATA_OUT_OF_BOUNDS;
            logDeviceControl.setRequestStatus(PARAMETER_EXCEPTION);
            result = false;
        }

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("deviceCode" , deviceIndexSet.getDeviceCode());
        wrapper.eq("isDelete" , com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<DeviceInfo> list = deviceInfoService.list(wrapper);

        if (list.size()>0){
            DeviceInfo info = list.get(0);
            logDeviceControl.setFrontId(info.getMonitorStationId());
        }

        if(result){
            if (list.size()>0){
                QueryWrapper<DeviceIndicatorParamRel> queryWrapper = new QueryWrapper();
                queryWrapper.eq("indicatorCode",indicatorCode);
                queryWrapper.eq("deviceCode",deviceCode);

                Integer count = deviceIndicatorParamRelService.count(queryWrapper);

                if(count!=0){
                    queryWrapper.eq("enableSetting",ENABLESETTING_YES);
                    count = deviceIndicatorParamRelService.count(queryWrapper);
                    if(count!=0){
                        DeviceInfo info = list.get(0);
                        logDeviceControl.setServerCode(info.getCollectServerCode());
                        deviceIndexSet.setRequestId(requestId);
                        deviceIndexSet.setDeviceId(info.getDeviceId());
                        deviceIndexSet.setServerCode(info.getCollectServerCode());
                        boolean flag = sendMessage(deviceIndexSet,3);
                        if(flag){
                            logDeviceControl.setRequestStatus(SETTING);
                        }else{
                            logDeviceControl.setRequestStatus(EXCEPTION);
                            logDeviceControl.setResponseTime(new Date());
                            msg = ROCKETMQ_SEND_ERROR;
                            result = false;
                        }
                    }else{
                        msg = DEVICE_ENABLESETTING_NO;
                        logDeviceControl.setRequestStatus(PARAMETER_EXCEPTION);
                        result = false;
                    }
                }else{
                    msg = HAS_NO_DEVICE_INDICATOR_REL;
                    logDeviceControl.setRequestStatus(PARAMETER_EXCEPTION);
                    result = false;
                }
            }else{
                msg = DEVICE_NOT_FOUND;
                logDeviceControl.setRequestStatus(PARAMETER_EXCEPTION);
                result = false;
            }
        }
        logDeviceControl.setResponseContent(msg);
        logDeviceControlService.save(logDeviceControl);
        logDeviceControl.setTopic(Constants.LOG_DEVICE_CONTROL);
        noticeMessageService.processNoticeMessage(DEVICE_CONTROL,logDeviceControl);
        // 暂时不用发送值web，只发送执行结果
        // sendToWeb(logDeviceControl);
        return returnMap(result,msg,logDeviceControl);
    }

    private boolean sendMessage(DeviceIndexSet deviceIndexSet,Integer retryTimes){
        for (int i = 0; i < retryTimes; i++) {
            try {
                rocketMQTemplate.convertAndSend(RocketMqDeviceIndicatorParamRel.indicator_TOPIC, deviceIndexSet);
                return true;
            } catch (Exception e) {
                log.error("发送消息失败，重试次数：{}", i + 1, e);
                // 这里可以根据实际情况进行重试策略，例如增加等待时间、更换消息队列等
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return false;
    }

    private  String getRequestId() {
        UUID uuid = UUID.randomUUID();
        long timestamp = System.currentTimeMillis();
        String id = uuid.toString() + "-" + timestamp;
        return id;
    }

    private void sendToWeb(LogDeviceControl logDeviceControl) {
        logDeviceControl.setTopic(LOG_DEVICE_CONTROL);
        SseEmitterManager.sendMessageToAllMatchSseEmitter(logDeviceControl);
    }

}




