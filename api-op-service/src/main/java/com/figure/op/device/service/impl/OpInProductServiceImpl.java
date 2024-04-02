package com.figure.op.device.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.device.domain.*;
import com.figure.op.device.domain.bo.OpInProductBo;
import com.figure.op.device.domain.bo.OpInProductQueryBo;
import com.figure.op.device.domain.bo.OpProductInfoBo;
import com.figure.op.device.domain.vo.OpInProductListVo;
import com.figure.op.device.domain.vo.OpInProductPageVo;
import com.figure.op.device.domain.vo.OpInProductVo;
import com.figure.op.device.domain.vo.OpProductInfoVo;
import com.figure.op.device.mapper.*;
import com.figure.op.device.service.IOpInProductService;
import com.figure.op.device.service.IOpProductInfoService;
import com.figure.op.system.domain.SysUserInfo;
import com.figure.op.system.helper.LoginHelper;
import com.figure.op.system.mapper.SysUserInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 入库服务实现
 * @author fsn
 */
@Service
public class OpInProductServiceImpl implements IOpInProductService {

    @Resource
    private OpInProductMapper opInProductMapper;

    @Resource
    private IOpProductInfoService opProductInfoService;

    @Resource
    private OpProductInfoMapper opProductInfoMapper;

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

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
    public TableDataInfo<OpInProductPageVo> page(OpInProductQueryBo queryBo, PageQuery pageQuery) {

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
        // 查询器材库存
        LambdaQueryWrapper<OpProductInfo> opProductInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        opProductInfoLambdaQueryWrapper.like(queryBo.getOpProductName() != null && !"".equals(queryBo.getOpProductName()), OpProductInfo::getOpProductName, queryBo.getOpProductName());
        opProductInfoLambdaQueryWrapper.eq(queryBo.getDeviceTypeId() != null, OpProductInfo::getDeviceTypeId, queryBo.getDeviceTypeId());
        opProductInfoLambdaQueryWrapper.eq(queryBo.getBrandId() != null, OpProductInfo::getBrandId, queryBo.getBrandId());
        opProductInfoLambdaQueryWrapper.eq(queryBo.getModelId() != null, OpProductInfo::getModelId, queryBo.getModelId());
        List<OpProductInfo> opProductInfoList = opProductInfoMapper.selectList(opProductInfoLambdaQueryWrapper);
        // 取出器材库存id
        List<Integer> opProductIdList = opProductInfoList.stream()
                .map(OpProductInfo::getOpProductId)
                .collect(Collectors.toList());
        if (opProductIdList.size() == 0){
            return TableDataInfo.build(new Page<>());
        }

        // 查询用户列表
        List<SysUserInfo> sysUserInfoList = sysUserInfoMapper.selectList(null);
        Map<Integer, String> userIdToNameMap = sysUserInfoList.stream()
                .collect(Collectors.toMap(SysUserInfo::getUserId, SysUserInfo::getUserName));

        LambdaQueryWrapper<OpInProduct> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(OpInProduct::getOpProductId, opProductIdList);
        lambdaQueryWrapper.between(queryBo.getStartCreateTime() != null && queryBo.getEndCreateTime() != null, OpInProduct::getCreateTime, queryBo.getStartCreateTime(), queryBo.getEndCreateTime());
        lambdaQueryWrapper.orderByDesc(OpInProduct::getInId);
        Page<OpInProduct> result = opInProductMapper.selectPage(pageQuery.build(), lambdaQueryWrapper);
        List<OpInProductPageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), OpInProductPageVo.class);
        if (resultList != null){
            for (OpInProductPageVo opInProductPageVo : resultList) {
                // 匹配
                OpProductInfo foundProductInfo = opProductInfoList.stream()
                        .filter(productInfo -> productInfo.getOpProductId().equals(opInProductPageVo.getOpProductId()))
                        .findFirst()
                        .orElse(null);
                if (foundProductInfo != null) {
                    opInProductPageVo.setOpProductName(foundProductInfo.getOpProductName());
                    opInProductPageVo.setDeviceTypeId(foundProductInfo.getDeviceTypeId());
                    opInProductPageVo.setDeviceTypeName(deviceIdToNameMap.get(foundProductInfo.getDeviceTypeId()));
                    opInProductPageVo.setBrandId(foundProductInfo.getBrandId());
                    opInProductPageVo.setBrandName(brandIdToNameMap.get(foundProductInfo.getBrandId()));
                    opInProductPageVo.setModelId(foundProductInfo.getModelId());
                    opInProductPageVo.setModelCode(modelIdToCodeMap.get(foundProductInfo.getModelId()));
                }
                opInProductPageVo.setOperator(userIdToNameMap.get(opInProductPageVo.getOperatorId()));
            }
        }

        Page<OpInProductPageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);

        return TableDataInfo.build(resultPage);
    }
    /**
     * 分页列表2
     */
    @Override
    public TableDataInfo<OpInProductPageVo> queryPageList2(OpInProductQueryBo queryBo, PageQuery pageQuery) {
        Page<OpInProductPageVo> result = opInProductMapper.selectVoPage(pageQuery.build(), queryBo);
        return TableDataInfo.build(result);
    }

    /**
     * 全部列表
     */
    @Override
    public List<OpInProductListVo> queryList(OpInProduct opInProduct) {
        LambdaQueryWrapper<OpInProduct> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<OpInProduct> opInProductList = opInProductMapper.selectList(lambdaQueryWrapper);
        return BeanCopyUtils.copyList(opInProductList, OpInProductListVo.class);
    }

    /**
     * 详情
     */
    @Override
    public OpInProductVo queryById(Integer id) {
        OpInProduct opInProduct = opInProductMapper.selectById(id);
        return BeanCopyUtils.copy(opInProduct, OpInProductVo.class);
    }

    /**
     * 新增入库
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(OpInProductBo bo) {
        Integer opProductId = bo.getOpProductId();
        // 对已有库存增加
        if (opProductId != null) {
            // 查询库存管理（悲观锁）
            OpProductInfo opProductInfo = opProductInfoMapper.selectByIdLock(bo.getOpProductId());
            OpProductInfoBo opProductInfoBo = BeanUtil.toBean(opProductInfo, OpProductInfoBo.class);
            Integer oldInAllAmount = opProductInfo.getInAllAmount();
            Integer oldStock = opProductInfo.getStock();
            Integer newInAllAmount = oldInAllAmount + bo.getAmount();
            Integer newStock = oldStock + bo.getAmount();
            opProductInfoBo.setInAllAmount(newInAllAmount);
            opProductInfoBo.setStock(newStock);
            opProductInfoService.updateByBo(opProductInfoBo);
            bo.setOpProductId(opProductInfoBo.getOpProductId());
            bo.setInType("NORMAL");
            bo.setUnit(opProductInfo.getUnit());
        // 新建库存
        } else {
            if (bo.getDeviceTypeId() == null){
                throw new ServiceException("请选择器材类型");
            }
            if (bo.getBrandId() == null){
                throw new ServiceException("请选择产品厂商");
            }
            if (bo.getModelId() == null){
                throw new ServiceException("请选择产品型号");
            }
            OpProductInfoBo productInfoBo = new OpProductInfoBo();
            productInfoBo.setOpProductName(bo.getOpProductName());
            productInfoBo.setDeviceTypeId(bo.getDeviceTypeId());
            productInfoBo.setBrandId(bo.getBrandId());
            productInfoBo.setModelId(bo.getModelId());
            productInfoBo.setInAllAmount(bo.getAmount());
            productInfoBo.setStock(bo.getAmount());
            productInfoBo.setUnit(bo.getUnit());
            opProductInfoService.insertByBo(productInfoBo);
            bo.setOpProductId(productInfoBo.getOpProductId());
            bo.setInType("NORMAL");
        }
        OpInProduct add = BeanUtil.toBean(bo, OpInProduct.class);
        add.setOperatorId(LoginHelper.getUserId());
        // 新增入库记录
        boolean flag = opInProductMapper.insert(add) > 0;
        if (flag) {
            bo.setInId(add.getInId());
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(OpInProductBo bo) {
        OpInProduct update = BeanUtil.toBean(bo, OpInProduct.class);
        return opInProductMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return opInProductMapper.deleteById(id) > 0;
    }
}
