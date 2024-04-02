package com.figure.system.controller.spectrum;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.figure.core.base.BaseController;
import com.figure.core.model.spectrum.SpectrumReferlineInfo;
import com.figure.core.model.spectrum.SpectrumServiceDeviceRel;
import com.figure.core.model.spectrum.SpectrumServiceInfo;
import com.figure.core.query.spectrum.SpectrumServiceDeviceRelQuery;
import com.figure.core.service.spectrum.ISpectrumReferlineInfoService;
import com.figure.core.service.spectrum.ISpectrumServiceDeviceRelService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频谱服务和频谱仪设备关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2023-11-24 16:44:13
 */
@RestController
@RequestMapping("/spectrumServiceDeviceRel")
@Api(value = "频谱服务和频谱仪设备关联相关接口", tags = "频谱服务和频谱仪设备关联相关接口")
public class SpectrumServiceDeviceRelController extends BaseController {

    @Resource
    private ISpectrumServiceDeviceRelService spectrumServiceDeviceRelService;

    @Resource
    private ISpectrumReferlineInfoService spectrumReferlineInfoService;

    /**
     * 根据id获取频谱服务和频谱仪设备关联
     *
     * @param id 频谱服务和频谱仪设备关联ID
     * @return 频谱服务和频谱仪设备关联
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取频谱服务和频谱仪设备关联", notes = "根据id获取频谱服务和频谱仪设备关联")
    public SpectrumServiceDeviceRel selectSpectrumServiceDeviceRelById(@PathVariable("id") Integer id) {
        return spectrumServiceDeviceRelService.getById(id);
    }

    /**
     * 查询频谱服务和频谱仪设备关联列表
     *
     * @param spectrumServiceDeviceRelQuery 频谱服务和频谱仪设备关联
     * @return 频谱服务和频谱仪设备关联集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询频谱服务和频谱仪设备关联列表", notes = "查询频谱服务和频谱仪设备关联列表")
    public TableDataInfo<SpectrumServiceDeviceRel> selectSpectrumServiceDeviceRelList(SpectrumServiceDeviceRelQuery spectrumServiceDeviceRelQuery) {
        return toPageResult(spectrumServiceDeviceRelService.list(spectrumServiceDeviceRelQuery.autoPageWrapper()));
    }

    /**
     * 查询频谱服务和频谱仪设备关联列表
     *
     * @param spectrumServiceDeviceRelQuery 频谱服务和频谱仪设备关联
     * @return 频谱服务和频谱仪设备关联集合
     */
    @GetMapping("/listrel")
    @ApiOperation(value = "查询频谱服务和频谱仪设备关联列表", notes = "查询频谱服务和频谱仪设备关联列表")
    public TableDataInfo<HashMap<String, Object>> selectSpectrumServiceDeviceRelListRel(SpectrumServiceDeviceRelQuery spectrumServiceDeviceRelQuery) {
        return toPageResult(spectrumServiceDeviceRelService.listRel(spectrumServiceDeviceRelQuery));
    }

    /**
     * 新增保存频谱服务和频谱仪设备关联
     *
     * @param spectrumServiceDeviceRel 频谱服务和频谱仪设备关联
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存频谱服务和频谱仪设备关联", notes = "新增保存频谱服务和频谱仪设备关联")
    public Map<String, Object> insertSpectrumServiceDeviceRel(@RequestBody SpectrumServiceDeviceRel spectrumServiceDeviceRel) {
        spectrumServiceDeviceRel.setStartFrequency(spectrumServiceDeviceRel.getCenterFrequency() - (spectrumServiceDeviceRel.getFrequencySpan() / 2));
        spectrumServiceDeviceRel.setEndFrequency(spectrumServiceDeviceRel.getCenterFrequency() + (spectrumServiceDeviceRel.getFrequencySpan() / 2));
        return beforeReturnMap(spectrumServiceDeviceRelService.save(spectrumServiceDeviceRel), spectrumServiceDeviceRel.getId());
    }

    /**
     * 批量新增保存频谱服务和频谱仪设备关联
     *
     * @param spectrumServiceDeviceRel 频谱服务和频谱仪设备关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存频谱服务和频谱仪设备关联", notes = "批量新增保存频谱服务和频谱仪设备关联")
    public Map<String, Object> saveBatchSpectrumServiceDeviceRel(@RequestBody List<SpectrumServiceDeviceRel> spectrumServiceDeviceRel) {
        return beforeReturnMap(spectrumServiceDeviceRelService.saveBatch(spectrumServiceDeviceRel), spectrumServiceDeviceRel);
    }

    /**
     * 修改保存频谱服务和频谱仪设备关联
     *
     * @param spectrumServiceDeviceRel 频谱服务和频谱仪设备关联
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存频谱服务和频谱仪设备关联", notes = "修改保存频谱服务和频谱仪设备关联")
    public Map<String, Object> updateSpectrumServiceDeviceRelById(@RequestBody SpectrumServiceDeviceRel spectrumServiceDeviceRel) {
        spectrumServiceDeviceRel.setStartFrequency(spectrumServiceDeviceRel.getCenterFrequency() - (spectrumServiceDeviceRel.getFrequencySpan() / 2));
        spectrumServiceDeviceRel.setEndFrequency(spectrumServiceDeviceRel.getCenterFrequency() + (spectrumServiceDeviceRel.getFrequencySpan() / 2));
        return beforeReturnMap(spectrumServiceDeviceRelService.updateById(spectrumServiceDeviceRel));
    }

    /**
     * 批量删除频谱服务和频谱仪设备关联
     *
     * @param spectrumServiceDeviceRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除频谱服务和频谱仪设备关联", notes = "删除频谱服务和频谱仪设备关联")
    public Map<String, Object> removeSpectrumServiceDeviceRelByIds(@RequestBody SpectrumServiceDeviceRel spectrumServiceDeviceRel) {
        return beforeReturnMap(spectrumServiceDeviceRelService.removeByIds(StringUtils.StringToIntList(spectrumServiceDeviceRel.getIds())));
    }

    /**
     * 更新频谱服务和设备缓存
     * @param status
     * @return
     */
    Map<String, Object> beforeReturnMap(boolean status){
        this.spectrumServiceDeviceRelService.spectrumServiceDataToRedis();
        return returnMap(status);
    }
    /**
     * 更新频谱服务和设备缓存
     * @param status
     * @return
     */
    Map<String, Object> beforeReturnMap(boolean status, Object data){
        this.spectrumServiceDeviceRelService.spectrumServiceDataToRedis();
        return returnMap(status,data);
    }
    /**
     * 下发频谱参数
     *
     * @param spectrumServiceDeviceRel 下发频谱参数
     * @return 结果
     */
    @PostMapping("/spectrumSet")
    @ApiOperation(value = "下发频谱参数", notes = "下发频谱参数")
    public Map<String, Object> spectrumSet(@RequestBody SpectrumServiceDeviceRel spectrumServiceDeviceRel) {
        LambdaUpdateWrapper<SpectrumReferlineInfo> spectrumReferlineInfoLambdaUpdateWrapper = Wrappers.lambdaUpdate();
        spectrumReferlineInfoLambdaUpdateWrapper.eq(SpectrumReferlineInfo::getSpectrumCode,spectrumServiceDeviceRel.getSpectrumCode())
                .set(SpectrumReferlineInfo::getLevel,spectrumServiceDeviceRel.getLevel());
        this.spectrumReferlineInfoService.update(spectrumReferlineInfoLambdaUpdateWrapper);
        return returnMap(spectrumServiceDeviceRelService.spectrumSet(spectrumServiceDeviceRel));
    }

}