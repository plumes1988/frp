package com.figure.system.controller.alarm;

import com.figure.core.base.BaseController;
import com.figure.core.model.alarm.AlarmEventInfo;
import com.figure.core.query.alarm.AlarmEventInfoQuery;
import com.figure.core.service.alarm.IAlarmEventInfoService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 播出事件信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2023-03-22 10:53:57
 */
@RestController
@RequestMapping("/alarmEventInfo")
@Api(value = "播出事件信息相关接口", tags = "播出事件信息相关接口")
public class AlarmEventInfoController extends BaseController {

    @Resource
    private IAlarmEventInfoService alarmEventInfoService;

    /**
     * 根据eventId获取播出事件信息
     *
     * @param eventId 播出事件信息ID
     * @return 播出事件信息
     */
    @GetMapping("/get/{eventId}")
    @ApiOperation(value = "根据eventId获取播出事件信息", notes = "根据eventId获取播出事件信息")
    public AlarmEventInfo selectAlarmEventInfoById(@PathVariable("eventId") Integer eventId) {
        return alarmEventInfoService.getById(eventId);
    }

    /**
     * 查询播出事件信息列表
     *
     * @param alarmEventInfoQuery 播出事件信息
     * @return 播出事件信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询播出事件信息列表", notes = "查询播出事件信息列表")
    public TableDataInfo<AlarmEventInfo> selectAlarmEventInfoList(AlarmEventInfoQuery alarmEventInfoQuery) {
        alarmEventInfoQuery.setOrderByColumn("startTime");
        return toPageResult(alarmEventInfoService.list(alarmEventInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存播出事件信息
     *
     * @param alarmEventInfo 播出事件信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存播出事件信息", notes = "新增保存播出事件信息")
    public Map<String, Object> insertAlarmEventInfo(@RequestBody AlarmEventInfo alarmEventInfo) {
        return returnMap(alarmEventInfoService.save(alarmEventInfo), alarmEventInfo.getEventId());
    }

    /**
     * 批量新增保存播出事件信息
     *
     * @param alarmEventInfo 播出事件信息
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存播出事件信息", notes = "批量新增保存播出事件信息")
    public Map<String, Object> saveBatchAlarmEventInfo(@RequestBody List<AlarmEventInfo> alarmEventInfo) {
        return returnMap(alarmEventInfoService.saveBatch(alarmEventInfo), alarmEventInfo);
    }

    /**
     * 修改保存播出事件信息
     *
     * @param alarmEventInfo 播出事件信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存播出事件信息", notes = "修改保存播出事件信息")
    public Map<String, Object> updateAlarmEventInfoById(@RequestBody AlarmEventInfo alarmEventInfo) {
        return returnMap(alarmEventInfoService.updateById(alarmEventInfo));
    }

    /**
     * 批量删除播出事件信息
     *
     * @param alarmEventInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除播出事件信息", notes = "删除播出事件信息")
    public Map<String, Object> removeAlarmEventInfoByIds(@RequestBody AlarmEventInfo alarmEventInfo) {
        return returnMap(alarmEventInfoService.removeByIds(StringUtils.StringToIntList(alarmEventInfo.getIds())));
    }

}