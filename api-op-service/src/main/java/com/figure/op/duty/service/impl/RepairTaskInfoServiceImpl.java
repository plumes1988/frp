package com.figure.op.duty.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.duty.domain.*;
import com.figure.op.duty.domain.bo.RepairTaskInfoBo;
import com.figure.op.duty.domain.bo.RepairTaskInfoQueryBo;
import com.figure.op.duty.domain.bo.RepairTaskStatusBo;
import com.figure.op.duty.domain.vo.RepairPlanInfoVo;
import com.figure.op.duty.domain.vo.RepairTaskInfoListVo;
import com.figure.op.duty.domain.vo.RepairTaskInfoPageVo;
import com.figure.op.duty.domain.vo.RepairTaskInfoVo;
import com.figure.op.duty.mapper.RepairEventInfoMapper;
import com.figure.op.duty.mapper.RepairPlanInfoMapper;
import com.figure.op.duty.mapper.RepairTaskInfoMapper;
import com.figure.op.duty.service.IRepairPlanInfoService;
import com.figure.op.duty.service.IRepairTaskInfoService;
import com.figure.op.system.domain.SysUserInfo;
import com.figure.op.system.domain.vo.SysFrontStationVo;
import com.figure.op.system.mapper.SysUserInfoMapper;
import com.figure.op.system.service.ISysFrontStationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 检修任务服务实现
 * @author fsn
 */
@Service
public class RepairTaskInfoServiceImpl implements IRepairTaskInfoService {

    @Resource
    private RepairTaskInfoMapper repairTaskInfoMapper;

    @Resource
    private RepairPlanInfoMapper repairPlanInfoMapper;

    @Resource
    private IRepairPlanInfoService repairPlanInfoService;

    @Resource
    private ISysFrontStationService frontStationService;

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    @Resource
    private RepairEventInfoMapper repairEventInfoMapper;


    /**
     * 全部列表
     */
    @Override
    public List<RepairTaskInfoListVo> queryList() {
        List<RepairTaskInfo> repairTaskInfoList = repairTaskInfoMapper.selectList(null);
        return BeanCopyUtils.copyList(repairTaskInfoList, RepairTaskInfoListVo.class);
    }

    /**
     * 分页列表
     */
    @Override
    public TableDataInfo<RepairTaskInfoPageVo> page(RepairTaskInfoQueryBo queryBo, PageQuery pageQuery) {

        LambdaQueryWrapper<RepairTaskInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(queryBo.getRepairTaskId() != null, RepairTaskInfo::getRepairTaskId, queryBo.getRepairTaskId());
        lambdaQueryWrapper.like(queryBo.getTaskName() != null && !"".equals(queryBo.getTaskName()), RepairTaskInfo::getTaskName, queryBo.getTaskName());
        lambdaQueryWrapper.eq(queryBo.getRepairPlanId() != null, RepairTaskInfo::getRepairPlanId, queryBo.getRepairPlanId());
        lambdaQueryWrapper.in(queryBo.getTaskReviewerId() != null, RepairTaskInfo::getTaskReviewerId, queryBo.getTaskReviewerId());
        lambdaQueryWrapper.eq(queryBo.getTaskStatus() != null && !"".equals(queryBo.getTaskStatus()), RepairTaskInfo::getTaskStatus, queryBo.getTaskStatus());
        lambdaQueryWrapper.eq(queryBo.getStationId() != null, RepairTaskInfo::getRepairStation, queryBo.getStationId());
        lambdaQueryWrapper.between(queryBo.getStartTaskDate() != null && queryBo.getEndTaskDate() != null, RepairTaskInfo::getTaskDate, queryBo.getStartTaskDate(), queryBo.getEndTaskDate());

        // 对下属的检修事件进行查询
        if (queryBo.getWorkerId() != null || (queryBo.getActStatus() != null && !"".equals(queryBo.getActStatus())) || queryBo.getRepairAct() != null && !"".equals(queryBo.getRepairAct()) || queryBo.getDeviceId() != null){
            LambdaQueryWrapper<RepairEventInfo> lqw1 = new LambdaQueryWrapper<>();
            lqw1.eq(queryBo.getWorkerId() != null, RepairEventInfo::getWorkerId, queryBo.getWorkerId());
            lqw1.eq(queryBo.getActStatus() != null && !"".equals(queryBo.getActStatus()), RepairEventInfo::getActStatus, queryBo.getActStatus());
            lqw1.eq(queryBo.getRepairAct() != null && !"".equals(queryBo.getRepairAct()), RepairEventInfo::getRepairAct, queryBo.getRepairAct());
            lqw1.eq(queryBo.getDeviceId() != null, RepairEventInfo::getDeviceId, queryBo.getDeviceId());
            List<RepairEventInfo> repairEventInfoList = repairEventInfoMapper.selectList(lqw1);
            List<Integer> taskIdList = repairEventInfoList.stream().map(RepairEventInfo::getRepairTaskId).distinct().collect(Collectors.toList());
            // 若查不到 则直接返回空
            if (taskIdList.isEmpty()){
                return TableDataInfo.build(new Page<>());
            }else {
                lambdaQueryWrapper.in(RepairTaskInfo::getRepairTaskId, taskIdList);
            }
        }

        lambdaQueryWrapper.orderByDesc(RepairTaskInfo::getRepairTaskId);
        Page<RepairTaskInfo> result = repairTaskInfoMapper.selectPage(pageQuery.build(), lambdaQueryWrapper);

        List<RepairTaskInfoPageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), RepairTaskInfoPageVo.class);

        if (resultList != null){
            resultList.forEach(item -> {
                RepairPlanInfoVo repairPlanInfoVo = repairPlanInfoService.queryById(item.getRepairPlanId());
                if (repairPlanInfoVo != null) {
                    item.setPlanName(repairPlanInfoVo.getRepairPlanName());
                }
                SysFrontStationVo sysFrontStationVo = frontStationService.queryById(item.getRepairStation());
                if (sysFrontStationVo != null) {
                    item.setRepairStationName(sysFrontStationVo.getFrontName());
                }
                SysUserInfo userInfo = sysUserInfoMapper.selectById(item.getTaskReviewerId());
                if (userInfo != null) {
                    item.setTaskReviewerName(userInfo.getUserName());
                }
            });
        }

        Page<RepairTaskInfoPageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);
        return TableDataInfo.build(resultPage);
    }

    /**
     * 详情
     */
    @Override
    public RepairTaskInfoVo queryById(Integer id) {
        RepairTaskInfo repairTaskInfo = repairTaskInfoMapper.selectById(id);
        return BeanCopyUtils.copy(repairTaskInfo, RepairTaskInfoVo.class);
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(RepairTaskInfoBo bo) {
        RepairTaskInfo update = BeanUtil.toBean(bo, RepairTaskInfo.class);
        return repairTaskInfoMapper.updateById(update) > 0;
    }

    /**
     * 更新状态
     * @param bo 检修任务状态对象
     * @return 结果
     */
    @Override
    public Boolean updateStatus(RepairTaskStatusBo bo) {
        // 3 审核退回
        if ("3".equals(bo.getTaskStatus())){
            if (bo.getReason() != null && "".equals(bo.getReason())){
                throw new ServiceException("审核退回需填写退回原因");
            }
        }
        RepairTaskInfo update = BeanUtil.toBean(bo, RepairTaskInfo.class);
        return repairTaskInfoMapper.updateById(update) > 0;
    }
}
