package com.figure.op.cameramanager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.op.cameramanager.domain.CameraAreaInfo;
import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.cameramanager.domain.Pair;
import com.figure.op.cameramanager.domain.bo.CameraAreInfoBo;
import com.figure.op.cameramanager.domain.vo.RuleInfoVo;
import com.figure.op.cameramanager.mapper.CameraAreMapper;
import com.figure.op.cameramanager.mapper.CameraInfoMapper;
import com.figure.op.cameramanager.netsdk.HCNetBase;
import com.figure.op.cameramanager.service.CameraAreService;
import com.figure.op.common.domain.R;
import com.figure.op.common.enums.CommonStatusEnum;
import com.figure.op.common.exception.GlobalException;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.system.controller.SysInfoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/12 13:56
 * @Version 1.5
 */
@Service
public class CameraAreServiceImpl implements CameraAreService {

    private static final Logger log = LoggerFactory.getLogger(SysInfoController.class);

    @Resource
    private CameraAreMapper cameraAreMapper;

    @Resource
    private HCNetBase hcNetBase;

    @Resource
    private CameraInfoMapper cameraInfoMapper;

    @Override
    public List<RuleInfoVo> listAreaName(Integer cameraId) {
        try{
            boolean flag = hcNetBase.init();
        }catch (Exception e) {
            throw new GlobalException("摄像头初始化失败");
        }

        //查询摄像头信息
        CameraInfo cameraInfo = cameraInfoMapper.selectById(cameraId);

        Pair<Integer, Integer> pair = hcNetBase.login_V40(cameraInfo.getIp(), cameraInfo.getPort().shortValue(),
                cameraInfo.getUsername(), cameraInfo.getPassword());

        if (pair == null) {
            throw new GlobalException("摄像头连接失败，请检查ip，端口，通道，用户名，密码等信息");
        }
        List<RuleInfoVo> ruleInfoVo = hcNetBase.getRuleInfoVo(pair.getKey1());


        //注销用户
        if (hcNetBase.logout(pair.getKey1())) {
            System.out.println("注销成功");
        }

        return ruleInfoVo;
    }

    @Override
    public R addCameraInfo(CameraAreInfoBo bo) {
        //判断该区域是否已经绑定部件

        QueryWrapper<CameraAreaInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("ruleAreaId",bo.getRuleAreaId()).eq("areaName",bo.getAreaName());
        List<CameraAreaInfo> cameraAreaInfos = cameraAreMapper.selectList(wrapper);
        if (cameraAreaInfos.size() > 0){
            return R.fail("该区域已被绑定");
        }
        QueryWrapper<CameraAreaInfo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("partCode",bo.getPartCode()).eq("partName",bo.getPartName());
        cameraAreaInfos = cameraAreMapper.selectList(wrapper2);
        if (cameraAreaInfos.size() > 0){
            return R.fail("该部件已被绑定");
        }

        CameraAreaInfo cameraAreInfo = BeanCopyUtils.copy(bo, CameraAreaInfo.class);
        Boolean flag = cameraAreMapper.insert(cameraAreInfo) > 0;
        if (flag){
            return R.ok("热感区域绑定部件成功");
        }
        return R.fail("热感区域绑定部件失败");
    }

    @Override
    public Boolean deleteByCameraAreaId(Integer cameraAreaId) {

        return cameraAreMapper.deleteById(cameraAreaId)>0;
    }

    @Override
    public R<Void> updateByBo(CameraAreInfoBo bo) {
        //判断该区域是否已经绑定部件
        QueryWrapper<CameraAreaInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("ruleAreaId",bo.getRuleAreaId()).eq("areaName",bo.getAreaName());
        wrapper.notIn("cameraAreaId",bo.getCameraAreaId());
        List<CameraAreaInfo> cameraAreaInfos = cameraAreMapper.selectList(wrapper);
        if (cameraAreaInfos.size() > 0){
            return R.fail("该区域已被绑定");
        }
        QueryWrapper<CameraAreaInfo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("partCode",bo.getPartCode()).eq("partName",bo.getPartName());
        wrapper2.notIn("cameraAreaId",bo.getCameraAreaId());
        cameraAreaInfos = cameraAreMapper.selectList(wrapper2);
        if (cameraAreaInfos.size() > 0){
            return R.fail("该部件已被绑定");
        }
        CameraAreaInfo cameraAreaInfo = BeanUtil.toBean(bo, CameraAreaInfo.class);
        Boolean flag = cameraAreMapper.updateById(cameraAreaInfo) > 0;
        if (flag) {
            return R.ok("修改成功");
        }
        return R.fail("修改失败,请重试");
    }

    @Override
    public List<CameraAreaInfo> listCameraAreaInfo(Integer cameraId) {
        List<CameraAreaInfo> cameraAreaInfos = cameraAreMapper.selectList(new QueryWrapper<CameraAreaInfo>().eq("cameraId", cameraId));
        return cameraAreaInfos;
    }


}
