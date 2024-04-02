package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicThresholdRule;
import com.figure.core.query.logic.LogicThresholdRuleQuery;
import com.figure.core.service.logic.ILogicThresholdRuleService;
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
 * 报警门限规则信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:14
 */
@RestController
@RequestMapping("/logicThresholdRule")
@Api(value = "报警门限规则信息相关接口", tags = "报警门限规则信息相关接口")
public class LogicThresholdRuleController extends BaseController {

    @Resource
    private ILogicThresholdRuleService logicThresholdRuleService;

    /**
     * 根据modelId获取报警门限规则信息
     *
     * @param modelId 报警门限规则信息ID
     * @return 报警门限规则信息
     */
    @GetMapping("/get/{modelId}")
    @ApiOperation(value = "根据modelId获取报警门限规则信息", notes = "根据modelId获取报警门限规则信息")
    public LogicThresholdRule selectLogicThresholdRuleById(@PathVariable("modelId") Integer modelId) {
        return logicThresholdRuleService.getById(modelId);
    }

    /**
     * 查询报警门限规则信息列表
     *
     * @param logicThresholdRuleQuery 报警门限规则信息
     * @return 报警门限规则信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询报警门限规则信息列表", notes = "查询报警门限规则信息列表")
    public TableDataInfo<LogicThresholdRule> selectLogicThresholdRuleList(LogicThresholdRuleQuery logicThresholdRuleQuery) {
        return toPageResult(logicThresholdRuleService.list(logicThresholdRuleQuery.autoPageWrapper()));
    }

    /**
     * 新增保存报警门限规则信息
     *
     * @param logicThresholdRule 报警门限规则信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存报警门限规则信息", notes = "新增保存报警门限规则信息")
    public Map<String, Object> insertLogicThresholdRule(@RequestBody LogicThresholdRule logicThresholdRule) {
        return returnMap(logicThresholdRuleService.save(logicThresholdRule), logicThresholdRule.getModelId());
    }

    /**
     * 批量新增保存报警门限规则信息
     *
     * @param logicThresholdRule 报警门限规则信息
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存报警门限规则信息", notes = "批量新增保存报警门限规则信息")
    public Map<String, Object> saveBatchLogicThresholdRule(@RequestBody List<LogicThresholdRule> logicThresholdRule) {
        return returnMap(logicThresholdRuleService.saveBatch(logicThresholdRule), logicThresholdRule);
    }

    /**
     * 修改保存报警门限规则信息
     *
     * @param logicThresholdRule 报警门限规则信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存报警门限规则信息", notes = "修改保存报警门限规则信息")
    public Map<String, Object> updateLogicThresholdRuleById(@RequestBody LogicThresholdRule logicThresholdRule) {
        return returnMap(logicThresholdRuleService.updateById(logicThresholdRule));
    }

    /**
     * 批量删除报警门限规则信息
     *
     * @param logicThresholdRule 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除报警门限规则信息", notes = "删除报警门限规则信息")
    public Map<String, Object> removeLogicThresholdRuleByIds(@RequestBody LogicThresholdRule logicThresholdRule) {
        return returnMap(logicThresholdRuleService.removeByIds(StringUtils.StringToIntList(logicThresholdRule.getIds())));
    }

}