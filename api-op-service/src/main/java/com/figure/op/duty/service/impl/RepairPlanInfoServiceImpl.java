package com.figure.op.duty.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.duty.domain.*;
import com.figure.op.duty.domain.bo.RepairPlanInfoBo;
import com.figure.op.duty.domain.bo.RepairPlanInfoCancelBo;
import com.figure.op.duty.domain.bo.RepairPlanInfoQueryBo;
import com.figure.op.duty.domain.vo.RepairPlanInfoListVo;
import com.figure.op.duty.domain.vo.RepairPlanInfoPageVo;
import com.figure.op.duty.domain.vo.RepairPlanInfoVo;
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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 检修计划服务实现
 * @author fsn
 */
@Service
public class RepairPlanInfoServiceImpl implements IRepairPlanInfoService {

    @Resource
    private RepairPlanInfoMapper repairPlanInfoMapper;

    @Resource
    private RepairTaskInfoMapper repairTaskInfoMapper;

    @Resource
    private ISysFrontStationService frontStationService;

    @Resource
    private RepairEventInfoMapper repairEventInfoMapper;

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;



    /**
     * 全部列表
     */
    @Override
    public List<RepairPlanInfoListVo> queryList() {
        List<RepairPlanInfo> repairPlanInfoList = repairPlanInfoMapper.selectList(null);
        return BeanCopyUtils.copyList(repairPlanInfoList, RepairPlanInfoListVo.class);
    }

