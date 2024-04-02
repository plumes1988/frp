package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicCustomizeInfo;
import com.figure.core.query.logic.LogicCustomizeInfoQuery;
import com.figure.core.service.logic.ILogicCustomizeInfoService;
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
 * 自定义业务逻辑 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:15
 */
@RestController
@RequestMapping("/logicCustomizeInfo")
@Api(value = "自定义业务逻辑相关接口", tags = "自定义业务逻辑相关接口")
public class LogicCustomizeInfoController extends BaseController {

    @Resource
    private ILogicCustomizeInfoService logicCustomizeInfoService;

    /**
     * 根据id获取自定义业务逻辑
     *
     * @param id 自定义业务逻辑ID
     * @return 自定义业务逻辑
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取自定义业务逻辑", notes = "根据id获取自定义业务逻辑")
    public LogicCustomizeInfo selectLogicCustomizeInfoById(@PathVariable("id") Integer id) {
        return logicCustomizeInfoService.getById(id);
    }

    /**
     * 查询自定义业务逻辑列表
     *
     * @param logicCustomizeInfoQuery 自定义业务逻辑
     * @return 自定义业务逻辑集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询自定义业务逻辑列表", notes = "查询自定义业务逻辑列表")
    public TableDataInfo<LogicCustomizeInfo> selectLogicCustomizeInfoList(LogicCustomizeInfoQuery logicCustomizeInfoQuery) {
        return toPageResult(logicCustomizeInfoService.list(logicCustomizeInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存自定义业务逻辑
     *
     * @param logicCustomizeInfo 自定义业务逻辑
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存自定义业务逻辑", notes = "新增保存自定义业务逻辑")
    public Map<String, Object> insertLogicCustomizeInfo(@RequestBody LogicCustomizeInfo logicCustomizeInfo) {
        return returnMap(logicCustomizeInfoService.save(logicCustomizeInfo), logicCustomizeInfo.getId());
    }

    /**
     * 批量新增保存自定义业务逻辑
     *
     * @param logicCustomizeInfo 自定义业务逻辑
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存自定义业务逻辑", notes = "批量新增保存自定义业务逻辑")
    public Map<String, Object> saveBatchLogicCustomizeInfo(@RequestBody List<LogicCustomizeInfo> logicCustomizeInfo) {
        return returnMap(logicCustomizeInfoService.saveBatch(logicCustomizeInfo), logicCustomizeInfo);
    }

    /**
     * 修改保存自定义业务逻辑
     *
     * @param logicCustomizeInfo 自定义业务逻辑
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存自定义业务逻辑", notes = "修改保存自定义业务逻辑")
    public Map<String, Object> updateLogicCustomizeInfoById(@RequestBody LogicCustomizeInfo logicCustomizeInfo) {
        return returnMap(logicCustomizeInfoService.updateById(logicCustomizeInfo));
    }

    /**
     * 批量删除自定义业务逻辑
     *
     * @param logicCustomizeInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除自定义业务逻辑", notes = "删除自定义业务逻辑")
    public Map<String, Object> removeLogicCustomizeInfoByIds(@RequestBody LogicCustomizeInfo logicCustomizeInfo) {
        return returnMap(logicCustomizeInfoService.removeByIds(StringUtils.StringToIntList(logicCustomizeInfo.getIds())));
    }

}