package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicCompareModel;
import com.figure.core.query.logic.LogicCompareModelQuery;
import com.figure.core.service.logic.ILogicCompareModelService;
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
 * 异态比对分组配置模板 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:15
 */
@RestController
@RequestMapping("/logicCompareModel")
@Api(value = "异态比对分组配置模板相关接口", tags = "异态比对分组配置模板相关接口")
public class LogicCompareModelController extends BaseController {

    @Resource
    private ILogicCompareModelService logicCompareModelService;

    /**
     * 根据id获取异态比对分组配置模板
     *
     * @param id 异态比对分组配置模板ID
     * @return 异态比对分组配置模板
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取异态比对分组配置模板", notes = "根据id获取异态比对分组配置模板")
    public LogicCompareModel selectLogicCompareModelById(@PathVariable("id") Integer id) {
        return logicCompareModelService.getById(id);
    }

    /**
     * 查询异态比对分组配置模板列表
     *
     * @param logicCompareModelQuery 异态比对分组配置模板
     * @return 异态比对分组配置模板集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询异态比对分组配置模板列表", notes = "查询异态比对分组配置模板列表")
    public TableDataInfo<LogicCompareModel> selectLogicCompareModelList(LogicCompareModelQuery logicCompareModelQuery) {
        return toPageResult(logicCompareModelService.list(logicCompareModelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存异态比对分组配置模板
     *
     * @param logicCompareModel 异态比对分组配置模板
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存异态比对分组配置模板", notes = "新增保存异态比对分组配置模板")
    public Map<String, Object> insertLogicCompareModel(@RequestBody LogicCompareModel logicCompareModel) {
        return returnMap(logicCompareModelService.save(logicCompareModel), logicCompareModel.getId());
    }

    /**
     * 批量新增保存异态比对分组配置模板
     *
     * @param logicCompareModel 异态比对分组配置模板
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存异态比对分组配置模板", notes = "批量新增保存异态比对分组配置模板")
    public Map<String, Object> saveBatchLogicCompareModel(@RequestBody List<LogicCompareModel> logicCompareModel) {
        return returnMap(logicCompareModelService.saveBatch(logicCompareModel), logicCompareModel);
    }

    /**
     * 修改保存异态比对分组配置模板
     *
     * @param logicCompareModel 异态比对分组配置模板
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存异态比对分组配置模板", notes = "修改保存异态比对分组配置模板")
    public Map<String, Object> updateLogicCompareModelById(@RequestBody LogicCompareModel logicCompareModel) {
        return returnMap(logicCompareModelService.updateById(logicCompareModel));
    }

    /**
     * 批量删除异态比对分组配置模板
     *
     * @param logicCompareModel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除异态比对分组配置模板", notes = "删除异态比对分组配置模板")
    public Map<String, Object> removeLogicCompareModelByIds(@RequestBody LogicCompareModel logicCompareModel) {
        return returnMap(logicCompareModelService.removeByIds(StringUtils.StringToIntList(logicCompareModel.getIds())));
    }

}