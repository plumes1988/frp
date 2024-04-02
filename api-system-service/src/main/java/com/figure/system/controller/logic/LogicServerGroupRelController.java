package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicServerGroupRel;
import com.figure.core.query.logic.LogicServerGroupRelQuery;
import com.figure.core.service.logic.ILogicServerGroupRelService;
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
 * 逻辑中心服务器与逻辑分析组关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:14
 */
@RestController
@RequestMapping("/logicServerGroupRel")
@Api(value = "逻辑中心服务器与逻辑分析组关联相关接口", tags = "逻辑中心服务器与逻辑分析组关联相关接口")
public class LogicServerGroupRelController extends BaseController {

    @Resource
    private ILogicServerGroupRelService logicServerGroupRelService;

    /**
     * 根据id获取逻辑中心服务器与逻辑分析组关联
     *
     * @param id 逻辑中心服务器与逻辑分析组关联ID
     * @return 逻辑中心服务器与逻辑分析组关联
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取逻辑中心服务器与逻辑分析组关联", notes = "根据id获取逻辑中心服务器与逻辑分析组关联")
    public LogicServerGroupRel selectLogicServerGroupRelById(@PathVariable("id") Integer id) {
        return logicServerGroupRelService.getById(id);
    }

    /**
     * 查询逻辑中心服务器与逻辑分析组关联列表
     *
     * @param logicServerGroupRelQuery 逻辑中心服务器与逻辑分析组关联
     * @return 逻辑中心服务器与逻辑分析组关联集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询逻辑中心服务器与逻辑分析组关联列表", notes = "查询逻辑中心服务器与逻辑分析组关联列表")
    public TableDataInfo<LogicServerGroupRel> selectLogicServerGroupRelList(LogicServerGroupRelQuery logicServerGroupRelQuery) {
        return toPageResult(logicServerGroupRelService.list(logicServerGroupRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存逻辑中心服务器与逻辑分析组关联
     *
     * @param logicServerGroupRel 逻辑中心服务器与逻辑分析组关联
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存逻辑中心服务器与逻辑分析组关联", notes = "新增保存逻辑中心服务器与逻辑分析组关联")
    public Map<String, Object> insertLogicServerGroupRel(@RequestBody LogicServerGroupRel logicServerGroupRel) {
        return returnMap(logicServerGroupRelService.save(logicServerGroupRel), logicServerGroupRel.getId());
    }

    /**
     * 批量新增保存逻辑中心服务器与逻辑分析组关联
     *
     * @param logicServerGroupRel 逻辑中心服务器与逻辑分析组关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存逻辑中心服务器与逻辑分析组关联", notes = "批量新增保存逻辑中心服务器与逻辑分析组关联")
    public Map<String, Object> saveBatchLogicServerGroupRel(@RequestBody List<LogicServerGroupRel> logicServerGroupRel) {
        return returnMap(logicServerGroupRelService.saveBatch(logicServerGroupRel), logicServerGroupRel);
    }

    /**
     * 修改保存逻辑中心服务器与逻辑分析组关联
     *
     * @param logicServerGroupRel 逻辑中心服务器与逻辑分析组关联
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存逻辑中心服务器与逻辑分析组关联", notes = "修改保存逻辑中心服务器与逻辑分析组关联")
    public Map<String, Object> updateLogicServerGroupRelById(@RequestBody LogicServerGroupRel logicServerGroupRel) {
        return returnMap(logicServerGroupRelService.updateById(logicServerGroupRel));
    }

    /**
     * 批量删除逻辑中心服务器与逻辑分析组关联
     *
     * @param logicServerGroupRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除逻辑中心服务器与逻辑分析组关联", notes = "删除逻辑中心服务器与逻辑分析组关联")
    public Map<String, Object> removeLogicServerGroupRelByIds(@RequestBody LogicServerGroupRel logicServerGroupRel) {
        return returnMap(logicServerGroupRelService.removeByIds(StringUtils.StringToIntList(logicServerGroupRel.getIds())));
    }

}