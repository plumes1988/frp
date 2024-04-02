package com.figure.op.duty.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.duty.domain.RepairEventExecute;
import com.figure.op.duty.domain.RepairEventInfo;
import com.figure.op.duty.domain.RepairTaskInfo;
import com.figure.op.duty.domain.bo.RepairEventInfoBo;
import com.figure.op.duty.domain.bo.RepairEventInfoQueryBo;
import com.figure.op.duty.domain.vo.*;
import com.figure.op.duty.mapper.RepairEventExecuteMapper;
import com.figure.op.duty.mapper.RepairEventInfoMapper;
import com.figure.op.duty.mapper.RepairTaskInfoMapper;
import com.figure.op.duty.service.IRepairEventInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 检修事件服务实现
 * @author fsn
 */
@Service
public class RepairEventInfoServiceImpl implements IRepairEventInfoService {

    @Resource
    private RepairEventInfoMapper repairEventInfoMapper;

    @Resource
    private RepairTaskInfoMapper repairTaskInfoMapper;

    @Resource
    private RepairEventExecuteMapper eventExecuteMapper;

    /**
     * 全部列表
     */
    @Override
    public List<RepairEventInfoListVo> queryList(RepairEventInfoQueryBo queryBo) {
        return repairEventInfoMapper.selectVoList(queryBo);
    }

    /**
     * 分页列表
     */
    @Override
    public TableDataInfo<RepairEventInfoPageVo> page(RepairEventInfoQueryBo queryBo, PageQuery pageQuery) {
        Page<RepairEventInfoPageVo> result = repairEventInfoMapper.selectVoPage(pageQuery.build(), queryBo);
        return TableDataInfo.build(result);
    }

    /**
     * 详情
     */
    @Override
    public RepairEventInfoVo queryById(Integer id) {
        RepairEventInfo repairEventInfo = repairEventInfoMapper.selectById(id);
        return BeanCopyUtils.copy(repairEventInfo, RepairEventInfoVo.class);
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(RepairEventInfoBo bo) {
        Date now = new Date();
        // 查询检修任务(悲观锁)
        RepairTaskInfo repairTaskInfo = repairTaskInfoMapper.selectByIdLock(bo.getRepairTaskId());
        if (repairTaskInfo == null){
            throw new ServiceException("检修任务不存在");
        }
        // 判断是否允许新增
        validateAllowSaveOrUpdate(bo);

        RepairEventInfo add = BeanUtil.toBean(bo, RepairEventInfo.class);
        add.setStationId(repairTaskInfo.getRepairStation());
        add.setEventDate(repairTaskInfo.getTaskDate());
        add.setExecuteUpdateTime(now);
        boolean flag = repairEventInfoMapper.insert(add) > 0;
        if (flag) {
            bo.setRepairEventId(add.getRepairEventId());
            RepairEventExecute repairEventExecute = new RepairEventExecute();
            repairEventExecute.setRepairEventId(String.valueOf(add.getRepairEventId()));
            repairEventExecute.setExecuteStatus("0");
            repairEventExecute.setCreateTime(now);
            repairEventExecute.setUpdateTime(now);
            eventExecuteMapper.insert(repairEventExecute);
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(RepairEventInfoBo bo) {
        // 查询检修任务(悲观锁)
        RepairTaskInfo repairTaskInfo = repairTaskInfoMapper.selectByIdLock(bo.getRepairTaskId());
        if (repairTaskInfo == null){
            throw new ServiceException("检修任务不存在");
        }
        // 判断是否允许新增
        validateAllowSaveOrUpdate(bo);

        RepairEventInfo update = BeanUtil.toBean(bo, RepairEventInfo.class);
        update.setEventDate(repairTaskInfo.getTaskDate());
        return repairEventInfoMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return repairEventInfoMapper.deleteById(id) > 0;
    }



    @Override
    public List<DeviceCalVo> cal(RepairEventInfoQueryBo queryBo) {
        if (queryBo.getRepairAct() == null || "".equals(queryBo.getRepairAct())){
            throw new ServiceException("检修动作不能为空");
        }
        if (queryBo.getDeviceIds() != null && !"".equals(queryBo.getDeviceIds())){
            String[] deviceIdsArray = queryBo.getDeviceIds().split(",");
            queryBo.setDeviceIdList(Arrays.asList(deviceIdsArray));
        }

        return repairEventInfoMapper.cal(queryBo);
    }


    /**
     * 判断是否允许新增或更新
     */
    private void validateAllowSaveOrUpdate(RepairEventInfoBo repairEventInfoBo) {
        // 查询检修任务下 是否存在相同检修动作的检修设备
        LambdaQueryWrapper<RepairEventInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(RepairEventInfo::getRepairTaskId, repairEventInfoBo.getRepairTaskId())
                .eq(RepairEventInfo::getRepairAct, repairEventInfoBo.getRepairAct())
                .eq(RepairEventInfo::getDeviceId, repairEventInfoBo.getDeviceId());

        // 若为更新 则排除自身
        if (repairEventInfoBo.getRepairEventId() != null){
            lqw.ne(RepairEventInfo::getRepairEventId, repairEventInfoBo.getRepairEventId());
        }

        if (repairEventInfoMapper.selectCount(lqw) > 0){
            throw new ServiceException("该检修任务下已存在相同检修动作和设备的检修事件！");
        }
    }
}
