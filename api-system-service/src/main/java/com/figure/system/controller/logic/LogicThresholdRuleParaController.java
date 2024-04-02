package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicThresholdRulePara;
import com.figure.core.query.logic.LogicThresholdRuleParaQuery;
import com.figure.core.service.logic.ILogicThresholdRuleParaService;
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
 * 报警门限规则参数 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:14
 */
@RestController
@RequestMapping("/logicThresholdRulePara")
@Api(value = "报警门限规则参数相关接口", tags = "报警门限规则参数相关接口")
public class LogicThresholdRuleParaController extends BaseController {

    @Resource
    private ILogicThresholdRuleParaService logicThresholdRuleParaService;

    /**
     * 根据id获取报警门限规则参数
     *
     * @param id 报警门限规则参数ID
     * @return 报警门限规则参数
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取报警门限规则参数", notes = "根据id获取报警门限规则参数")
    public LogicThresholdRulePara selectLogicThresholdRuleParaById(@PathVariable("id") Integer id) {
        return logicThresholdRuleParaService.getById(id);
    }

    /**
     * 查询报警门限规则参数列表
     *
     * @param logicThresholdRuleParaQuery 报警门限规则参数
     * @return 报警门限规则参数集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询报警门限规则参数列表", notes = "查询报警门限规则参数列表")
    public TableDataInfo<LogicThresholdRulePara> selectLogicThresholdRuleParaList(LogicThresholdRuleParaQuery logicThresholdRuleParaQuery) {
        return toPageResult(logicThresholdRuleParaService.list(logicThresholdRuleParaQuery.autoPageWrapper()));
    }

    /**
     * 新增保存报警门限规则参数
     *
     * @param logicThresholdRulePara 报警门限规则参数
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存报警门限规则参数", notes = "新增保存报警门限规则参数")
    public Map<String, Object> insertLogicThresholdRulePara(@RequestBody LogicThresholdRulePara logicThresholdRulePara) {
        return returnMap(logicThresholdRuleParaService.save(logicThresholdRulePara), logicThresholdRulePara.getId());
    }

    /**
     * 批量新增保存报警门限规则参数
     *
     * @param logicThresholdRulePara 报警门限规则参数
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存报警门限规则参数", notes = "批量新增保存报警门限规则参数")
    public Map<String, Object> saveBatchLogicThresholdRulePara(@RequestBody List<LogicThresholdRulePara> logicThresholdRulePara) {
        return returnMap(logicThresholdRuleParaService.saveBatch(logicThresholdRulePara), logicThresholdRulePara);
    }

    /**
     * 修改保存报警门限规则参数
     *
     * @param logicThresholdRulePara 报警门限规则参数
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存报警门限规则参数", notes = "修改保存报警门限规则参数")
    public Map<String, Object> updateLogicThresholdRuleParaById(@RequestBody LogicThresholdRulePara logicThresholdRulePara) {
        return returnMap(logicThresholdRuleParaService.updateById(logicThresholdRulePara));
    }

    /**
     * 批量删除报警门限规则参数
     *
     * @param logicThresholdRulePara 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除报警门限规则参数", notes = "删除报警门限规则参数")
    public Map<String, Object> removeLogicThresholdRuleParaByIds(@RequestBody LogicThresholdRulePara logicThresholdRulePara) {
        return returnMap(logicThresholdRuleParaService.removeByIds(StringUtils.StringToIntList(logicThresholdRulePara.getIds())));
    }

}