package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicMonitorchannelInfo;
import com.figure.core.model.logic.LogicMonitorchannelInfoList;
import com.figure.core.query.logic.LogicMonitorchannelInfoQuery;
import com.figure.core.service.logic.ILogicMonitorchannelInfoService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 监控频道管理信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@RestController
@RequestMapping("/logicMonitorchannelInfo")
public class LogicMonitorchannelInfoController extends BaseController {

    @Resource
    private ILogicMonitorchannelInfoService logicMonitorchannelInfoService;

    /**
     * 根据monitorChannelCode获取监控频道管理信息
     *
     * @param monitorChannelCode 监控频道管理信息ID
     * @return 监控频道管理信息
     */
    @GetMapping("/get/{monitorChannelCode}")
    public LogicMonitorchannelInfo selectLogicMonitorchannelInfoById(@PathVariable("monitorChannelCode") String monitorChannelCode) {
        return logicMonitorchannelInfoService.getById(monitorChannelCode);
    }

    /**
     * 查询监控频道管理信息列表
     *
     * @param logicMonitorchannelInfoQuery 监控频道管理信息
     * @return 监控频道管理信息集合
     */
    @GetMapping("/list")
    public TableDataInfo<LogicMonitorchannelInfo> selectLogicMonitorchannelInfoList(LogicMonitorchannelInfoQuery logicMonitorchannelInfoQuery) {
        return toPageResult(logicMonitorchannelInfoService.list(logicMonitorchannelInfoQuery.autoPageWrapper()));
    }

    /**
     * 查询监控频道管理tree列表
     *
     * @param logicMonitorchannelInfoQuery 监控频道管理信息
     * @return 监控频道管理信息集合
     */
    @GetMapping("/treelist")
    public TableDataInfo<LogicMonitorchannelInfoList> selectLogicMonitorchannelTreeList(LogicMonitorchannelInfoQuery logicMonitorchannelInfoQuery) {
        return toPageResult(logicMonitorchannelInfoService.listByQuery(logicMonitorchannelInfoQuery.autoPageWrapper()));
    }

    /**
     * 查询监控频道管理map列表
     *
     * @param logicMonitorchannelInfo 监控频道管理信息
     * @return 监控频道管理信息集合
     */
    @GetMapping("/maplist")
    public Map<String, Object> selectLogicMonitorchannelMapList(LogicMonitorchannelInfo logicMonitorchannelInfo) {
        return returnMap(logicMonitorchannelInfoService.selectMonitorchannelMapList());
    }

    /**
     * 新增保存监控频道管理信息
     *
     * @param logicMonitorchannelInfo 监控频道管理信息
     * @return 结果
     */
    @PostMapping("/add")
    public Map<String, Object> insertLogicMonitorchannelInfo(@RequestBody LogicMonitorchannelInfo logicMonitorchannelInfo) {
        return returnMap(logicMonitorchannelInfoService.save(logicMonitorchannelInfo), logicMonitorchannelInfo.getMonitorChannelCode());
    }

    /**
     * 批量新增保存监控频道管理信息
     *
     * @param logicMonitorchannelInfo 监控频道管理信息
     * @return 结果
     */
    @PostMapping("/addbatch")
    public Map<String, Object> saveBatchLogicMonitorchannelInfo(@RequestBody List<LogicMonitorchannelInfo> logicMonitorchannelInfo) {
        return returnMap(logicMonitorchannelInfoService.saveBatch(logicMonitorchannelInfo), logicMonitorchannelInfo);
    }

    /**
     * 修改保存监控频道管理信息
     *
     * @param logicMonitorchannelInfo 监控频道管理信息
     * @return 结果
     */
    @PostMapping("/edit")
    public Map<String, Object> updateLogicMonitorchannelInfoById(@RequestBody LogicMonitorchannelInfo logicMonitorchannelInfo) {
        return returnMap(logicMonitorchannelInfoService.updateById(logicMonitorchannelInfo));
    }

    /**
     * 批量删除监控频道管理信息
     *
     * @param logicMonitorchannelInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    public Map<String, Object> removeLogicMonitorchannelInfoByIds(@RequestBody LogicMonitorchannelInfo logicMonitorchannelInfo) {
        return returnMap(logicMonitorchannelInfoService.removeByIds(StringUtils.StringToStringList(logicMonitorchannelInfo.getIds())));
    }

}