package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicMonitorunitInterfaceInfo;
import com.figure.core.query.logic.LogicMonitorunitInterfaceInfoQuery;
import com.figure.core.service.logic.ILogicMonitorunitInterfaceInfoService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 监控单元接口管理信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@RestController
@RequestMapping("/logicMonitorunitInterfaceInfo")
public class LogicMonitorunitInterfaceInfoController extends BaseController {

    @Resource
    private ILogicMonitorunitInterfaceInfoService logicMonitorunitInterfaceInfoService;

    /**
     * 根据interfaceId获取监控单元接口管理信息
     *
     * @param interfaceId 监控单元接口管理信息ID
     * @return 监控单元接口管理信息
     */
    @GetMapping("/get/{interfaceId}")
    public LogicMonitorunitInterfaceInfo selectLogicMonitorunitInterfaceInfoById(@PathVariable("interfaceId") String interfaceId) {
        return logicMonitorunitInterfaceInfoService.getById(interfaceId);
    }

    /**
     * 查询监控单元接口管理信息列表
     *
     * @param logicMonitorunitInterfaceInfoQuery 监控单元接口管理信息
     * @return 监控单元接口管理信息集合
     */
    @GetMapping("/list")
    public TableDataInfo<LogicMonitorunitInterfaceInfo> selectLogicMonitorunitInterfaceInfoList(LogicMonitorunitInterfaceInfoQuery logicMonitorunitInterfaceInfoQuery) {
        return toPageResult(logicMonitorunitInterfaceInfoService.list(logicMonitorunitInterfaceInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存监控单元接口管理信息
     *
     * @param logicMonitorunitInterfaceInfo 监控单元接口管理信息
     * @return 结果
     */
    @PostMapping("/add")
    public Map<String, Object> insertLogicMonitorunitInterfaceInfo(@RequestBody LogicMonitorunitInterfaceInfo logicMonitorunitInterfaceInfo) {
        return returnMap(logicMonitorunitInterfaceInfoService.save(logicMonitorunitInterfaceInfo), logicMonitorunitInterfaceInfo.getInterfaceId());
    }

    /**
     * 批量新增保存监控单元接口管理信息
     *
     * @param logicMonitorunitInterfaceInfo 监控单元接口管理信息
     * @return 结果
     */
    @PostMapping("/addbatch")
    public Map<String, Object> saveBatchLogicMonitorunitInterfaceInfo(@RequestBody List<LogicMonitorunitInterfaceInfo> logicMonitorunitInterfaceInfo) {
        return returnMap(logicMonitorunitInterfaceInfoService.saveBatch(logicMonitorunitInterfaceInfo), logicMonitorunitInterfaceInfo);
    }

    /**
     * 修改保存监控单元接口管理信息
     *
     * @param logicMonitorunitInterfaceInfo 监控单元接口管理信息
     * @return 结果
     */
    @PostMapping("/edit")
    public Map<String, Object> updateLogicMonitorunitInterfaceInfoById(@RequestBody LogicMonitorunitInterfaceInfo logicMonitorunitInterfaceInfo) {
        return returnMap(logicMonitorunitInterfaceInfoService.updateById(logicMonitorunitInterfaceInfo));
    }

    /**
     * 批量删除监控单元接口管理信息
     *
     * @param logicMonitorunitInterfaceInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    public Map<String, Object> removeLogicMonitorunitInterfaceInfoByIds(@RequestBody LogicMonitorunitInterfaceInfo logicMonitorunitInterfaceInfo) {
        return returnMap(logicMonitorunitInterfaceInfoService.removeByIds(StringUtils.StringToStringList(logicMonitorunitInterfaceInfo.getIds())));
    }

}