package com.figure.system.controller.signal;

import com.figure.core.base.BaseController;
import com.figure.core.model.signal.SignalAnalysisServer;
import com.figure.core.query.signal.SignalAnalysisServerQuery;
import com.figure.core.service.signal.ISignalAnalysisServerService;
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
 * 多源分析服务管理 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-11-28 09:58:04
 */
@RestController
@RequestMapping("/signalAnalysisServer")
@Api(value = "多源分析服务管理相关接口", tags = "多源分析服务管理相关接口")
public class SignalAnalysisServerController extends BaseController {

    @Resource
    private ISignalAnalysisServerService signalAnalysisServerService;

    /**
     * 根据id获取多源分析服务管理
     *
     * @param id 多源分析服务管理ID
     * @return 多源分析服务管理
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取多源分析服务管理", notes = "根据id获取多源分析服务管理")
    public SignalAnalysisServer selectSignalAnalysisServerById(@PathVariable("id") Integer id) {
        return signalAnalysisServerService.getById(id);
    }

    /**
     * 查询多源分析服务管理列表
     *
     * @param signalAnalysisServerQuery 多源分析服务管理
     * @return 多源分析服务管理集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询多源分析服务管理列表", notes = "查询多源分析服务管理列表")
    public TableDataInfo<SignalAnalysisServer> selectSignalAnalysisServerList(SignalAnalysisServerQuery signalAnalysisServerQuery) {
        return toPageResult(signalAnalysisServerService.list(signalAnalysisServerQuery.autoPageWrapper()));
    }

    /**
     * 新增保存多源分析服务管理
     *
     * @param signalAnalysisServer 多源分析服务管理
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存多源分析服务管理", notes = "新增保存多源分析服务管理")
    public Map<String, Object> insertSignalAnalysisServer(@RequestBody SignalAnalysisServer signalAnalysisServer) {
        return returnMap(signalAnalysisServerService.save(signalAnalysisServer), signalAnalysisServer.getId());
    }

    /**
     * 批量新增保存多源分析服务管理
     *
     * @param signalAnalysisServer 多源分析服务管理
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存多源分析服务管理", notes = "批量新增保存多源分析服务管理")
    public Map<String, Object> saveBatchSignalAnalysisServer(@RequestBody List<SignalAnalysisServer> signalAnalysisServer) {
        return returnMap(signalAnalysisServerService.saveBatch(signalAnalysisServer), signalAnalysisServer);
    }

    /**
     * 修改保存多源分析服务管理
     *
     * @param signalAnalysisServer 多源分析服务管理
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存多源分析服务管理", notes = "修改保存多源分析服务管理")
    public Map<String, Object> updateSignalAnalysisServerById(@RequestBody SignalAnalysisServer signalAnalysisServer) {
        return returnMap(signalAnalysisServerService.updateById(signalAnalysisServer));
    }

    /**
     * 批量删除多源分析服务管理
     *
     * @param signalAnalysisServer 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除多源分析服务管理", notes = "删除多源分析服务管理")
    public Map<String, Object> removeSignalAnalysisServerByIds(@RequestBody SignalAnalysisServer signalAnalysisServer) {
        return returnMap(signalAnalysisServerService.removeByIds(StringUtils.StringToIntList(signalAnalysisServer.getIds())));
    }

}