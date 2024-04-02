package com.figure.op.cameramanager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.cameramanager.domain.CameraAreaInfo;
import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.cameramanager.domain.Pair;
import com.figure.op.cameramanager.domain.ThermalImageInfo;
import com.figure.op.cameramanager.domain.bo.CameraInfoBo;
import com.figure.op.cameramanager.domain.vo.CameraInfoVo;
import com.figure.op.cameramanager.mapper.CameraAreMapper;
import com.figure.op.cameramanager.mapper.CameraInfoMapper;
import com.figure.op.cameramanager.mapper.ThermalImageInfoMapper;
import com.figure.op.cameramanager.netsdk.HCNetBase;
import com.figure.op.cameramanager.service.CameraInfoService;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.exception.GlobalException;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.videoplay.domain.AnalysisImageInfo;
import com.figure.op.videoplay.service.PlayBackService;
import com.figure.op.videoplay.utils.SysJobService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.io.File;

/**
 * @Author liqiang
 * @Date 2023/9/10 8:58
 * @Version 1.5
 */
@Service
public class CameraInfoServiceImpl implements CameraInfoService {

    @Resource
    private CameraInfoMapper cameraInfoMapper;

    @Resource
    private PlayBackService playBackService;

    @Resource
    private ThermalImageInfoMapper thermalImageInfoMapper;

    @Resource
    private CameraAreMapper cameraMapper;


    @Resource
    private SysJobService sysJobService;

    @Resource
    private HCNetBase hcNetBase;

    @Value("${file.url_prefix}")
    private String url_prefix;

    @Value("${file.path}")
    private String path;


    @Override
    public R insertByBo(CameraInfoBo bo) {
        //判断设备编号是否唯一
        CameraInfo cameraCode = cameraInfoMapper.selectOne(new QueryWrapper<CameraInfo>().eq("cameraCode", bo.getCameraCode()));
        if (cameraCode != null) {
            return R.fail("摄像机设备编号不唯一，请重新输入");
        }
        cameraCode = BeanCopyUtils.copy(bo, CameraInfo.class);
        Boolean flag = cameraInfoMapper.insert(cameraCode) > 0;
        if (flag) {
            CameraInfo newCameraInfo = cameraInfoMapper.selectOne(new QueryWrapper<CameraInfo>().eq("cameraCode", bo.getCameraCode()));
            //todo 增加一条关于这个摄像头的图像分析设置
            ThermalImageInfo thermalImageInfo = new ThermalImageInfo();
            thermalImageInfo.setCameraId(newCameraInfo.getCameraId());
            thermalImageInfo.setAnalysisSwitch(1);
            thermalImageInfo.setReferenceImagePath(File.separator+"reference"+File.separator + newCameraInfo.getCameraCode());
            thermalImageInfo.setAnalysisImagePath(File.separator+"analysis" +File.separator+ newCameraInfo.getCameraCode());
            thermalImageInfo.setAnalysisImageOutPath(File.separator+"analysisout"+File.separator + newCameraInfo.getCameraCode());
            thermalImageInfo.setAnalysisCron("0 0/1 * * * ?");
            thermalImageInfo.setAnalysisCronTime(60);
            thermalImageInfo.setMonitorCron("0 0/1 * * * ?");
            thermalImageInfo.setMonitorCronTime(60);
            Boolean insertResult = thermalImageInfoMapper.insert(thermalImageInfo) > 0;
            if (!insertResult) {
                throw new GlobalException("摄像头的图像分析设置初始化失败");
            }
            //增加图像分析设置成功
            if (!sysJobService.addAnalysisImageSysJobByThermalInfo(thermalImageInfo)) {
                throw new GlobalException("分析画面保存失败");
            }
            // 开启监测温度
            try {
                sysJobService.addThermometryMonitorJob(newCameraInfo);
            } catch (GlobalException e) {
                throw new GlobalException("开启温度监测失败!");
            }

            if (!playBackService.addVideoTape(newCameraInfo)) {
                throw new GlobalException("视频录像启动失败");
            }
            return R.ok("添加成功");
        }
        return R.fail("添加失败，请重试");
    }

    @Override
    public Page<CameraInfoVo> list(PageQuery pageQuery, String cameraName) {
        Page<CameraInfoVo> result = cameraInfoMapper.queryList(pageQuery.build(), cameraName);
        return result;
    }

    @Override
    public R<Void> updateByBo(@Validated @RequestBody  CameraInfoBo bo) {
        //查询编号是否唯一
        CameraInfo cameraInfo = cameraInfoMapper.selectOne
                (new QueryWrapper<CameraInfo>().eq("cameraCode", bo.getCameraCode())
                        .notIn("cameraId", bo.getCameraId()));
        if (cameraInfo != null) {
            return R.fail("修改的摄像机设备编号不唯一，请重新输入");
        }

        //当监测设备修改时，删除区域绑定信息
        CameraInfo oldCameraInfo = cameraInfoMapper.selectOne(new QueryWrapper<CameraInfo>().eq("cameraId", bo.getCameraId()));
        if (oldCameraInfo.getDeviceCode() != bo.getDeviceCode()) {
            try {
                cameraMapper.delete(new QueryWrapper<CameraAreaInfo>().eq("cameraId",bo.getCameraId()));
            } catch (GlobalException e) {
                e.printStackTrace();
            }
        }

        cameraInfo = BeanUtil.toBean(bo, CameraInfo.class);
        Boolean flag = cameraInfoMapper.updateById(cameraInfo) > 0;
        if (flag) {
            if (!playBackService.updateVideoTape(cameraInfo)) {
                return R.fail("视频录像更新失败");
            }
            try {
                sysJobService.deleteThermometryMonitorJob(oldCameraInfo);
                sysJobService.addThermometryMonitorJob(cameraInfo);

            } catch (GlobalException e) {
                return R.fail("修改监测温度失败");
            }


            try {
                sysJobService.editSysJobByCameraId(cameraInfo.getCameraId());
            } catch (GlobalException e) {
                return R.fail("修改截图失败");
            }
            return R.ok("修改成功");
        }
        return R.fail("修改失败,请重试");
    }

