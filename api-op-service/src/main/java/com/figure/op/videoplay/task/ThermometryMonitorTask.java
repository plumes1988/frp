package com.figure.op.videoplay.task;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.figure.op.cameramanager.domain.CameraAreaInfo;
import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.cameramanager.domain.Pair;
import com.figure.op.cameramanager.mapper.CameraAreMapper;
import com.figure.op.cameramanager.netsdk.HCNetBase;
import com.figure.op.cameramanager.netsdk.HCNetSDK;
import com.figure.op.common.exception.GlobalException;
import com.figure.op.videoplay.domain.RealMonitor;
import com.figure.op.videoplay.domain.WarningRecord;
import com.figure.op.videoplay.mapper.RealMonitorMapper;
import com.figure.op.videoplay.mapper.WarningRecordMapper;
import com.figure.op.videoplay.service.RealViewService;
import com.figure.op.videoplay.utils.CronTaskRegistrar;
import com.figure.op.videoplay.utils.ScheduledTask;
import com.sun.jna.ptr.IntByReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sdt
 * @version 1.0
 * @description:TODO
 * @date 2023/9/14 10:28
 */
@Component("thermometryMonitorTask")
public class ThermometryMonitorTask {

    @Resource
    private HCNetBase hcNetBase;

    @Resource
    private CameraAreMapper cameraAreMapper;

    @Resource
    private RealMonitorMapper realMonitorMapper;

    @Resource
    private WarningRecordMapper warningRecordMapper;

    private ConcurrentHashMap<Integer, Date> map = new ConcurrentHashMap<Integer, Date>();

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    /**
     * @Description:摄像头温度监测核心算法
     * @Author:李志杰
     * @Date:2023/9/14
     */
    public void run(String parms) {


        //设置固定时间
        Calendar calendar=Calendar.getInstance();
        calendar.set(1971,00,01,00,00,00);
        Date calendarTime = calendar.getTime();

//        System.out.println(parms);
        //参数转对象
        CameraInfo cameraInfo = JSON.parseObject(parms, CameraInfo.class);
        //初始化sdk
        try {
            boolean flag = hcNetBase.init();
        } catch (Exception e) {
            throw new GlobalException("摄像机初始化错误");
        }


        //登录摄像头
        Pair<Integer, Integer> pair = hcNetBase.login_V40(cameraInfo.getIp(), cameraInfo.getPort().shortValue(), cameraInfo.getUsername(), cameraInfo.getPassword());
        if (pair == null) {
            throw new GlobalException("摄像头登录失败！！");
        }


        Map<Runnable, ScheduledTask> scheduledTasks = cronTaskRegistrar.getScheduledTasks();
        System.out.println(scheduledTasks);
        //当前的登录摄像头的userid
        Integer userId = pair.getKey1();

        //根据像头查对应的部件信息
        QueryWrapper<CameraAreaInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("cameraId", cameraInfo.getCameraId());
        List<CameraAreaInfo> cameraAreInfos = cameraAreMapper.selectList(wrapper);

        if (cameraAreInfos == null || cameraAreInfos.size() < 1) {
            //注销摄像头用户
            hcNetBase.logout(userId);
            return;
        }
        for (int j = 0; j < cameraAreInfos.size(); j++) {
            //当前部件信息
            CameraAreaInfo areaInfo = cameraAreInfos.get(j);

            HCNetSDK.NET_DVR_THERMOMETRYRULE_TEMPERATURE_INFO m_TherTemInfo = new HCNetSDK.NET_DVR_THERMOMETRYRULE_TEMPERATURE_INFO();
            IntByReference lpBytesReturned = new IntByReference(0);
            if (!HCNetBase.hCNetSDK.NET_DVR_GetDVRConfig(userId, HCNetSDK.NET_DVR_GET_THERMOMETRYRULE_TEMPERATURE_INFO, areaInfo.getRuleAreaId(), m_TherTemInfo.getPointer(), 16 * 1024, lpBytesReturned)) {
                System.out.println("手动获取测温规则温度信息失败，错误号：" + HCNetBase.hCNetSDK.NET_DVR_GetLastError());
            } else {
                m_TherTemInfo.read();
                //将温度值计入温度值列表
                RealMonitor realMonitor = new RealMonitor();
                realMonitor.setCameraId(cameraInfo.getCameraId());
                realMonitor.setRuleAreaId(areaInfo.getRuleAreaId());
                realMonitor.setAreaName(areaInfo.getAreaName());
                realMonitor.setPartCode(areaInfo.getPartCode());
                realMonitor.setPartName(areaInfo.getPartName());
                realMonitor.setTemperature(m_TherTemInfo.fMaxTemperature);
                realMonitor.setInsertTime(new Date());
                realMonitorMapper.insert(realMonitor);
                //如果温度过高，计入报警列表
                if (m_TherTemInfo.fMaxTemperature > areaInfo.getAlarmThreshold()) {
                    //作为查询标志
                    //如果map里面没数据，则记录这次的数据
                    if (map.get(areaInfo.getCameraAreaId()) == null) {
                        Date date = new Date();
                        map.put(areaInfo.getCameraAreaId(), date);
                        //将当前的数据写入库
                        WarningRecord warningRecord = new WarningRecord();
                        warningRecord.setCameraId(cameraInfo.getCameraId());
                        warningRecord.setCameraName(cameraInfo.getCameraName());
                        warningRecord.setRuleAreaId(areaInfo.getRuleAreaId());
                        warningRecord.setAreaName(areaInfo.getAreaName());
                        warningRecord.setDeviceName(areaInfo.getDeviceName());
                        warningRecord.setPartCode(areaInfo.getPartCode());
                        warningRecord.setPartName(areaInfo.getPartName());
                        warningRecord.setStartTime(date);
                        warningRecord.setEndTime(calendarTime);
                        warningRecordMapper.insert(warningRecord);
                    }
                }
                //如果温度小于阈值，且map中有数据，就更新map和endTime
                else {
                    if (map.get(areaInfo.getCameraAreaId()) != null) {

                        Date date = new Date();
                        WarningRecord warningRecord = new WarningRecord();
                        warningRecord.setEndTime(date);

                        //设置固定时间

                        calendar.set(1972,00,01,00,00,00);
                        Date calendarTime1 = calendar.getTime();

                        LambdaUpdateWrapper<WarningRecord> updateWrapper = Wrappers.<WarningRecord>lambdaUpdate()
                                .lt(WarningRecord::getEndTime, calendarTime1).
                                eq(WarningRecord::getPartCode,areaInfo.getPartCode())
                                .eq(WarningRecord::getCameraId,areaInfo.getCameraId());
                        warningRecordMapper.update(warningRecord,updateWrapper);
                        //删除map中数据
                        map.remove(areaInfo.getCameraAreaId());
                    }
                }

            }
        }
        //注销摄像头用户
        hcNetBase.logout(userId);
    }
}
