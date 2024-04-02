package com.figure.system.controller.signal;

import com.figure.core.base.BaseController;
import com.figure.core.model.signal.SignalProgramInfo;
import com.figure.core.query.signal.SignalProgramInfoQuery;
import com.figure.core.service.signal.ISignalProgramInfoService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.text.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.figure.core.util.page.TableDataInfo;

/**
 * <p>
 * 节目信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-16 11:23:19
 */
@RestController
@RequestMapping("/signalProgramInfo")
@Api(value = "节目信息相关接口", tags = "节目信息相关接口")
public class SignalProgramInfoController extends BaseController {

    @Resource
    private ISignalProgramInfoService signalProgramInfoService;

    /**
     * 根据id获取节目信息
     *
     * @param id 节目信息ID
     * @return 节目信息
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取节目信息", notes = "根据id获取节目信息")
    public SignalProgramInfo selectSignalProgramInfoById(@PathVariable("id") Integer id) {
        return signalProgramInfoService.getById(id);
    }

    /**
     * 查询节目信息列表
     *
     * @param signalProgramInfoQuery 节目信息
     * @return 节目信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询节目信息列表", notes = "查询节目信息列表")
    public TableDataInfo<SignalProgramInfo> selectSignalProgramInfoList(SignalProgramInfoQuery signalProgramInfoQuery) {
        return toPageResult(signalProgramInfoService.list(signalProgramInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存节目信息
     *
     * @param signalProgramInfo 节目信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存节目信息", notes = "新增保存节目信息")
    public Map<String, Object> insertSignalProgramInfo(@RequestBody SignalProgramInfo signalProgramInfo) {
        return returnMap(signalProgramInfoService.save(signalProgramInfo));
    }

    /**
     * 批量新增保存节目信息
     *
     * @param signalProgramInfo 节目信息
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存节目信息", notes = "批量新增保存节目信息")
    public Map<String, Object> saveBatchSignalProgramInfo(@RequestBody List<SignalProgramInfo> signalProgramInfo) {
        return returnMap(signalProgramInfoService.saveBatch(signalProgramInfo));
    }

    /**
     * 修改保存节目信息
     *
     * @param signalProgramInfo 节目信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存节目信息", notes = "修改保存节目信息")
    public Map<String, Object> updateSignalProgramInfoById(@RequestBody SignalProgramInfo signalProgramInfo) {
        return returnMap(signalProgramInfoService.updateById(signalProgramInfo));
    }

    /**
     * 批量删除节目信息
     *
     * @param signalProgramInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除节目信息", notes = "删除节目信息")
    public Map<String, Object> removeSignalProgramInfoByIds(@RequestBody SignalProgramInfo signalProgramInfo) {
        return returnMap(signalProgramInfoService.removeByIds(StringUtils.StringToIntList(signalProgramInfo.getIds())));
    }

}