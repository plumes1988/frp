package com.figure.op.device.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.device.domain.DeviceTypeInfo;
import com.figure.op.device.domain.bo.DeviceTypeInfoBo;
import com.figure.op.device.domain.bo.DeviceTypeInfoQueryBo;
import com.figure.op.device.domain.vo.DeviceTypeInfoListVo;
import com.figure.op.device.domain.vo.DeviceTypeInfoPageVo;
import com.figure.op.device.domain.vo.DeviceTypeInfoVo;
import com.figure.op.device.mapper.DeviceTypeInfoMapper;
import com.figure.op.device.service.IDeviceTypeInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class DeviceTypeInfoServiceImpl implements IDeviceTypeInfoService {

    @Resource
    private DeviceTypeInfoMapper DeviceTypeInfoMapper;

    /**
     * 分页列表1
     */
    @Override
    public TableDataInfo<DeviceTypeInfoPageVo> page(DeviceTypeInfoQueryBo queryBo, PageQuery pageQuery) {
        LambdaQueryWrapper<DeviceTypeInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(queryBo.getDeviceTypeName())) {
            lambdaQueryWrapper.like(DeviceTypeInfo::getDeviceTypeName, queryBo.getDeviceTypeName());
        } else {
            lambdaQueryWrapper.eq(DeviceTypeInfo::getParentId, 0);
        }
        lambdaQueryWrapper.orderByDesc(DeviceTypeInfo::getSort);
        Page<DeviceTypeInfo> result = DeviceTypeInfoMapper.selectPage(pageQuery.build(), lambdaQueryWrapper);

        List<DeviceTypeInfoPageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), DeviceTypeInfoPageVo.class);
        for (DeviceTypeInfoPageVo item : resultList) {
            handleChildren(item);
        }
        Page<DeviceTypeInfoPageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);

        return TableDataInfo.build(resultPage);
    }

    public void handleChildren(DeviceTypeInfoPageVo deviceTypeInfoPageVo) {
        Integer deviceTypeId = deviceTypeInfoPageVo.getDeviceTypeId();
        LambdaQueryWrapper<DeviceTypeInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(deviceTypeId != null, DeviceTypeInfo::getParentId, deviceTypeId);
        lambdaQueryWrapper.orderByDesc(DeviceTypeInfo::getSort);
        List<DeviceTypeInfo> children = DeviceTypeInfoMapper.selectList(lambdaQueryWrapper);
        List<DeviceTypeInfoPageVo> deviceTypeInfoPageVos = BeanCopyUtils.copyList(children, DeviceTypeInfoPageVo.class);
        deviceTypeInfoPageVo.setChildren(deviceTypeInfoPageVos);
        if (deviceTypeInfoPageVos.size() > 0) {
            deviceTypeInfoPageVos.forEach(item -> {
                handleChildren(item);
            });
        }
    }

    public void handleChildren(DeviceTypeInfoListVo deviceTypeInfoPageVo) {
        Integer deviceTypeId = deviceTypeInfoPageVo.getDeviceTypeId();
        LambdaQueryWrapper<DeviceTypeInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(deviceTypeId != null, DeviceTypeInfo::getParentId, deviceTypeId);
        lambdaQueryWrapper.orderByDesc(DeviceTypeInfo::getSort);
        List<DeviceTypeInfo> children = DeviceTypeInfoMapper.selectList(lambdaQueryWrapper);
        List<DeviceTypeInfoListVo> deviceTypeInfoPageVos = BeanCopyUtils.copyList(children, DeviceTypeInfoListVo.class);
        deviceTypeInfoPageVo.setChildren(deviceTypeInfoPageVos);
        if (deviceTypeInfoPageVos.size() > 0) {
            deviceTypeInfoPageVos.forEach(item -> {
                handleChildren(item);
            });
        }
    }


    /**
     * 分页列表2
     */
    @Override
    public TableDataInfo<DeviceTypeInfoPageVo> queryPageList2(DeviceTypeInfoQueryBo queryBo, PageQuery pageQuery) {
        Page<DeviceTypeInfoPageVo> result = DeviceTypeInfoMapper.selectVoPage(pageQuery.build(), queryBo);
        return TableDataInfo.build(result);
    }

    /**
     * 全部列表
     */
    @Override
    public List<DeviceTypeInfoListVo> queryList(DeviceTypeInfo deviceTypeInfo) {
        LambdaQueryWrapper<DeviceTypeInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(deviceTypeInfo.getParentId() != null, DeviceTypeInfo::getParentId, deviceTypeInfo.getParentId());
        List<DeviceTypeInfo> DeviceTypeInfoList = DeviceTypeInfoMapper.selectList(lambdaQueryWrapper);
        List<DeviceTypeInfoListVo> deviceTypeInfoListVos = BeanCopyUtils.copyList(DeviceTypeInfoList, DeviceTypeInfoListVo.class);
        for (DeviceTypeInfoListVo item : deviceTypeInfoListVos) {
            handleChildren(item);
        }
        return deviceTypeInfoListVos;
    }

    /**
     * 详情
     */
    @Override
    public DeviceTypeInfoVo queryById(Integer id) {
        DeviceTypeInfo DeviceTypeInfo = DeviceTypeInfoMapper.selectById(id);
        return BeanCopyUtils.copy(DeviceTypeInfo, DeviceTypeInfoVo.class);
    }

    /**
     * 新增
     */
    @Override
    public Boolean insertByBo(DeviceTypeInfoBo bo) {
        DeviceTypeInfo add = BeanUtil.toBean(bo, DeviceTypeInfo.class);
        boolean flag = DeviceTypeInfoMapper.insert(add) > 0;
        if (flag) {
            bo.setDeviceTypeId(add.getDeviceTypeId());
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(DeviceTypeInfoBo bo) {
        DeviceTypeInfo update = BeanUtil.toBean(bo, DeviceTypeInfo.class);
        return DeviceTypeInfoMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return DeviceTypeInfoMapper.deleteById(id) > 0;
    }
}