    @Override
    public TableDataInfo<RepairPlanInfoPageVo> page(RepairPlanInfoQueryBo queryBo, PageQuery pageQuery) {
        LambdaQueryWrapper<RepairPlanInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(queryBo.getRepairPlanId() != null, RepairPlanInfo::getRepairPlanId, queryBo.getRepairPlanId());
        lambdaQueryWrapper.like(queryBo.getRepairPlanName() != null, RepairPlanInfo::getRepairPlanName, queryBo.getRepairPlanName());
        lambdaQueryWrapper.eq(queryBo.getRepairType() != null, RepairPlanInfo::getRepairType, queryBo.getRepairType());
        lambdaQueryWrapper.eq(queryBo.getRepairManagerId() != null, RepairPlanInfo::getRepairManagerId, queryBo.getRepairManagerId());
        lambdaQueryWrapper.eq(queryBo.getRepairStation() != null, RepairPlanInfo::getRepairStation, queryBo.getRepairStation());
        if (queryBo.getStartTime() != null && queryBo.getEndTime() != null) {
            lambdaQueryWrapper.and(
                    queryWrapper -> queryWrapper
                            .and(qw -> qw.le(RepairPlanInfo::getRepairStartDay, queryBo.getEndTime())
                                            .ge(RepairPlanInfo::getRepairStartDay, queryBo.getStartTime())
                            ).or(qw -> qw.le(RepairPlanInfo::getRepairEndDay, queryBo.getEndTime())
                                            .ge(RepairPlanInfo::getRepairEndDay, queryBo.getStartTime())
                            )
            );
        }
        lambdaQueryWrapper.orderByDesc(RepairPlanInfo::getRepairPlanId);
        Page<RepairPlanInfo> result = repairPlanInfoMapper.selectPage(pageQuery.build(), lambdaQueryWrapper);

        List<RepairPlanInfoPageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), RepairPlanInfoPageVo.class);
        resultList.forEach(item -> {
            SysFrontStationVo sysFrontStationVo = frontStationService.queryById(Integer.valueOf(item.getRepairStation()));
            if (sysFrontStationVo != null) {
                item.setRepairStationName(sysFrontStationVo.getFrontName());
            }
        });
        Page<RepairPlanInfoPageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);
        return TableDataInfo.build(resultPage);
    }

    /**
     * 详情
     */
    @Override
    public RepairPlanInfoVo queryById(Integer id) {
        RepairPlanInfo repairPlanInfo = repairPlanInfoMapper.selectById(id);
        return BeanCopyUtils.copy(repairPlanInfo, RepairPlanInfoVo.class);
    }

    /**
     * 新增检修计划
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(RepairPlanInfoBo bo) {

        // 限制日期范围
        Date startDay = DateUtil.beginOfDay(bo.getRepairStartTime());
        Date endDay = DateUtil.beginOfDay(bo.getRepairEndTime());
        int maxTotalDay = 366;
        int totalDay = (int) DateUtil.between(startDay, endDay, DateUnit.DAY, false) + 1;
        if (totalDay > maxTotalDay){
            throw new ServiceException("检修计划日期范围请选择一年(366天)以内");
        }

        // 判断是否进行重复生成
        if ("1".equals(bo.getIsRepeat())){
            if (bo.getRepeatRule() == null || "".equals(bo.getRepeatRule())){
                throw new ServiceException("请选择重复规则");
            }
        }else {
            if (totalDay != 1){
                throw new ServiceException("单次计划的起止日期只能选择同一天");
            }
        }

        /* 新增检修计划 */
        RepairPlanInfo add = BeanUtil.toBean(bo, RepairPlanInfo.class);
        add.setRepairStartDay(startDay);
        add.setRepairEndDay(endDay);
        // 检修负责人名称
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectById(bo.getRepairManagerId());
        add.setRepairManagerName(sysUserInfo.getUserName());
        boolean flag = repairPlanInfoMapper.insert(add) > 0;

        /* 新增检修任务 */
        String timeFormat = "HH:mm:ss";
        String startTime = DateUtil.format(bo.getRepairStartTime(), timeFormat);
        String endTime = DateUtil.format(bo.getRepairEndTime(), timeFormat);
        // 获取重复日期的列表
        List<Date> repeatTimeList = getRepeatTimeListByMonth(startDay, endDay, bo.getIsRepeat(), bo.getRepeatRule(), bo.getRepeatTime());
        if (repeatTimeList.size() == 0){
            throw new ServiceException("计划日期范围内没有符合当前重复规则的日期");
        }

        List<RepairTaskInfo> addList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        // 若重复日期有x天 则新增x条符合重复规则的对应日期的检修任务
        for (Date day : repeatTimeList) {
            RepairTaskInfo repairTaskInfo = new RepairTaskInfo();
            repairTaskInfo.setRepairPlanId(add.getRepairPlanId());
            repairTaskInfo.setTaskName(add.getRepairPlanName() + "-" + sdf.format(day));
            repairTaskInfo.setRepairStation(add.getRepairStation());
            repairTaskInfo.setTaskDate(day);
            repairTaskInfo.setTaskStartTime(DateUtil.parse(sdf2.format(day) + " " + startTime));
            repairTaskInfo.setTaskEndTime(DateUtil.parse(sdf2.format(day) + " " + endTime));
            // 0 待提交
            repairTaskInfo.setTaskStatus("0");
            addList.add(repairTaskInfo);
        }
        int result = repairTaskInfoMapper.batchInsert(addList);
        if (result != addList.size()){
            throw new ServiceException("批量插入异常");
        }
        return flag;
    }

    /**
     * 作废
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancel(RepairPlanInfoCancelBo cancelBo) {
        // 查询检修计划
        RepairPlanInfo repairPlanInfo = repairPlanInfoMapper.selectById(cancelBo.getPlanId());
        if (repairPlanInfo == null){
            throw new ServiceException("该检修计划不存在");
        }
        if (repairPlanInfo.getCancelDay() != null){
            throw new ServiceException("该检修计划已作废，不能重复操作");
        }

        // 查询大于作废日期的检修任务列表
        LambdaQueryWrapper<RepairTaskInfo> lqw1 = new LambdaQueryWrapper<>();
        lqw1.ge(RepairTaskInfo::getTaskDate, cancelBo.getCancelStartDate());
        List<RepairTaskInfo> taskList = repairTaskInfoMapper.selectList(lqw1);
        if (taskList == null || taskList.size() == 0){
            throw new ServiceException("作废开始日期应该处于检修计划日期范围内");
        }
        // 查询是否存在已进行提交(非待提交)的检修任务
        List<RepairTaskInfo> filteredList = taskList.stream()
                .filter(task -> !"0".equals(task.getTaskStatus()))
                .collect(Collectors.toList());
        if (filteredList.size() != 0){
            throw new ServiceException("作废日期范围内已存在检修任务进行了提交操作，请重新选择");
        }

        // 获取检修任务ID列表
        List<Integer> taskIdList = taskList.stream()
                .map(RepairTaskInfo::getRepairTaskId)
                .collect(Collectors.toList());

        // 查询检修任务下是否存在检修事件
        LambdaQueryWrapper<RepairEventInfo> lqw2 = new LambdaQueryWrapper<>();
        lqw2.in(RepairEventInfo::getRepairTaskId, taskIdList);
        List<RepairEventInfo> eventInfoList = repairEventInfoMapper.selectList(lqw2);
        if (eventInfoList != null && eventInfoList.size() > 0){
            throw new ServiceException("作废日期范围内存在已和检修任务绑定的检修事件，请重新选择");
        }

        // 删除检修任务
        repairTaskInfoMapper.deleteBatchIds(taskIdList);

        // 更新检修计划
        RepairPlanInfo update = new RepairPlanInfo();
        update.setRepairPlanId(cancelBo.getPlanId());
        update.setCancelDay(cancelBo.getCancelStartDate());
        repairPlanInfoMapper.updateById(update);

        return true;
    }

    /**
     * 获取重复时间列表
     */
    private List<Date> getRepeatTimeListByMonth(Date startDay, Date endDay, String isRepeat, String repeatRule, String repeatTime) {

        List<Date> repeatTimeList = new ArrayList<>();

        // 若为单次 则直接返回起始日期
        if ("0".equals(isRepeat)){
            repeatTimeList.add(startDay);
            return repeatTimeList;
        }

        int x = 0; int y = 0;
        if ("月".equals(repeatRule)){
            String[] repeatTimeArr = repeatTime.split(",");
            // 第x个周y
             x = Integer.parseInt(repeatTimeArr[0]);
             y = Integer.parseInt(repeatTimeArr[1]);
        }else if ("周".equals(repeatRule)){
            y = Integer.parseInt(repeatTime);
        }

        if (y == 7){
            y = 1;
        }else {
            y = y + 1;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDay);
        switch (repeatRule){
            case "月":
                while (calendar.getTime().before(endDay) || calendar.getTime().equals(endDay)) {
                    int count = 0;
                    while (count < x) {
                        if (calendar.get(Calendar.DAY_OF_WEEK) == y) {
                            count++;
                            if (count == x){
                                // 如果在日期范围内 则添加
                                if (calendar.getTime().before(endDay) || calendar.getTime().equals(endDay)){
                                    repeatTimeList.add(calendar.getTime());
                                }
                            }
                        }
                        // 增加一天
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    // 移动到下个月的开始
                    calendar.add(Calendar.MONTH, 1);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                }
                break;
            case "周":
                while (calendar.getTime().before(endDay) || calendar.getTime().equals(endDay)) {
                    if (calendar.get(Calendar.DAY_OF_WEEK) == y){
                        repeatTimeList.add(calendar.getTime());
                    }
                    // 增加一天
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
                break;
            case "日":
                while (calendar.getTime().before(endDay) || calendar.getTime().equals(endDay)) {
                    repeatTimeList.add(calendar.getTime());
                    // 增加一天
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
                break;
            default:
                throw new ServiceException("请选择正确的重复规则");
        }

        return repeatTimeList;
    }


}
