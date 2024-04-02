package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicServerInfo;
import com.figure.core.query.logic.LogicServerInfoQuery;
import com.figure.core.service.logic.ILogicServerInfoService;
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
 * 逻辑中心服务器 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:14
 */
@RestController
@RequestMapping("/logicServerInfo")
@Api(value = "逻辑中心服务器相关接口", tags = "逻辑中心服务器相关接口")
public class LogicServerInfoController extends BaseController {

    @Resource
    private ILogicServerInfoService logicServerInfoService;

    /**
     * 根据id获取逻辑中心服务器
     *
     * @param id 逻辑中心服务器ID
     * @return 逻辑中心服务器
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取逻辑中心服务器", notes = "根据id获取逻辑中心服务器")
    public LogicServerInfo selectLogicServerInfoById(@PathVariable("id") Integer id) {
        return logicServerInfoService.getById(id);
    }

    /**
     * 查询逻辑中心服务器列表
     *
     * @param logicServerInfoQuery 逻辑中心服务器
     * @return 逻辑中心服务器集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询逻辑中心服务器列表", notes = "查询逻辑中心服务器列表")
    public TableDataInfo<LogicServerInfo> selectLogicServerInfoList(LogicServerInfoQuery logicServerInfoQuery) {
        return toPageResult(logicServerInfoService.list(logicServerInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存逻辑中心服务器
     *
     * @param logicServerInfo 逻辑中心服务器
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存逻辑中心服务器", notes = "新增保存逻辑中心服务器")
    public Map<String, Object> insertLogicServerInfo(@RequestBody LogicServerInfo logicServerInfo) {
        return returnMap(logicServerInfoService.save(logicServerInfo), logicServerInfo.getId());
    }

    /**
     * 批量新增保存逻辑中心服务器
     *
     * @param logicServerInfo 逻辑中心服务器
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存逻辑中心服务器", notes = "批量新增保存逻辑中心服务器")
    public Map<String, Object> saveBatchLogicServerInfo(@RequestBody List<LogicServerInfo> logicServerInfo) {
        return returnMap(logicServerInfoService.saveBatch(logicServerInfo), logicServerInfo);
    }

    /**
     * 修改保存逻辑中心服务器
     *
     * @param logicServerInfo 逻辑中心服务器
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存逻辑中心服务器", notes = "修改保存逻辑中心服务器")
    public Map<String, Object> updateLogicServerInfoById(@RequestBody LogicServerInfo logicServerInfo) {
        return returnMap(logicServerInfoService.updateById(logicServerInfo));
    }

    /**
     * 批量删除逻辑中心服务器
     *
     * @param logicServerInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除逻辑中心服务器", notes = "删除逻辑中心服务器")
    public Map<String, Object> removeLogicServerInfoByIds(@RequestBody LogicServerInfo logicServerInfo) {
        return returnMap(logicServerInfoService.removeByIds(StringUtils.StringToIntList(logicServerInfo.getIds())));
    }

}