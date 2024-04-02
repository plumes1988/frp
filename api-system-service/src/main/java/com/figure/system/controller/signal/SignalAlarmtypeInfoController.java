package com.figure.system.controller.signal;

import com.figure.core.base.BaseController;
import com.figure.core.constant.Constants;
import com.figure.core.model.signal.SignalAlarmtypeInfo;
import com.figure.core.query.signal.SignalAlarmtypeInfoQuery;
import com.figure.core.service.signal.ISignalAlarmtypeInfoService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 默认报警类型信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:14
 */
@RestController
@RequestMapping("/signalAlarmtypeInfo")
@Api(value = "默认报警类型信息相关接口", tags = "默认报警类型信息相关接口")
public class SignalAlarmtypeInfoController extends BaseController {

    @Resource
    private ISignalAlarmtypeInfoService signalAlarmtypeInfoService;

    /**
     * 根据id获取默认报警类型信息
     *
     * @param id 默认报警类型信息ID
     * @return 默认报警类型信息
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取默认报警类型信息", notes = "根据id获取默认报警类型信息")
    public SignalAlarmtypeInfo selectSignalAlarmtypeInfoById(@PathVariable("id") Integer id) {
        return signalAlarmtypeInfoService.getById(id);
    }

    /**
     * 查询默认报警类型信息列表
     *
     * @param signalAlarmtypeInfoQuery 默认报警类型信息
     * @return 默认报警类型信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询默认报警类型信息列表", notes = "查询默认报警类型信息列表")
    public TableDataInfo<SignalAlarmtypeInfo> selectSignalAlarmtypeInfoList(SignalAlarmtypeInfoQuery signalAlarmtypeInfoQuery) {
        return toPageResult(signalAlarmtypeInfoService.list(signalAlarmtypeInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存默认报警类型信息
     *
     * @param signalAlarmtypeInfo 默认报警类型信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存默认报警类型信息", notes = "新增保存默认报警类型信息")
    public Map<String, Object> insertSignalAlarmtypeInfo(@RequestBody SignalAlarmtypeInfo signalAlarmtypeInfo) {
        return returnMap(signalAlarmtypeInfoService.save(signalAlarmtypeInfo), signalAlarmtypeInfo.getId());
    }

    /**
     * 批量新增保存默认报警类型信息
     *
     * @param signalAlarmtypeInfo 默认报警类型信息
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存默认报警类型信息", notes = "批量新增保存默认报警类型信息")
    public Map<String, Object> saveBatchSignalAlarmtypeInfo(@RequestBody List<SignalAlarmtypeInfo> signalAlarmtypeInfo) {
        return returnMap(signalAlarmtypeInfoService.saveBatch(signalAlarmtypeInfo), signalAlarmtypeInfo);
    }

    /**
     * 修改保存默认报警类型信息
     *
     * @param signalAlarmtypeInfo 默认报警类型信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存默认报警类型信息", notes = "修改保存默认报警类型信息")
    public Map<String, Object> updateSignalAlarmtypeInfoById(@RequestBody SignalAlarmtypeInfo signalAlarmtypeInfo) {
        return returnMap(signalAlarmtypeInfoService.updateById(signalAlarmtypeInfo));
    }

    /**
     * 批量删除默认报警类型信息
     *
     * @param signalAlarmtypeInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除默认报警类型信息", notes = "删除默认报警类型信息")
    public Map<String, Object> removeSignalAlarmtypeInfoByIds(@RequestBody SignalAlarmtypeInfo signalAlarmtypeInfo) {
        List<SignalAlarmtypeInfo> signalAlarmtypeInfoList = new ArrayList<>();
        List<Integer> alarmTypeInfoIdList = StringUtils.StringToIntList(signalAlarmtypeInfo.getIds());
        for (Integer alarmTypeInfoId : alarmTypeInfoIdList) {
            SignalAlarmtypeInfo signalAlarmtypeInfoId = new SignalAlarmtypeInfo();
            signalAlarmtypeInfoId.setId(alarmTypeInfoId);
            signalAlarmtypeInfoId.setIsDelete(Constants.DATATABLE_ISDELETE_DELETED);
            signalAlarmtypeInfoList.add(signalAlarmtypeInfoId);
        }
        return returnMap(signalAlarmtypeInfoService.updateBatchById(signalAlarmtypeInfoList));
    }

}