package com.figure.system.controller.signal;

import com.figure.core.base.BaseController;
import com.figure.core.model.signal.SignalEpgInfo;
import com.figure.core.query.signal.SignalEpgInfoQuery;
import com.figure.core.service.signal.ISignalEpgInfoService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 节目单信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-05-25 17:10:38
 */
@RestController
@RequestMapping("/signalEpgInfo")
@Api(value = "节目单信息相关接口", tags = "节目单信息相关接口")
public class SignalEpgInfoController extends BaseController {

    @Resource
    private ISignalEpgInfoService signalEpgInfoService;

    /**
     * 根据epgId获取节目单信息
     *
     * @param epgId 节目单信息ID
     * @return 节目单信息
     */
    @GetMapping("/get/{epgId}")
    @ApiOperation(value = "根据epgId获取节目单信息", notes = "根据epgId获取节目单信息")
    public SignalEpgInfo selectSignalEpgInfoById(@PathVariable("epgId") Long epgId) {
        return signalEpgInfoService.getById(epgId);
    }

    /**
     * 查询节目单信息列表
     *
     * @param signalEpgInfoQuery 节目单信息
     * @return 节目单信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询节目单信息列表", notes = "查询节目单信息列表")
    public TableDataInfo<SignalEpgInfo> selectSignalEpgInfoList(SignalEpgInfoQuery signalEpgInfoQuery) {
        return toPageResult(signalEpgInfoService.list(signalEpgInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存节目单信息
     *
     * @param signalEpgInfo 节目单信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存节目单信息" , notes = "新增保存节目单信息")
    public Map<String,Object> insertSignalEpgInfo(@RequestBody SignalEpgInfo signalEpgInfo) {
        return returnMap(signalEpgInfoService.save(signalEpgInfo));
    }

    /**
     * 修改保存节目单信息
     *
     * @param signalEpgInfo 节目单信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存节目单信息" , notes = "修改保存节目单信息")
    public Map<String,Object> updateSignalEpgInfoById(@RequestBody SignalEpgInfo signalEpgInfo) {
        return returnMap(signalEpgInfoService.updateById(signalEpgInfo));
    }

    /**
     * 批量删除节目单信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除节目单信息" , notes = "删除节目单信息")
    public Map<String,Object> removeSignalEpgInfoByIds(String ids) {
        return returnMap(signalEpgInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids))));
    }

}