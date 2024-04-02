package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicCustomizeInfoPara;
import com.figure.core.model.logic.LogicCustomizeInfoParaList;
import com.figure.core.query.logic.LogicCustomizeInfoParaQuery;
import com.figure.core.service.logic.ILogicCustomizeInfoParaService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自定义业务逻辑参数 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:15
 */
@RestController
@RequestMapping("/logicCustomizeInfoPara")
@Api(value = "自定义业务逻辑参数相关接口", tags = "自定义业务逻辑参数相关接口")
public class LogicCustomizeInfoParaController extends BaseController {

    @Resource
    private ILogicCustomizeInfoParaService logicCustomizeInfoParaService;

    /**
     * 根据id获取自定义业务逻辑参数
     *
     * @param id 自定义业务逻辑参数ID
     * @return 自定义业务逻辑参数
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取自定义业务逻辑参数", notes = "根据id获取自定义业务逻辑参数")
    public LogicCustomizeInfoPara selectLogicCustomizeInfoParaById(@PathVariable("id") Integer id) {
        return logicCustomizeInfoParaService.getById(id);
    }

    /**
     * 查询自定义业务逻辑参数列表
     *
     * @param logicCustomizeInfoParaQuery 自定义业务逻辑参数
     * @return 自定义业务逻辑参数集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询自定义业务逻辑参数列表", notes = "查询自定义业务逻辑参数列表")
    public TableDataInfo<LogicCustomizeInfoPara> selectLogicCustomizeInfoParaList(LogicCustomizeInfoParaQuery logicCustomizeInfoParaQuery) {
        return toPageResult(logicCustomizeInfoParaService.list(logicCustomizeInfoParaQuery.autoPageWrapper()));
    }

    /**
     * 查询自定义业务逻辑参数列表
     *
     * @param logicCustomizeInfoParaQuery 自定义业务逻辑参数
     * @return 自定义业务逻辑参数集合
     */
    @GetMapping("/treelist")
    @ApiOperation(value = "查询自定义业务逻辑参数列表", notes = "查询自定义业务逻辑参数列表")
    public TableDataInfo<LogicCustomizeInfoParaList> selectLogicCustomizeInfoParaTreeList(LogicCustomizeInfoParaQuery logicCustomizeInfoParaQuery) {
        return toPageResult(logicCustomizeInfoParaService.treelist(logicCustomizeInfoParaQuery.autoPageWrapper()));
    }

    /**
     * 新增保存自定义业务逻辑参数
     *
     * @param logicCustomizeInfoPara 自定义业务逻辑参数
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存自定义业务逻辑参数", notes = "新增保存自定义业务逻辑参数")
    public Map<String, Object> insertLogicCustomizeInfoPara(@RequestBody LogicCustomizeInfoPara logicCustomizeInfoPara) {
        return returnMap(logicCustomizeInfoParaService.save(logicCustomizeInfoPara), logicCustomizeInfoPara.getId());
    }

    /**
     * 批量新增保存自定义业务逻辑参数
     *
     * @param logicCustomizeInfoPara 自定义业务逻辑参数
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存自定义业务逻辑参数", notes = "批量新增保存自定义业务逻辑参数")
    public Map<String, Object> saveBatchLogicCustomizeInfoPara(@RequestBody List<LogicCustomizeInfoPara> logicCustomizeInfoPara) {
        return returnMap(logicCustomizeInfoParaService.saveBatch(logicCustomizeInfoPara), logicCustomizeInfoPara);
    }

    /**
     * 修改保存自定义业务逻辑参数
     *
     * @param logicCustomizeInfoPara 自定义业务逻辑参数
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存自定义业务逻辑参数", notes = "修改保存自定义业务逻辑参数")
    public Map<String, Object> updateLogicCustomizeInfoParaById(@RequestBody LogicCustomizeInfoPara logicCustomizeInfoPara) {
        return returnMap(logicCustomizeInfoParaService.updateById(logicCustomizeInfoPara));
    }

    /**
     * 批量删除自定义业务逻辑参数
     *
     * @param logicCustomizeInfoPara 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除自定义业务逻辑参数", notes = "删除自定义业务逻辑参数")
    public Map<String, Object> removeLogicCustomizeInfoParaByIds(@RequestBody LogicCustomizeInfoPara logicCustomizeInfoPara) {
        return returnMap(logicCustomizeInfoParaService.removeByIds(StringUtils.StringToIntList(logicCustomizeInfoPara.getIds())));
    }

}