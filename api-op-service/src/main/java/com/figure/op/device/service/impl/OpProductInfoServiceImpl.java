package com.figure.op.device.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.device.domain.*;
import com.figure.op.device.domain.bo.OpProductInfoBo;
import com.figure.op.device.domain.bo.OpProductInfoQueryBo;
import com.figure.op.device.domain.vo.OpProductInfoListVo;
import com.figure.op.device.domain.vo.OpProductInfoPageVo;
import com.figure.op.device.domain.vo.OpProductInfoVo;
import com.figure.op.device.mapper.DeviceProductInfoMapper;
import com.figure.op.device.mapper.DeviceProductModelMapper;
import com.figure.op.device.mapper.DeviceTypeInfoMapper;
import com.figure.op.device.mapper.OpProductInfoMapper;
import com.figure.op.device.service.IOpProductInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class OpProductInfoServiceImpl implements IOpProductInfoService {

    @Resource
    private OpProductInfoMapper opProductInfoMapper;

    @Resource
    private DeviceTypeInfoMapper deviceTypeInfoMapper;

    @Resource
    private DeviceProductInfoMapper deviceProductInfoMapper;

    @Resource
    private DeviceProductModelMapper deviceProductModelMapper;

    /**
     * 分页列表
     */
    @Override
    public TableDataInfo<OpProductInfoPageVo> page(OpProductInfoQueryBo queryBo, PageQuery pageQuery) {

        // 查询器材类型
        List<DeviceTypeInfo> deviceTypeInfoList = deviceTypeInfoMapper.selectList(null);
        Map<Integer, String> deviceIdToNameMap = deviceTypeInfoList.stream()
                .collect(Collectors.toMap(DeviceTypeInfo::getDeviceTypeId, DeviceTypeInfo::getDeviceTypeName));
        // 查询产品厂商
        List<DeviceProductInfo> deviceProductInfoList = deviceProductInfoMapper.selectList(null);
        Map<Integer, String> brandIdToNameMap = deviceProductInfoList.stream()
                .collect(Collectors.toMap(DeviceProductInfo::getProductId, DeviceProductInfo::getProductName));
        // 查询产品型号
        List<DeviceProductModel> deviceProductModelList = deviceProductModelMapper.selectList(null);
        Map<Integer, String> modelIdToCodeMap = deviceProductModelList.stream()
                .collect(Collectors.toMap(DeviceProductModel::getModelId, DeviceProductModel::getModelCode));

        LambdaQueryWrapper<OpProductInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(queryBo.getOpProductName() != null, OpProductInfo::getOpProductName, queryBo.getOpProductName());
        lambdaQueryWrapper.eq(queryBo.getDeviceTypeId() != null, OpProductInfo::getDeviceTypeId, queryBo.getDeviceTypeId());
        lambdaQueryWrapper.eq(queryBo.getBrandId() != null, OpProductInfo::getBrandId, queryBo.getBrandId());
        lambdaQueryWrapper.eq(queryBo.getModelId() != null, OpProductInfo::getModelId, queryBo.getModelId());
        lambdaQueryWrapper.orderByDesc(OpProductInfo::getOpProductId);
        Page<OpProductInfo> result = opProductInfoMapper.selectPage(pageQuery.build(), lambdaQueryWrapper);
        List<OpProductInfoPageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), OpProductInfoPageVo.class);
        if (resultList != null){
            for (OpProductInfoPageVo opProductInfoPageVo : resultList) {
                opProductInfoPageVo.setDeviceTypeName(deviceIdToNameMap.get(opProductInfoPageVo.getDeviceTypeId()));
                opProductInfoPageVo.setBrandName(brandIdToNameMap.get(opProductInfoPageVo.getBrandId()));
                opProductInfoPageVo.setModelCode(modelIdToCodeMap.get(opProductInfoPageVo.getModelId()));
            }
        }
        Page<OpProductInfoPageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);
        return TableDataInfo.build(resultPage);
    }



    /**
     * 分页列表2
     */
    @Override
    public TableDataInfo<OpProductInfoPageVo> queryPageList2(OpProductInfoQueryBo queryBo, PageQuery pageQuery) {
        Page<OpProductInfoPageVo> result = opProductInfoMapper.selectVoPage(pageQuery.build(), queryBo);
        return TableDataInfo.build(result);
    }

    /**
     * 全部列表
     */
    @Override
    public List<OpProductInfoListVo> queryList(OpProductInfo opProductInfo) {
        LambdaQueryWrapper<OpProductInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<OpProductInfo> opProductInfoList = opProductInfoMapper.selectList(lambdaQueryWrapper);
        return BeanCopyUtils.copyList(opProductInfoList, OpProductInfoListVo.class);
    }

    /**
     * 详情
     */
    @Override
    public OpProductInfoVo queryById(Integer id) {
        OpProductInfo opProductInfo = opProductInfoMapper.selectById(id);
        return BeanCopyUtils.copy(opProductInfo, OpProductInfoVo.class);
    }

    /**
     * 新增
     */
    @Override
    public Boolean insertByBo(OpProductInfoBo bo) {
        OpProductInfo add = BeanUtil.toBean(bo, OpProductInfo.class);
        boolean flag = opProductInfoMapper.insert(add) > 0;
        if (flag) {
            bo.setOpProductId(add.getOpProductId());
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(OpProductInfoBo bo) {
        OpProductInfo update = BeanUtil.toBean(bo, OpProductInfo.class);
        return opProductInfoMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return opProductInfoMapper.deleteById(id) > 0;
    }
}
