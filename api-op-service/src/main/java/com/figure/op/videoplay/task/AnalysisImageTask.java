package com.figure.op.videoplay.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.cameramanager.domain.Pair;
import com.figure.op.cameramanager.domain.ThermalImageInfo;
import com.figure.op.cameramanager.mapper.CameraInfoMapper;
import com.figure.op.cameramanager.mapper.ThermalImageInfoMapper;
import com.figure.op.cameramanager.netsdk.HCNetBase;
import com.figure.op.common.exception.GlobalException;
import com.figure.op.videoplay.domain.AnalysisImageInfo;
import com.figure.op.videoplay.mapper.AnalysisImageMapper;
import com.figure.op.videoplay.other.CommandManager;
import com.figure.op.videoplay.other.CommandManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sdt
 * @version 1.0
 * @description:分析画面保存目录
 * @date 2023/9/14 10:28
 */
@Component("analysisImageTask")
@Slf4j
public class AnalysisImageTask {

    public static CommandManager manager = new CommandManagerImpl(10);

    @Resource
    CameraInfoMapper cameraInfoMapper;

    @Resource
    private ThermalImageInfoMapper thermalImageInfoMapper;

    @Resource
    private AnalysisImageMapper analysisImageMapper;

    private ConcurrentHashMap<Integer, Date> map = new ConcurrentHashMap<Integer, Date>();

    @Resource
    private HCNetBase hcNetBase;

    /**
     * @Description:分析画面保存目录核心算法
     * @Author:李强
     * @Date:2023/9/27
     */
    public void run(String cameraId) {

        CameraInfo bo = cameraInfoMapper.selectOne(new QueryWrapper<CameraInfo>().eq("cameraId", Integer.parseInt(cameraId)));

        //初始化sdk
        try {
            boolean flag = hcNetBase.init();
        } catch (Exception e) {
            throw new GlobalException("摄像机初始化错误");
        }

        //登录摄像头
        Pair<Integer, Integer> pair = hcNetBase.login_V40(bo.getIp(), bo.getPort().shortValue(), bo.getUsername(), bo.getPassword());
        if (pair == null) {
            throw new GlobalException("摄像头登录失败！！");
        }

        //当前的登录摄像头的userid
        Integer userId = pair.getKey1();

        ThermalImageInfo thermalImageInfo = thermalImageInfoMapper.selectOne(new QueryWrapper<ThermalImageInfo>().eq("cameraId", bo.getCameraId()));
        /*设备抓图*/
        try {
            //edit by lizhijie
//            //添加当前日期，存入当前日期分类
//            Date date = new Date();
//            String day = sdf.format(date);
            //2023-11-14 lq修改
            String imageName = bo.getUsername();
            hcNetBase.cronCaptureJPEGPicture(userId, 1, (short) 9, bo.getCameraId(), thermalImageInfo.getAnalysisImagePath()+File.separator,imageName);
        }catch (GlobalException e) {
            throw new GlobalException("抓图失败！！");
        }
//        AnalysisImageInfo analysisImageInfo = new AnalysisImageInfo();
//        analysisImageInfo.setCameraId(bo.getCameraId());
//        analysisImageInfo.setUrl(imageSrc);
//        Boolean flag = analysisImageMapper.insert(analysisImageInfo) > 0;
//        if (flag) {
//            log.info("插入成功");
//        }
        /** 登出 **/
        //注销摄像头用户
        hcNetBase.logout(userId);
    }
}
