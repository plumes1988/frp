package com.figure.op.duty.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.device.domain.DeviceInfo;
import com.figure.op.device.domain.vo.DeviceProductInfoVo;
import com.figure.op.device.mapper.DeviceInfoMapper;
import com.figure.op.device.service.IDeviceInfoService;
import com.figure.op.duty.domain.RepairOrderInfo;
import com.figure.op.duty.domain.bo.*;
import com.figure.op.duty.domain.vo.RepairOrderInfoListVo;
import com.figure.op.duty.domain.vo.RepairOrderInfoPageVo;
import com.figure.op.duty.domain.vo.RepairOrderInfoVo;
import com.figure.op.duty.mapper.RepairOrderInfoMapper;
import com.figure.op.duty.service.IRepairOrderInfoService;
import com.figure.op.system.domain.OutPriceInfo;
import com.figure.op.system.domain.SysUserInfo;
import com.figure.op.system.domain.vo.SysFrontStationVo;
import com.figure.op.system.helper.LoginHelper;
import com.figure.op.system.mapper.SysUserInfoMapper;
import com.figure.op.system.service.ISysFrontStationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class RepairOrderInfoServiceImpl implements IRepairOrderInfoService {

    @Resource
    private RepairOrderInfoMapper repairOrderInfoMapper;

    @Resource
    private ISysFrontStationService frontStationService;

    @Resource
    private IDeviceInfoService productInfoService;

    @Resource
    private DeviceInfoMapper deviceInfoMapper;

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    /**
     * 全部列表
     */
    @Override
    public List<RepairOrderInfoListVo> queryList() {
        List<RepairOrderInfo> repairOrderInfoList = repairOrderInfoMapper.selectList(null);
        return BeanCopyUtils.copyList(repairOrderInfoList, RepairOrderInfoListVo.class);
    }

    /**
     * 详情
     */
    @Override
    public RepairOrderInfoVo queryById(Integer id) {
        RepairOrderInfo repairOrderInfo = repairOrderInfoMapper.selectById(id);
        return BeanCopyUtils.copy(repairOrderInfo, RepairOrderInfoVo.class);
    }

    /**
     * 新增
     */
    @Override
    public Boolean insertByBo(RepairOrderInfoBo bo) {
        RepairOrderInfo add = BeanUtil.toBean(bo, RepairOrderInfo.class);
        SysFrontStationVo sysFrontStationVo = frontStationService.queryById(add.getStationId());
        if (sysFrontStationVo != null) {
            add.setStation(sysFrontStationVo.getFrontName());
        }
        DeviceInfo deviceInfo = deviceInfoMapper.selectById(bo.getDeviceId());
        if (deviceInfo != null) {
            add.setDevice(deviceInfo.getDeviceName());
        }
        boolean flag = repairOrderInfoMapper.insert(add) > 0;
        if (flag) {
            bo.setRepairOrderId(add.getRepairOrderId());
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(RepairOrderInfoBo bo) {
        RepairOrderInfo update = BeanUtil.toBean(bo, RepairOrderInfo.class);
        return repairOrderInfoMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return repairOrderInfoMapper.deleteById(id) > 0;
    }

    @Override
    public TableDataInfo<RepairOrderInfoPageVo> page(RepairOrderInfoQueryBo queryBo, PageQuery pageQuery) {
        LambdaQueryWrapper<RepairOrderInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(queryBo.getRepairOrderId() != null, RepairOrderInfo::getRepairOrderId, queryBo.getRepairOrderId());
        lambdaQueryWrapper.eq(queryBo.getStationId() != null, RepairOrderInfo::getStationId, queryBo.getStationId());
        lambdaQueryWrapper.like(!StringUtils.isEmpty(queryBo.getDevice()), RepairOrderInfo::getDevice, queryBo.getDevice());
        lambdaQueryWrapper.eq(!StringUtils.isEmpty(queryBo.getSubmitter()), RepairOrderInfo::getSubmitter, queryBo.getSubmitter());
        lambdaQueryWrapper.eq(!StringUtils.isEmpty(queryBo.getReviewer()), RepairOrderInfo::getReviewer, queryBo.getReviewer());
        lambdaQueryWrapper.eq(!StringUtils.isEmpty(queryBo.getActor()), RepairOrderInfo::getActor, queryBo.getActor());
        String pageType = queryBo.getPageType();

        if (queryBo.getStartTime() != null && queryBo.getEndTime() != null) {
            if ("0".equals(pageType)) {
                lambdaQueryWrapper.between(RepairOrderInfo::getSubmitTime, queryBo.getStartTime(), queryBo.getEndTime());
            } else if ("1".equals(pageType)) {
                lambdaQueryWrapper.between(RepairOrderInfo::getReviewTime, queryBo.getStartTime(), queryBo.getEndTime());
            } else if ("2".equals(pageType)) {
                lambdaQueryWrapper.between(RepairOrderInfo::getActTime, queryBo.getStartTime(), queryBo.getEndTime());
            }
        }

        if ("0".equals(pageType)) {
            lambdaQueryWrapper.ne(true, RepairOrderInfo::getReviewStatus, "2");
            lambdaQueryWrapper.ne(true, RepairOrderInfo::getActStatus, "2");
            // 审核工单只显示待审核的
        } else if ("1".equals(pageType)) {
            lambdaQueryWrapper.eq(RepairOrderInfo::getReviewStatus, "1");
        } else if ("2".equals(pageType)) {
            lambdaQueryWrapper.ne(true, RepairOrderInfo::getActStatus, '0');
        }

        lambdaQueryWrapper.orderByDesc(RepairOrderInfo::getRepairOrderId);
        Page<RepairOrderInfo> result = repairOrderInfoMapper.selectPage(pageQuery.build(), lambdaQueryWrapper);
        List<RepairOrderInfoPageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), RepairOrderInfoPageVo.class);

        if (resultList != null){
            for (RepairOrderInfoPageVo repairOrderInfoPageVo : resultList) {
                SysFrontStationVo sysFrontStationVo = frontStationService.queryById(repairOrderInfoPageVo.getStationId());
                if (sysFrontStationVo != null) {
                    repairOrderInfoPageVo.setRepairStationName(sysFrontStationVo.getFrontName());
                }
                DeviceInfo deviceInfo = deviceInfoMapper.selectById(repairOrderInfoPageVo.getDeviceId());
                if (deviceInfo != null) {
                    repairOrderInfoPageVo.setDevice(deviceInfo.getDeviceName());
                }
                SysUserInfo userInfo1 = sysUserInfoMapper.selectById(repairOrderInfoPageVo.getSubmitterId());
                if (userInfo1 != null) {
                    repairOrderInfoPageVo.setSubmitter(userInfo1.getUserName());
                }
                SysUserInfo userInfo2 = sysUserInfoMapper.selectById(repairOrderInfoPageVo.getReviewerId());
                if (userInfo2 != null) {
                    repairOrderInfoPageVo.setReviewer(userInfo2.getUserName());
                }
                SysUserInfo userInfo3 = sysUserInfoMapper.selectById(repairOrderInfoPageVo.getActorId());
                if (userInfo3 != null) {
                    repairOrderInfoPageVo.setActor(userInfo3.getUserName());
                }
            }
        }else {
            return TableDataInfo.build();
        }

        Page<RepairOrderInfoPageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);
        return TableDataInfo.build(resultPage);
    }


    /**
     * 更新提交状态
     */
    @Override
    public Boolean updateSubmitStatus(RepairOrderInfoSubmitStatusBo bo) {

        // 判断是当前维修工单是否允许提交
        RepairOrderInfo repairOrderInfo = repairOrderInfoMapper.selectById(bo.getRepairOrderId());
        if (repairOrderInfo != null) {
            if ("1".equals(repairOrderInfo.getReviewStatus())){
                throw new ServiceException("该工单正在等待审核,不能重复提交");
            }
            if ("2".equals(repairOrderInfo.getReviewStatus())){
                throw new ServiceException("该工单已审核通过,不能重复提交");
            }
        }

        // 若提交 则审核状态更新为1待审核
        if ("1".equals(bo.getSubmitStatus())){
            RepairOrderInfo update = BeanUtil.toBean(bo, RepairOrderInfo.class);
            update.setReviewStatus("1");
            update.setSubmitterId(LoginHelper.getUserId());
            update.setSubmitTime(new Date());
            return repairOrderInfoMapper.updateById(update) > 0;
        // 若取消(待提交) 审核状态更新为0
        }else if ("0".equals(bo.getSubmitStatus())){
            // 构建更新构造器 仅对部分字段进行更新(将提交人和提交时间置空)
            LambdaUpdateWrapper<RepairOrderInfo> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(RepairOrderInfo::getSubmitterId, null);
            updateWrapper.set(RepairOrderInfo::getSubmitTime, null);
            updateWrapper.set(RepairOrderInfo::getReviewStatus, "0");
            updateWrapper.eq(RepairOrderInfo::getRepairOrderId, bo.getRepairOrderId());
            return repairOrderInfoMapper.update(null, updateWrapper) > 0;
        }

        return false;
    }

    /**
     * 更新审核状态
     */
    @Override
    public Boolean updateReviewStatus(RepairOrderInfoReviewStatusBo bo) {

        // 判断是当前审核工单是否允许审核
        RepairOrderInfo repairOrderInfo = repairOrderInfoMapper.selectById(bo.getRepairOrderId());
        if (repairOrderInfo != null) {
            if ("0".equals(repairOrderInfo.getSubmitStatus())){
                throw new ServiceException("该工单正在等待提交,无法审核");
            }
            if (!"1".equals(repairOrderInfo.getReviewStatus())){
                throw new ServiceException("该工单已审核,不能重复审核");
            }
        }

        RepairOrderInfo update = BeanUtil.toBean(bo, RepairOrderInfo.class);
        // 若通过 则执行状态更新为1待执行
        if ("2".equals(bo.getReviewStatus())){
            update.setActStatus("1");
        }
        update.setReviewerId(LoginHelper.getUserId());
        update.setReviewTime(new Date());
        return repairOrderInfoMapper.updateById(update) > 0;
    }


    /**
     * 更新执行状态
     */
    @Override
    public Boolean updateActStatus(RepairOrderInfoActStatusBo bo) {

        // 判断是当前执行工单是否允许执行
        RepairOrderInfo repairOrderInfo = repairOrderInfoMapper.selectById(bo.getRepairOrderId());
        if (repairOrderInfo != null) {
            if (!"2".equals(repairOrderInfo.getReviewStatus())){
                throw new ServiceException("该工单未审核通过,无法执行");
            }
            if (!"1".equals(repairOrderInfo.getActStatus())){
                throw new ServiceException("该工单已执行,不能重复执行");
            }
        }

        RepairOrderInfo update = BeanUtil.toBean(bo, RepairOrderInfo.class);
        update.setActorId(LoginHelper.getUserId());
        update.setActTime(new Date());
        return repairOrderInfoMapper.updateById(update) > 0;
    }
}
