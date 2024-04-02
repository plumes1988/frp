package com.figure.op.cameramanager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.cameramanager.domain.CameraTypeInfo;
import com.figure.op.cameramanager.domain.bo.CameraTypeInfoBo;
import com.figure.op.cameramanager.mapper.CameraInfoMapper;
import com.figure.op.cameramanager.mapper.CameraTypeInfoMapper;
import com.figure.op.cameramanager.service.CameraTypeInfoService;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/9 21:37
 * @Version 1.5
 */
@Service
public class CameraTypeInfoServiceImpl implements CameraTypeInfoService {
    @Resource
    private CameraTypeInfoMapper cameraTypeInfoMapper;
    @Resource
    private CameraInfoMapper cameraInfoMapper;

    @Override
    public R insertByBo(CameraTypeInfoBo bo) {
        QueryWrapper<CameraTypeInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("cameraTypeCode", bo.getCameraTypeCode());
        CameraTypeInfo cameraTypeInfo = cameraTypeInfoMapper.selectOne(wrapper);
        if (cameraTypeInfo != null) {
            return R.fail("摄像机类型编号已存在");
        }
        cameraTypeInfo = BeanUtil.toBean(bo, CameraTypeInfo.class);
        Boolean flag = cameraTypeInfoMapper.insert(cameraTypeInfo) > 0;
        if (flag) {
            return R.ok("新增摄像机类型成功");
        }
        return R.fail("新增摄像机类型失败，请重试");
    }

    @Override
    public Page<CameraTypeInfo> list(PageQuery pageQuery) {
        QueryWrapper<CameraTypeInfo> wrapper = new QueryWrapper<>();
        Page<CameraTypeInfo> cameraTypeInfoPage = cameraTypeInfoMapper.selectPage(pageQuery.build(), wrapper);
        return cameraTypeInfoPage;
    }

    @Override
    public Boolean updateByBo(CameraTypeInfoBo bo) {
        QueryWrapper<CameraTypeInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("cameraTypeCode", bo.getCameraTypeCode());
        wrapper.notIn("cameraTypeId", bo.getCameraTypeId());
        CameraTypeInfo cameraTypeInfo = cameraTypeInfoMapper.selectOne(wrapper);
        if (cameraTypeInfo != null) {
            return false;
        }
        CameraTypeInfo update = BeanUtil.toBean(bo, CameraTypeInfo.class);
        return cameraTypeInfoMapper.updateById(update) > 0;
    }

    @Override
    public R deleteByCameraTypeCode(String cameraTypeCode) {
        //查询该类型下是否有摄像机
        QueryWrapper<CameraInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("cameraTypeCode", cameraTypeCode);
        List<CameraInfo> cameraInfos = cameraInfoMapper.selectList(wrapper);
        if (cameraInfos.size() > 0) {
            return R.fail("该类型下有摄像机，不可删除");
        }
        Boolean flag = cameraTypeInfoMapper.delete(new QueryWrapper<CameraTypeInfo>()
                .eq("cameraTypeCode", cameraTypeCode)) > 0;
        if (flag) {
            return R.ok("摄像机类型删除成功");
        }
        return R.fail("摄像机类型删除失败");
    }
}
