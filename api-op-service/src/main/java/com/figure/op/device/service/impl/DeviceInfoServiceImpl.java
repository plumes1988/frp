package com.figure.op.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.device.domain.DeviceInfo;
import com.figure.op.device.domain.bo.DeviceInfoQueryBo;
import com.figure.op.device.domain.vo.*;
import com.figure.op.device.mapper.DeviceInfoMapper;
import com.figure.op.device.service.IDeviceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 设备服务实现
 * @author fsn
 */
@Service
public class DeviceInfoServiceImpl implements IDeviceInfoService {

    @Resource
    private DeviceInfoMapper deviceInfoMapper;

    /**
     * 设备-全部列表
     */
    @Override
    public List<DeviceInfoListVo> queryDeviceList() {
        List<DeviceInfo> deviceInfoList = deviceInfoMapper.selectList(null);
        return BeanCopyUtils.copyList(deviceInfoList, DeviceInfoListVo.class);
    }


    /**
     * 设备-分页列表
     */
    @Override
    public TableDataInfo<DeviceInfoPageVo> queryDevicePage(DeviceInfoQueryBo queryBo, PageQuery pageQuery) {
        LambdaQueryWrapper<DeviceInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(queryBo.getFrontId() != null, DeviceInfo::getMonitorStationId, queryBo.getFrontId());
        lqw.eq(queryBo.getPositionId() != null, DeviceInfo::getLogicPositionId, queryBo.getPositionId());
        lqw.eq(queryBo.getDeviceType() != null && !"".equals(queryBo.getDeviceType()), DeviceInfo::getDeviceType, queryBo.getDeviceType());
        lqw.eq(queryBo.getDeviceSubType() != null && !"".equals(queryBo.getDeviceSubType()), DeviceInfo::getDeviceSubType, queryBo.getDeviceSubType());
        lqw.eq(queryBo.getModelId() != null, DeviceInfo::getModelId, queryBo.getModelId());
        lqw.like(queryBo.getDeviceName() != null && !"".equals(queryBo.getDeviceName()), DeviceInfo::getDeviceName, queryBo.getDeviceName());

        Page<DeviceInfo> result = deviceInfoMapper.selectPage(pageQuery.build(), lqw);
        List<DeviceInfoPageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), DeviceInfoPageVo.class);
        Page<DeviceInfoPageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);

        return TableDataInfo.build(resultPage);
    }


    /**
     * 设备查询参数-采集站列表
     */
    @Override
    public List<MonitorStationVo> queryMonitorStationList() {
        return deviceInfoMapper.selectMonitorStationList();
    }


    /**
     * 设备查询参数-采集点列表
     */
    @Override
    public List<LogicPositionVo> queryLogicPositionList() {
        return deviceInfoMapper.selectLogicPositionList();
    }


    /**
     * 设备查询参数-设备类型列表
     */
    @Override
    public List<String> queryDeviceTypeList() {
        return deviceInfoMapper.selectDeviceTypeList();
    }

    /**
     * 设备查询参数-设备子类列表
     */
    @Override
    public List<String> queryDeviceSubTypeList() {
        return deviceInfoMapper.selectDeviceSubTypeList();
    }

    /**
     * 设备查询参数-设备型号列表
     */
    @Override
    public List<ModelVo> queryModelList(Integer brandId) {
        return deviceInfoMapper.selectModelList(brandId);
    }

    /**
     * 设备查询参数-设备厂商列表
     */
    @Override
    public List<DeviceProductInfoVo> queryProductInfoList() {
        return deviceInfoMapper.selectProductInfoList();
    }
}
