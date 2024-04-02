package com.figure.system.controller.signal;

import com.figure.core.base.BaseController;
import com.figure.core.model.signal.SignalAlarmthresholdRule;
import com.figure.core.query.signal.SignalAlarmthresholdRuleQuery;
import com.figure.core.service.signal.ISignalAlarmthresholdRuleService;
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
 * 信号报警门限规则 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-12-17 15:01:23
 */
@RestController
@RequestMapping("/signalAlarmthresholdRule")
@Api(value = "信号报警门限规则相关接口", tags = "信号报警门限规则相关接口")
public class SignalAlarmthresholdRuleController extends BaseController {

    @Resource
    private ISignalAlarmthresholdRuleService signalAlarmthresholdRuleService;

    /**
     * 根据ruleId获取信号报警门限规则
     *
     * @param ruleId 信号报警门限规则ID
     * @return 信号报警门限规则
     */
    @GetMapping("/get/{ruleId}")
    @ApiOperation(value = "根据ruleId获取信号报警门限规则", notes = "根据ruleId获取信号报警门限规则")
    public SignalAlarmthresholdRule selectSignalAlarmthresholdRuleById(@PathVariable("ruleId") Integer ruleId) {
        return signalAlarmthresholdRuleService.getById(ruleId);
    }

    /**
     * 查询信号报警门限规则列表
     *
     * @param signalAlarmthresholdRuleQuery 信号报警门限规则
     * @return 信号报警门限规则集合
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询信号报警门限规则列表", notes = "查询信号报警门限规则列表")
    public TableDataInfo<SignalAlarmthresholdRule> selectSignalAlarmthresholdRuleList(SignalAlarmthresholdRuleQuery signalAlarmthresholdRuleQuery) {
        if (signalAlarmthresholdRuleQuery.getRuleId() != null) {
            signalAlarmthresholdRuleQuery.setRuleIdList(Arrays.asList(Convert.toIntArray(signalAlarmthresholdRuleQuery.getRuleId())));
            signalAlarmthresholdRuleQuery.setRuleId(null);
        }
        return toPageResult(signalAlarmthresholdRuleService.listByQuery(signalAlarmthresholdRuleQuery.autoPageWrapper()));
    }

    /**
     * 新增保存信号报警门限规则
     *
     * @param signalAlarmthresholdRule 信号报警门限规则
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存信号报警门限规则", notes = "新增保存信号报警门限规则")
    public Map<String, Object> insertSignalAlarmthresholdRule(@RequestBody SignalAlarmthresholdRule signalAlarmthresholdRule) {
        return returnMap(signalAlarmthresholdRuleService.save(signalAlarmthresholdRule), signalAlarmthresholdRule);
    }

    /**
     * 修改保存信号报警门限规则
     *
     * @param signalAlarmthresholdRule 信号报警门限规则
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存信号报警门限规则", notes = "修改保存信号报警门限规则")
    public Map<String, Object> updateSignalAlarmthresholdRuleById(@RequestBody SignalAlarmthresholdRule signalAlarmthresholdRule) {
        return returnMap(signalAlarmthresholdRuleService.updateById(signalAlarmthresholdRule), signalAlarmthresholdRule);
    }

    /**
     * 批量删除信号报警门限规则
     *
     * @param signalAlarmthresholdRule 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除信号报警门限规则", notes = "删除信号报警门限规则")
    public Map<String, Object> removeSignalAlarmthresholdRuleByIds(@RequestBody SignalAlarmthresholdRule signalAlarmthresholdRule) {
        return returnMap(signalAlarmthresholdRuleService.removeByIds(StringUtils.StringToIntList(signalAlarmthresholdRule.getIds())));
    }

}