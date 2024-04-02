package com.figure.op.videoplay.runner;

import com.alibaba.fastjson.JSONObject;
import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.cameramanager.domain.ThermalImageInfo;
import com.figure.op.cameramanager.service.ThermalImageInfoService;
import com.figure.op.videoplay.domain.SysJob;
import com.figure.op.videoplay.mapper.RealMonitorMapper;
import com.figure.op.videoplay.service.RealViewService;
import com.figure.op.videoplay.utils.CronTaskRegistrar;
import com.figure.op.videoplay.utils.PeriodicTaskRegister;
import com.figure.op.videoplay.utils.SchedulingRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author sdt
 * @version 1.0
 * @description:TODO
 * @date 2023/9/13 12:00
 */
@Component
public class AutoSaveThermometryRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AutoSaveThermometryRunner.class);


    @Autowired
    private PeriodicTaskRegister periodicTaskRegister;

    @Resource
    RealViewService realViewService;


    @Autowired
    private ThermalImageInfoService thermalImageInfoService;

    @Override
    public void run(String... args) {

        //查摄像头
        List<CameraInfo> cameraList = realViewService.getCameraList();

        for (CameraInfo cameraInfo : cameraList) {

            SysJob job = new SysJob();

            Integer timeSpace = thermalImageInfoService.getCornByCarameId(cameraInfo.getCameraId());
            if (timeSpace==null){
                continue;
            }
            job.setJobId(cameraInfo.getCameraId());
            job.setBeanName("thermometryMonitorTask");
            job.setMethodName("run");
            job.setMethodParams(JSONObject.toJSON(cameraInfo).toString());
            SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
            periodicTaskRegister.addCronTask(task, timeSpace, TimeUnit.SECONDS);
        }
    }
}
