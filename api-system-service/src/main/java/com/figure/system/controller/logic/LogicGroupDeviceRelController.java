package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicGroupDeviceRel;
import com.figure.core.model.logic.LogicGroupDeviceRelList;
import com.figure.core.query.logic.LogicGroupDeviceRelQuery;
import com.figure.core.service.logic.ILogicGroupDeviceRelService;
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
 * 逻辑分析组与设备关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2023-03-10 16:19:09
 */
@RestController
@RequestMapping("/logicGroupDeviceRel")
@Api(value = "逻辑分析组与设备关联相关接口", tags = "逻辑分析组与设备关联相关接口")
public class LogicGroupDeviceRelController extends BaseController {

    @Resource
    private ILogicGroupDeviceRelService logicGroupDeviceRelService;

    /**
     * 根据id获取逻辑分析组与设备关联
     *
     * @param id 逻辑分析组与设备关联ID
     * @return 逻辑分析组与设备关联
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取逻辑分析组与设备关联", notes = "根据id获取逻辑分析组与设备关联")
    public LogicGroupDeviceRel selectLogicGroupDeviceRelById(@PathVariable("id") Integer id) {
        return logicGroupDeviceRelService.getById(id);
    }

    /**
     * 查询逻辑分析组与设备关联列表
     *
     * @param logicGroupDeviceRelQuery 逻辑分析组与设备关联
     * @return 逻辑分析组与设备关联集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询逻辑分析组与设备关联列表", notes = "查询逻辑分析组与设备关联列表")
    public TableDataInfo<LogicGroupDeviceRel> selectLogicGroupDeviceRelList(LogicGroupDeviceRelQuery logicGroupDeviceRelQuery) {
        return toPageResult(logicGroupDeviceRelService.list(logicGroupDeviceRelQuery.autoPageWrapper()));
    }

    /**
     * 查询逻辑分析组与设备关联列表
     *
     * @param logicGroupDeviceRelQuery 逻辑分析组与设备关联
     * @return 逻辑分析组与设备关联集合
     */
    @GetMapping("/treelist")
    @ApiOperation(value = "查询逻辑分析组与设备关联列表", notes = "查询逻辑分析组与设备关联列表")
    public TableDataInfo<LogicGroupDeviceRelList> selectLogicGroupDeviceRelTreeList(LogicGroupDeviceRelQuery logicGroupDeviceRelQuery) {
        return toPageResult(logicGroupDeviceRelService.treelist(logicGroupDeviceRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存逻辑分析组与设备关联
     *
     * @param logicGroupDeviceRel 逻辑分析组与设备关联
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存逻辑分析组与设备关联", notes = "新增保存逻辑分析组与设备关联")
    public Map<String, Object> insertLogicGroupDeviceRel(@RequestBody LogicGroupDeviceRel logicGroupDeviceRel) {
        return returnMap(logicGroupDeviceRelService.save(logicGroupDeviceRel), logicGroupDeviceRel.getId());
    }

    /**
     * 批量新增保存逻辑分析组与设备关联
     *
     * @param logicGroupDeviceRel 逻辑分析组与设备关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存逻辑分析组与设备关联", notes = "批量新增保存逻辑分析组与设备关联")
    public Map<String, Object> saveBatchLogicGroupDeviceRel(@RequestBody List<LogicGroupDeviceRel> logicGroupDeviceRel) {
        return returnMap(logicGroupDeviceRelService.saveBatch(logicGroupDeviceRel), logicGroupDeviceRel);
    }

    /**
     * 修改保存逻辑分析组与设备关联
     *
     * @param logicGroupDeviceRel 逻辑分析组与设备关联
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存逻辑分析组与设备关联", notes = "修改保存逻辑分析组与设备关联")
    public Map<String, Object> updateLogicGroupDeviceRelById(@RequestBody LogicGroupDeviceRel logicGroupDeviceRel) {
        return returnMap(logicGroupDeviceRelService.updateById(logicGroupDeviceRel));
    }

    /**
     * 批量删除逻辑分析组与设备关联
     *
     * @param logicGroupDeviceRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除逻辑分析组与设备关联", notes = "删除逻辑分析组与设备关联")
    public Map<String, Object> removeLogicGroupDeviceRelByIds(@RequestBody LogicGroupDeviceRel logicGroupDeviceRel) {
        return returnMap(logicGroupDeviceRelService.removeByIds(StringUtils.StringToIntList(logicGroupDeviceRel.getIds())));
    }

}