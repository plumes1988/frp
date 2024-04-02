package com.figure.op.duty.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.duty.domain.bo.*;
import com.figure.op.duty.domain.vo.*;
import com.figure.op.duty.service.IDutyScheduleInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 排班管理控制器
 * @author fsn
 */
@RestController
@RequestMapping("/dutyScheduleInfo")
public class DutyScheduleInfoController {

    @Resource
    private IDutyScheduleInfoService dutyScheduleInfoService;

    /**
     * 排班管理-排班计划-分页列表
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    @GetMapping("/page")
    public TableDataInfo<DutyScheduleInfoPageVo> page(DutyScheduleInfoQueryBo queryBo, PageQuery pageQuery) {
        return dutyScheduleInfoService.queryPage(queryBo, pageQuery);
    }

    /**
     * 排班管理-排班计划-全部列表
     * @return 分页列表
     */
    @GetMapping("/list")
    public R<List<DutyScheduleInfoListVo>> list() {
        return R.ok(dutyScheduleInfoService.queryList());
    }

    /**
     * 排班管理-排班任务列表
     * @param queryBo 查询对象
     * @return 列表
     */
    @GetMapping("/taskList")
    public R<List<DutyScheduleTaskListVo>> taskList(DutyScheduleTaskQueryBo queryBo) {
        return R.ok(dutyScheduleInfoService.queryTaskList(queryBo));
    }

    /**
     * 新增排班计划
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody DutyScheduleInfoBo bo) {
        return R.toAjax(dutyScheduleInfoService.insertByBo(bo));
    }

    /**
     * 作废排班计划
     */
    @PutMapping("/cancel")
    public R<Void> cancel(@Validated @RequestBody DutyScheduleInfoCancelBo cancelBo) {
        return R.toAjax(dutyScheduleInfoService.cancel(cancelBo));
    }

    /**
     * 排班管理-排班任务-值班人员列表
     * @param scheduleTaskId 排班任务ID
     * @return 列表
     */
    @GetMapping("/workerList")
    public R<List<DutyScheduleTaskWorkerListVo>> workerList(@RequestParam("scheduleTaskId") Integer scheduleTaskId) {
        return R.ok(dutyScheduleInfoService.queryWorkerList(scheduleTaskId));
    }

    /**
     * 新增值班人员
     */
    @PostMapping("/addWorker")
    public R<Void> addWorker(@Validated(AddGroup.class) @RequestBody DutyScheduleTaskWorkerBo bo) {
        return R.toAjax(dutyScheduleInfoService.insertWorker(bo));
    }

    /**
     * 删除值班人员
     */
    @DeleteMapping("/deleteWorker/{id}")
    public R<Void> deleteWorker(@PathVariable Integer id) {
        return R.toAjax(dutyScheduleInfoService.deleteWorker(id));
    }

    /**
     * 新增考勤状态
     */
    @PostMapping("/addAttendance")
    public R<Void> addAttendance(@Validated(AddGroup.class) @RequestBody DutyScheduleTaskAttendanceBo bo) {
        return R.toAjax(dutyScheduleInfoService.insertAttendance(bo));
    }

    /**
     * 考勤状态分页列表
     */
    @GetMapping("/attendancePage")
    public TableDataInfo<DutyScheduleTaskAttendanceListVo> attendancePage(@Validated DutyScheduleTaskAttendanceQueryBo queryBo, PageQuery pageQuery) {
        return dutyScheduleInfoService.queryAttendancePage(queryBo, pageQuery);
    }

    /**
     * 排班/值班统计
     */
    @GetMapping("/statisticsPage")
    public TableDataInfo<DutyScheduleStatisticsPageVo> statisticsPage(DutyScheduleInfoStatisticsQueryBo queryBo, PageQuery pageQuery) {
        return dutyScheduleInfoService.queryStatisticsPage(queryBo, pageQuery);
    }

    /**
     * 查询指定台站的当天排班任务
     * @param stationId 台站ID
     * @return 排班任务
     */
    @GetMapping("/queryTaskByStationIdAndSameTime/{stationId}")
    public R<DutyScheduleTaskVo> queryTaskByStationIdAndSameTime(@PathVariable Integer stationId) {
        return R.ok(dutyScheduleInfoService.queryTaskByStationIdAndSameTime(stationId));
    }

}
