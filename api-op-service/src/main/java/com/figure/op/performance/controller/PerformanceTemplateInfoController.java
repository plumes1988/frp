package com.figure.op.performance.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.performance.domain.PerformanceTemplateInfoDo;
import com.figure.op.performance.domain.bo.*;
import com.figure.op.performance.domain.vo.tempInfo.PerformanceTemplateInfoPageVo;
import com.figure.op.performance.domain.vo.tempInfo.PerformanceTemplateInfoVo;
import com.figure.op.performance.service.PerformanceTemplateInfoService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 绩效模板明细Controller
 *
 * @date 2023-08-25
 */
@RestController
@RequestMapping("/performanceTemplateInfo")
@Api(tags = "绩效模板明细")
public class PerformanceTemplateInfoController {

    @Resource
    private PerformanceTemplateInfoService performanceTemplateInfoService;

    /**
     * 所有简化绩效模板明细
     */
    @GetMapping("/list-all-simple")
    @ApiOperation(value = "所有简化绩效模板明细")
    public R<List<PerformanceTemplateInfoVo>> selectSimpleList() {
        return R.ok(performanceTemplateInfoService.selectSimpleList());
    }

    /**
     * 所有父级简化绩效模板明细
     * 根据关联模板id查询所有一级明细
     */
    @GetMapping("/list-first-simple")
    @ApiOperation(value = "所有父级简化绩效模板明细")
    public R<List<PerformanceTemplateInfoVo>> selectSimpleFirstList(@RequestParam(value = "templateId", required = false) Long templateId) {
        return R.ok(performanceTemplateInfoService.selectSimpleFirstList(templateId));
    }

    /**
     * 查询绩效模板明细分页
     */
    @GetMapping("/page")
    @ApiOperation(value = "查询绩效模板明细分页")
    public TableDataInfo<PerformanceTemplateInfoPageVo> page(@Valid PerformanceTemplateInfoQueryBo queryBo, PageQuery pageQuery) {
        return performanceTemplateInfoService.selectPerformanceTemplateInfoPage(queryBo, pageQuery);
    }

    /**
     * 获取绩效模板明细详细信息
     */
    @ApiOperation(value = "获取绩效模板明细详细信息")
    @GetMapping(value = "/get")
    public R<PerformanceTemplateInfoDo> getInfo(Long id) {
        return R.ok(performanceTemplateInfoService.selectPerformanceTemplateInfoById(id));
    }

    /**
     * 新增绩效模板明细（父级）
     */
    @PostMapping("/createParent")
    public R<Void> createParent(@Validated(AddGroup.class) @RequestBody PerformanceTemplateInfoFirstBo bo) {
        return R.toAjax(performanceTemplateInfoService.createParent(bo));
    }

    /**
     * 编辑绩效模板明细（父级）
     */
    @PostMapping("/updateParent")
    public R<Void> updateParent(@Validated(EditGroup.class) @RequestBody PerformanceTemplateInfoFirstBo bo) {
        return R.toAjax(performanceTemplateInfoService.updateParent(bo));
    }

    /**
     * 新增绩效模板明细（子级）
     */
    @PostMapping("/createChild")
    public R<Void> createChild(@Validated(AddGroup.class) @RequestBody PerformanceTemplateInfoSecondBo bo) {
        return R.toAjax(performanceTemplateInfoService.createChild(bo));
    }

    /**
     * 编辑绩效模板明细（子级）
     */
    @PostMapping("/updateChild")
    public R<Void> updateChild(@Validated(EditGroup.class) @RequestBody PerformanceTemplateInfoSecondBo bo) {
        return R.toAjax(performanceTemplateInfoService.updateChild(bo));
    }

//    /**
//     * 新增绩效模板明细
//     */
//    @ApiOperation(value = "新增绩效模板明细")
//    @PostMapping("/create")
//    public R<Void> add(@RequestBody PerformanceTemplateInfoVo reqVo) {
//        return R.toAjax(performanceTemplateInfoService.create(reqVo));
//    }
//
//    /**
//     * 批量新增绩效模板明细
//     */
//    @ApiOperation(value = "批量新增绩效模板明细")
//    @PostMapping("/batchCreate")
//    public R<Void> batchCreate(@RequestBody PerformanceTemplateInfoCreateBo bo) {
//        return R.toAjax(performanceTemplateInfoService.batchCreate(bo));
//    }
//
//    /**
//     * 批量编辑绩效模板明细
//     */
//    @ApiOperation(value = "批量编辑绩效模板明细")
//    @PostMapping("/batchUpdate")
//    public R batchUpdate(@RequestBody PerformanceTemplateInfoUpdateBo reqVo) {
//        return R.ok(performanceTemplateInfoService.batchUpdate(reqVo));
//    }
//
//
//    /**
//     * 修改绩效模板明细
//     */
//    @ApiOperation(value = "修改绩效模板明细")
//    @PutMapping("/update")
//    public R edit(@RequestBody PerformanceTemplateInfoVo reqVo) {
//        return R.ok(performanceTemplateInfoService.update(reqVo));
//    }

    /**
     * 删除绩效模板明细
     */
    @ApiOperation(value = "删除绩效模板明细")
    @DeleteMapping("/delete")
    public R remove(Long id) {
        return R.ok(performanceTemplateInfoService.deletePerformanceTemplateInfoById(id));
    }


    /**
     * 导出绩效模板明细列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, PerformanceTemplateInfoDo performanceTemplateInfo) {
//        List<PerformanceTemplateInfo> list = performanceTemplateInfoService.selectPerformanceTemplateInfoList(performanceTemplateInfo);
//        ExcelUtil<PerformanceTemplateInfo> util = new ExcelUtil<PerformanceTemplateInfo>(PerformanceTemplateInfo.class);
//        util.exportExcel(response, list, "绩效模板明细数据");
    }


    /**
     * 获取绩效模板是否完整
     */
    @GetMapping(value = "/validateTemplateInfoComplete")
    public R<Boolean> validateTemplateInfoComplete(Long templateId) {
        return R.ok(performanceTemplateInfoService.validateTemplateInfoComplete(templateId));
    }
}
