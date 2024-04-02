package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicThresholdRuleColumnRel;
import com.figure.core.query.logic.LogicThresholdRuleColumnRelQuery;
import com.figure.core.service.logic.ILogicThresholdRuleColumnRelService;
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
 * 栏目和报警门限规则关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:14
 */
@RestController
@RequestMapping("/logicThresholdRuleColumnRel")
@Api(value = "栏目和报警门限规则关联相关接口", tags = "栏目和报警门限规则关联相关接口")
public class LogicThresholdRuleColumnRelController extends BaseController {

    @Resource
    private ILogicThresholdRuleColumnRelService logicThresholdRuleColumnRelService;

    /**
     * 根据id获取栏目和报警门限规则关联
     *
     * @param id 栏目和报警门限规则关联ID
     * @return 栏目和报警门限规则关联
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取栏目和报警门限规则关联", notes = "根据id获取栏目和报警门限规则关联")
    public LogicThresholdRuleColumnRel selectLogicThresholdRuleColumnRelById(@PathVariable("id") Integer id) {
        return logicThresholdRuleColumnRelService.getById(id);
    }

    /**
     * 查询栏目和报警门限规则关联列表
     *
     * @param logicThresholdRuleColumnRelQuery 栏目和报警门限规则关联
     * @return 栏目和报警门限规则关联集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询栏目和报警门限规则关联列表", notes = "查询栏目和报警门限规则关联列表")
    public TableDataInfo<LogicThresholdRuleColumnRel> selectLogicThresholdRuleColumnRelList(LogicThresholdRuleColumnRelQuery logicThresholdRuleColumnRelQuery) {
        return toPageResult(logicThresholdRuleColumnRelService.list(logicThresholdRuleColumnRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存栏目和报警门限规则关联
     *
     * @param logicThresholdRuleColumnRel 栏目和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存栏目和报警门限规则关联", notes = "新增保存栏目和报警门限规则关联")
    public Map<String, Object> insertLogicThresholdRuleColumnRel(@RequestBody LogicThresholdRuleColumnRel logicThresholdRuleColumnRel) {
        return returnMap(logicThresholdRuleColumnRelService.save(logicThresholdRuleColumnRel), logicThresholdRuleColumnRel.getId());
    }

    /**
     * 批量新增保存栏目和报警门限规则关联
     *
     * @param logicThresholdRuleColumnRel 栏目和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存栏目和报警门限规则关联", notes = "批量新增保存栏目和报警门限规则关联")
    public Map<String, Object> saveBatchLogicThresholdRuleColumnRel(@RequestBody List<LogicThresholdRuleColumnRel> logicThresholdRuleColumnRel) {
        return returnMap(logicThresholdRuleColumnRelService.saveBatch(logicThresholdRuleColumnRel), logicThresholdRuleColumnRel);
    }

    /**
     * 修改保存栏目和报警门限规则关联
     *
     * @param logicThresholdRuleColumnRel 栏目和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存栏目和报警门限规则关联", notes = "修改保存栏目和报警门限规则关联")
    public Map<String, Object> updateLogicThresholdRuleColumnRelById(@RequestBody LogicThresholdRuleColumnRel logicThresholdRuleColumnRel) {
        return returnMap(logicThresholdRuleColumnRelService.updateById(logicThresholdRuleColumnRel));
    }

    /**
     * 批量删除栏目和报警门限规则关联
     *
     * @param logicThresholdRuleColumnRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除栏目和报警门限规则关联", notes = "删除栏目和报警门限规则关联")
    public Map<String, Object> removeLogicThresholdRuleColumnRelByIds(@RequestBody LogicThresholdRuleColumnRel logicThresholdRuleColumnRel) {
        return returnMap(logicThresholdRuleColumnRelService.removeByIds(StringUtils.StringToIntList(logicThresholdRuleColumnRel.getIds())));
    }

}