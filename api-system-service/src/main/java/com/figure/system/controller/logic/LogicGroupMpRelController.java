package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicGroupMpRel;
import com.figure.core.query.logic.LogicGroupMpRelQuery;
import com.figure.core.service.logic.ILogicGroupMpRelService;
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
 * 逻辑分析组与频道关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:15
 */
@RestController
@RequestMapping("/logicGroupMpRel")
@Api(value = "逻辑分析组与频道关联相关接口", tags = "逻辑分析组与频道关联相关接口")
public class LogicGroupMpRelController extends BaseController {

    @Resource
    private ILogicGroupMpRelService logicGroupMpRelService;

    /**
     * 根据id获取逻辑分析组与频道关联
     *
     * @param id 逻辑分析组与频道关联ID
     * @return 逻辑分析组与频道关联
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取逻辑分析组与频道关联", notes = "根据id获取逻辑分析组与频道关联")
    public LogicGroupMpRel selectLogicGroupMpRelById(@PathVariable("id") Integer id) {
        return logicGroupMpRelService.getById(id);
    }

    /**
     * 查询逻辑分析组与频道关联列表
     *
     * @param logicGroupMpRelQuery 逻辑分析组与频道关联
     * @return 逻辑分析组与频道关联集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询逻辑分析组与频道关联列表", notes = "查询逻辑分析组与频道关联列表")
    public TableDataInfo<LogicGroupMpRel> selectLogicGroupMpRelList(LogicGroupMpRelQuery logicGroupMpRelQuery) {
        return toPageResult(logicGroupMpRelService.list(logicGroupMpRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存逻辑分析组与频道关联
     *
     * @param logicGroupMpRel 逻辑分析组与频道关联
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存逻辑分析组与频道关联", notes = "新增保存逻辑分析组与频道关联")
    public Map<String, Object> insertLogicGroupMpRel(@RequestBody LogicGroupMpRel logicGroupMpRel) {
        return returnMap(logicGroupMpRelService.save(logicGroupMpRel), logicGroupMpRel.getId());
    }

    /**
     * 批量新增保存逻辑分析组与频道关联
     *
     * @param logicGroupMpRel 逻辑分析组与频道关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存逻辑分析组与频道关联", notes = "批量新增保存逻辑分析组与频道关联")
    public Map<String, Object> saveBatchLogicGroupMpRel(@RequestBody List<LogicGroupMpRel> logicGroupMpRel) {
        return returnMap(logicGroupMpRelService.saveBatch(logicGroupMpRel), logicGroupMpRel);
    }

    /**
     * 修改保存逻辑分析组与频道关联
     *
     * @param logicGroupMpRel 逻辑分析组与频道关联
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存逻辑分析组与频道关联", notes = "修改保存逻辑分析组与频道关联")
    public Map<String, Object> updateLogicGroupMpRelById(@RequestBody LogicGroupMpRel logicGroupMpRel) {
        return returnMap(logicGroupMpRelService.updateById(logicGroupMpRel));
    }

    /**
     * 批量删除逻辑分析组与频道关联
     *
     * @param logicGroupMpRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除逻辑分析组与频道关联", notes = "删除逻辑分析组与频道关联")
    public Map<String, Object> removeLogicGroupMpRelByIds(@RequestBody LogicGroupMpRel logicGroupMpRel) {
        return returnMap(logicGroupMpRelService.removeByIds(StringUtils.StringToIntList(logicGroupMpRel.getIds())));
    }

}