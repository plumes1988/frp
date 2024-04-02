package com.figure.system.controller.alarm;

import com.figure.core.base.BaseController;
import com.figure.core.model.alarm.AlarmSwitchEventInfo;
import com.figure.core.query.alarm.AlarmSwitchEventInfoQuery;
import com.figure.core.service.alarm.IAlarmSwitchEventInfoService;
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
 * 信号倒换记录信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2023-03-22 10:53:57
 */
@RestController
@RequestMapping("/alarmSwitchEventInfo")
@Api(value = "信号倒换记录信息相关接口", tags = "信号倒换记录信息相关接口")
public class AlarmSwitchEventInfoController extends BaseController {

    @Resource
    private IAlarmSwitchEventInfoService alarmSwitchEventInfoService;

    /**
     * 根据eventId获取信号倒换记录信息
     *
     * @param eventId 信号倒换记录信息ID
     * @return 信号倒换记录信息
     */
    @GetMapping("/get/{eventId}")
    @ApiOperation(value = "根据eventId获取信号倒换记录信息", notes = "根据eventId获取信号倒换记录信息")
    public AlarmSwitchEventInfo selectAlarmSwitchEventInfoById(@PathVariable("eventId") Integer eventId) {
        return alarmSwitchEventInfoService.getById(eventId);
    }

    /**
     * 查询信号倒换记录信息列表
     *
     * @param alarmSwitchEventInfoQuery 信号倒换记录信息
     * @return 信号倒换记录信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询信号倒换记录信息列表", notes = "查询信号倒换记录信息列表")
    public TableDataInfo<AlarmSwitchEventInfo> selectAlarmSwitchEventInfoList(AlarmSwitchEventInfoQuery alarmSwitchEventInfoQuery) {
        return toPageResult(alarmSwitchEventInfoService.list(alarmSwitchEventInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存信号倒换记录信息
     *
     * @param alarmSwitchEventInfo 信号倒换记录信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存信号倒换记录信息", notes = "新增保存信号倒换记录信息")
    public Map<String, Object> insertAlarmSwitchEventInfo(@RequestBody AlarmSwitchEventInfo alarmSwitchEventInfo) {
        return returnMap(alarmSwitchEventInfoService.save(alarmSwitchEventInfo), alarmSwitchEventInfo.getEventId());
    }

    /**
     * 批量新增保存信号倒换记录信息
     *
     * @param alarmSwitchEventInfo 信号倒换记录信息
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存信号倒换记录信息", notes = "批量新增保存信号倒换记录信息")
    public Map<String, Object> saveBatchAlarmSwitchEventInfo(@RequestBody List<AlarmSwitchEventInfo> alarmSwitchEventInfo) {
        return returnMap(alarmSwitchEventInfoService.saveBatch(alarmSwitchEventInfo), alarmSwitchEventInfo);
    }

    /**
     * 修改保存信号倒换记录信息
     *
     * @param alarmSwitchEventInfo 信号倒换记录信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存信号倒换记录信息", notes = "修改保存信号倒换记录信息")
    public Map<String, Object> updateAlarmSwitchEventInfoById(@RequestBody AlarmSwitchEventInfo alarmSwitchEventInfo) {
        return returnMap(alarmSwitchEventInfoService.updateById(alarmSwitchEventInfo));
    }

    /**
     * 批量删除信号倒换记录信息
     *
     * @param alarmSwitchEventInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除信号倒换记录信息", notes = "删除信号倒换记录信息")
    public Map<String, Object> removeAlarmSwitchEventInfoByIds(@RequestBody AlarmSwitchEventInfo alarmSwitchEventInfo) {
        return returnMap(alarmSwitchEventInfoService.removeByIds(StringUtils.StringToIntList(alarmSwitchEventInfo.getIds())));
    }

}