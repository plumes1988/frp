package com.figure.op.videoplay.service.impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.op.cameramanager.domain.CameraAreaInfo;
import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.cameramanager.domain.Pair;
import com.figure.op.cameramanager.domain.bo.CameraInfoBo;
import com.figure.op.cameramanager.domain.vo.CameraInfoVo;
import com.figure.op.cameramanager.mapper.CameraAreMapper;
import com.figure.op.cameramanager.mapper.CameraInfoMapper;
import com.figure.op.cameramanager.netsdk.HCNetBase;
import com.figure.op.cameramanager.netsdk.HCNetSDK;
import com.figure.op.common.domain.R;
import com.figure.op.common.exception.GlobalException;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.videoplay.controller.RealViewController;
import com.figure.op.videoplay.domain.vo.RealPartThermometry;
import com.figure.op.videoplay.other.CommandManager;
import com.figure.op.videoplay.other.CommandManagerImpl;
import com.figure.op.videoplay.other.data.CommandTasker;
import com.figure.op.videoplay.service.RealViewService;
import com.sun.jna.ptr.IntByReference;
import com.zaxxer.hikari.util.ConcurrentBag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author lizhijie
 * @version 1.0
 * @date 2023/9/12 11:01
 */
@Service
public class RealViewServiceImpl implements RealViewService {

    public static CommandManager manager = new CommandManagerImpl(5);

    @Value("${nginx.rtmp.url}")
    String rtmpTempUrl;

    @Value("${nginx.http_flv.url}")
    String httpFlv;

    @Resource
    private HCNetBase hcNetBase;


    @Resource
    private CameraAreMapper cameraAreMapper;

    @Resource
    CameraInfoMapper cameraInfoMapper;

    @Override
    public List<CameraInfo> getCameraList() {
        List<CameraInfo> cameraInfos = cameraInfoMapper.selectList(new QueryWrapper<>());
        return cameraInfos;
    }

    @Override
    public CameraInfo getCameraInfoById(Integer id) {
        QueryWrapper<CameraInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("cameraId", id);
        CameraInfo cameraInfo = cameraInfoMapper.selectOne(wrapper);
        return cameraInfo;
    }

