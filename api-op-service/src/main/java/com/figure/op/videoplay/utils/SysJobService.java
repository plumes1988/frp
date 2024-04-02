package com.figure.op.videoplay.utils;

import com.alibaba.fastjson.JSONObject;
import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.cameramanager.domain.ThermalImageInfo;
import com.figure.op.cameramanager.domain.bo.ThermalImageInfoBo;
import com.figure.op.cameramanager.mapper.CameraInfoMapper;
import com.figure.op.cameramanager.service.ThermalImageInfoService;
import com.figure.op.common.exception.GlobalException;
import com.figure.op.videoplay.domain.SysJob;
import com.figure.op.videoplay.service.RealViewService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author sdt
 * @version 1.0
 * @description:TODO
 * @date 2023/9/14 10:05
 */
@Service
@Slf4j
public class SysJobService {
    @Autowired
    private PeriodicTaskRegister periodicTaskRegister;


    @Resource
    RealViewService realViewService;

    @Resource
    CameraInfoMapper cameraInfoMapper;

    @Autowired
    private ThermalImageInfoService thermalImageInfoService;

    /**
     * corn变化时修改温度监测定时任务
     */

    public void editSysJobByCorn(ThermalImageInfo thermalImageInfo) {
        SysJob job = new SysJob();
        job.setJobId(thermalImageInfo.getCameraId());
        job.setBeanName("thermometryMonitorTask");
        job.setMethodName("run");
        //根据id获取摄像头信息
        CameraInfo cameraInfo = realViewService.getCameraInfoById(thermalImageInfo.getCameraId());
        if (cameraInfo == null) {
            return;
        }
        job.setMethodParams(JSONObject.toJSON(cameraInfo).toString());
        //先移除
        SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
        periodicTaskRegister.removeCronTask(task);
        System.out.println("任务删除完毕");
        periodicTaskRegister.addCronTask(task, thermalImageInfo.getMonitorCronTime(), TimeUnit.SECONDS);

        System.out.println("定时任务编辑完毕");
    }


    /**
     * 删除温度监测定时任务
     */
    public void deleteThermometryMonitorJob(CameraInfo cameraInfo) {

        SysJob job = new SysJob();
        job.setJobId(cameraInfo.getCameraId());
        job.setBeanName("thermometryMonitorTask");
        job.setMethodName("run");
        job.setMethodParams(JSONObject.toJSON(cameraInfo).toString());
        //先移除
        SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
        periodicTaskRegister.removeCronTask(task);
        System.out.println("任务删除完毕");
    }

    /**
     * 增加温度监测定时任务
     */
    public void addThermometryMonitorJob(CameraInfo cameraInfo) {

        SysJob job = new SysJob();
        job.setJobId(cameraInfo.getCameraId());
        job.setBeanName("thermometryMonitorTask");
        job.setMethodName("run");
        job.setMethodParams(JSONObject.toJSON(cameraInfo).toString());
        SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());

        //查询corn
        Integer timeSpace = thermalImageInfoService.getCornByCarameId(cameraInfo.getCameraId());
        periodicTaskRegister.addCronTask(task, timeSpace, TimeUnit.SECONDS);
        System.out.println("任务增加完毕");


    }


    public Boolean addAnalysisImageSysJobByThermalInfo(ThermalImageInfo thermalImageInfo) {
        SysJob job = new SysJob();
        job.setJobId(thermalImageInfo.getCameraId());
        job.setBeanName("analysisImageTask");
        job.setMethodName("run");
        job.setMethodParams(thermalImageInfo.getCameraId().toString());
        SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
        try {
            periodicTaskRegister.addCronTask(task, thermalImageInfo.getAnalysisCronTime(), TimeUnit.SECONDS);
        } catch (Exception e) {
            new GlobalException("添加图像监测任务失败");
            return false;
        }
        return true;
    }

    public Boolean editAnalysisImageSysJobByThermalInfo(ThermalImageInfo thermalImageInfo) {
        SysJob job = new SysJob();
        job.setJobId(thermalImageInfo.getCameraId());
        job.setBeanName("analysisImageTask");
        job.setMethodName("run");
        job.setMethodParams(thermalImageInfo.getCameraId().toString());
        SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
        //先移除
        periodicTaskRegister.removeCronTask(task);
        System.out.println("任务删除完毕");
        periodicTaskRegister.addCronTask(task, thermalImageInfo.getAnalysisCronTime(), TimeUnit.SECONDS);
        System.out.println("定时任务编辑完毕");
        return true;
    }

    public void editSysJobByCameraId(Integer cameraId) {
        SysJob job = new SysJob();
        job.setJobId(cameraId);
        job.setBeanName("analysisImageTask");
        job.setMethodName("run");
        job.setMethodParams(cameraId.toString());

        //先移除
        SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
        periodicTaskRegister.removeCronTask(task);
        System.out.println("任务删除完毕");
        //查询corn
        Integer corn = thermalImageInfoService.getAnasysCornByCarameId(cameraId);
        periodicTaskRegister.addCronTask(task, corn, TimeUnit.SECONDS);
        System.out.println("定时任务编辑完毕");
    }


    public Boolean stopAnalysisImageSysJobByThermalInfo(ThermalImageInfoBo bo) {
        //查询摄像头列表
        SysJob job = new SysJob();
        job.setJobId(bo.getCameraId());
        job.setBeanName("analysisImageTask");
        job.setMethodName("run");
        job.setMethodParams(bo.getCameraId().toString());
        //先移除
        SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
        try {
            periodicTaskRegister.removeCronTask(task);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Boolean stopAnalysisImageSysJobByThermal(ThermalImageInfo info) {
        SysJob job = new SysJob();
        job.setJobId(info.getCameraId());
        job.setBeanName("analysisImageTask");
        job.setMethodName("run");
        job.setMethodParams(info.getCameraId().toString());
        //先移除
        SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
        try {
            periodicTaskRegister.removeCronTask(task);
        } catch (Exception e) {
            new GlobalException("停止监测任务异常");
            return false;
        }
        return true;
    }


}
