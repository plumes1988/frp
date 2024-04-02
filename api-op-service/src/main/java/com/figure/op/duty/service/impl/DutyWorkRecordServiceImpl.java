package com.figure.op.duty.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.device.domain.DeviceTypeInfo;
import com.figure.op.dict.domain.DictDataDO;
import com.figure.op.dict.service.DictDataService;
import com.figure.op.duty.domain.*;
import com.figure.op.duty.domain.bo.DutyWorkRecordBo;
import com.figure.op.duty.domain.bo.DutyWorkRecordQueryBo;
import com.figure.op.duty.domain.vo.*;
import com.figure.op.duty.mapper.*;
import com.figure.op.duty.service.IDutyWorkRecordService;
import com.figure.op.system.domain.SysUserInfo;
import com.figure.op.system.helper.LoginHelper;
import com.figure.op.system.mapper.SysUserInfoMapper;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class DutyWorkRecordServiceImpl implements IDutyWorkRecordService {

    @Resource
    private DutyWorkRecordMapper dutyWorkRecordMapper;

    @Resource
    private DutyScheduleInfoServiceImpl dutyScheduleInfoService;

    @Resource
    private DutyInfoServiceImpl dutyInfoService;

    @Resource
    private DictDataService dictDataService;

    @Resource
    private DutyScheduleInfoMapper dutyScheduleInfoMapper;

    @Resource
    private DutyScheduleTaskMapper dutyScheduleTaskMapper;

    @Resource
    private DutyWorkRecordWorkerMapper dutyWorkRecordWorkerMapper;

    @Resource
    private DutyInfoMapper dutyInfoMapper;

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    /**
     * 分页列表
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页
     */
    @Override
    public TableDataInfo<DutyWorkRecordPageVo> page(DutyWorkRecordQueryBo queryBo, PageQuery pageQuery) {
        // 查询班次属性列表
        List<DictDataDO>  shiftAttributeList = dictDataService.getDictDataList("shift_attribute");
        // 列表转Map
        Map<String, String> shiftAttributeMap = shiftAttributeList.stream()
                .collect(Collectors.toMap(DictDataDO::getValue, DictDataDO::getLabel));

        // 查询值班任务类型列表
        List<DictDataDO>  dutyTypeList = dictDataService.getDictDataList("task_type");
        // 列表转Map
        Map<String, String> dutyTypeMap = dutyTypeList.stream()
                .collect(Collectors.toMap(DictDataDO::getValue, DictDataDO::getLabel));

        Page<DutyWorkRecordPageVo> result = dutyWorkRecordMapper.selectVoPage(pageQuery.build(), queryBo);
        result.getRecords().forEach(item -> {
            item.setScheduleAttrName(shiftAttributeMap.get(item.getScheduleAttr()));
            item.setDutyType(dutyTypeMap.get(item.getDutyType()));
        });
        return TableDataInfo.build(result);
    }

    /**
     * 全部列表
     */
    @Override
    public List<DutyWorkRecordListVo> queryList(DutyWorkRecordQueryBo queryBo) {
        return dutyWorkRecordMapper.selectVoList(queryBo);
    }

    /**
     * 详情
     */
    @Override
    public DutyWorkRecordVo queryById(Integer workRecordId) {
        DutyWorkRecord dutyWorkRecord = dutyWorkRecordMapper.selectById(workRecordId);
        if (dutyWorkRecord == null){
            throw new ServiceException("该记录不存在！");
        }
        DutyWorkRecordVo dutyWorkRecordVo = BeanUtil.toBean(dutyWorkRecord, DutyWorkRecordVo.class);
        DutyScheduleInfo dutyScheduleInfo = dutyScheduleInfoMapper.selectById(dutyWorkRecord.getScheduleId());
        if (dutyScheduleInfo != null){
            dutyWorkRecordVo.setScheduleAttr(dutyScheduleInfo.getScheduleAttr());
            dutyWorkRecordVo.setScheduleName(dutyScheduleInfo.getScheduleName());
        }

        DutyInfo dutyInfo = dutyInfoMapper.selectById(dutyWorkRecord.getDutyId());
        if (dutyInfo != null){
            dutyWorkRecordVo.setDutyName(dutyInfo.getDutyName());
            dutyWorkRecordVo.setDutyType(dutyInfo.getDutyType());
        }

        if (dutyWorkRecord.getWorkerIds() != null && !"".equals(dutyWorkRecord.getWorkerIds())){
            List<Integer> workIdList = Arrays.stream(dutyWorkRecord.getWorkerIds().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            LambdaQueryWrapper<SysUserInfo> lqw = new LambdaQueryWrapper<>();
            lqw.in(SysUserInfo::getUserId, workIdList);
            List<SysUserInfo> sysUserInfoList = sysUserInfoMapper.selectList(lqw);
            String workerNames = sysUserInfoList.stream()
                    .map(SysUserInfo::getUserName)
                    .collect(Collectors.joining(","));
            dutyWorkRecordVo.setWorkerNames(workerNames);
        }

        SysUserInfo sysUserInfo = sysUserInfoMapper.selectById(dutyWorkRecord.getActWorkerId());
        if (sysUserInfo != null){
            dutyWorkRecordVo.setActWorkerName(sysUserInfo.getUserName());
        }

        DictDataDO shiftAttribute = dictDataService.getDictData("shift_attribute", dutyWorkRecordVo.getScheduleAttr());
        if (shiftAttribute != null) {
            dutyWorkRecordVo.setScheduleAttrName(shiftAttribute.getLabel());
        }
        DictDataDO dutyType = dictDataService.getDictData("task_type", dutyWorkRecordVo.getDutyType());
        if (dutyType != null) {
            dutyWorkRecordVo.setDutyType(dutyType.getLabel());
        }
        return dutyWorkRecordVo;
    }

    /**
     * 新增值班记录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(DutyWorkRecordBo bo) {
        // 获取当前日期
        Date currentDate = DateUtil.beginOfDay(DateUtil.date());
        // 查询排班计划
        DutyScheduleInfo dutyScheduleInfo = dutyScheduleInfoMapper.selectById(bo.getScheduleId());
        if (dutyScheduleInfo == null){
            throw new ServiceException("该排班计划不存在");
        }
        // 查询当前日期排班任务
        LambdaQueryWrapper<DutyScheduleTask> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DutyScheduleTask::getScheduleId, bo.getScheduleId());
        lqw.eq(DutyScheduleTask::getScheduleDate, currentDate);
        lqw.last("limit 1");
        DutyScheduleTask dutyScheduleTask = dutyScheduleTaskMapper.selectOne(lqw);
        if (dutyScheduleTask == null){
            throw new ServiceException("所属排班计划下没有今天的排班任务");
        }

        // 新增值班记录
        DutyWorkRecord add = BeanUtil.toBean(bo, DutyWorkRecord.class);
        add.setScheduleTaskId(dutyScheduleTask.getScheduleTaskId());
        add.setScheduleDate(currentDate);
        add.setRecordTime(currentDate);
        add.setCate("2");
        add.setWorkerIds(LoginHelper.getUserId().toString());
        // 新增即执行
        add.setActWorkerId(LoginHelper.getUserId());
        add.setActResult("1");
        dutyWorkRecordMapper.insert(add);

        // 新增值班记录和值班人员关联表
        DutyWorkRecordWorker dutyWorkRecordWorker = new DutyWorkRecordWorker();
        dutyWorkRecordWorker.setWorkRecordId(add.getWorkRecordId());
        dutyWorkRecordWorker.setWorkerId(LoginHelper.getUserId());
        dutyWorkRecordWorkerMapper.insert(dutyWorkRecordWorker);

        return true;
    }

    /**
     * 更新值班记录
     */
    @Override
    public Boolean updateByBo(DutyWorkRecordBo bo) {
        // 查询是否是当天 非当天不允许修改
        DutyWorkRecord dutyWorkRecord = dutyWorkRecordMapper.selectById(bo.getWorkRecordId());
        if (dutyWorkRecord == null){
            throw new ServiceException("值班记录不存在！");
        }
        LocalDate scheduleDate = dutyWorkRecord.getScheduleDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (!LocalDate.now().equals(scheduleDate)){
            throw new ServiceException("仅当天的值班记录才允许修改");
        }
        DutyWorkRecord update = BeanUtil.toBean(bo, DutyWorkRecord.class);
        // 排班计划和值班人员不允许修改
        update.setScheduleId(null);
        update.setWorkerIds(null);
        update.setActTime(new Date());
        update.setActResult("1");
        update.setActWorkerId(LoginHelper.getUserId());
        return dutyWorkRecordMapper.updateById(update) > 0;
    }

    /**
     * 删除值班记录
     */
    @Override
    public Boolean deleteById(Integer id) {
        DutyWorkRecord dutyWorkRecord = dutyWorkRecordMapper.selectById(id);
        if ("1".equals(dutyWorkRecord.getCate())){
            throw new ServiceException("该值班记录为排班计划自动生成，不能删除");
        }
        return dutyWorkRecordMapper.deleteById(id) > 0;
    }

}
