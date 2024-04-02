package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicThresholdRuleProgramRel;
import com.figure.core.query.logic.LogicThresholdRuleProgramRelQuery;
import com.figure.core.service.logic.ILogicThresholdRuleProgramRelService;
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
 * 节目和报警门限规则关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:15
 */
@RestController
@RequestMapping("/logicThresholdRuleProgramRel")
@Api(value = "节目和报警门限规则关联相关接口", tags = "节目和报警门限规则关联相关接口")
public class LogicThresholdRuleProgramRelController extends BaseController {

    @Resource
    private ILogicThresholdRuleProgramRelService logicThresholdRuleProgramRelService;

    /**
     * 根据id获取节目和报警门限规则关联
     *
     * @param id 节目和报警门限规则关联ID
     * @return 节目和报警门限规则关联
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取节目和报警门限规则关联", notes = "根据id获取节目和报警门限规则关联")
    public LogicThresholdRuleProgramRel selectLogicThresholdRuleProgramRelById(@PathVariable("id") Integer id) {
        return logicThresholdRuleProgramRelService.getById(id);
    }

    /**
     * 查询节目和报警门限规则关联列表
     *
     * @param logicThresholdRuleProgramRelQuery 节目和报警门限规则关联
     * @return 节目和报警门限规则关联集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询节目和报警门限规则关联列表", notes = "查询节目和报警门限规则关联列表")
    public TableDataInfo<LogicThresholdRuleProgramRel> selectLogicThresholdRuleProgramRelList(LogicThresholdRuleProgramRelQuery logicThresholdRuleProgramRelQuery) {
        return toPageResult(logicThresholdRuleProgramRelService.list(logicThresholdRuleProgramRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存节目和报警门限规则关联
     *
     * @param logicThresholdRuleProgramRel 节目和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存节目和报警门限规则关联", notes = "新增保存节目和报警门限规则关联")
    public Map<String, Object> insertLogicThresholdRuleProgramRel(@RequestBody LogicThresholdRuleProgramRel logicThresholdRuleProgramRel) {
        return returnMap(logicThresholdRuleProgramRelService.save(logicThresholdRuleProgramRel), logicThresholdRuleProgramRel.getId());
    }

    /**
     * 批量新增保存节目和报警门限规则关联
     *
     * @param logicThresholdRuleProgramRel 节目和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存节目和报警门限规则关联", notes = "批量新增保存节目和报警门限规则关联")
    public Map<String, Object> saveBatchLogicThresholdRuleProgramRel(@RequestBody List<LogicThresholdRuleProgramRel> logicThresholdRuleProgramRel) {
        return returnMap(logicThresholdRuleProgramRelService.saveBatch(logicThresholdRuleProgramRel), logicThresholdRuleProgramRel);
    }

    /**
     * 修改保存节目和报警门限规则关联
     *
     * @param logicThresholdRuleProgramRel 节目和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存节目和报警门限规则关联", notes = "修改保存节目和报警门限规则关联")
    public Map<String, Object> updateLogicThresholdRuleProgramRelById(@RequestBody LogicThresholdRuleProgramRel logicThresholdRuleProgramRel) {
        return returnMap(logicThresholdRuleProgramRelService.updateById(logicThresholdRuleProgramRel));
    }

    /**
     * 批量删除节目和报警门限规则关联
     *
     * @param logicThresholdRuleProgramRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除节目和报警门限规则关联", notes = "删除节目和报警门限规则关联")
    public Map<String, Object> removeLogicThresholdRuleProgramRelByIds(@RequestBody LogicThresholdRuleProgramRel logicThresholdRuleProgramRel) {
        return returnMap(logicThresholdRuleProgramRelService.removeByIds(StringUtils.StringToIntList(logicThresholdRuleProgramRel.getIds())));
    }

}