    @Override
    public Boolean deleteByCameraId(Integer cameraId) {
        CameraInfo cameraInfo = cameraInfoMapper.selectOne(new QueryWrapper<CameraInfo>().eq("cameraId", cameraId));
        ThermalImageInfo info = thermalImageInfoMapper.selectOne(new QueryWrapper<ThermalImageInfo>().eq("cameraId", cameraId));
        if (info.getAnalysisSwitch() == 1) {
            //todo 关闭任务
            try {
                sysJobService.deleteThermometryMonitorJob(cameraInfo);
            } catch (GlobalException e) {
                throw new GlobalException("删除该摄像头的温度监测失败");
            }
            try {
                sysJobService.stopAnalysisImageSysJobByThermal(info);
            } catch (GlobalException e) {
                throw new GlobalException("删除该摄像头的抓图设置失败");
            }
        }
        //删除关于这个摄像头的图像分析设置
        Boolean flag = thermalImageInfoMapper.delete(new QueryWrapper<ThermalImageInfo>().eq("cameraId", cameraId)) > 0;
        if (!flag) {
            throw new GlobalException("删除该摄像头的图像分析设置失败");
        }
        //删除关于这个摄像头的热感特定区域
        try {
            cameraMapper.delete(new QueryWrapper<CameraAreaInfo>().eq("cameraId", cameraId));
        } catch (GlobalException e) {
            throw new GlobalException("热感特定区域删除失败");
        }

        try {
            playBackService.stopAndDeleteDir(cameraId);
        } catch (GlobalException e) {
            throw new GlobalException("视频录像暂停失败");
        }

        return cameraInfoMapper.deleteById(cameraId) > 0;
    }

    @Override
    public R getReferencePicture(Integer cameraId) {
        ThermalImageInfo thermalImageInfo = thermalImageInfoMapper.selectOne(new QueryWrapper<ThermalImageInfo>().eq("cameraId", cameraId));
        String referenceImagePath = thermalImageInfo.getReferenceImagePath();

        String tempPath = path + referenceImagePath;
        // 创建 File 对象
        File directory = new File(tempPath);
        // 调用遍历目录的方法
        String result = traverseDirectory(directory);
        if (result != null) {
            File file = new File(result);
            String filename = file.getName();
            return R.ok(url_prefix + referenceImagePath + "/" + filename);
        }
        return R.fail("该目录下无热感分析参考画面");
    }

    @Override
    public R getAnalysisOutImage(Integer cameraId) {
        ThermalImageInfo thermalImageInfo = thermalImageInfoMapper.selectOne(new QueryWrapper<ThermalImageInfo>().eq("cameraId", cameraId));
        String analysisImageOutPath = thermalImageInfo.getAnalysisImageOutPath();

        String tempPath = path + analysisImageOutPath;
        // 创建 File 对象
        File directory = new File(tempPath);
        // 调用遍历目录的方法
        String result = traverseDirectory(directory);
        if (result != null) {
            File file = new File(result);
            String filename = file.getName();
            return R.ok(url_prefix + analysisImageOutPath + "/" + filename);
        }
        return R.fail("该目录下无分析结果图片");
    }

    @Override
    public R setReferenceImage(Integer cameraId) {

        CameraInfo cameraInfo = cameraInfoMapper.selectOne(new QueryWrapper<CameraInfo>().eq("cameraId", cameraId));
        ThermalImageInfo thermalImageInfo = thermalImageInfoMapper.selectOne(new QueryWrapper<ThermalImageInfo>().eq("cameraId", cameraId));
        String imageSrc;
        if (thermalImageInfo.getAnalysisSwitch() == 1) {
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
            //当前的登录摄像头的userid
            Integer userId = pair.getKey1();
            /*设备抓图*/
            try {
                imageSrc = hcNetBase.captureReferenceJPEGPicture(userId, 1, (short) 9, cameraInfo.getCameraId(), thermalImageInfo.getReferenceImagePath(),cameraInfo.getUsername());
            } catch (GlobalException e) {
                throw new GlobalException("抓图失败！！");
            }
            //注销摄像头用户
            hcNetBase.logout(userId);
            return R.ok(imageSrc);
        }

        return R.fail("请先打开热感图像分析开关！");

    }

    public String traverseDirectory(File directory) {
        // 检查目录是否存在
        if (directory.exists()) {
            // 获取目录下所有文件和文件夹
            File[] files = directory.listFiles();

            if (files != null) {
                // 遍历文件和文件夹
                for (File file : files) {
                    if (file.isDirectory()) {
                        // 如果是目录，递归调用遍历方法
                        traverseDirectory(file);
                    } else {
                        // 如果是文件，进行相关操作
                        return file.getAbsolutePath();
                    }
                }
            }
        }
        return null;
    }
}
