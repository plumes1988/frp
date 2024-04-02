package com.figure.system.controller.report;

import com.figure.core.base.BaseController;
import com.figure.core.model.report.ReportDeviceInfo;
import com.figure.core.query.report.ReportDeviceInfoQuery;
import com.figure.core.service.report.IReportDeviceInfoService;
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
 * 上报服务设备管理 前端控制器
 * </p>
 *
 * @author feather
 * @date 2024-03-29 14:12:20
 */
@RestController
@RequestMapping("/reportDeviceInfo")
@Api(value = "上报服务设备管理相关接口" , tags = "上报服务设备管理相关接口")
public class ReportDeviceInfoController extends BaseController {

    @Resource
    private IReportDeviceInfoService reportDeviceInfoService;

    /**
     * 根据id获取上报服务设备管理
     *
     * @param id 上报服务设备管理ID
     * @return 上报服务设备管理
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取上报服务设备管理" , notes = "根据id获取上报服务设备管理")
    public ReportDeviceInfo selectReportDeviceInfoById(@PathVariable("id") Integer id) {
        return reportDeviceInfoService.getById(id);
    }

    /**
     * 查询上报服务设备管理列表
     *
     * @param reportDeviceInfoQuery 上报服务设备管理
     * @return 上报服务设备管理集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询上报服务设备管理列表", notes = "查询上报服务设备管理列表")
    public TableDataInfo<ReportDeviceInfo> selectReportDeviceInfoList(ReportDeviceInfoQuery reportDeviceInfoQuery) {
        return toPageResult(reportDeviceInfoService.list(reportDeviceInfoQuery.autoPageWrapper()));
    }

    /**
     * 查询上报服务设备管理列表
     *
     * @param reportDeviceInfoQuery 上报服务设备管理
     * @return 上报服务设备管理集合
     */
    @GetMapping("/listrel")
    @ApiOperation(value = "查询上报服务设备管理列表", notes = "查询上报服务设备管理列表")
    public TableDataInfo<HashMap<String, Object>> selectReportDeviceInfoListRel(ReportDeviceInfoQuery reportDeviceInfoQuery) {
        return toPageResult(reportDeviceInfoService.listRel(reportDeviceInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存上报服务设备管理
     *
     * @param reportDeviceInfo 上报服务设备管理
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存上报服务设备管理", notes = "新增保存上报服务设备管理")
    public Map<String, Object> insertReportDeviceInfo(@RequestBody ReportDeviceInfo reportDeviceInfo) {
        return returnMap(reportDeviceInfoService.save(reportDeviceInfo),reportDeviceInfo.getId());
    }

     /**
     * 批量新增保存上报服务设备管理
     *
     * @param reportDeviceInfo 上报服务设备管理
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存上报服务设备管理", notes = "批量新增保存上报服务设备管理")
    public Map<String, Object> saveBatchReportDeviceInfo(@RequestBody List<ReportDeviceInfo> reportDeviceInfo) {
        return returnMap(reportDeviceInfoService.saveBatch(reportDeviceInfo), reportDeviceInfo);
    }

    /**
     * 修改保存上报服务设备管理
     *
     * @param reportDeviceInfo 上报服务设备管理
     * @return 结果
     */
    @PostMapping("/editbatch")
    @ApiOperation(value = "修改保存上报服务设备管理", notes = "修改保存上报服务设备管理")
    public Map<String, Object> updateReportDeviceInfoById(@RequestBody List<ReportDeviceInfo> reportDeviceInfo) {
        return returnMap(reportDeviceInfoService.updateBatchById(reportDeviceInfo));
    }

    /**
     * 修改保存上报服务设备管理
     *
     * @param reportDeviceInfo 上报服务设备管理
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存上报服务设备管理", notes = "修改保存上报服务设备管理")
    public Map<String, Object> updateReportDeviceInfoById(@RequestBody ReportDeviceInfo reportDeviceInfo) {
        return returnMap(reportDeviceInfoService.updateById(reportDeviceInfo));
    }

    /**
     * 批量删除上报服务设备管理
     *
     * @param reportDeviceInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除上报服务设备管理" , notes = "删除上报服务设备管理")
    public Map<String,Object> removeReportDeviceInfoByIds(@RequestBody ReportDeviceInfo reportDeviceInfo) {
        return returnMap(reportDeviceInfoService.removeByIds(StringUtils.StringToIntList(reportDeviceInfo.getIds())));
    }

}