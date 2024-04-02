package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicInterfaceChannelRel;
import com.figure.core.model.logic.LogicInterfaceChannelRelList;
import com.figure.core.query.logic.LogicInterfaceChannelRelListQuery;
import com.figure.core.query.logic.LogicInterfaceChannelRelQuery;
import com.figure.core.service.logic.ILogicInterfaceChannelRelService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 切换器接口和节目信息关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-12-02 10:18:53
 */
@RestController
@RequestMapping("/logicInterfaceChannelRel")
public class LogicInterfaceChannelRelController extends BaseController {

    @Resource
    private ILogicInterfaceChannelRelService logicInterfaceChannelRelService;

    /**
     * 根据id获取切换器接口和节目信息关联
     *
     * @param id 切换器接口和节目信息关联ID
     * @return 切换器接口和节目信息关联
     */
    @GetMapping("/get/{id}")
    public LogicInterfaceChannelRel selectLogicInterfaceChannelRelById(@PathVariable("id") Integer id) {
        return logicInterfaceChannelRelService.getById(id);
    }

    /**
     * 查询切换器接口和节目信息关联列表
     *
     * @param logicInterfaceChannelRelQuery 切换器接口和节目信息关联
     * @return 切换器接口和节目信息关联集合
     */
    @GetMapping("/list")
    public TableDataInfo<LogicInterfaceChannelRel> selectLogicInterfaceChannelRelList(LogicInterfaceChannelRelQuery logicInterfaceChannelRelQuery) {
        return toPageResult(logicInterfaceChannelRelService.list(logicInterfaceChannelRelQuery.autoPageWrapper()));
    }

    /**
     * 查询切换器接口和节目信息关联列表
     *
     * @param logicInterfaceChannelRelListQuery 切换器接口和节目信息关联
     * @return 切换器接口和节目信息关联集合
     */
    @GetMapping("/treelist")
    public TableDataInfo<LogicInterfaceChannelRelList> selectLogicInterfaceChannelRelTreeList(LogicInterfaceChannelRelListQuery logicInterfaceChannelRelListQuery) {
        return toPageResult(logicInterfaceChannelRelService.treelist(logicInterfaceChannelRelListQuery.autoPageWrapper()));
    }

    /**
     * 新增保存切换器接口和节目信息关联
     *
     * @param logicInterfaceChannelRel 切换器接口和节目信息关联
     * @return 结果
     */
    @PostMapping("/add")
    public Map<String, Object> insertLogicInterfaceChannelRel(@RequestBody LogicInterfaceChannelRel logicInterfaceChannelRel) {
        return returnMap(logicInterfaceChannelRelService.save(logicInterfaceChannelRel), logicInterfaceChannelRel.getId());
    }

    /**
     * 批量新增保存切换器接口和节目信息关联
     *
     * @param logicInterfaceChannelRel 切换器接口和节目信息关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    public Map<String, Object> saveBatchLogicInterfaceChannelRel(@RequestBody List<LogicInterfaceChannelRel> logicInterfaceChannelRel) {
        return returnMap(logicInterfaceChannelRelService.saveBatch(logicInterfaceChannelRel), logicInterfaceChannelRel);
    }

    /**
     * 修改保存切换器接口和节目信息关联
     *
     * @param logicInterfaceChannelRel 切换器接口和节目信息关联
     * @return 结果
     */
    @PostMapping("/edit")
    public Map<String, Object> updateLogicInterfaceChannelRelById(@RequestBody LogicInterfaceChannelRel logicInterfaceChannelRel) {
        return returnMap(logicInterfaceChannelRelService.updateById(logicInterfaceChannelRel));
    }

    /**
     * 批量删除切换器接口和节目信息关联
     *
     * @param logicInterfaceChannelRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    public Map<String, Object> removeLogicInterfaceChannelRelByIds(@RequestBody LogicInterfaceChannelRel logicInterfaceChannelRel) {
        return returnMap(logicInterfaceChannelRelService.removeByIds(StringUtils.StringToIntList(logicInterfaceChannelRel.getIds())));
    }

}