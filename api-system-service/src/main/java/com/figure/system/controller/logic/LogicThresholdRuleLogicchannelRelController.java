package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicThresholdRuleLogicchannelRel;
import com.figure.core.query.logic.LogicThresholdRuleLogicchannelRelQuery;
import com.figure.core.service.logic.ILogicThresholdRuleLogicchannelRelService;
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
 * 逻辑频道和报警门限规则关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:14
 */
@RestController
@RequestMapping("/logicThresholdRuleLogicchannelRel")
@Api(value = "逻辑频道和报警门限规则关联相关接口", tags = "逻辑频道和报警门限规则关联相关接口")
public class LogicThresholdRuleLogicchannelRelController extends BaseController {

    @Resource
    private ILogicThresholdRuleLogicchannelRelService logicThresholdRuleLogicchannelRelService;

    /**
     * 根据id获取逻辑频道和报警门限规则关联
     *
     * @param id 逻辑频道和报警门限规则关联ID
     * @return 逻辑频道和报警门限规则关联
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取逻辑频道和报警门限规则关联", notes = "根据id获取逻辑频道和报警门限规则关联")
    public LogicThresholdRuleLogicchannelRel selectLogicThresholdRuleLogicchannelRelById(@PathVariable("id") Integer id) {
        return logicThresholdRuleLogicchannelRelService.getById(id);
    }

    /**
     * 查询逻辑频道和报警门限规则关联列表
     *
     * @param logicThresholdRuleLogicchannelRelQuery 逻辑频道和报警门限规则关联
     * @return 逻辑频道和报警门限规则关联集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询逻辑频道和报警门限规则关联列表", notes = "查询逻辑频道和报警门限规则关联列表")
    public TableDataInfo<LogicThresholdRuleLogicchannelRel> selectLogicThresholdRuleLogicchannelRelList(LogicThresholdRuleLogicchannelRelQuery logicThresholdRuleLogicchannelRelQuery) {
        return toPageResult(logicThresholdRuleLogicchannelRelService.list(logicThresholdRuleLogicchannelRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存逻辑频道和报警门限规则关联
     *
     * @param logicThresholdRuleLogicchannelRel 逻辑频道和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存逻辑频道和报警门限规则关联", notes = "新增保存逻辑频道和报警门限规则关联")
    public Map<String, Object> insertLogicThresholdRuleLogicchannelRel(@RequestBody LogicThresholdRuleLogicchannelRel logicThresholdRuleLogicchannelRel) {
        return returnMap(logicThresholdRuleLogicchannelRelService.save(logicThresholdRuleLogicchannelRel), logicThresholdRuleLogicchannelRel.getId());
    }

    /**
     * 批量新增保存逻辑频道和报警门限规则关联
     *
     * @param logicThresholdRuleLogicchannelRel 逻辑频道和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存逻辑频道和报警门限规则关联", notes = "批量新增保存逻辑频道和报警门限规则关联")
    public Map<String, Object> saveBatchLogicThresholdRuleLogicchannelRel(@RequestBody List<LogicThresholdRuleLogicchannelRel> logicThresholdRuleLogicchannelRel) {
        return returnMap(logicThresholdRuleLogicchannelRelService.saveBatch(logicThresholdRuleLogicchannelRel), logicThresholdRuleLogicchannelRel);
    }

    /**
     * 修改保存逻辑频道和报警门限规则关联
     *
     * @param logicThresholdRuleLogicchannelRel 逻辑频道和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存逻辑频道和报警门限规则关联", notes = "修改保存逻辑频道和报警门限规则关联")
    public Map<String, Object> updateLogicThresholdRuleLogicchannelRelById(@RequestBody LogicThresholdRuleLogicchannelRel logicThresholdRuleLogicchannelRel) {
        return returnMap(logicThresholdRuleLogicchannelRelService.updateById(logicThresholdRuleLogicchannelRel));
    }

    /**
     * 批量删除逻辑频道和报警门限规则关联
     *
     * @param logicThresholdRuleLogicchannelRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除逻辑频道和报警门限规则关联", notes = "删除逻辑频道和报警门限规则关联")
    public Map<String, Object> removeLogicThresholdRuleLogicchannelRelByIds(@RequestBody LogicThresholdRuleLogicchannelRel logicThresholdRuleLogicchannelRel) {
        return returnMap(logicThresholdRuleLogicchannelRelService.removeByIds(StringUtils.StringToIntList(logicThresholdRuleLogicchannelRel.getIds())));
    }

}