package com.figure.op.duty.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.duty.domain.bo.*;
import com.figure.op.duty.domain.vo.*;

import java.util.List;

/**
 * 排班管理服务接口
 * @author fsn
 */
public interface IDutyScheduleInfoService {

    /**
     * 排班计划分页列表
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<DutyScheduleInfoPageVo> queryPage(DutyScheduleInfoQueryBo queryBo, PageQuery pageQuery);

    /**
     * 排班计划全部列表
     * @return 全部列表
     */
    List<DutyScheduleInfoListVo> queryList();

    /**
     * 排班任务列表
     * @param queryBo 查询对象
     * @return 列表
     */
    List<DutyScheduleTaskListVo> queryTaskList(DutyScheduleTaskQueryBo queryBo);

    /**
     * 排班任务-值班人员列表
     * @param scheduleTaskId 排班任务ID
     * @return 值班人员列表
     */
    List<DutyScheduleTaskWorkerListVo> queryWorkerList(Integer scheduleTaskId);

    /**
     * 新增排班计划
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(DutyScheduleInfoBo bo);

    /**
     * 作废排班计划
     * @param cancelBo 作废对象
     * @return 结果
     */
    Boolean cancel(DutyScheduleInfoCancelBo cancelBo);

    /**
     * 新增值班人员
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertWorker(DutyScheduleTaskWorkerBo bo);

    /**
     * 删除值班人员
     * @param id 主键ID
     * @return 结果
     */
    Boolean deleteWorker(Integer id);

    /**
     * 新增考勤
     * @param bo 考勤对象
     * @return 结果
     */
    Boolean insertAttendance(DutyScheduleTaskAttendanceBo bo);

    /**
     * 考勤状态分页列表
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<DutyScheduleTaskAttendanceListVo> queryAttendancePage(DutyScheduleTaskAttendanceQueryBo queryBo, PageQuery pageQuery);

    /**
     * 排班/值班统计分页列表
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<DutyScheduleStatisticsPageVo> queryStatisticsPage(DutyScheduleInfoStatisticsQueryBo queryBo, PageQuery pageQuery);

    /**
     * 查询指定台站的当天排班任务
     * @param stationId 台站ID
     * @return 排班任务
     */
    DutyScheduleTaskVo queryTaskByStationIdAndSameTime(Integer stationId);
}
