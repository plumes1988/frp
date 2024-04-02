package com.figure.system.controller.report;

import com.figure.core.base.BaseController;
import com.figure.core.model.report.ReportDeviceIndicatorRel;
import com.figure.core.query.report.ReportDeviceIndicatorRelQuery;
import com.figure.core.service.report.IReportDeviceIndicatorRelService;
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
 * 上报设备与指标关联 前端控制器
 * </p>
 *
 * @author feather
 * @date 2024-03-29 14:12:20
 */
@RestController
@RequestMapping("/reportDeviceIndicatorRel")
@Api(value = "上报设备与指标关联相关接口" , tags = "上报设备与指标关联相关接口")
public class ReportDeviceIndicatorRelController extends BaseController {

    @Resource
    private IReportDeviceIndicatorRelService reportDeviceIndicatorRelService;

    /**
     * 根据id获取上报设备与指标关联
     *
     * @param id 上报设备与指标关联ID
     * @return 上报设备与指标关联
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取上报设备与指标关联" , notes = "根据id获取上报设备与指标关联")
    public ReportDeviceIndicatorRel selectReportDeviceIndicatorRelById(@PathVariable("id") Integer id) {
        return reportDeviceIndicatorRelService.getById(id);
    }

    /**
     * 查询上报设备与指标关联列表
     *
     * @param reportDeviceIndicatorRelQuery 上报设备与指标关联
     * @return 上报设备与指标关联集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询上报设备与指标关联列表", notes = "查询上报设备与指标关联列表")
    public TableDataInfo<ReportDeviceIndicatorRel> selectReportDeviceIndicatorList(ReportDeviceIndicatorRelQuery reportDeviceIndicatorRelQuery) {
        return toPageResult(reportDeviceIndicatorRelService.list(reportDeviceIndicatorRelQuery.autoPageWrapper()));
    }

    /**
     * 查询上报设备与指标关联列表
     *
     * @param reportDeviceIndicatorRelQuery 上报设备与指标关联
     * @return 上报设备与指标关联集合
     */
    @GetMapping("/listRel")
    @ApiOperation(value = "查询上报设备与指标关联列表", notes = "查询上报设备与指标关联列表")
    public TableDataInfo<Map<String, Object>> selectReportDeviceIndicatorRelList(ReportDeviceIndicatorRelQuery reportDeviceIndicatorRelQuery) {
        return toPageResult(reportDeviceIndicatorRelService.listRel(reportDeviceIndicatorRelQuery.autoPageWrapper()));
    }

    /**
     * 新增保存上报设备与指标关联
     *
     * @param reportDeviceIndicatorRel 上报设备与指标关联
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存上报设备与指标关联", notes = "新增保存上报设备与指标关联")
    public Map<String, Object> insertReportDeviceIndicatorRel(@RequestBody ReportDeviceIndicatorRel reportDeviceIndicatorRel) {
        return returnMap(reportDeviceIndicatorRelService.save(reportDeviceIndicatorRel),reportDeviceIndicatorRel.getId());
    }

     /**
     * 批量新增保存上报设备与指标关联
     *
     * @param reportDeviceIndicatorRel 上报设备与指标关联
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存上报设备与指标关联" , notes = "批量新增保存上报设备与指标关联")
    public Map<String,Object> saveBatchReportDeviceIndicatorRel(@RequestBody List<ReportDeviceIndicatorRel> reportDeviceIndicatorRel) {
        return returnMap(reportDeviceIndicatorRelService.saveBatch(reportDeviceIndicatorRel), reportDeviceIndicatorRel);
    }
  
    /**
     * 修改保存上报设备与指标关联
     *
     * @param reportDeviceIndicatorRel 上报设备与指标关联
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存上报设备与指标关联", notes = "修改保存上报设备与指标关联")
    public Map<String, Object> updateReportDeviceIndicatorRelById(@RequestBody ReportDeviceIndicatorRel reportDeviceIndicatorRel) {
        return returnMap(reportDeviceIndicatorRelService.updateById(reportDeviceIndicatorRel));
    }

    /**
     * 修改保存上报设备与指标关联
     *
     * @param reportDeviceIndicatorRel 上报设备与指标关联
     * @return 结果
     */
    @PostMapping("/editbatch")
    @ApiOperation(value = "修改保存上报设备与指标关联", notes = "修改保存上报设备与指标关联")
    public Map<String, Object> updateBatchReportDeviceIndicatorRelById(@RequestBody List<ReportDeviceIndicatorRel> reportDeviceIndicatorRel) {
        return returnMap(reportDeviceIndicatorRelService.updateBatchById(reportDeviceIndicatorRel));
    }

    /**
     * 批量删除上报设备与指标关联
     *
     * @param reportDeviceIndicatorRel 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除上报设备与指标关联", notes = "删除上报设备与指标关联")
    public Map<String, Object> removeReportDeviceIndicatorRelByIds(@RequestBody ReportDeviceIndicatorRel reportDeviceIndicatorRel) {
        return returnMap(reportDeviceIndicatorRelService.removeByIds(StringUtils.StringToIntList(reportDeviceIndicatorRel.getIds())));
    }

}