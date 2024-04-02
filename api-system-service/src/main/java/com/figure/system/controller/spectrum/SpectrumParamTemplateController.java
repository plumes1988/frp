package com.figure.system.controller.spectrum;

import com.figure.core.base.BaseController;
import com.figure.core.model.spectrum.SpectrumParamTemplate;
import com.figure.core.query.spectrum.SpectrumParamTemplateQuery;
import com.figure.core.service.spectrum.ISpectrumParamTemplateService;
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
 * 频谱仪参数设置模板 前端控制器
 * </p>
 *
 * @author feather
 * @date 2024-03-29 14:12:20
 */
@RestController
@RequestMapping("/spectrumParamTemplate")
@Api(value = "频谱仪参数设置模板相关接口" , tags = "频谱仪参数设置模板相关接口")
public class SpectrumParamTemplateController extends BaseController {

    @Resource
    private ISpectrumParamTemplateService spectrumParamTemplateService;

    /**
     * 根据id获取频谱仪参数设置模板
     *
     * @param id 频谱仪参数设置模板ID
     * @return 频谱仪参数设置模板
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取频谱仪参数设置模板" , notes = "根据id获取频谱仪参数设置模板")
    public SpectrumParamTemplate selectSpectrumParamTemplateById(@PathVariable("id") Integer id) {
        return spectrumParamTemplateService.getById(id);
    }

    /**
     * 查询频谱仪参数设置模板列表
     *
     * @param spectrumParamTemplateQuery 频谱仪参数设置模板
     * @return 频谱仪参数设置模板集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询频谱仪参数设置模板列表" , notes = "查询频谱仪参数设置模板列表")
    public TableDataInfo<SpectrumParamTemplate> selectSpectrumParamTemplateList(SpectrumParamTemplateQuery spectrumParamTemplateQuery) {
        return toPageResult(spectrumParamTemplateService.list(spectrumParamTemplateQuery.autoPageWrapper()));
    }

    /**
     * 新增保存频谱仪参数设置模板
     *
     * @param spectrumParamTemplate 频谱仪参数设置模板
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存频谱仪参数设置模板" , notes = "新增保存频谱仪参数设置模板")
    public Map<String,Object> insertSpectrumParamTemplate(@RequestBody SpectrumParamTemplate spectrumParamTemplate) {
        return returnMap(spectrumParamTemplateService.save(spectrumParamTemplate),spectrumParamTemplate.getId());
    }

     /**
     * 批量新增保存频谱仪参数设置模板
     *
     * @param spectrumParamTemplate 频谱仪参数设置模板
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存频谱仪参数设置模板" , notes = "批量新增保存频谱仪参数设置模板")
    public Map<String,Object> saveBatchSpectrumParamTemplate(@RequestBody List<SpectrumParamTemplate> spectrumParamTemplate) {
        return returnMap(spectrumParamTemplateService.saveBatch(spectrumParamTemplate), spectrumParamTemplate);
    }
  
    /**
     * 修改保存频谱仪参数设置模板
     *
     * @param spectrumParamTemplate 频谱仪参数设置模板
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存频谱仪参数设置模板" , notes = "修改保存频谱仪参数设置模板")
    public Map<String,Object> updateSpectrumParamTemplateById(@RequestBody SpectrumParamTemplate spectrumParamTemplate) {
        return returnMap(spectrumParamTemplateService.updateById(spectrumParamTemplate));
    }

    /**
     * 批量删除频谱仪参数设置模板
     *
     * @param spectrumParamTemplate 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除频谱仪参数设置模板" , notes = "删除频谱仪参数设置模板")
    public Map<String,Object> removeSpectrumParamTemplateByIds(@RequestBody SpectrumParamTemplate spectrumParamTemplate) {
        return returnMap(spectrumParamTemplateService.removeByIds(StringUtils.StringToIntList(spectrumParamTemplate.getIds())));
    }

}