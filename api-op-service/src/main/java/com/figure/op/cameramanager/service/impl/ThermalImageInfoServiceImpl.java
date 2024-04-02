package com.figure.op.cameramanager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.op.cameramanager.domain.ThermalImageInfo;
import com.figure.op.cameramanager.domain.bo.ThermalImageInfoBo;
import com.figure.op.cameramanager.mapper.ThermalImageInfoMapper;
import com.figure.op.cameramanager.service.ThermalImageInfoService;
import com.figure.op.common.domain.R;
import com.figure.op.common.exception.GlobalException;
import com.figure.op.videoplay.utils.SysJobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/14 9:32
 * @Version 1.5
 */
@Service
public class ThermalImageInfoServiceImpl implements ThermalImageInfoService {

    @Resource
    private ThermalImageInfoMapper thermalImageInfoMapper;
    @Resource
    private SysJobService sysJobService;

    @Override
    public ThermalImageInfo list(String cameraId) {
        ThermalImageInfo thermalImageInfo = thermalImageInfoMapper.selectOne(new QueryWrapper<ThermalImageInfo>().eq("cameraId", cameraId));
        return thermalImageInfo;
    }

    @Override
    public R updateInfo(ThermalImageInfoBo bo) {
        ThermalImageInfo oldInfo = thermalImageInfoMapper.selectOne(new QueryWrapper<ThermalImageInfo>().eq("id", bo.getId()));
        Integer monitorCronTime = bo.getMonitorCronTime();
        if (monitorCronTime < 60) {
            bo.setMonitorCron(String.format("0/%d * * * * ?", monitorCronTime));
        } else {
            bo.setMonitorCron("0 0/1 * * * ?");
        }
        if (bo.getAnalysisCronTime() < 60) {
            bo.setAnalysisCron(String.format("0/%d * * * * ?", bo.getAnalysisCronTime()));
        } else {
            bo.setAnalysisCron("0 0/1 * * * ?");
        }
        ThermalImageInfo newInfo = BeanUtil.toBean(bo, ThermalImageInfo.class);
        newInfo.setCameraId(oldInfo.getCameraId());

        Boolean flag = thermalImageInfoMapper.updateById(newInfo) > 0;
        if (flag) {
            //如果开关关闭不用监测图片
            if (bo.getAnalysisSwitch() == 0) {
                //关闭图像监测任务
                Boolean stopResult = sysJobService.stopAnalysisImageSysJobByThermalInfo(bo);
                if (!stopResult) {
                    return R.ok("关闭图像监测任务失败");
                }
            } else {
                //停止任务，重开任务
                Boolean stopResult = sysJobService.stopAnalysisImageSysJobByThermalInfo(bo);
                if (!stopResult) {
                    return R.ok("重开关闭图像监测任务失败");
                }
                //重开任务
                sysJobService.editAnalysisImageSysJobByThermalInfo(newInfo);
            }

            //变更温度任务监测时间
            if (!oldInfo.getMonitorCron().equals(bo.getMonitorCron())) {
                sysJobService.editSysJobByCorn(newInfo);
            }

            return R.ok("变更成功");
        }
        return R.fail("变更失败");
    }

    @Override
    public List<ThermalImageInfo> queryMonitorCron() {
        QueryWrapper<ThermalImageInfo> wrapper = new QueryWrapper<>();
        List<ThermalImageInfo> thermalImageInfos = thermalImageInfoMapper.selectList(wrapper);
        return thermalImageInfos;
    }

    @Override
    public Integer getCornByCarameId(Integer id) {

        QueryWrapper<ThermalImageInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("cameraId", id);
        ThermalImageInfo thermalImageInfo = thermalImageInfoMapper.selectOne(wrapper);
        if (thermalImageInfo == null) {
            return null;
        }
        return thermalImageInfo.getMonitorCronTime();
    }

    @Override
    public Integer getAnasysCornByCarameId(Integer id) {

        QueryWrapper<ThermalImageInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("cameraId", id);
        ThermalImageInfo thermalImageInfo = thermalImageInfoMapper.selectOne(wrapper);
        if (thermalImageInfo == null) {
            return null;
        }
        return thermalImageInfo.getAnalysisCronTime();
    }
}
