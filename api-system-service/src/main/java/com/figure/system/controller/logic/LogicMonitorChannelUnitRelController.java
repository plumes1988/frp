package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicMonitorChannelUnitRel;
import com.figure.core.query.logic.LogicMonitorChannelUnitRelQuery;
import com.figure.core.service.logic.ILogicMonitorChannelUnitRelService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 监控频道与监控单元关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@RestController
@RequestMapping("/logicMonitorChannelUnitRel")
public class LogicMonitorChannelUnitRelController extends BaseController {

    @Resource
    private ILogicMonitorChannelUnitRelService logicMonitorChannelUnitRelService;

    /**
     * 根据id获取监控频道与监控单元关联
     *
     * @param id 监控频道与监控单元关联ID
     * @return 监控频道与监控单元关联
     */
    @GetMapping("/get/{id}")
    public LogicMonitorChannelUnitRel selectLogicMonitorChannelUnitRelById(@PathVariable("id") Integer id) {
        return logicMonitorChannelUnitRelService.getById(id);
    }

    /**
     * 查询监控频道与监控单元关联列表
     *
     * @param logicMonitorChannelUnitRelQuery 监控频道与监控单元关联
     * @return 监控频道与监控单元关联集合
     */
    @GetMapping("/list")
    public TableDataInfo<LogicMonitorChannelUnitRel> selectLogicMonitorChannelUnitRelList(LogicMonitorChannelUnitRelQuery logicMonitorChannelUnitRelQuery) {
        return toPageResult(logicMonitorChannelUnitRelService.list(logicMonitorChannelUnitRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存监控频道与监控单元关联
     *
     * @param logicMonitorChannelUnitRel 监控频道与监控单元关联
     * @return 结果
     */
    @PostMapping("/add")
    public Map<String, Object> insertLogicMonitorChannelUnitRel(@RequestBody LogicMonitorChannelUnitRel logicMonitorChannelUnitRel) {
        return returnMap(logicMonitorChannelUnitRelService.save(logicMonitorChannelUnitRel), logicMonitorChannelUnitRel.getId());
    }

    /**
     * 批量新增保存监控频道与监控单元关联
     *
     * @param logicMonitorChannelUnitRel 监控频道与监控单元关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    public Map<String, Object> saveBatchLogicMonitorChannelUnitRel(@RequestBody List<LogicMonitorChannelUnitRel> logicMonitorChannelUnitRel) {
        return returnMap(logicMonitorChannelUnitRelService.saveBatch(logicMonitorChannelUnitRel), logicMonitorChannelUnitRel);
    }

    /**
     * 修改保存监控频道与监控单元关联
     *
     * @param logicMonitorChannelUnitRel 监控频道与监控单元关联
     * @return 结果
     */
    @PostMapping("/edit")
    public Map<String, Object> updateLogicMonitorChannelUnitRelById(@RequestBody LogicMonitorChannelUnitRel logicMonitorChannelUnitRel) {
        return returnMap(logicMonitorChannelUnitRelService.updateById(logicMonitorChannelUnitRel));
    }

    /**
     * 批量删除监控频道与监控单元关联
     *
     * @param logicMonitorChannelUnitRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    public Map<String, Object> removeLogicMonitorChannelUnitRelByIds(@RequestBody LogicMonitorChannelUnitRel logicMonitorChannelUnitRel) {
        return returnMap(logicMonitorChannelUnitRelService.removeByIds(StringUtils.StringToIntList(logicMonitorChannelUnitRel.getIds())));
    }

}