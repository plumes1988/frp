package com.figure.system.controller.spectrum;

import com.figure.core.base.BaseController;
import com.figure.core.model.spectrum.SpectrumReferlineInfo;
import com.figure.core.query.spectrum.SpectrumReferlineInfoQuery;
import com.figure.core.service.spectrum.ISpectrumReferlineInfoService;
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
 * 频谱仪参考频谱 前端控制器
 * </p>
 *
 * @author feather
 * @date 2023-12-07 13:48:22
 */
@RestController
@RequestMapping("/spectrumReferlineInfo")
@Api(value = "频谱仪参考频谱相关接口", tags = "频谱仪参考频谱相关接口")
public class SpectrumReferlineInfoController extends BaseController {

    @Resource
    private ISpectrumReferlineInfoService spectrumReferlineInfoService;

    /**
     * 根据id获取频谱仪参考频谱
     *
     * @param id 频谱仪参考频谱ID
     * @return 频谱仪参考频谱
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取频谱仪参考频谱", notes = "根据id获取频谱仪参考频谱")
    public SpectrumReferlineInfo selectSpectrumReferlineInfoById(@PathVariable("id") Integer id) {
        return spectrumReferlineInfoService.getById(id);
    }

    /**
     * 查询频谱仪参考频谱列表
     *
     * @param spectrumReferlineInfoQuery 频谱仪参考频谱
     * @return 频谱仪参考频谱集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询频谱仪参考频谱列表", notes = "查询频谱仪参考频谱列表")
    public TableDataInfo<SpectrumReferlineInfo> selectSpectrumReferlineInfoList(SpectrumReferlineInfoQuery spectrumReferlineInfoQuery) {
        return toPageResult(spectrumReferlineInfoService.list(spectrumReferlineInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存频谱仪参考频谱
     *
     * @param spectrumReferlineInfo 频谱仪参考频谱
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存频谱仪参考频谱", notes = "新增保存频谱仪参考频谱")
    public Map<String, Object> insertSpectrumReferlineInfo(@RequestBody SpectrumReferlineInfo spectrumReferlineInfo) {
        return returnMap(spectrumReferlineInfoService.save(spectrumReferlineInfo), spectrumReferlineInfo.getId());
    }

    /**
     * 批量新增保存频谱仪参考频谱
     *
     * @param spectrumReferlineInfo 频谱仪参考频谱
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存频谱仪参考频谱", notes = "批量新增保存频谱仪参考频谱")
    public Map<String, Object> saveBatchSpectrumReferlineInfo(@RequestBody List<SpectrumReferlineInfo> spectrumReferlineInfo) {
        return returnMap(spectrumReferlineInfoService.saveBatch(spectrumReferlineInfo), spectrumReferlineInfo);
    }

    /**
     * 修改保存频谱仪参考频谱
     *
     * @param spectrumReferlineInfo 频谱仪参考频谱
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存频谱仪参考频谱", notes = "修改保存频谱仪参考频谱")
    public Map<String, Object> updateSpectrumReferlineInfoById(@RequestBody SpectrumReferlineInfo spectrumReferlineInfo) {
        return returnMap(spectrumReferlineInfoService.updateById(spectrumReferlineInfo));
    }

    /**
     * 批量删除频谱仪参考频谱
     *
     * @param spectrumReferlineInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除频谱仪参考频谱", notes = "删除频谱仪参考频谱")
    public Map<String, Object> removeSpectrumReferlineInfoByIds(@RequestBody SpectrumReferlineInfo spectrumReferlineInfo) {
        return returnMap(spectrumReferlineInfoService.removeByIds(StringUtils.StringToIntList(spectrumReferlineInfo.getIds())));
    }

}