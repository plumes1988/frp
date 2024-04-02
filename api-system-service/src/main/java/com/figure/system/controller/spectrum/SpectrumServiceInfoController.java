package com.figure.system.controller.spectrum;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.figure.core.base.BaseController;
import com.figure.core.constant.Constants;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.spectrum.SpectrumServiceInfo;
import com.figure.core.query.spectrum.SpectrumServiceInfoQuery;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.spectrum.ISpectrumServiceInfoService;
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
 * 频谱服务信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2023-11-24 16:44:13
 */
@RestController
@RequestMapping("/spectrumServiceInfo")
@Api(value = "频谱服务信息相关接口", tags = "频谱服务信息相关接口")
public class SpectrumServiceInfoController extends BaseController {

    @Resource
    private ISpectrumServiceInfoService spectrumServiceInfoService;

    @Resource
    private IDeviceInfoService deviceInfoService;

    /**
     * 根据id获取频谱服务信息
     *
     * @param id 频谱服务信息ID
     * @return 频谱服务信息
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取频谱服务信息", notes = "根据id获取频谱服务信息")
    public SpectrumServiceInfo selectSpectrumServiceInfoById(@PathVariable("id") Integer id) {
        return spectrumServiceInfoService.getById(id);
    }

    /**
     * 根据serviceCode获取频谱服务信息
     *
     * @param id 频谱服务信息id
     * @return 频谱服务信息
     */
    @GetMapping("/getDetailById/{id}")
    @ApiOperation(value = "根据id获取频谱服务信息", notes = "根据id获取频谱服务信息")
    public Map<String, Object> selectSpectrumServiceDetailById(@PathVariable("id") Integer id) {
        return returnMap(true, spectrumServiceInfoService.getDetailById(id));
    }

    /**
     * 查询频谱服务信息列表
     *
     * @param spectrumServiceInfoQuery 频谱服务信息
     * @return 频谱服务信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询频谱服务信息列表", notes = "查询频谱服务信息列表")
    public TableDataInfo<SpectrumServiceInfo> selectSpectrumServiceInfoList(SpectrumServiceInfoQuery spectrumServiceInfoQuery) {
        return toPageResult(spectrumServiceInfoService.list(spectrumServiceInfoQuery.autoPageWrapper()));
    }

    /**
     * 查询频谱服务信息列表
     *
     * @param spectrumServiceInfoQuery 频谱服务信息
     * @return 频谱服务信息集合
     */
    @GetMapping("/listrel")
    @ApiOperation(value = "查询频谱服务信息列表", notes = "查询频谱服务信息列表")
    public TableDataInfo<SpectrumServiceInfo> selectSpectrumServiceInfoTreeList(SpectrumServiceInfoQuery spectrumServiceInfoQuery) {
        return toPageResult(spectrumServiceInfoService.listrel(spectrumServiceInfoQuery.autoPageWrapper()));
    }


    /**
     * 新增保存频谱服务信息
     *
     * @param spectrumServiceInfo 频谱服务信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存频谱服务信息", notes = "新增保存频谱服务信息")
    public Map<String, Object> insertSpectrumServiceInfo(@RequestBody SpectrumServiceInfo spectrumServiceInfo) {
        if (StringUtils.isNotNull(spectrumServiceInfo.getServiceCodes())) {
            for (String serviceCode : spectrumServiceInfo.getServiceCodes()) {
                LambdaQueryWrapper<DeviceInfo> deviceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
                deviceInfoLambdaQueryWrapper.eq(DeviceInfo::getDeviceCode, serviceCode).eq(DeviceInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
                DeviceInfo deviceInfo = this.deviceInfoService.getOne(deviceInfoLambdaQueryWrapper);
                if (deviceInfo != null) {
                    SpectrumServiceInfo serviceInfo = spectrumServiceInfo;
                    serviceInfo.setServiceCode(serviceCode);
                    serviceInfo.setServiceName(deviceInfo.getDeviceName());
                    this.spectrumServiceInfoService.save(serviceInfo);
                }
            }
            return returnMap(true);
        }

        return returnMap(spectrumServiceInfoService.save(spectrumServiceInfo), spectrumServiceInfo.getId());
    }

    /**
     * 批量新增保存频谱服务信息
     *
     * @param spectrumServiceInfo 频谱服务信息
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存频谱服务信息", notes = "批量新增保存频谱服务信息")
    public Map<String, Object> saveBatchSpectrumServiceInfo(@RequestBody List<SpectrumServiceInfo> spectrumServiceInfo) {
        return returnMap(spectrumServiceInfoService.saveBatch(spectrumServiceInfo), spectrumServiceInfo);
    }

    /**
     * 修改保存频谱服务信息
     *
     * @param spectrumServiceInfo 频谱服务信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存频谱服务信息", notes = "修改保存频谱服务信息")
    public Map<String, Object> updateSpectrumServiceInfoById(@RequestBody SpectrumServiceInfo spectrumServiceInfo) {
        return returnMap(spectrumServiceInfoService.updateById(spectrumServiceInfo));
    }

    /**
     * 批量删除频谱服务信息
     *
     * @param spectrumServiceInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除频谱服务信息", notes = "删除频谱服务信息")
    public Map<String, Object> removeSpectrumServiceInfoByIds(@RequestBody SpectrumServiceInfo spectrumServiceInfo) {
        return returnMap(spectrumServiceInfoService.removeByIds(StringUtils.StringToIntList(spectrumServiceInfo.getIds())));
    }

}