    @Override
    public String getRealPlayUrl(CameraInfoBo bo) {
        String channelName = bo.getCameraId().toString();
        //登录一下，如果摄像头不在线就删除manager中的信息，并返回错误信息给前端
        try {
            boolean flag = hcNetBase.init();
        } catch (Exception e) {
            return "摄像头初始化错误";
        }
        //登录设备
        Pair<Integer, Integer> pair = hcNetBase.login_V40(bo.getIp(), bo.getPort().shortValue(), bo.getUsername(), bo.getPassword());
        if (pair == null) {
            manager.stop(channelName);
            throw new GlobalException("摄像头登录失败！！");
        }
        //注销摄像头释放资源
        hcNetBase.logout(pair.getKey1());
        //通过id查询这个任务
        CommandTasker info = manager.query(channelName);
        String rtmpUrl = rtmpTempUrl + channelName;
        String rtspUrl = "rtsp://" + bo.getUsername() + ":" + bo.getPassword() + "@" + bo.getIp() + ":554" + "/" + "/Streaming/Channels/" + bo.getChannelNo();
//       如果任务没存在，开启视频流
        if (Objects.isNull(info)) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new GlobalException("摄像头延时设置失败！！");
            }
            String command = "ffmpeg -loglevel quiet  -i " + rtspUrl + " -f flv -vcodec copy -acodec copy " + rtmpUrl;
            //执行原生ffmpeg命令（不包含ffmpeg的执行路径，该路径会从配置文件中自动读取）
            String start = manager.start(channelName, command);
        }
        String httpFlvUrl = httpFlv + channelName;
        return httpFlvUrl;
    }

    @Override
    public R stopStream(String cameraId) {
        CommandTasker info = manager.query(cameraId);

        if (!Objects.isNull(info)) {
            try {
                manager.stop(cameraId);
            } catch (Exception e) {
                return R.fail(e.getMessage());
            }
            return R.ok("流已暂停");
        }
        return R.ok("流不存在");
    }

    @Override
    public List<RealPartThermometry> getRealThermometry(CameraInfoBo bo) {
        try {
            boolean flag = hcNetBase.init();
        } catch (Exception e) {
            System.out.println("摄像机初始化错误");
        }

        QueryWrapper<CameraAreaInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("cameraId", bo.getCameraId());
        List<CameraAreaInfo> cameraAreInfos = cameraAreMapper.selectList(wrapper);
        if (cameraAreInfos.size() < 1) {
            return null;
        }

        //返回数组
        List<RealPartThermometry> resultList = new ArrayList<>();

        //登录设备
        Pair<Integer, Integer> pair = hcNetBase.login_V40(bo.getIp(), bo.getPort().shortValue(), bo.getUsername(), bo.getPassword());
        if (pair == null) {
            throw new GlobalException("摄像头登录失败！！");
        }

        Integer userId = pair.getKey1();
        try {
            boolean flag = hcNetBase.init();
        } catch (Exception e) {
            System.out.println("摄像机初始化错误");
        }
        for (int i = 0; i < cameraAreInfos.size(); i++) {
            CameraAreaInfo areaInfo = cameraAreInfos.get(i);
            HCNetSDK.NET_DVR_THERMOMETRYRULE_TEMPERATURE_INFO m_TherTemInfo = new HCNetSDK.NET_DVR_THERMOMETRYRULE_TEMPERATURE_INFO();
            IntByReference lpBytesReturned = new IntByReference(0);
            if (!HCNetBase.hCNetSDK.NET_DVR_GetDVRConfig(userId, HCNetSDK.NET_DVR_GET_THERMOMETRYRULE_TEMPERATURE_INFO, areaInfo.getRuleAreaId(), m_TherTemInfo.getPointer(), 16 * 1024, lpBytesReturned)) {
                System.out.println("手动获取测温规则温度信息失败，错误号：" + HCNetBase.hCNetSDK.NET_DVR_GetLastError());
            } else {
                m_TherTemInfo.read();
                RealPartThermometry thermometry = new RealPartThermometry();
                float temperature = m_TherTemInfo.fMaxTemperature;
                thermometry.setRuleId(areaInfo.getRuleAreaId()).setPartName(areaInfo.getPartName()).setThermometry(temperature);
                if (temperature > areaInfo.getAlarmThreshold()) {
                    thermometry.setExceedFlag(true);
                }
                resultList.add(thermometry);
            }
        }
        //注销用户
        if (hcNetBase.logout(userId)) {
            System.out.println("注销成功");
        }
        //注销设备
        return resultList;
    }

    @Override
    public R getRealExceedCamera() {
        QueryWrapper<CameraInfo> wrapper = new QueryWrapper<>();
        List<CameraInfo> cameraInfos = cameraInfoMapper.selectList(wrapper);
        if (cameraInfos.size() < 0) {
            return R.fail("无热感相机在线");
        }
        final ArrayList<String> result = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(cameraInfos.size());

        for (CameraInfo cameraInfo : cameraInfos) {
            executorService.submit(() -> {
                CameraInfoBo bo = BeanCopyUtils.copy(cameraInfo, CameraInfoBo.class);
                List<RealPartThermometry> realThermometry = getRealThermometry(bo);
                if (realThermometry.size() < 0) {
                    return;
                }
                for (RealPartThermometry realPartThermometry : realThermometry) {
                    if (realPartThermometry.getExceedFlag() == null) {
                        continue;
                    }
                    result.add(bo.getCameraName());
                    break;
                }
            });

        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            //waiting...
        }
        if (result.size() > 0) {
            List<String> collect = result.stream().sorted(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            }).collect(Collectors.toList());
            String msg = "";
            for (String s : collect) {
                msg += s + "、";
            }
            return R.ok(msg.substring(0, msg.length() - 1) + "存在部件温度异常，请及时查看。");
        }
        return R.fail("无报警摄像机");
    }
}
