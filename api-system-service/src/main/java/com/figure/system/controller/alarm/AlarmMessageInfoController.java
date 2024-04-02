package com.figure.system.controller.alarm;

import com.figure.core.base.BaseController;
import com.figure.core.model.alarm.AlarmMessageInfo;
import com.figure.core.query.alarm.AlarmMessageInfoQuery;
import com.figure.core.service.alarm.IAlarmMessageInfoService;
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
 * 信号报警记录 前端控制器
 * </p>
 *
 * @author feather
 * @date 2023-03-22 10:53:57
 */
@RestController
@RequestMapping("/alarmMessageInfo")
@Api(value = "信号报警记录相关接口", tags = "信号报警记录相关接口")
public class AlarmMessageInfoController extends BaseController {

    @Resource
    private IAlarmMessageInfoService alarmMessageInfoService;

    /**
     * 根据alarmId获取信号报警记录
     *
     * @param alarmId 信号报警记录ID
     * @return 信号报警记录
     */
    @GetMapping("/get/{alarmId}")
    @ApiOperation(value = "根据alarmId获取信号报警记录", notes = "根据alarmId获取信号报警记录")
    public AlarmMessageInfo selectAlarmMessageInfoById(@PathVariable("alarmId") Long alarmId) {
        return alarmMessageInfoService.getById(alarmId);
    }

    /**
     * 查询信号报警记录列表
     *
     * @param alarmMessageInfoQuery 信号报警记录
     * @return 信号报警记录集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询信号报警记录列表", notes = "查询信号报警记录列表")
    public TableDataInfo<AlarmMessageInfo> selectAlarmMessageInfoList(AlarmMessageInfoQuery alarmMessageInfoQuery) {
        alarmMessageInfoQuery.setOrderByColumn("startTime");
        return toPageResult(alarmMessageInfoService.list(alarmMessageInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存信号报警记录
     *
     * @param alarmMessageInfo 信号报警记录
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存信号报警记录", notes = "新增保存信号报警记录")
    public Map<String, Object> insertAlarmMessageInfo(@RequestBody AlarmMessageInfo alarmMessageInfo) {
        return returnMap(alarmMessageInfoService.save(alarmMessageInfo), alarmMessageInfo.getAlarmId());
    }

    /**
     * 批量新增保存信号报警记录
     *
     * @param alarmMessageInfo 信号报警记录
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存信号报警记录", notes = "批量新增保存信号报警记录")
    public Map<String, Object> saveBatchAlarmMessageInfo(@RequestBody List<AlarmMessageInfo> alarmMessageInfo) {
        return returnMap(alarmMessageInfoService.saveBatch(alarmMessageInfo), alarmMessageInfo);
    }

    /**
     * 修改保存信号报警记录
     *
     * @param alarmMessageInfo 信号报警记录
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存信号报警记录", notes = "修改保存信号报警记录")
    public Map<String, Object> updateAlarmMessageInfoById(@RequestBody AlarmMessageInfo alarmMessageInfo) {
        return returnMap(alarmMessageInfoService.updateById(alarmMessageInfo));
    }

    /**
     * 批量删除信号报警记录
     *
     * @param alarmMessageInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除信号报警记录", notes = "删除信号报警记录")
    public Map<String, Object> removeAlarmMessageInfoByIds(@RequestBody AlarmMessageInfo alarmMessageInfo) {
        return returnMap(alarmMessageInfoService.removeByIds(StringUtils.StringToIntList(alarmMessageInfo.getIds())));
    }

}