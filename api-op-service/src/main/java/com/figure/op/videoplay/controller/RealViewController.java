package com.figure.op.videoplay.controller;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.op.cameramanager.domain.CameraAreaInfo;
import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.cameramanager.domain.Pair;
import com.figure.op.cameramanager.domain.bo.CameraInfoBo;
import com.figure.op.cameramanager.netsdk.HCNetBase;
import com.figure.op.cameramanager.netsdk.HCNetSDK;
import com.figure.op.cameramanager.service.CameraAreService;
import com.figure.op.common.domain.R;
import com.figure.op.common.exception.GlobalException;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.videoplay.domain.vo.RealPartThermometry;
import com.figure.op.videoplay.service.RealViewService;
import com.figure.op.videoplay.utils.SysJobService;
import com.sun.jna.Pointer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhijie
 * @version 1.0
 * @date 2023/9/12 10:48
 */
@RestController
@Api(tags = "热感摄像头实时查看画面")
@RequestMapping("/realview")
public class RealViewController {

    @Resource
    RealViewService realViewService;

    @Resource
    private CameraAreService cameraAreService;

    @Resource
    private HCNetBase hcNetBase;
    public FileOutputStream thermometryupload = null;


    @GetMapping("getCameraList")
    @ApiOperation("获取摄像头列表")
    public R getCameraList() {
        List<CameraInfo> cameraList = realViewService.getCameraList();
        return R.ok(cameraList);
    }

    @PostMapping("getRealPlayUrl")
    @ApiOperation("获取摄像头实时播放地址")
    public R getRealPlayUrl(@Validated(EditGroup.class) @RequestBody CameraInfoBo bo) {
        try {
            hcNetBase.init();
        } catch (Exception e) {
            System.out.println("摄像机初始化错误");
        }
        //登录设备
        Pair<Integer, Integer> pair = hcNetBase.login_V40(bo.getIp(), bo.getPort().shortValue(), bo.getUsername(), bo.getPassword());
        if (pair==null){
            throw new GlobalException("摄像头登录失败！！");
        }
        Integer userId = pair.getKey1();
        hcNetBase.logout(userId);
        String realPlayUrl = realViewService.getRealPlayUrl(bo);
        return R.ok(realPlayUrl);
    }

    @PostMapping("stopStream")
    @ApiOperation("关闭流任务")
    public R stopStream(String cameraId) {
        return realViewService.stopStream(cameraId);
    }

    @PostMapping("getRealThermometry")
    @ApiOperation("获取摄像头相关部件实时温度")
    public R getRealThermometry(@Validated(EditGroup.class) @RequestBody CameraInfoBo bo) {
        List<RealPartThermometry> list = realViewService.getRealThermometry(bo);
        return R.ok(list);
    }

    @GetMapping("getRealExceedCamera")
    @ApiOperation("获取实时报警摄像机")
    public R getRealExceedCamera() {
        return realViewService.getRealExceedCamera();
    }



}
