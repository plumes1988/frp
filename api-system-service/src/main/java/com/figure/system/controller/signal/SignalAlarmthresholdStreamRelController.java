package com.figure.system.controller.signal;

import com.figure.core.base.BaseController;
import com.figure.core.model.signal.SignalAlarmthresholdStreamRel;
import com.figure.core.model.signal.SignalAlarmthresholdStreamRelList;
import com.figure.core.query.signal.SignalAlarmthresholdStreamRelQuery;
import com.figure.core.service.signal.ISignalAlarmthresholdStreamRelService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.text.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.figure.core.util.page.TableDataInfo;

/**
 * <p>
 * 报警对象与报警门限规则关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-12-17 15:01:23
 */
@RestController
@RequestMapping("/signalAlarmthresholdStreamRel")
@Api(value = "报警对象与报警门限规则关联相关接口", tags = "报警对象与报警门限规则关联相关接口")
public class SignalAlarmthresholdStreamRelController extends BaseController {

    @Resource
    private ISignalAlarmthresholdStreamRelService signalAlarmthresholdStreamRelService;

    /**
     * 根据objectType获取报警对象与报警门限规则关联
     *
     * @param objectType 报警对象与报警门限规则关联ID
     * @return 报警对象与报警门限规则关联
     */
    @GetMapping("/get/{objectType}")
    @ApiOperation(value = "根据objectType获取报警对象与报警门限规则关联", notes = "根据objectType获取报警对象与报警门限规则关联")
    public SignalAlarmthresholdStreamRel selectSignalAlarmthresholdStreamRelById(@PathVariable("objectType") Integer objectType) {
        return signalAlarmthresholdStreamRelService.getById(objectType);
    }

    /**
     * 查询报警对象与报警门限规则关联列表
     *
     * @param signalAlarmthresholdStreamRelQuery 报警对象与报警门限规则关联
     * @return 报警对象与报警门限规则关联集合
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询报警对象与报警门限规则关联列表", notes = "查询报警对象与报警门限规则关联列表")
    public TableDataInfo<SignalAlarmthresholdStreamRel> selectSignalAlarmthresholdStreamRelList(SignalAlarmthresholdStreamRelQuery signalAlarmthresholdStreamRelQuery) {
        return toPageResult(signalAlarmthresholdStreamRelService.listByQuery(signalAlarmthresholdStreamRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存报警对象与报警门限规则关联
     *
     * @param signalAlarmthresholdStreamRelList 报警对象与报警门限规则关联
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存报警对象与报警门限规则关联", notes = "新增保存报警对象与报警门限规则关联")
    public Map<String, Object> insertSignalAlarmthresholdStreamRel(@RequestBody SignalAlarmthresholdStreamRelList signalAlarmthresholdStreamRelList) {
        return returnMap(signalAlarmthresholdStreamRelService.saveBatch(signalAlarmthresholdStreamRelList.getSignalAlarmthresholdStreamRelJson()),
                signalAlarmthresholdStreamRelList.getSignalAlarmthresholdStreamRelJson());
    }

    /**
     * 修改保存报警对象与报警门限规则关联
     *
     * @param signalAlarmthresholdStreamRelList 报警对象与报警门限规则关联
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存报警对象与报警门限规则关联", notes = "修改保存报警对象与报警门限规则关联")
    public Map<String, Object> updateSignalAlarmthresholdStreamRelById(@RequestBody SignalAlarmthresholdStreamRelList signalAlarmthresholdStreamRelList) {
        return returnMap(signalAlarmthresholdStreamRelService.updateBatchById(signalAlarmthresholdStreamRelList.getSignalAlarmthresholdStreamRelJson()),
                signalAlarmthresholdStreamRelList.getSignalAlarmthresholdStreamRelJson());
    }

    /**
     * 批量删除报警对象与报警门限规则关联
     *
     * @param signalAlarmthresholdStreamRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除报警对象与报警门限规则关联", notes = "删除报警对象与报警门限规则关联")
    public Map<String, Object> removeSignalAlarmthresholdStreamRelByIds(@RequestBody SignalAlarmthresholdStreamRel signalAlarmthresholdStreamRel) {
        return returnMap(signalAlarmthresholdStreamRelService.removeByIds(StringUtils.StringToStringList(signalAlarmthresholdStreamRel.getIds())));
    }

}