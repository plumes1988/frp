package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicMonitorunitInfo;
import com.figure.core.model.logic.LogicMonitorunitInfoList;
import com.figure.core.query.logic.LogicMonitorunitInfoQuery;
import com.figure.core.service.logic.ILogicMonitorunitInfoService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 监控单元信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@RestController
@RequestMapping("/logicMonitorunitInfo")
public class LogicMonitorunitInfoController extends BaseController {

    @Resource
    private ILogicMonitorunitInfoService logicMonitorunitInfoService;

    /**
     * 根据unitId获取监控单元信息
     *
     * @param unitId 监控单元信息ID
     * @return 监控单元信息
     */
    @GetMapping("/get/{unitId}")
    public LogicMonitorunitInfo selectLogicMonitorunitInfoById(@PathVariable("unitId") Integer unitId) {
        return logicMonitorunitInfoService.getById(unitId);
    }

    /**
     * 查询监控单元信息列表
     *
     * @param logicMonitorunitInfoQuery 监控单元信息
     * @return 监控单元信息集合
     */
    @GetMapping("/relList")
    public TableDataInfo<LogicMonitorunitInfoList> selectLogicMonitorunitInfoRelList(LogicMonitorunitInfoQuery logicMonitorunitInfoQuery) {
        return toPageResult(logicMonitorunitInfoService.relList(logicMonitorunitInfoQuery.autoPageWrapper()));
    }

    /**
     * 查询监控单元信息列表
     *
     * @param logicMonitorunitInfoQuery 监控单元信息
     * @return 监控单元信息集合
     */
    @GetMapping("/list")
    public TableDataInfo<LogicMonitorunitInfo> selectLogicMonitorunitInfoList(LogicMonitorunitInfoQuery logicMonitorunitInfoQuery) {
        return toPageResult(logicMonitorunitInfoService.list(logicMonitorunitInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存监控单元信息
     *
     * @param logicMonitorunitInfo 监控单元信息
     * @return 结果
     */
    @PostMapping("/add")
    public Map<String, Object> insertLogicMonitorunitInfo(@RequestBody LogicMonitorunitInfo logicMonitorunitInfo) {
        return returnMap(logicMonitorunitInfoService.save(logicMonitorunitInfo), logicMonitorunitInfo.getUnitId());
    }

    /**
     * 批量新增保存监控单元信息
     *
     * @param logicMonitorunitInfo 监控单元信息
     * @return 结果
     */
    @PostMapping("/addbatch")
    public Map<String, Object> saveBatchLogicMonitorunitInfo(@RequestBody List<LogicMonitorunitInfo> logicMonitorunitInfo) {
        return returnMap(logicMonitorunitInfoService.saveBatch(logicMonitorunitInfo), logicMonitorunitInfo);
    }

    /**
     * 修改保存监控单元信息
     *
     * @param logicMonitorunitInfo 监控单元信息
     * @return 结果
     */
    @PostMapping("/edit")
    public Map<String, Object> updateLogicMonitorunitInfoById(@RequestBody LogicMonitorunitInfo logicMonitorunitInfo) {
        return returnMap(logicMonitorunitInfoService.updateById(logicMonitorunitInfo));
    }

    /**
     * 批量删除监控单元信息
     *
     * @param logicMonitorunitInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    public Map<String, Object> removeLogicMonitorunitInfoByIds(@RequestBody LogicMonitorunitInfo logicMonitorunitInfo) {
        this.logicMonitorunitInfoService.deleteMonitorunitRel(StringUtils.StringToIntList(logicMonitorunitInfo.getIds()));
        return returnMap(logicMonitorunitInfoService.removeByIds(StringUtils.StringToIntList(logicMonitorunitInfo.getIds())));
    }

}