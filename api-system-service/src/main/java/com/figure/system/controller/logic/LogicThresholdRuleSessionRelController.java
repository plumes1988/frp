package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicThresholdRuleSessionRel;
import com.figure.core.query.logic.LogicThresholdRuleSessionRelQuery;
import com.figure.core.service.logic.ILogicThresholdRuleSessionRelService;
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
 * 重点时段和报警门限规则关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:15
 */
@RestController
@RequestMapping("/logicThresholdRuleSessionRel")
@Api(value = "重点时段和报警门限规则关联相关接口", tags = "重点时段和报警门限规则关联相关接口")
public class LogicThresholdRuleSessionRelController extends BaseController {

    @Resource
    private ILogicThresholdRuleSessionRelService logicThresholdRuleSessionRelService;

    /**
     * 根据id获取重点时段和报警门限规则关联
     *
     * @param id 重点时段和报警门限规则关联ID
     * @return 重点时段和报警门限规则关联
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取重点时段和报警门限规则关联", notes = "根据id获取重点时段和报警门限规则关联")
    public LogicThresholdRuleSessionRel selectLogicThresholdRuleSessionRelById(@PathVariable("id") Integer id) {
        return logicThresholdRuleSessionRelService.getById(id);
    }

    /**
     * 查询重点时段和报警门限规则关联列表
     *
     * @param logicThresholdRuleSessionRelQuery 重点时段和报警门限规则关联
     * @return 重点时段和报警门限规则关联集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询重点时段和报警门限规则关联列表", notes = "查询重点时段和报警门限规则关联列表")
    public TableDataInfo<LogicThresholdRuleSessionRel> selectLogicThresholdRuleSessionRelList(LogicThresholdRuleSessionRelQuery logicThresholdRuleSessionRelQuery) {
        return toPageResult(logicThresholdRuleSessionRelService.list(logicThresholdRuleSessionRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存重点时段和报警门限规则关联
     *
     * @param logicThresholdRuleSessionRel 重点时段和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存重点时段和报警门限规则关联", notes = "新增保存重点时段和报警门限规则关联")
    public Map<String, Object> insertLogicThresholdRuleSessionRel(@RequestBody LogicThresholdRuleSessionRel logicThresholdRuleSessionRel) {
        return returnMap(logicThresholdRuleSessionRelService.save(logicThresholdRuleSessionRel), logicThresholdRuleSessionRel.getId());
    }

    /**
     * 批量新增保存重点时段和报警门限规则关联
     *
     * @param logicThresholdRuleSessionRel 重点时段和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存重点时段和报警门限规则关联", notes = "批量新增保存重点时段和报警门限规则关联")
    public Map<String, Object> saveBatchLogicThresholdRuleSessionRel(@RequestBody List<LogicThresholdRuleSessionRel> logicThresholdRuleSessionRel) {
        return returnMap(logicThresholdRuleSessionRelService.saveBatch(logicThresholdRuleSessionRel), logicThresholdRuleSessionRel);
    }

    /**
     * 修改保存重点时段和报警门限规则关联
     *
     * @param logicThresholdRuleSessionRel 重点时段和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存重点时段和报警门限规则关联", notes = "修改保存重点时段和报警门限规则关联")
    public Map<String, Object> updateLogicThresholdRuleSessionRelById(@RequestBody LogicThresholdRuleSessionRel logicThresholdRuleSessionRel) {
        return returnMap(logicThresholdRuleSessionRelService.updateById(logicThresholdRuleSessionRel));
    }

    /**
     * 批量删除重点时段和报警门限规则关联
     *
     * @param logicThresholdRuleSessionRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除重点时段和报警门限规则关联", notes = "删除重点时段和报警门限规则关联")
    public Map<String, Object> removeLogicThresholdRuleSessionRelByIds(@RequestBody LogicThresholdRuleSessionRel logicThresholdRuleSessionRel) {
        return returnMap(logicThresholdRuleSessionRelService.removeByIds(StringUtils.StringToIntList(logicThresholdRuleSessionRel.getIds())));
    }

}