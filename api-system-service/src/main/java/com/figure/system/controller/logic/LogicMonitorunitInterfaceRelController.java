package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicMonitorunitInterfaceRel;
import com.figure.core.query.logic.LogicMonitorunitInterfaceRelQuery;
import com.figure.core.service.logic.ILogicMonitorunitInterfaceRelService;
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
 * 监控单元和接口信息关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2023-02-21 10:41:10
 */
@RestController
@RequestMapping("/logicMonitorunitInterfaceRel")
@Api(value = "监控单元和接口信息关联相关接口", tags = "监控单元和接口信息关联相关接口")
public class LogicMonitorunitInterfaceRelController extends BaseController {

    @Resource
    private ILogicMonitorunitInterfaceRelService logicMonitorunitInterfaceRelService;

    /**
     * 根据id获取监控单元和接口信息关联
     *
     * @param id 监控单元和接口信息关联ID
     * @return 监控单元和接口信息关联
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取监控单元和接口信息关联", notes = "根据id获取监控单元和接口信息关联")
    public LogicMonitorunitInterfaceRel selectLogicMonitorunitInterfaceRelById(@PathVariable("id") Integer id) {
        return logicMonitorunitInterfaceRelService.getById(id);
    }

    /**
     * 查询监控单元和接口信息关联列表
     *
     * @param logicMonitorunitInterfaceRelQuery 监控单元和接口信息关联
     * @return 监控单元和接口信息关联集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询监控单元和接口信息关联列表", notes = "查询监控单元和接口信息关联列表")
    public TableDataInfo<LogicMonitorunitInterfaceRel> selectLogicMonitorunitInterfaceRelList(LogicMonitorunitInterfaceRelQuery logicMonitorunitInterfaceRelQuery) {
        return toPageResult(logicMonitorunitInterfaceRelService.list(logicMonitorunitInterfaceRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存监控单元和接口信息关联
     *
     * @param logicMonitorunitInterfaceRel 监控单元和接口信息关联
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存监控单元和接口信息关联", notes = "新增保存监控单元和接口信息关联")
    public Map<String, Object> insertLogicMonitorunitInterfaceRel(@RequestBody LogicMonitorunitInterfaceRel logicMonitorunitInterfaceRel) {
        return returnMap(logicMonitorunitInterfaceRelService.save(logicMonitorunitInterfaceRel), logicMonitorunitInterfaceRel.getId());
    }

    /**
     * 批量新增保存监控单元和接口信息关联
     *
     * @param logicMonitorunitInterfaceRel 监控单元和接口信息关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存监控单元和接口信息关联", notes = "批量新增保存监控单元和接口信息关联")
    public Map<String, Object> saveBatchLogicMonitorunitInterfaceRel(@RequestBody List<LogicMonitorunitInterfaceRel> logicMonitorunitInterfaceRel) {
        return returnMap(logicMonitorunitInterfaceRelService.saveBatch(logicMonitorunitInterfaceRel), logicMonitorunitInterfaceRel);
    }

    /**
     * 修改保存监控单元和接口信息关联
     *
     * @param logicMonitorunitInterfaceRel 监控单元和接口信息关联
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存监控单元和接口信息关联", notes = "修改保存监控单元和接口信息关联")
    public Map<String, Object> updateLogicMonitorunitInterfaceRelById(@RequestBody LogicMonitorunitInterfaceRel logicMonitorunitInterfaceRel) {
        return returnMap(logicMonitorunitInterfaceRelService.updateById(logicMonitorunitInterfaceRel));
    }

    /**
     * 批量删除监控单元和接口信息关联
     *
     * @param logicMonitorunitInterfaceRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除监控单元和接口信息关联", notes = "删除监控单元和接口信息关联")
    public Map<String, Object> removeLogicMonitorunitInterfaceRelByIds(@RequestBody LogicMonitorunitInterfaceRel logicMonitorunitInterfaceRel) {
        return returnMap(logicMonitorunitInterfaceRelService.removeByIds(StringUtils.StringToIntList(logicMonitorunitInterfaceRel.getIds())));
    }

}