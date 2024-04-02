package com.figure.system.scheduled;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.service.device.IDeviceHistoryIndicatorService;
import com.figure.core.service.device.IDeviceIndicatorParamRelService;
import com.figure.core.service.device.IDeviceIndicatorParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
//@Component
//@EnableScheduling
public class DeviceHistoryIndicatorClearScheduler {

    @Autowired
    IDeviceIndicatorParamService deviceIndicatorParamService;

    @Resource
    private IDeviceIndicatorParamRelService deviceIndicatorParamRelService;

    @Resource
    private IDeviceHistoryIndicatorService deviceHistoryIndicatorService;

    @Scheduled(fixedRate = 1 * 1000 * 60 * 10, initialDelay = 20 * 1000)//间隔1秒
    private void clear() {
        log.info("开始执行历史指标清理任务......");
        QueryWrapper queryWrapper = new QueryWrapper();
        List<DeviceIndicatorParamRel> deviceIndicatorParamRels = deviceIndicatorParamRelService.list(queryWrapper);

        for(DeviceIndicatorParamRel deviceIndicatorParamRel:deviceIndicatorParamRels){
           Integer retentionTime = deviceIndicatorParamRel.getRetentionTime();
           if(retentionTime==null){
             continue;
           }
           Date time = new Date(new Date().getTime()-1000*60*60*24*retentionTime);
           deviceHistoryIndicatorService.deleteByCondition(deviceIndicatorParamRel.getDeviceCode(),time,deviceIndicatorParamRel.getIndicatorCode());
        }
        log.info("执行历史指标清理任务完成......");
    }
}
