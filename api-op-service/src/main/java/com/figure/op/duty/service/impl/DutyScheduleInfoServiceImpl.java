package com.figure.op.duty.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.cameramanager.domain.FrontStationInfo;
import com.figure.op.cameramanager.mapper.FrontStationInfoMapper;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.dict.domain.DictDataDO;
import com.figure.op.dict.service.DictDataService;
import com.figure.op.duty.domain.*;
import com.figure.op.duty.domain.bo.*;
import com.figure.op.duty.domain.vo.*;
import com.figure.op.duty.mapper.*;
import com.figure.op.duty.service.IDutyScheduleInfoService;
import com.figure.op.system.mapper.SysUserInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 排班计划实现
 * @author fsn
 */
@Service
public class DutyScheduleInfoServiceImpl implements IDutyScheduleInfoService {

    @Resource
    private DutyScheduleInfoMapper dutyScheduleInfoMapper;

    @Resource
    private DutyWorkRecordMapper dutyWorkRecordMapper;

    @Resource
    private DutyScheduleInfoDutyMapper dutyScheduleInfoDutyMapper;

    @Resource
    private DutyScheduleTaskWorkerMapper dutyScheduleTaskWorkerMapper;

    @Resource
    private DutyScheduleTaskMapper dutyScheduleTaskMapper;

    @Resource
    private DutyWorkRecordWorkerMapper dutyWorkRecordWorkerMapper;

    @Resource
    private DutyScheduleTaskAttendanceMapper dutyScheduleTaskAttendanceMapper;

    @Resource
    private DictDataService dictDataService;

    @Resource
    private FrontStationInfoMapper stationInfoMapper;

    /**
     * 排班计划分页列表
     */
    @Override
    public TableDataInfo<DutyScheduleInfoPageVo> queryPage(DutyScheduleInfoQueryBo queryBo, PageQuery pageQuery) {
        Page<DutyScheduleInfoPageVo> result = dutyScheduleInfoMapper.selectVoPage(pageQuery.build(), queryBo);
        result.getRecords().forEach(item -> {
            DictDataDO shiftAttribute = dictDataService.getDictData("shift_attribute", item.getScheduleAttr());
            if (shiftAttribute != null) {
                item.setScheduleAttrName(shiftAttribute.getLabel());
            }
        });
        return TableDataInfo.build(result);
    }

    /**
     * 排班计划全部列表
     */
    @Override
    public List<DutyScheduleInfoListVo> queryList() {
        LambdaQueryWrapper<DutyScheduleInfo> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(DutyScheduleInfo::getScheduleId);
        List<DutyScheduleInfo> list = dutyScheduleInfoMapper.selectList(lqw);
        return BeanCopyUtils.copyList(list, DutyScheduleInfoListVo.class);
    }

    /**
     * 查询指定台站的当天排班任务
     */
    @Override
    public DutyScheduleTaskVo queryTaskByStationIdAndSameTime(Integer stationId){

        // 获取当前日期
        Date currentDate = DateUtil.beginOfDay(DateUtil.date());
        // 获取当前时间
        LocalTime currentTime = LocalTime.now();

        // 查询指定台站下、符合日期范围排班计划（业务限制保证唯一）
        LambdaQueryWrapper<DutyScheduleInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DutyScheduleInfo::getStationId, stationId);
        lqw.le(DutyScheduleInfo::getStartDay, currentDate);
        lqw.ge(DutyScheduleInfo::getEndDay, currentDate);
        lqw.le(DutyScheduleInfo::getStartTime, currentTime);
        lqw.ge(DutyScheduleInfo::getEndTime, currentTime);
        lqw.last("limit 1");
        DutyScheduleInfo dutyScheduleInfo = dutyScheduleInfoMapper.selectOne(lqw);
        if (dutyScheduleInfo == null){
            throw new ServiceException("当前台站下未找到符合当前日期和时间段的排班计划");
        }

        // 查询排班任务
        DutyScheduleTask dutyScheduleTask =  dutyScheduleTaskMapper.selectOne(new LambdaQueryWrapper<DutyScheduleTask>()
                .eq(DutyScheduleTask::getScheduleId, dutyScheduleInfo.getScheduleId())
                .eq(DutyScheduleTask::getScheduleDate, currentDate)
                .last("limit 1"));

        DutyScheduleTaskVo dutyScheduleTaskVo = BeanCopyUtils.copy(dutyScheduleTask, DutyScheduleTaskVo.class);
        if (dutyScheduleTaskVo == null){
            throw new ServiceException("当前台站下未找到符合当前日期和时间段的排班任务");
        }

