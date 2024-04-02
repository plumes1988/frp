package com.figure.op.performance.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.performance.domain.PerformanceTemplateDo;
import com.figure.op.performance.domain.bo.PerformanceTemplateBo;
import com.figure.op.performance.domain.vo.temp.PerformanceTemplateVo;
import com.figure.op.performance.service.PerformanceTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * 绩效模板Controller
 *
 * @date 2023-08-25
 */
@RestController
@RequestMapping("/performanceTemplate")
@Api(tags = "绩效模板")
public class PerformanceTemplateController {

    @Resource
    private PerformanceTemplateService performanceTemplateService;

    /**
     * 获得绩效模板的分页列表
     *
     * @return 简化列表
     */
    @GetMapping("/list-all-simple")
    @ApiOperation(value = "所有简化绩效模板")
    public R selectSimpleList() {
        return R.ok(performanceTemplateService.selectSimpleList());
    }


    /**
     * 获得绩效模板的分页列表
     *
     * @param reqVO 查询条件请求对象
     * @return 分页列表
     */
    @GetMapping("/page")
    @ApiOperation(value = "获得绩效模板的分页")
    public TableDataInfo<PerformanceTemplateVo> getPerformanceTemplatePage(@Valid PerformanceTemplateBo reqVO, PageQuery pageQuery) {
        return performanceTemplateService.getPerformanceTemplatePage(reqVO, pageQuery);
    }

    /**
     * 获取绩效模板详细信息
     */
    @GetMapping(value = "/get")
    @ApiOperation(value = "获取绩效模板详细信息")
    public R getInfo(@RequestParam("id") Long id) {
        return R.ok(performanceTemplateService.selectById(id));
    }

    /**
     * 获取绩效模板明细
     */
    @GetMapping(value = "/detail")
    @ApiOperation(value = "获取绩效模板明细")
    public R detail(@RequestParam("id") Long id) {
        return R.ok(performanceTemplateService.selectById(id));
    }

    /**
     * 新增绩效模板
     */
    @PostMapping("/create")
    @ApiOperation(value = "新增绩效模板")
    public R add(@Valid @RequestBody PerformanceTemplateBo reqVo) {
        return R.ok(performanceTemplateService.insert(reqVo));
    }

    /**
     * 修改绩效模板
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改绩效模板")
    public R edit(@RequestBody PerformanceTemplateBo reqVo) {
        performanceTemplateService.update(reqVo);
        return R.ok(true);
    }

    /**
     * 删除绩效模板
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除绩效模板")
    public R remove(Long id) {
        return R.ok(performanceTemplateService.deletePerformanceTemplateById(id));
    }


    /**
     * 导出绩效模板列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, PerformanceTemplateDo performanceTemplate) {
        List<PerformanceTemplateDo> list = performanceTemplateService.selectPerformanceTemplateList(performanceTemplate);
//        ExcelUtil<PerformanceTemplateDo> util = new ExcelUtil<PerformanceTemplateDo>(PerformanceTemplateDo.class);
//        util.exportExcel(response, list, "绩效模板数据");
    }
}
