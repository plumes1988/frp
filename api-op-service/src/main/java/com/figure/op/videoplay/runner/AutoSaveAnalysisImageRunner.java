package com.figure.op.videoplay.runner;

import com.figure.op.cameramanager.domain.ThermalImageInfo;
import com.figure.op.cameramanager.service.ThermalImageInfoService;
import com.figure.op.videoplay.domain.SysJob;
import com.figure.op.videoplay.utils.CronTaskRegistrar;
import com.figure.op.videoplay.utils.PeriodicTaskRegister;
import com.figure.op.videoplay.utils.SchedulingRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author sdt
 * @version 1.0
 * @description:TODO
 * @date 2023/9/13 12:00
 */
@Component
public class AutoSaveAnalysisImageRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AutoSaveAnalysisImageRunner.class);


    @Autowired
    private PeriodicTaskRegister periodicTaskRegister;

    @Autowired
    private ThermalImageInfoService thermalImageInfoService;

    @Override
    public void run(String... args) {
        List<ThermalImageInfo> thermalImageInfos = thermalImageInfoService.queryMonitorCron();
        for (int i = 0; i < thermalImageInfos.size(); i++) {
            SysJob sysJob = new SysJob();
            sysJob.setJobId(thermalImageInfos.get(i).getCameraId());
            sysJob.setBeanName("analysisImageTask");
            sysJob.setMethodName("run");
            sysJob.setMethodParams(thermalImageInfos.get(i).getCameraId().toString());
            SchedulingRunnable task = new SchedulingRunnable(sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams());
            periodicTaskRegister.addCronTask(task, thermalImageInfos.get(i).getAnalysisCronTime(), TimeUnit.SECONDS);
            logger.info("定时任务已加载完毕...");
        }
    }
}
