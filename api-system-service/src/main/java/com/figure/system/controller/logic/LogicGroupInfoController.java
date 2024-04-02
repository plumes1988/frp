package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicGroupInfo;
import com.figure.core.query.logic.LogicGroupInfoQuery;
import com.figure.core.service.logic.ILogicGroupInfoService;
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
 * 逻辑分析组 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:15
 */
@RestController
@RequestMapping("/logicGroupInfo")
@Api(value = "逻辑分析组相关接口", tags = "逻辑分析组相关接口")
public class LogicGroupInfoController extends BaseController {

    @Resource
    private ILogicGroupInfoService logicGroupInfoService;

    /**
     * 根据id获取逻辑分析组
     *
     * @param id 逻辑分析组ID
     * @return 逻辑分析组
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取逻辑分析组", notes = "根据id获取逻辑分析组")
    public LogicGroupInfo selectLogicGroupInfoById(@PathVariable("id") Integer id) {
        return logicGroupInfoService.getById(id);
    }

    /**
     * 查询逻辑分析组列表
     *
     * @param logicGroupInfoQuery 逻辑分析组
     * @return 逻辑分析组集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询逻辑分析组列表", notes = "查询逻辑分析组列表")
    public TableDataInfo<LogicGroupInfo> selectLogicGroupInfoList(LogicGroupInfoQuery logicGroupInfoQuery) {
        return toPageResult(logicGroupInfoService.list(logicGroupInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存逻辑分析组
     *
     * @param logicGroupInfo 逻辑分析组
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存逻辑分析组", notes = "新增保存逻辑分析组")
    public Map<String, Object> insertLogicGroupInfo(@RequestBody LogicGroupInfo logicGroupInfo) {
        return returnMap(logicGroupInfoService.save(logicGroupInfo), logicGroupInfo.getId());
    }

    /**
     * 批量新增保存逻辑分析组
     *
     * @param logicGroupInfo 逻辑分析组
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存逻辑分析组", notes = "批量新增保存逻辑分析组")
    public Map<String, Object> saveBatchLogicGroupInfo(@RequestBody List<LogicGroupInfo> logicGroupInfo) {
        return returnMap(logicGroupInfoService.saveBatch(logicGroupInfo), logicGroupInfo);
    }

    /**
     * 修改保存逻辑分析组
     *
     * @param logicGroupInfo 逻辑分析组
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存逻辑分析组", notes = "修改保存逻辑分析组")
    public Map<String, Object> updateLogicGroupInfoById(@RequestBody LogicGroupInfo logicGroupInfo) {
        return returnMap(logicGroupInfoService.updateById(logicGroupInfo));
    }

    /**
     * 批量删除逻辑分析组
     *
     * @param logicGroupInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除逻辑分析组", notes = "删除逻辑分析组")
    public Map<String, Object> removeLogicGroupInfoByIds(@RequestBody LogicGroupInfo logicGroupInfo) {
        return returnMap(logicGroupInfoService.removeByIds(StringUtils.StringToIntList(logicGroupInfo.getIds())));
    }

}