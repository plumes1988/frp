package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicThresholdRuleChannelRel;
import com.figure.core.query.logic.LogicThresholdRuleChannelRelQuery;
import com.figure.core.service.logic.ILogicThresholdRuleChannelRelService;
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
 * 信号频道和报警门限规则关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2023-03-07 16:00:32
 */
@RestController
@RequestMapping("/logicThresholdRuleChannelRel")
@Api(value = "信号频道和报警门限规则关联相关接口", tags = "信号频道和报警门限规则关联相关接口")
public class LogicThresholdRuleChannelRelController extends BaseController {

    @Resource
    private ILogicThresholdRuleChannelRelService logicThresholdRuleChannelRelService;

    /**
     * 根据id获取信号频道和报警门限规则关联
     *
     * @param id 信号频道和报警门限规则关联ID
     * @return 信号频道和报警门限规则关联
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取信号频道和报警门限规则关联", notes = "根据id获取信号频道和报警门限规则关联")
    public LogicThresholdRuleChannelRel selectLogicThresholdRuleChannelRelById(@PathVariable("id") Integer id) {
        return logicThresholdRuleChannelRelService.getById(id);
    }

    /**
     * 查询信号频道和报警门限规则关联列表
     *
     * @param logicThresholdRuleChannelRelQuery 信号频道和报警门限规则关联
     * @return 信号频道和报警门限规则关联集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询信号频道和报警门限规则关联列表", notes = "查询信号频道和报警门限规则关联列表")
    public TableDataInfo<LogicThresholdRuleChannelRel> selectLogicThresholdRuleChannelRelList(LogicThresholdRuleChannelRelQuery logicThresholdRuleChannelRelQuery) {
        return toPageResult(logicThresholdRuleChannelRelService.list(logicThresholdRuleChannelRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存信号频道和报警门限规则关联
     *
     * @param logicThresholdRuleChannelRel 信号频道和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存信号频道和报警门限规则关联", notes = "新增保存信号频道和报警门限规则关联")
    public Map<String, Object> insertLogicThresholdRuleChannelRel(@RequestBody LogicThresholdRuleChannelRel logicThresholdRuleChannelRel) {
        return returnMap(logicThresholdRuleChannelRelService.save(logicThresholdRuleChannelRel), logicThresholdRuleChannelRel.getId());
    }

    /**
     * 批量新增保存信号频道和报警门限规则关联
     *
     * @param logicThresholdRuleChannelRel 信号频道和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存信号频道和报警门限规则关联", notes = "批量新增保存信号频道和报警门限规则关联")
    public Map<String, Object> saveBatchLogicThresholdRuleChannelRel(@RequestBody List<LogicThresholdRuleChannelRel> logicThresholdRuleChannelRel) {
        return returnMap(logicThresholdRuleChannelRelService.saveBatch(logicThresholdRuleChannelRel), logicThresholdRuleChannelRel);
    }

    /**
     * 修改保存信号频道和报警门限规则关联
     *
     * @param logicThresholdRuleChannelRel 信号频道和报警门限规则关联
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存信号频道和报警门限规则关联", notes = "修改保存信号频道和报警门限规则关联")
    public Map<String, Object> updateLogicThresholdRuleChannelRelById(@RequestBody LogicThresholdRuleChannelRel logicThresholdRuleChannelRel) {
        return returnMap(logicThresholdRuleChannelRelService.updateById(logicThresholdRuleChannelRel));
    }

    /**
     * 批量删除信号频道和报警门限规则关联
     *
     * @param logicThresholdRuleChannelRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除信号频道和报警门限规则关联", notes = "删除信号频道和报警门限规则关联")
    public Map<String, Object> removeLogicThresholdRuleChannelRelByIds(@RequestBody LogicThresholdRuleChannelRel logicThresholdRuleChannelRel) {
        return returnMap(logicThresholdRuleChannelRelService.removeByIds(StringUtils.StringToIntList(logicThresholdRuleChannelRel.getIds())));
    }

}