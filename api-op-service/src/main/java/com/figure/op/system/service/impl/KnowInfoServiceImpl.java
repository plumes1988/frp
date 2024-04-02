package com.figure.op.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.device.domain.DeviceInfo;
import com.figure.op.device.domain.vo.DeviceProductInfoVo;
import com.figure.op.device.mapper.DeviceInfoMapper;
import com.figure.op.device.service.IDeviceInfoService;
import com.figure.op.system.domain.KnowInfo;
import com.figure.op.system.domain.SysUserInfo;
import com.figure.op.system.domain.bo.KnowInfoBo;
import com.figure.op.system.domain.bo.KnowInfoQueryBo;
import com.figure.op.system.domain.vo.KnowInfoListVo;
import com.figure.op.system.domain.vo.KnowInfoPageVo;
import com.figure.op.system.domain.vo.KnowInfoVo;
import com.figure.op.system.mapper.KnowInfoMapper;
import com.figure.op.system.mapper.SysUserInfoMapper;
import com.figure.op.system.service.IKnowInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class KnowInfoServiceImpl implements IKnowInfoService {

    @Resource
    private KnowInfoMapper knowInfoMapper;

    @Resource
    private IDeviceInfoService deviceProductInfoService;

    @Resource
    private DeviceInfoMapper deviceInfoMapper;

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    /**
     * 全部列表
     */
    @Override
    public List<KnowInfoListVo> queryList(KnowInfo queryBo) {
        LambdaQueryWrapper<KnowInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<KnowInfo> knowInfoList = knowInfoMapper.selectList(lambdaQueryWrapper);
        for (KnowInfo knowInfo : knowInfoList) {
            DeviceInfo deviceInfo = deviceInfoMapper.selectById(knowInfo.getDeviceId());
            knowInfo.setDevice(deviceInfo.getDeviceName());
        }
        return BeanCopyUtils.copyList(knowInfoList, KnowInfoListVo.class);
    }

    /**
     * 详情
     */
    @Override
    public KnowInfoVo queryById(Integer id) {
        KnowInfo knowInfo = knowInfoMapper.selectById(id);
        DeviceInfo deviceInfo = deviceInfoMapper.selectById(knowInfo.getDeviceId());
        knowInfo.setDevice(deviceInfo.getDeviceName());
        return BeanCopyUtils.copy(knowInfo, KnowInfoVo.class);
    }

    /**
     * 新增
     */
    @Override
    public Boolean insertByBo(KnowInfoBo bo) {
        KnowInfo add = BeanUtil.toBean(bo, KnowInfo.class);
        boolean flag = knowInfoMapper.insert(add) > 0;
        if (flag) {
            bo.setKnowId(add.getKnowId());
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(KnowInfoBo bo) {
        KnowInfo update = BeanUtil.toBean(bo, KnowInfo.class);
        return knowInfoMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return knowInfoMapper.deleteById(id) > 0;
    }

    @Override
    public TableDataInfo<KnowInfoPageVo> page(KnowInfoQueryBo queryBo, PageQuery pageQuery) {
        LambdaQueryWrapper<KnowInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(queryBo.getKnowId() != null, KnowInfo::getKnowId, queryBo.getKnowId());
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(queryBo.getTopic()), KnowInfo::getTopic, queryBo.getTopic());
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(queryBo.getKnowKeywords()), KnowInfo::getKnowKeywords, queryBo.getKnowKeywords());
        lambdaQueryWrapper.eq(queryBo.getDeviceId() != null, KnowInfo::getDeviceId, queryBo.getDeviceId());
        lambdaQueryWrapper.eq(queryBo.getUpdateUserId() != null, KnowInfo::getUpdateUserId, queryBo.getUpdateUserId());
        lambdaQueryWrapper.between(queryBo.getStartUpdateTime() != null && queryBo.getEndUpdateTime() != null, KnowInfo::getUpdateTime, queryBo.getStartUpdateTime(), queryBo.getEndUpdateTime());
        lambdaQueryWrapper.orderByDesc(KnowInfo::getKnowId);
        Page<KnowInfo> result = knowInfoMapper.selectPage(pageQuery.build(), lambdaQueryWrapper);

        List<KnowInfoPageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), KnowInfoPageVo.class);
        if (resultList != null){
            for (KnowInfoPageVo knowInfoPageVo : resultList) {
                DeviceInfo deviceInfo = deviceInfoMapper.selectById(knowInfoPageVo.getDeviceId());
                knowInfoPageVo.setDevice(deviceInfo.getDeviceName());
                SysUserInfo sysUserInfo = sysUserInfoMapper.selectById(knowInfoPageVo.getUpdateUserId());
                if (sysUserInfo != null){
                    knowInfoPageVo.setUpdateUserName(sysUserInfo.getUserName());
                }
            }
        }

        Page<KnowInfoPageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);
        return TableDataInfo.build(resultPage);
    }
}