        // 填充排班计划信息
        dutyScheduleTaskVo.setScheduleId(dutyScheduleInfo.getScheduleId());
        dutyScheduleTaskVo.setScheduleName(dutyScheduleInfo.getScheduleName());
        dutyScheduleTaskVo.setScheduleAttr(dutyScheduleInfo.getScheduleAttr());
        DictDataDO shiftAttribute = dictDataService.getDictData("shift_attribute", dutyScheduleInfo.getScheduleAttr());
        if (shiftAttribute != null) {
            dutyScheduleTaskVo.setScheduleAttrName(shiftAttribute.getLabel());
        }
        dutyScheduleTaskVo.setStartTime(dutyScheduleInfo.getStartTime());
        dutyScheduleTaskVo.setEndTime(dutyScheduleInfo.getEndTime());

        // 填充台站名称
        dutyScheduleTaskVo.setStationId(dutyScheduleInfo.getStationId());
        FrontStationInfo stationInfo = stationInfoMapper.selectById(dutyScheduleInfo.getStationId());
        if (stationInfo != null){
            dutyScheduleTaskVo.setStationName(stationInfo.getFrontName());
        }

        return dutyScheduleTaskVo;
    }

    /**
     * 排班任务列表
     */
    @Override
    public List<DutyScheduleTaskListVo> queryTaskList(DutyScheduleTaskQueryBo queryBo) {
        if(queryBo.getStartDay() == null || queryBo.getEndDay() == null){
            throw new ServiceException("必须传入排班任务的开始和结束日期");
        }
        // 计算天数差异
        int dayDiff = (int) DateUtil.betweenDay(queryBo.getStartDay(), queryBo.getEndDay(), false) + 1;
        if (dayDiff < 1){
            throw new ServiceException("结束日期必须大于开始日期");
        }
        if (dayDiff > 60){
            throw new ServiceException("日期范围请选择60天内");
        }

        // 查询排班任务列表
        List<DutyScheduleTaskListVo> dutyScheduleTaskList = dutyScheduleTaskMapper.selectVoList(queryBo);
        String taskIdStr = dutyScheduleTaskList.stream()
                .map(DutyScheduleTaskListVo::getScheduleTaskId)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        // 查询关联的值班人员
        List<DutyScheduleTaskWorkerListVo> workerList = dutyScheduleTaskWorkerMapper.selectVoList(null, taskIdStr);
        // 查询关联的考勤记录
        DutyScheduleTaskAttendanceQueryBo attQueryBo = new DutyScheduleTaskAttendanceQueryBo();
        attQueryBo.setScheduleTaskIds(taskIdStr);
        List<DutyScheduleTaskAttendanceListVo> attendanceList = dutyScheduleTaskAttendanceMapper.selectVoList(attQueryBo);

        // 查询关联的值班记录
        DutyWorkRecordQueryBo workRecordQueryBo = new DutyWorkRecordQueryBo();
        workRecordQueryBo.setScheduleTaskIds(taskIdStr);
        List<DutyWorkRecordListVo> dutyWorkRecordList = dutyWorkRecordMapper.selectVoList(workRecordQueryBo);

        // 查询班次属性列表
        List<DictDataDO>  shiftAttributeList = dictDataService.getDictDataList("shift_attribute");
        // 列表转Map
        Map<String, String> shiftAttributeMap = shiftAttributeList.stream()
                .collect(Collectors.toMap(DictDataDO::getValue, DictDataDO::getLabel));

        // 遍历排班任务列表
        for (DutyScheduleTaskListVo scheduleTaskListVo : dutyScheduleTaskList) {
            // 转换班次属性
            scheduleTaskListVo.setScheduleAttrName(shiftAttributeMap.get(scheduleTaskListVo.getScheduleAttr()));

            Integer taskId = scheduleTaskListVo.getScheduleTaskId();
            /* 值班人员 */
            // 查询当前排班任务的值班人员
            List<DutyScheduleTaskWorkerListVo> taskWorkerList = workerList.stream()
                    .filter(worker -> worker.getScheduleTaskId().equals(taskId))
                    .collect(Collectors.toList());
            // 查询当前排班任务里异常考勤记录里的值班人员ID列表
            String expStatusStr = "1,2";
            List<Integer> attWorkerIdList = attendanceList.stream()
                    .filter(attendance -> attendance.getScheduleTaskId().equals(taskId))
                    .filter(attendance -> expStatusStr.contains(attendance.getDutyStatus()))
                    .map(DutyScheduleTaskAttendanceListVo::getWorkerId)
                    .collect(Collectors.toList());
            List<DutyScheduleTaskWorkerAttVo> workerAttList = BeanCopyUtils.copyList(taskWorkerList, DutyScheduleTaskWorkerAttVo.class);
            if (workerAttList != null){
                // 总值班人数
                String workerPercent2 = String.valueOf(workerAttList.size());
                // 初始化正常考勤数量
                int i = 0;
                // 遍历值班人员
                for (DutyScheduleTaskWorkerAttVo attVo : workerAttList) {
                    // 若存在异常考勤
                    if (attWorkerIdList.contains(attVo.getWorkerId())) {
                        attVo.setAttStatus("1");
                    }else {
                        i = i + 1;
                        attVo.setAttStatus("0");
                    }
                }
                String workerPercent1 = String.valueOf(i);
                scheduleTaskListVo.setWorkerPercent(workerPercent1 + "/" + workerPercent2);
                scheduleTaskListVo.setWorkerAttList(workerAttList);
            }

            /* 值班记录1 */
            // 查询当前排班任务的值班记录（排班计划自动生成）
            List<DutyWorkRecordListVo> taskWorkRecordList1 = dutyWorkRecordList.stream()
                    .filter(workRecord -> workRecord.getScheduleTaskId().equals(taskId))
                    .filter(workRecord -> "1".equals(workRecord.getCate()))
                    .collect(Collectors.toList());
            List<DutyScheduleTaskWorkStatusVo> workerRecordList1 = BeanCopyUtils.copyList(taskWorkRecordList1, DutyScheduleTaskWorkStatusVo.class);
            if (workerRecordList1 != null){
                // 总值班记录数量
                String workerRecordPercent2 = String.valueOf(workerRecordList1.size());
                // 初始化已完成数量
                int i = 0;
                for (DutyScheduleTaskWorkStatusVo workStatusVo : workerRecordList1) {
                    if ("1".equals(workStatusVo.getActResult())){
                        i = i + 1;
                    }
                }
                String workerRecordPercent1 = String.valueOf(i);
                scheduleTaskListVo.setWorkerRecordPercent(workerRecordPercent1 + "/" + workerRecordPercent2);
                scheduleTaskListVo.setWorkerRecordList1(workerRecordList1);
            }

            /* 值班记录2 */
            // 查询当前排班任务的值班记录（手动添加）
            List<DutyWorkRecordListVo> taskWorkRecordList2 = dutyWorkRecordList.stream()
                    .filter(workRecord -> workRecord.getScheduleTaskId().equals(taskId))
                    .filter(workRecord -> "2".equals(workRecord.getCate()))
                    .collect(Collectors.toList());
            List<DutyScheduleTaskWorkStatusVo> workerRecordList2 = BeanCopyUtils.copyList(taskWorkRecordList2, DutyScheduleTaskWorkStatusVo.class);
            if (workerRecordList2 != null){
                // 总值班记录数量
                String workerRecordCount = String.valueOf(workerRecordList2.size());
                scheduleTaskListVo.setWorkerRecordCount(workerRecordCount);
                scheduleTaskListVo.setWorkerRecordList2(workerRecordList2);
            }
        }

        return dutyScheduleTaskList;
    }


    /**
     * 值班人员列表
     */
    @Override
    public List<DutyScheduleTaskWorkerListVo> queryWorkerList(Integer scheduleTaskId) {
        return dutyScheduleTaskWorkerMapper.selectVoList(scheduleTaskId, null);
    }

    /**
     * 新增排班计划
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(DutyScheduleInfoBo bo) {
        boolean validateRes = validateScheduleInfoAllowAdd(bo);
        if (!validateRes){
            throw new ServiceException("同一台站下排班计划内相同日期的时间段不允许重复");
        }
        // 校验值班任务是否出现重复
        String dutyIds = bo.getDutyIds();
        String[] dutyIdsArray = dutyIds.split(",");
        Set<String> dutyIdsSet = new HashSet<>(Arrays.asList(dutyIdsArray));
        if (dutyIdsArray.length != dutyIdsSet.size()){
            throw new ServiceException("值班任务不允许重复添加同一个");
        }
        DutyScheduleInfo dutyScheduleInfo = BeanUtil.toBean(bo, DutyScheduleInfo.class);
        // 计算排班总时长和天数
        int totalDay;
        int totalTime;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            // 计算排班天数
            totalDay = (int) DateUtil.between(bo.getStartDay(), bo.getEndDay(), DateUnit.DAY, false) + 1;
            if (totalDay < 1){
                throw new ServiceException("排班结束日期必须大于开始日期");
            }
            int maxTotalDay = 366;
            if (totalDay > maxTotalDay){
                throw new ServiceException("排班日期范围请选择一年(366天)以内");
            }
            // 计算两个时间的小时差，向上取整
            int diff1 = (int) DateUtil.between(sdf.parse(bo.getStartTime()), sdf.parse(bo.getEndTime()), DateUnit.HOUR, false);
            totalTime = diff1 * totalDay;
            if (totalTime <= 0){
                throw new ServiceException("排班结束时间必须大于开始时间");
            }
            dutyScheduleInfo.setTime(diff1);
            dutyScheduleInfo.setTotalTime(totalTime);
            dutyScheduleInfo.setTotalDay(totalDay);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ServiceException("操作异常："+ e.getMessage());
        }
        // 新增排班计划
        boolean flag = dutyScheduleInfoMapper.insert(dutyScheduleInfo) > 0;
        if (flag) {
            bo.setScheduleId(dutyScheduleInfo.getScheduleId());
            int scheduleId = dutyScheduleInfo.getScheduleId();
            // 排班计划-值班任务关联表
            for (String dutyIdStr : dutyIdsArray) {
                DutyScheduleInfoDuty dutyScheduleInfoDuty = new DutyScheduleInfoDuty();
                dutyScheduleInfoDuty.setScheduleId(scheduleId);
                dutyScheduleInfoDuty.setDutyId(Integer.valueOf(dutyIdStr));
                dutyScheduleInfoDutyMapper.insert(dutyScheduleInfoDuty);
            }
            // 若排班天数有x天 则新增x条对应日期的排班任务
            Date day = bo.getStartDay();
            for (int i = 0; i < totalDay; i++) {
                DutyScheduleTask scheduleTask = new DutyScheduleTask();
                scheduleTask.setScheduleId(scheduleId);
                scheduleTask.setScheduleDate(day);
                dutyScheduleTaskMapper.insert(scheduleTask);
                int scheduleTaskId = scheduleTask.getScheduleTaskId();
                // 若值班任务有n个 则每个排班任务下 新增n个值班记录
                for (String dutyIdStr : dutyIdsArray) {
                    DutyWorkRecord dutyWorkRecord = new DutyWorkRecord();
                    dutyWorkRecord.setScheduleId(scheduleId);
                    dutyWorkRecord.setScheduleTaskId(scheduleTaskId);
                    dutyWorkRecord.setScheduleDate(day);
                    dutyWorkRecord.setRecordTime(day);
                    dutyWorkRecord.setDutyId(Integer.valueOf(dutyIdStr));
                    dutyWorkRecordMapper.insert(dutyWorkRecord);
                }
                // 下一天
                day = DateUtil.offsetDay(day, 1);
            }
        }
        return flag;
    }


    /**
     * 作废排班计划
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancel(DutyScheduleInfoCancelBo cancelBo) {

        // 查询排班计划
        DutyScheduleInfo dutyScheduleInfo = dutyScheduleInfoMapper.selectById(cancelBo.getScheduleId());
        if (dutyScheduleInfo == null){
            throw new ServiceException("该排班计划不存在");
        }
        if (dutyScheduleInfo.getCancelDay() != null){
            throw new ServiceException("该排班计划已作废，不能重复操作");
        }

        // 查询大于作废日期的排班任务列表
        LambdaQueryWrapper<DutyScheduleTask> lqw1 = new LambdaQueryWrapper<>();
        lqw1.ge(DutyScheduleTask::getScheduleDate, cancelBo.getCancelStartDate());
        List<DutyScheduleTask> scheduleTaskList = dutyScheduleTaskMapper.selectList(lqw1);
        if (scheduleTaskList == null || scheduleTaskList.size() == 0){
            throw new ServiceException("作废开始日期应该处于排班计划时间范围内");
        }
        // 获取排班任务ID列表
        List<Integer> taskIdList = scheduleTaskList.stream()
                .map(DutyScheduleTask::getScheduleTaskId)
                .collect(Collectors.toList());

        // 查询排班任务是否存在考勤记录
        LambdaQueryWrapper<DutyScheduleTaskAttendance> lqw2 = new LambdaQueryWrapper<>();
        lqw2.in(DutyScheduleTaskAttendance::getScheduleTaskId, taskIdList);
        List<DutyScheduleTaskAttendance> attendanceList = dutyScheduleTaskAttendanceMapper.selectList(lqw2);
        if (attendanceList != null && attendanceList.size() > 0){
            throw new ServiceException("作废日期范围内存在考勤记录，请重新选择");
        }
        // 查询值班记录列表
        LambdaQueryWrapper<DutyWorkRecord> lqw3 = new LambdaQueryWrapper<>();
        lqw3.in(DutyWorkRecord::getScheduleTaskId, taskIdList);
        List<DutyWorkRecord> workRecordList = dutyWorkRecordMapper.selectList(lqw3);
        // 获取值班记录ID列表
        List<Integer> workRecordIdList = workRecordList.stream()
                .map(DutyWorkRecord::getWorkRecordId)
                .collect(Collectors.toList());
        // 是否存在已执行的记录
        boolean existComplete = workRecordList.stream()
                .anyMatch(record -> "1".equals(record.getActResult()));
        if (existComplete){
            throw new ServiceException("作废日期范围内存在已完成的值班记录，请重新选择");
        }

        // 删除排班任务
        dutyScheduleTaskMapper.deleteBatchIds(taskIdList);
        // 删除值班记录
        dutyWorkRecordMapper.deleteBatchIds(workRecordIdList);

        // 更新排班计划
        DutyScheduleInfo update = new DutyScheduleInfo();
        update.setScheduleId(cancelBo.getScheduleId());
        update.setCancelDay(cancelBo.getCancelStartDate());
        dutyScheduleInfoMapper.updateById(update);

        return true;
    }

    /**
     * 新增值班人员
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertWorker(DutyScheduleTaskWorkerBo bo) {
        // 校验值班人员是否出现重复
        String workerIds = bo.getWorkerIds();
        String[] workerIdsArray = workerIds.split(",");
        Set<String> workerIdsSet = new HashSet<>(Arrays.asList(workerIdsArray));
        if (workerIdsArray.length != workerIdsSet.size()){
            throw new ServiceException("值班人员不允许重复添加");
        }
        // 查询相同值班人员是否已存在
        LambdaQueryWrapper<DutyScheduleTaskWorker> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DutyScheduleTaskWorker::getScheduleTaskId, bo.getScheduleTaskId());
        lqw.in(DutyScheduleTaskWorker::getWorkerId, workerIdsSet);
        List<DutyScheduleTaskWorker> sameWorkerList = dutyScheduleTaskWorkerMapper.selectList(lqw);
        if (sameWorkerList != null && sameWorkerList.size() > 0){
            throw new ServiceException("值班人员不允许重复添加");
        }

        // 更新排班任务
        DutyScheduleTask dutyScheduleTask = new DutyScheduleTask();
        dutyScheduleTask.setScheduleTaskId(bo.getScheduleTaskId());
        dutyScheduleTask.setWorkerIds(workerIds);
        dutyScheduleTaskMapper.updateById(dutyScheduleTask);
        // 新增排班任务和值班人员关联表
        for (String workerIdStr : workerIdsSet) {
            DutyScheduleTaskWorker scheduleTaskWorker = new DutyScheduleTaskWorker();
            scheduleTaskWorker.setScheduleId(bo.getScheduleId());
            scheduleTaskWorker.setScheduleTaskId(bo.getScheduleTaskId());
            scheduleTaskWorker.setWorkerId(Integer.valueOf(workerIdStr));
            dutyScheduleTaskWorkerMapper.insert(scheduleTaskWorker);
        }

        // 更新值班记录
        LambdaQueryWrapper<DutyWorkRecord> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(DutyWorkRecord::getScheduleTaskId, bo.getScheduleTaskId());
        List<DutyWorkRecord> dutyWorkRecordList = dutyWorkRecordMapper.selectList(lqw2);
        for (DutyWorkRecord dutyWorkRecord : dutyWorkRecordList) {
            DutyWorkRecord updateDutyWorkRecord = new DutyWorkRecord();
            updateDutyWorkRecord.setWorkRecordId(dutyWorkRecord.getWorkRecordId());
            updateDutyWorkRecord.setWorkerIds(workerIds);
            dutyWorkRecordMapper.updateById(updateDutyWorkRecord);
            // 新增值班记录和值班人员关联表
            for (String workerIdStr : workerIdsSet) {
                DutyWorkRecordWorker dutyWorkRecordWorker = new DutyWorkRecordWorker();
                dutyWorkRecordWorker.setWorkRecordId(dutyWorkRecord.getWorkRecordId());
                dutyWorkRecordWorker.setWorkerId(Integer.valueOf(workerIdStr));
                dutyWorkRecordWorkerMapper.insert(dutyWorkRecordWorker);
            }
        }

        return true;
    }

    /**
     * 删除值班人员
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteWorker(Integer id) {
        // 查询排班任务和值班人员关联记录
        DutyScheduleTaskWorker dutyScheduleTaskWorker = dutyScheduleTaskWorkerMapper.selectById(id);
        if (dutyScheduleTaskWorker == null){
            throw new ServiceException("该记录不存在");
        }
        Integer scheduleTaskId = dutyScheduleTaskWorker.getScheduleTaskId();
        Integer workerId = dutyScheduleTaskWorker.getWorkerId();
        // 查询排班任务
        DutyScheduleTask dutyScheduleTask = dutyScheduleTaskMapper.selectById(scheduleTaskId);
        String[] workerIdArray = dutyScheduleTask.getWorkerIds().split(",");
        List<String> workerIdList = new ArrayList<>(Arrays.asList(workerIdArray));
        // 移除指定值班人员
        workerIdList.remove(workerId.toString());
        String workerIdString = String.join(",", workerIdList);

        // 更新排班任务
        DutyScheduleTask updateDutyScheduleTask = new DutyScheduleTask();
        updateDutyScheduleTask.setScheduleTaskId(scheduleTaskId);
        updateDutyScheduleTask.setWorkerIds(workerIdString);
        dutyScheduleTaskMapper.updateById(updateDutyScheduleTask);
        // 删除排班任务和值班人员关联表
        dutyScheduleTaskWorkerMapper.deleteById(id);

        // 更新值班记录
        LambdaQueryWrapper<DutyWorkRecord> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(DutyWorkRecord::getScheduleTaskId, scheduleTaskId);
        List<DutyWorkRecord> dutyWorkRecordList = dutyWorkRecordMapper.selectList(lqw2);
        for (DutyWorkRecord dutyWorkRecord : dutyWorkRecordList) {
            DutyWorkRecord updateDutyWorkRecord = new DutyWorkRecord();
            updateDutyWorkRecord.setWorkRecordId(dutyWorkRecord.getWorkRecordId());
            updateDutyWorkRecord.setWorkerIds(workerIdString);
            dutyWorkRecordMapper.updateById(updateDutyWorkRecord);
            // 删除值班记录和值班人员关联表
            LambdaQueryWrapper<DutyWorkRecordWorker> lqw3 = new LambdaQueryWrapper<>();
            lqw3.eq(DutyWorkRecordWorker::getWorkRecordId, dutyWorkRecord.getWorkRecordId());
            lqw3.eq(DutyWorkRecordWorker::getWorkerId, workerId);
            dutyWorkRecordWorkerMapper.delete(lqw3);
        }

        return true;
    }

    /**
     * 新增考勤
     */
    @Override
    public Boolean insertAttendance(DutyScheduleTaskAttendanceBo bo) {
        // 查询排班信息
        DutyScheduleInfo dutyScheduleInfo = dutyScheduleInfoMapper.selectById(bo.getScheduleId());
        if (dutyScheduleInfo == null){
            throw new ServiceException("该排班计划不存在");
        }
        DutyScheduleTaskAttendance add = new DutyScheduleTaskAttendance();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String scheduleStartTimeStr = dateFormat1.format(dutyScheduleInfo.getStartDay()) + " " + dutyScheduleInfo.getStartTime();
        String scheduleEndTimeStr = dateFormat1.format(dutyScheduleInfo.getEndDay()) + " " + dutyScheduleInfo.getEndTime();
        switch (bo.getDutyStatus()){
            // 请假
            case "1":
                if (bo.getLeaveStartTime() == null || bo.getLeaveEndTime() == null){
                    throw new ServiceException("请假状态下必须选择请假起止时间");
                }
                // 请假起止日期需为同一天
                if (!DateUtil.isSameDay(bo.getLeaveStartTime(), bo.getLeaveEndTime())){
                    throw new ServiceException("请假起止日期需为同一天");
                }
                // 请假开始时间必须大于等于排班计划的开始时间
                try {
                    Date scheduleStartTimeDate = dateFormat2.parse(scheduleStartTimeStr);
                    if (bo.getLeaveStartTime().compareTo(scheduleStartTimeDate) < 0){
                        throw new ServiceException("请假开始时间必须大于等于排班计划开始时间");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // 请假结束时间必须小于等于排班计划的结束时间
                try {
                    Date scheduleEndTimeDate = dateFormat2.parse(scheduleEndTimeStr);
                    if (bo.getLeaveEndTime().compareTo(scheduleEndTimeDate) > 0){
                        throw new ServiceException("请假结束时间必须小于等于排班计划结束时间");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // 设置请假日期
                add.setLeaveDate(bo.getLeaveStartTime());
                // 计算请假时长
                Integer leaveTime = (int) DateUtil.between(bo.getLeaveStartTime(), bo.getLeaveEndTime(), DateUnit.HOUR, true);
                add.setLeaveTime(leaveTime);
                // 请假天数默认为1
                add.setLeaveDay(1);

                // 设置排班日期并查询对应的排班任务
                Date scheduleDate1 = DateUtil.beginOfDay(bo.getLeaveStartTime());
                LambdaQueryWrapper<DutyScheduleTask> lqw1 = new LambdaQueryWrapper<>();
                lqw1.eq(DutyScheduleTask::getScheduleId, bo.getScheduleId());
                lqw1.eq(DutyScheduleTask::getScheduleDate, scheduleDate1);
                DutyScheduleTask dutyScheduleTask1 = dutyScheduleTaskMapper.selectOne(lqw1);
                if (dutyScheduleTask1 == null){
                    throw new ServiceException("未查询到对应日期的排班任务");
                }
                if (dutyScheduleTask1.getWorkerIds() == null || "".equals(dutyScheduleTask1.getWorkerIds())){
                    throw new ServiceException("请先对该日期的排班任务设置值班人员");
                }
                add.setScheduleId(bo.getScheduleId());
                add.setWorkerId(bo.getWorkerId());
                add.setDutyStatus(bo.getDutyStatus());
                add.setLeaveStartTime(bo.getLeaveStartTime());
                add.setLeaveEndTime(bo.getLeaveEndTime());
                add.setScheduleDate(scheduleDate1);
                add.setScheduleTaskId(dutyScheduleTask1.getScheduleTaskId());
                break;
            // 旷工
            case "2":
                if (bo.getAbsentDate() == null){
                    throw new ServiceException("旷工必须选择旷工日期");
                }
                // 旷工日期必须大于等于排班开始日期 小于于等于排班结束日期
                if (bo.getAbsentDate().compareTo(dutyScheduleInfo.getStartDay()) < 0 || bo.getAbsentDate().compareTo(dutyScheduleInfo.getEndDay()) > 0){
                    throw new ServiceException("旷工日期必须处在排班日期范围内");
                }
                // 设置旷工开始时间和结束时间
                try {
                    String absentStartTimeStr = dateFormat1.format(bo.getAbsentDate()) + " " + dutyScheduleInfo.getStartTime();
                    String absentEndTimeStr = dateFormat1.format(bo.getAbsentDate()) + " " + dutyScheduleInfo.getEndTime();
                    Date absentStartTimeDate = dateFormat2.parse(absentStartTimeStr);
                    Date absentEndTimeDate = dateFormat2.parse(absentEndTimeStr);
                    add.setAbsentStartTime(absentStartTimeDate);
                    add.setAbsentEndTime(absentEndTimeDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // 旷工天数默认为1；单日旷工时长=单日排班时长=排班总时长/排班总天数
                add.setAbsentDay(1);
                add.setAbsentTime(dutyScheduleInfo.getTotalTime() / dutyScheduleInfo.getTotalDay());

                // 设置排班日期并查询对应的排班任务
                Date scheduleDate2 = bo.getAbsentDate();
                LambdaQueryWrapper<DutyScheduleTask> lqw2 = new LambdaQueryWrapper<>();
                lqw2.eq(DutyScheduleTask::getScheduleId, bo.getScheduleId());
                lqw2.eq(DutyScheduleTask::getScheduleDate, scheduleDate2);
                DutyScheduleTask dutyScheduleTask2 = dutyScheduleTaskMapper.selectOne(lqw2);
                if (dutyScheduleTask2 == null){
                    throw new ServiceException("未查询到对应日期的排班任务");
                }
                if (dutyScheduleTask2.getWorkerIds() == null || "".equals(dutyScheduleTask2.getWorkerIds())){
                    throw new ServiceException("请先对该日期的排班任务设置值班人员");
                }
                add.setScheduleId(bo.getScheduleId());
                add.setWorkerId(bo.getWorkerId());
                add.setDutyStatus(bo.getDutyStatus());
                add.setAbsentDate(bo.getAbsentDate());
                add.setScheduleDate(scheduleDate2);
                add.setScheduleTaskId(dutyScheduleTask2.getScheduleTaskId());
                break;
            default:
                throw new ServiceException("值班状态不合法");
        }

        return dutyScheduleTaskAttendanceMapper.insert(add) > 0;
    }

    /**
     * 查询考勤状态列表
     */
    @Override
    public TableDataInfo<DutyScheduleTaskAttendanceListVo> queryAttendancePage(DutyScheduleTaskAttendanceQueryBo queryBo, PageQuery pageQuery) {
        Page<DutyScheduleTaskAttendanceListVo> result = dutyScheduleTaskAttendanceMapper.selectVoPage(pageQuery.build(), queryBo);
        return TableDataInfo.build(result);
    }


    /**
     * 排班/值班统计分页列表
     */
    @Override
    public TableDataInfo<DutyScheduleStatisticsPageVo> queryStatisticsPage(DutyScheduleInfoStatisticsQueryBo queryBo, PageQuery pageQuery) {
        Page<DutyScheduleStatisticsPageVo> result = dutyScheduleInfoMapper.selectStatisticsPage(pageQuery.build(), queryBo);
        return TableDataInfo.build(result);
//        // 查询排班列表
//        DutyScheduleInfoQueryBo dutyScheduleInfoQueryBo = new DutyScheduleInfoQueryBo();
//        dutyScheduleInfoQueryBo.setWorkerName(queryBo.getWorkerName());
//        dutyScheduleInfoQueryBo.setStartDay(queryBo.getScheduleStartDate());
//        dutyScheduleInfoQueryBo.setEndDay(queryBo.getScheduleEndDate());
//        List<DutyScheduleInfoListVo> scheduleInfoListVoList = queryList(dutyScheduleInfoQueryBo);
//        if (scheduleInfoListVoList == null || scheduleInfoListVoList.size() == 0){
//            return TableDataInfo.build();
//        }
//        // 获取排班ID列表
//        List<Integer> scheduleIdList = scheduleInfoListVoList.stream()
//                .map(DutyScheduleInfoListVo::getScheduleId)
//                .collect(Collectors.toList());
//
//        // 查询值班人员统计分页列表
//        Page<DutyScheduleStatisticsPageVo> result = dutyScheduleInfoMapper.selectStatisticsPage(pageQuery.build(), queryBo);
//        // 遍历每个值班人员
//        for (DutyScheduleStatisticsPageVo record : result.getRecords()) {
//            // 查询考勤 计算实际请假时长、请假天数、旷工时长、旷工天数
//            LambdaQueryWrapper<DutyScheduleTaskAttendance> lqw = new LambdaQueryWrapper<>();
//            lqw.eq(DutyScheduleTaskAttendance::getWorkerId, record.getWorkerId());
//            lqw.in(DutyScheduleTaskAttendance::getScheduleId, scheduleIdList);
//            List<DutyScheduleTaskAttendance> attendanceList = dutyScheduleInfoAttendanceMapper.selectList(lqw);
//            int leaveDays = attendanceList.stream()
//                    .mapToInt(DutyScheduleTaskAttendance::getLeaveDay)
//                    .sum();
//            int leaveTimes = attendanceList.stream()
//                    .mapToInt(DutyScheduleTaskAttendance::getLeaveTime)
//                    .sum();
//            int absentDays = attendanceList.stream()
//                    .mapToInt(DutyScheduleTaskAttendance::getAbsentDay)
//                    .sum();
//            int absentTimes = attendanceList.stream()
//                    .mapToInt(DutyScheduleTaskAttendance::getAbsentTime)
//                    .sum();
//            record.setLeaveTimes(leaveTimes);
//            record.setAbsentTimes(absentTimes);
//            // 实际值班天数 = 计划值班天数 - 请假天数 - 旷工天数
//            int realDays = record.getPlanDays() - leaveDays - absentDays;
//            // 实际值班时长 = 计划值班时长 - 请假时长 - 旷工时长
//            int realTimes = record.getPlanTimes() - leaveTimes - absentTimes;
//            record.setRealDays(realDays);
//            record.setRealTimes(realTimes);
//        }

    }

    /**
     * 判断值班人员以及日期是否在范围内
     */
    private boolean isDateInRange(DutyScheduleTaskAttendance entry, Date day, Integer workerId) {

        boolean result0 = Objects.equals(workerId, entry.getWorkerId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (entry.getLeaveStartTime() != null && entry.getLeaveEndTime() != null){
            String leaveStartTimeStr = sdf.format(entry.getLeaveStartTime());
            String leaveEndTimeStr = sdf.format(entry.getLeaveEndTime());
            Date leaveStartTime = setTimeHms(leaveStartTimeStr, "00:00:00");
            Date leaveEndTime = setTimeHms(leaveEndTimeStr, "23:59:59");
            boolean result1 = DateUtil.isIn(day, leaveStartTime, leaveEndTime);
            return (result0 && result1);
        }else if (entry.getAbsentStartTime() != null && entry.getAbsentEndTime() != null){
            String absentStartTimeStr = sdf.format(entry.getAbsentStartTime());
            String absentEndTimeStr = sdf.format(entry.getAbsentEndTime());
            Date absentStartTime = setTimeHms(absentStartTimeStr, "00:00:00");
            Date absentEndTime = setTimeHms(absentEndTimeStr, "23:59:59");
            boolean result2 = DateUtil.isIn(day, absentStartTime, absentEndTime);
            return (result0 && result2);
        }else {
            return false;
        }
    }

    /**
     * 为日期设置指定时分秒
     */
    private Date setTimeHms(String dateStr, String hms){
        // 将日期字符串和时间字符串合并
        String dateTimeString = dateStr + " " + hms;
        return DateUtil.parse(dateTimeString);
    }

    /**
     * 校验排班计划是否允许添加
     */
    private Boolean validateScheduleInfoAllowAdd(DutyScheduleInfoBo bo){
        List<DutyScheduleInfoListVo> dutyScheduleInfoListVos = dutyScheduleInfoMapper.selectSameTimeList(bo);
        if (dutyScheduleInfoListVos != null && dutyScheduleInfoListVos.size() > 0){
            return false;
        }else {
            return true;
        }
    }
}
