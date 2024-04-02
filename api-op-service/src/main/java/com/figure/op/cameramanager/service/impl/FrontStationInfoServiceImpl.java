package com.figure.op.cameramanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.op.cameramanager.domain.FrontStationInfo;
import com.figure.op.cameramanager.domain.vo.FrontStationInfoVo;
import com.figure.op.cameramanager.mapper.FrontStationInfoMapper;
import com.figure.op.cameramanager.service.FrontStationInfoService;
import com.figure.op.common.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/8 20:46
 * @Version 1.5
 */
@Service
public class FrontStationInfoServiceImpl implements FrontStationInfoService {

    @Resource
    private FrontStationInfoMapper frontStationInfoMapper;

    @Override
    public List<FrontStationInfoVo> queryFrontStation() {
        LambdaQueryWrapper<FrontStationInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(FrontStationInfo::getFrontId,FrontStationInfo::getFrontName);
        List<FrontStationInfo> frontStationInfos = frontStationInfoMapper.selectList(wrapper);
        List<FrontStationInfoVo> frontStationInfoVos = BeanCopyUtils.copyList(frontStationInfos, FrontStationInfoVo.class);
        return frontStationInfoVos;
    }
}
