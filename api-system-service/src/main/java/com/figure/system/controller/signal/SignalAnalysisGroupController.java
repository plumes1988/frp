package com.figure.system.controller.signal;

import com.figure.core.base.BaseController;
import com.figure.core.model.signal.SignalAnalysisGroup;
import com.figure.core.model.signal.SignalAnalysisGroupList;
import com.figure.core.query.signal.SignalAnalysisGroupQuery;
import com.figure.core.service.signal.ISignalAnalysisGroupService;
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
 * 特征比对分析组 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-11-28 09:58:04
 */
@RestController
@RequestMapping("/signalAnalysisGroup")
@Api(value = "特征比对分析组相关接口", tags = "特征比对分析组相关接口")
public class SignalAnalysisGroupController extends BaseController {

    @Resource
    private ISignalAnalysisGroupService signalAnalysisGroupService;

    /**
     * 根据id获取特征比对分析组
     *
     * @param id 特征比对分析组ID
     * @return 特征比对分析组
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取特征比对分析组", notes = "根据id获取特征比对分析组")
    public SignalAnalysisGroup selectSignalAnalysisGroupById(@PathVariable("id") Integer id) {
        return signalAnalysisGroupService.getById(id);
    }

    /**
     * 查询特征比对分析组列表
     *
     * @param signalAnalysisGroupQuery 特征比对分析组
     * @return 特征比对分析组集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询特征比对分析组列表", notes = "查询特征比对分析组列表")
    public TableDataInfo<SignalAnalysisGroup> selectSignalAnalysisGroupList(SignalAnalysisGroupQuery signalAnalysisGroupQuery) {
        return toPageResult(signalAnalysisGroupService.list(signalAnalysisGroupQuery.autoPageWrapper()));
    }

    /**
     * 查询特征比对分析组列表
     *
     * @param signalAnalysisGroupQuery 特征比对分析组
     * @return 特征比对分析组集合
     */
    @GetMapping("/treelist")
    @ApiOperation(value = "查询特征比对分析组列表", notes = "查询特征比对分析组列表")
    public TableDataInfo<SignalAnalysisGroupList> selectSignalAnalysisGroupTreeList(SignalAnalysisGroupQuery signalAnalysisGroupQuery) {
        return toPageResult(signalAnalysisGroupService.listByQuery(signalAnalysisGroupQuery.autoPageWrapper()));
    }

    /**
     * 新增保存特征比对分析组
     *
     * @param signalAnalysisGroup 特征比对分析组
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存特征比对分析组", notes = "新增保存特征比对分析组")
    public Map<String, Object> insertSignalAnalysisGroup(@RequestBody SignalAnalysisGroup signalAnalysisGroup) {
        return returnMap(signalAnalysisGroupService.save(signalAnalysisGroup), signalAnalysisGroup.getId());
    }

    /**
     * 批量新增保存特征比对分析组
     *
     * @param signalAnalysisGroup 特征比对分析组
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存特征比对分析组", notes = "批量新增保存特征比对分析组")
    public Map<String, Object> saveBatchSignalAnalysisGroup(@RequestBody List<SignalAnalysisGroup> signalAnalysisGroup) {
        return returnMap(signalAnalysisGroupService.saveBatch(signalAnalysisGroup), signalAnalysisGroup);
    }

    /**
     * 修改保存特征比对分析组
     *
     * @param signalAnalysisGroup 特征比对分析组
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存特征比对分析组", notes = "修改保存特征比对分析组")
    public Map<String, Object> updateSignalAnalysisGroupById(@RequestBody SignalAnalysisGroup signalAnalysisGroup) {
        return returnMap(signalAnalysisGroupService.updateById(signalAnalysisGroup));
    }

    /**
     * 批量删除特征比对分析组
     *
     * @param signalAnalysisGroup 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除特征比对分析组", notes = "删除特征比对分析组")
    public Map<String, Object> removeSignalAnalysisGroupByIds(@RequestBody SignalAnalysisGroup signalAnalysisGroup) {
        return returnMap(signalAnalysisGroupService.removeByIds(StringUtils.StringToIntList(signalAnalysisGroup.getIds())));
    }

}