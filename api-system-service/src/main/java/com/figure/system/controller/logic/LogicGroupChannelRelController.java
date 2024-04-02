package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicGroupChannelRel;
import com.figure.core.model.logic.LogicGroupChannelRelList;
import com.figure.core.query.logic.LogicGroupChannelRelQuery;
import com.figure.core.service.logic.ILogicGroupChannelRelService;
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
 * 逻辑分析组与频道关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:15
 */
@RestController
@RequestMapping("/logicGroupChannelRel")
@Api(value = "逻辑分析组与频道关联相关接口", tags = "逻辑分析组与频道关联相关接口")
public class LogicGroupChannelRelController extends BaseController {

    @Resource
    private ILogicGroupChannelRelService logicGroupChannelRelService;

    /**
     * 根据id获取逻辑分析组与频道关联
     *
     * @param id 逻辑分析组与频道关联ID
     * @return 逻辑分析组与频道关联
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取逻辑分析组与频道关联", notes = "根据id获取逻辑分析组与频道关联")
    public LogicGroupChannelRel selectLogicGroupChannelRelById(@PathVariable("id") Integer id) {
        return logicGroupChannelRelService.getById(id);
    }

    /**
     * 查询逻辑分析组与频道关联列表
     *
     * @param logicGroupChannelRelQuery 逻辑分析组与频道关联
     * @return 逻辑分析组与频道关联集合
     */
    @GetMapping("/treelist")
    @ApiOperation(value = "查询逻辑分析组与频道关联列表", notes = "查询逻辑分析组与频道关联列表")
    public TableDataInfo<LogicGroupChannelRelList> selectLogicGroupChannelRelTreeList(LogicGroupChannelRelQuery logicGroupChannelRelQuery) {
        return toPageResult(logicGroupChannelRelService.treelist(logicGroupChannelRelQuery.autoPageWrapper()));
    }

    /**
     * 查询逻辑分析组与频道关联列表
     *
     * @param logicGroupChannelRelQuery 逻辑分析组与频道关联
     * @return 逻辑分析组与频道关联集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询逻辑分析组与频道关联列表", notes = "查询逻辑分析组与频道关联列表")
    public TableDataInfo<LogicGroupChannelRel> selectLogicGroupChannelRelList(LogicGroupChannelRelQuery logicGroupChannelRelQuery) {
        return toPageResult(logicGroupChannelRelService.list(logicGroupChannelRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存逻辑分析组与频道关联
     *
     * @param logicGroupChannelRel 逻辑分析组与频道关联
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存逻辑分析组与频道关联", notes = "新增保存逻辑分析组与频道关联")
    public Map<String, Object> insertLogicGroupChannelRel(@RequestBody LogicGroupChannelRel logicGroupChannelRel) {
        return returnMap(logicGroupChannelRelService.save(logicGroupChannelRel), logicGroupChannelRel.getId());
    }

    /**
     * 批量新增保存逻辑分析组与频道关联
     *
     * @param logicGroupChannelRel 逻辑分析组与频道关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存逻辑分析组与频道关联", notes = "批量新增保存逻辑分析组与频道关联")
    public Map<String, Object> saveBatchLogicGroupChannelRel(@RequestBody List<LogicGroupChannelRel> logicGroupChannelRel) {
        return returnMap(logicGroupChannelRelService.saveBatch(logicGroupChannelRel), logicGroupChannelRel);
    }

    /**
     * 修改保存逻辑分析组与频道关联
     *
     * @param logicGroupChannelRel 逻辑分析组与频道关联
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存逻辑分析组与频道关联", notes = "修改保存逻辑分析组与频道关联")
    public Map<String, Object> updateLogicGroupChannelRelById(@RequestBody LogicGroupChannelRel logicGroupChannelRel) {
        return returnMap(logicGroupChannelRelService.updateById(logicGroupChannelRel));
    }

    /**
     * 批量删除逻辑分析组与频道关联
     *
     * @param logicGroupChannelRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除逻辑分析组与频道关联", notes = "删除逻辑分析组与频道关联")
    public Map<String, Object> removeLogicGroupChannelRelByIds(@RequestBody LogicGroupChannelRel logicGroupChannelRel) {
        return returnMap(logicGroupChannelRelService.removeByIds(StringUtils.StringToIntList(logicGroupChannelRel.getIds())));
    }

}