package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicCustomizeModel;
import com.figure.core.query.logic.LogicCustomizeModelQuery;
import com.figure.core.service.logic.ILogicCustomizeModelService;
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
 * 自定义业务逻辑模板 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:15
 */
@RestController
@RequestMapping("/logicCustomizeModel")
@Api(value = "自定义业务逻辑模板相关接口", tags = "自定义业务逻辑模板相关接口")
public class LogicCustomizeModelController extends BaseController {

    @Resource
    private ILogicCustomizeModelService logicCustomizeModelService;

    /**
     * 根据id获取自定义业务逻辑模板
     *
     * @param id 自定义业务逻辑模板ID
     * @return 自定义业务逻辑模板
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取自定义业务逻辑模板", notes = "根据id获取自定义业务逻辑模板")
    public LogicCustomizeModel selectLogicCustomizeModelById(@PathVariable("id") Integer id) {
        return logicCustomizeModelService.getById(id);
    }

    /**
     * 查询自定义业务逻辑模板列表
     *
     * @param logicCustomizeModelQuery 自定义业务逻辑模板
     * @return 自定义业务逻辑模板集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询自定义业务逻辑模板列表", notes = "查询自定义业务逻辑模板列表")
    public TableDataInfo<LogicCustomizeModel> selectLogicCustomizeModelList(LogicCustomizeModelQuery logicCustomizeModelQuery) {
        return toPageResult(logicCustomizeModelService.list(logicCustomizeModelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存自定义业务逻辑模板
     *
     * @param logicCustomizeModel 自定义业务逻辑模板
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存自定义业务逻辑模板", notes = "新增保存自定义业务逻辑模板")
    public Map<String, Object> insertLogicCustomizeModel(@RequestBody LogicCustomizeModel logicCustomizeModel) {
        return returnMap(logicCustomizeModelService.save(logicCustomizeModel), logicCustomizeModel.getId());
    }

    /**
     * 批量新增保存自定义业务逻辑模板
     *
     * @param logicCustomizeModel 自定义业务逻辑模板
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存自定义业务逻辑模板", notes = "批量新增保存自定义业务逻辑模板")
    public Map<String, Object> saveBatchLogicCustomizeModel(@RequestBody List<LogicCustomizeModel> logicCustomizeModel) {
        return returnMap(logicCustomizeModelService.saveBatch(logicCustomizeModel), logicCustomizeModel);
    }

    /**
     * 修改保存自定义业务逻辑模板
     *
     * @param logicCustomizeModel 自定义业务逻辑模板
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存自定义业务逻辑模板", notes = "修改保存自定义业务逻辑模板")
    public Map<String, Object> updateLogicCustomizeModelById(@RequestBody LogicCustomizeModel logicCustomizeModel) {
        return returnMap(logicCustomizeModelService.updateById(logicCustomizeModel));
    }

    /**
     * 批量删除自定义业务逻辑模板
     *
     * @param logicCustomizeModel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除自定义业务逻辑模板", notes = "删除自定义业务逻辑模板")
    public Map<String, Object> removeLogicCustomizeModelByIds(@RequestBody LogicCustomizeModel logicCustomizeModel) {
        return returnMap(logicCustomizeModelService.removeByIds(StringUtils.StringToIntList(logicCustomizeModel.getIds())));
    }

}