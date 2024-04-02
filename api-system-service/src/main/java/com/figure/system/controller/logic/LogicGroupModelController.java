package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicGroupModel;
import com.figure.core.query.logic.LogicGroupModelQuery;
import com.figure.core.service.logic.ILogicGroupModelService;
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
 * 逻辑分析组模板 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:15
 */
@RestController
@RequestMapping("/logicGroupModel")
@Api(value = "逻辑分析组模板相关接口", tags = "逻辑分析组模板相关接口")
public class LogicGroupModelController extends BaseController {

    @Resource
    private ILogicGroupModelService logicGroupModelService;

    /**
     * 根据id获取逻辑分析组模板
     *
     * @param id 逻辑分析组模板ID
     * @return 逻辑分析组模板
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取逻辑分析组模板", notes = "根据id获取逻辑分析组模板")
    public LogicGroupModel selectLogicGroupModelById(@PathVariable("id") Integer id) {
        return logicGroupModelService.getById(id);
    }

    /**
     * 查询逻辑分析组模板列表
     *
     * @param logicGroupModelQuery 逻辑分析组模板
     * @return 逻辑分析组模板集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询逻辑分析组模板列表", notes = "查询逻辑分析组模板列表")
    public TableDataInfo<LogicGroupModel> selectLogicGroupModelList(LogicGroupModelQuery logicGroupModelQuery) {
        return toPageResult(logicGroupModelService.list(logicGroupModelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存逻辑分析组模板
     *
     * @param logicGroupModel 逻辑分析组模板
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存逻辑分析组模板", notes = "新增保存逻辑分析组模板")
    public Map<String, Object> insertLogicGroupModel(@RequestBody LogicGroupModel logicGroupModel) {
        return returnMap(logicGroupModelService.save(logicGroupModel), logicGroupModel.getId());
    }

    /**
     * 批量新增保存逻辑分析组模板
     *
     * @param logicGroupModel 逻辑分析组模板
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存逻辑分析组模板", notes = "批量新增保存逻辑分析组模板")
    public Map<String, Object> saveBatchLogicGroupModel(@RequestBody List<LogicGroupModel> logicGroupModel) {
        return returnMap(logicGroupModelService.saveBatch(logicGroupModel), logicGroupModel);
    }

    /**
     * 修改保存逻辑分析组模板
     *
     * @param logicGroupModel 逻辑分析组模板
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存逻辑分析组模板", notes = "修改保存逻辑分析组模板")
    public Map<String, Object> updateLogicGroupModelById(@RequestBody LogicGroupModel logicGroupModel) {
        return returnMap(logicGroupModelService.updateById(logicGroupModel));
    }

    /**
     * 批量删除逻辑分析组模板
     *
     * @param logicGroupModel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除逻辑分析组模板", notes = "删除逻辑分析组模板")
    public Map<String, Object> removeLogicGroupModelByIds(@RequestBody LogicGroupModel logicGroupModel) {
        return returnMap(logicGroupModelService.removeByIds(StringUtils.StringToIntList(logicGroupModel.getIds())));
    }

}