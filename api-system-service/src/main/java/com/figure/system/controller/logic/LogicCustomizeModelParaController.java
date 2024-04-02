package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicCustomizeModelPara;
import com.figure.core.query.logic.LogicCustomizeModelParaQuery;
import com.figure.core.service.logic.ILogicCustomizeModelParaService;
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
 * 自定义业务逻辑参数模板 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:15
 */
@RestController
@RequestMapping("/logicCustomizeModelPara")
@Api(value = "自定义业务逻辑参数模板相关接口", tags = "自定义业务逻辑参数模板相关接口")
public class LogicCustomizeModelParaController extends BaseController {

    @Resource
    private ILogicCustomizeModelParaService logicCustomizeModelParaService;

    /**
     * 根据id获取自定义业务逻辑参数模板
     *
     * @param id 自定义业务逻辑参数模板ID
     * @return 自定义业务逻辑参数模板
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取自定义业务逻辑参数模板", notes = "根据id获取自定义业务逻辑参数模板")
    public LogicCustomizeModelPara selectLogicCustomizeModelParaById(@PathVariable("id") Integer id) {
        return logicCustomizeModelParaService.getById(id);
    }

    /**
     * 查询自定义业务逻辑参数模板列表
     *
     * @param logicCustomizeModelParaQuery 自定义业务逻辑参数模板
     * @return 自定义业务逻辑参数模板集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询自定义业务逻辑参数模板列表", notes = "查询自定义业务逻辑参数模板列表")
    public TableDataInfo<LogicCustomizeModelPara> selectLogicCustomizeModelParaList(LogicCustomizeModelParaQuery logicCustomizeModelParaQuery) {
        return toPageResult(logicCustomizeModelParaService.list(logicCustomizeModelParaQuery.autoPageWrapper()));
    }

    /**
     * 新增保存自定义业务逻辑参数模板
     *
     * @param logicCustomizeModelPara 自定义业务逻辑参数模板
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存自定义业务逻辑参数模板", notes = "新增保存自定义业务逻辑参数模板")
    public Map<String, Object> insertLogicCustomizeModelPara(@RequestBody LogicCustomizeModelPara logicCustomizeModelPara) {
        return returnMap(logicCustomizeModelParaService.save(logicCustomizeModelPara), logicCustomizeModelPara.getId());
    }

    /**
     * 批量新增保存自定义业务逻辑参数模板
     *
     * @param logicCustomizeModelPara 自定义业务逻辑参数模板
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存自定义业务逻辑参数模板", notes = "批量新增保存自定义业务逻辑参数模板")
    public Map<String, Object> saveBatchLogicCustomizeModelPara(@RequestBody List<LogicCustomizeModelPara> logicCustomizeModelPara) {
        return returnMap(logicCustomizeModelParaService.saveBatch(logicCustomizeModelPara), logicCustomizeModelPara);
    }

    /**
     * 修改保存自定义业务逻辑参数模板
     *
     * @param logicCustomizeModelPara 自定义业务逻辑参数模板
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存自定义业务逻辑参数模板", notes = "修改保存自定义业务逻辑参数模板")
    public Map<String, Object> updateLogicCustomizeModelParaById(@RequestBody LogicCustomizeModelPara logicCustomizeModelPara) {
        return returnMap(logicCustomizeModelParaService.updateById(logicCustomizeModelPara));
    }

    /**
     * 批量删除自定义业务逻辑参数模板
     *
     * @param logicCustomizeModelPara 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除自定义业务逻辑参数模板", notes = "删除自定义业务逻辑参数模板")
    public Map<String, Object> removeLogicCustomizeModelParaByIds(@RequestBody LogicCustomizeModelPara logicCustomizeModelPara) {
        return returnMap(logicCustomizeModelParaService.removeByIds(StringUtils.StringToIntList(logicCustomizeModelPara.getIds())));
    }

}