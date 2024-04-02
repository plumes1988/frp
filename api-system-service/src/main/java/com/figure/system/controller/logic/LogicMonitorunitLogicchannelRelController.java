package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicMonitorunitLogicchannelRel;
import com.figure.core.query.logic.LogicMonitorunitLogicchannelRelQuery;
import com.figure.core.service.logic.ILogicMonitorunitLogicchannelRelService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 监控单元和逻辑分析组关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@RestController
@RequestMapping("/logicMonitorunitLogicchannelRel")
public class LogicMonitorunitLogicchannelRelController extends BaseController {

    @Resource
    private ILogicMonitorunitLogicchannelRelService logicMonitorunitLogicchannelRelService;

    /**
     * 根据id获取监控单元和逻辑分析组关联
     *
     * @param id 监控单元和逻辑分析组关联ID
     * @return 监控单元和逻辑分析组关联
     */
    @GetMapping("/get/{id}")
    public LogicMonitorunitLogicchannelRel selectLogicMonitorunitLogicchannelRelById(@PathVariable("id") Integer id) {
        return logicMonitorunitLogicchannelRelService.getById(id);
    }

    /**
     * 查询监控单元和逻辑分析组关联列表
     *
     * @param logicMonitorunitLogicchannelRelQuery 监控单元和逻辑分析组关联
     * @return 监控单元和逻辑分析组关联集合
     */
    @GetMapping("/list")
    public TableDataInfo<LogicMonitorunitLogicchannelRel> selectLogicMonitorunitLogicchannelRelList(LogicMonitorunitLogicchannelRelQuery logicMonitorunitLogicchannelRelQuery) {
        return toPageResult(logicMonitorunitLogicchannelRelService.list(logicMonitorunitLogicchannelRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存监控单元和逻辑分析组关联
     *
     * @param logicMonitorunitLogicchannelRel 监控单元和逻辑分析组关联
     * @return 结果
     */
    @PostMapping("/add")
    public Map<String, Object> insertLogicMonitorunitLogicchannelRel(@RequestBody LogicMonitorunitLogicchannelRel logicMonitorunitLogicchannelRel) {
        return returnMap(logicMonitorunitLogicchannelRelService.save(logicMonitorunitLogicchannelRel), logicMonitorunitLogicchannelRel.getId());
    }

    /**
     * 批量新增保存监控单元和逻辑分析组关联
     *
     * @param logicMonitorunitLogicchannelRel 监控单元和逻辑分析组关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    public Map<String, Object> saveBatchLogicMonitorunitLogicchannelRel(@RequestBody List<LogicMonitorunitLogicchannelRel> logicMonitorunitLogicchannelRel) {
        return returnMap(logicMonitorunitLogicchannelRelService.saveBatch(logicMonitorunitLogicchannelRel), logicMonitorunitLogicchannelRel);
    }

    /**
     * 修改保存监控单元和逻辑分析组关联
     *
     * @param logicMonitorunitLogicchannelRel 监控单元和逻辑分析组关联
     * @return 结果
     */
    @PostMapping("/edit")
    public Map<String, Object> updateLogicMonitorunitLogicchannelRelById(@RequestBody LogicMonitorunitLogicchannelRel logicMonitorunitLogicchannelRel) {
        return returnMap(logicMonitorunitLogicchannelRelService.updateById(logicMonitorunitLogicchannelRel));
    }

    /**
     * 批量删除监控单元和逻辑分析组关联
     *
     * @param logicMonitorunitLogicchannelRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    public Map<String, Object> removeLogicMonitorunitLogicchannelRelByIds(@RequestBody LogicMonitorunitLogicchannelRel logicMonitorunitLogicchannelRel) {
        return returnMap(logicMonitorunitLogicchannelRelService.removeByIds(StringUtils.StringToIntList(logicMonitorunitLogicchannelRel.getIds())));
    }

}