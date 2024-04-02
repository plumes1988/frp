package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicMonitorchannelLogicchannelRel;
import com.figure.core.query.logic.LogicMonitorchannelLogicchannelRelQuery;
import com.figure.core.service.logic.ILogicMonitorchannelLogicchannelRelService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 监控频道与逻辑频道关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@RestController
@RequestMapping("/logicMonitorchannelLogicchannelRel")
public class LogicMonitorchannelLogicchannelRelController extends BaseController {

    @Resource
    private ILogicMonitorchannelLogicchannelRelService logicMonitorchannelLogicchannelRelService;

    /**
     * 根据id获取监控频道与逻辑频道关联
     *
     * @param id 监控频道与逻辑频道关联ID
     * @return 监控频道与逻辑频道关联
     */
    @GetMapping("/get/{id}")
    public LogicMonitorchannelLogicchannelRel selectLogicMonitorchannelLogicchannelRelById(@PathVariable("id") Integer id) {
        return logicMonitorchannelLogicchannelRelService.getById(id);
    }

    /**
     * 查询监控频道与逻辑频道关联列表
     *
     * @param logicMonitorchannelLogicchannelRelQuery 监控频道与逻辑频道关联
     * @return 监控频道与逻辑频道关联集合
     */
    @GetMapping("/list")
    public TableDataInfo<LogicMonitorchannelLogicchannelRel> selectLogicMonitorchannelLogicchannelRelList(LogicMonitorchannelLogicchannelRelQuery logicMonitorchannelLogicchannelRelQuery) {
        return toPageResult(logicMonitorchannelLogicchannelRelService.list(logicMonitorchannelLogicchannelRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存监控频道与逻辑频道关联
     *
     * @param logicMonitorchannelLogicchannelRel 监控频道与逻辑频道关联
     * @return 结果
     */
    @PostMapping("/add")
    public Map<String, Object> insertLogicMonitorchannelLogicchannelRel(@RequestBody LogicMonitorchannelLogicchannelRel logicMonitorchannelLogicchannelRel) {
        return returnMap(logicMonitorchannelLogicchannelRelService.save(logicMonitorchannelLogicchannelRel), logicMonitorchannelLogicchannelRel.getId());
    }

    /**
     * 批量新增保存监控频道与逻辑频道关联
     *
     * @param logicMonitorchannelLogicchannelRel 监控频道与逻辑频道关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    public Map<String, Object> saveBatchLogicMonitorchannelLogicchannelRel(@RequestBody List<LogicMonitorchannelLogicchannelRel> logicMonitorchannelLogicchannelRel) {
        return returnMap(logicMonitorchannelLogicchannelRelService.saveBatch(logicMonitorchannelLogicchannelRel), logicMonitorchannelLogicchannelRel);
    }

    /**
     * 修改保存监控频道与逻辑频道关联
     *
     * @param logicMonitorchannelLogicchannelRel 监控频道与逻辑频道关联
     * @return 结果
     */
    @PostMapping("/edit")
    public Map<String, Object> updateLogicMonitorchannelLogicchannelRelById(@RequestBody LogicMonitorchannelLogicchannelRel logicMonitorchannelLogicchannelRel) {
        return returnMap(logicMonitorchannelLogicchannelRelService.updateById(logicMonitorchannelLogicchannelRel));
    }

    /**
     * 批量删除监控频道与逻辑频道关联
     *
     * @param logicMonitorchannelLogicchannelRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    public Map<String, Object> removeLogicMonitorchannelLogicchannelRelByIds(@RequestBody LogicMonitorchannelLogicchannelRel logicMonitorchannelLogicchannelRel) {
        return returnMap(logicMonitorchannelLogicchannelRelService.removeByIds(StringUtils.StringToIntList(logicMonitorchannelLogicchannelRel.getIds())));
    }

}