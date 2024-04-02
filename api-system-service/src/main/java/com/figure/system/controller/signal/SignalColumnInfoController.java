package com.figure.system.controller.signal;

import com.figure.core.base.BaseController;
import com.figure.core.model.signal.SignalColumnInfo;
import com.figure.core.query.signal.SignalColumnInfoQuery;
import com.figure.core.service.signal.ISignalColumnInfoService;
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
 * 栏目信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-16 11:23:19
 */
@RestController
@RequestMapping("/signalColumnInfo")
@Api(value = "栏目信息相关接口", tags = "栏目信息相关接口")
public class SignalColumnInfoController extends BaseController {

    @Resource
    private ISignalColumnInfoService signalColumnInfoService;

    /**
     * 根据id获取栏目信息
     *
     * @param id 栏目信息ID
     * @return 栏目信息
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取栏目信息", notes = "根据id获取栏目信息")
    public SignalColumnInfo selectSignalColumnInfoById(@PathVariable("id") Integer id) {
        return signalColumnInfoService.getById(id);
    }

    /**
     * 查询栏目信息列表
     *
     * @param signalColumnInfoQuery 栏目信息
     * @return 栏目信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询栏目信息列表", notes = "查询栏目信息列表")
    public TableDataInfo<SignalColumnInfo> selectSignalColumnInfoList(SignalColumnInfoQuery signalColumnInfoQuery) {
        return toPageResult(signalColumnInfoService.list(signalColumnInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存栏目信息
     *
     * @param signalColumnInfo 栏目信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存栏目信息", notes = "新增保存栏目信息")
    public Map<String, Object> insertSignalColumnInfo(@RequestBody SignalColumnInfo signalColumnInfo) {
        return returnMap(signalColumnInfoService.save(signalColumnInfo));
    }

    /**
     * 批量新增保存栏目信息
     *
     * @param signalColumnInfo 栏目信息
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存栏目信息", notes = "批量新增保存栏目信息")
    public Map<String, Object> saveBatchSignalColumnInfo(@RequestBody List<SignalColumnInfo> signalColumnInfo) {
        return returnMap(signalColumnInfoService.saveBatch(signalColumnInfo));
    }

    /**
     * 修改保存栏目信息
     *
     * @param signalColumnInfo 栏目信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存栏目信息", notes = "修改保存栏目信息")
    public Map<String, Object> updateSignalColumnInfoById(@RequestBody SignalColumnInfo signalColumnInfo) {
        return returnMap(signalColumnInfoService.updateById(signalColumnInfo));
    }

    /**
     * 批量删除栏目信息
     *
     * @param signalColumnInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除栏目信息", notes = "删除栏目信息")
    public Map<String, Object> removeSignalColumnInfoByIds(@RequestBody SignalColumnInfo signalColumnInfo) {
        return returnMap(signalColumnInfoService.removeByIds(StringUtils.StringToIntList(signalColumnInfo.getIds())));
    }

}