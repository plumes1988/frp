package com.figure.op.duty.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.duty.domain.bo.ReportTemplateBo;
import com.figure.op.duty.domain.bo.ReportTemplateQueryBo;
import com.figure.op.duty.domain.bo.DutyScheduleInfoQueryBo;
import com.figure.op.duty.domain.vo.ReportTemplateListVo;
import com.figure.op.duty.domain.vo.ReportTemplatePageVo;
import com.figure.op.duty.domain.vo.ReportTemplateVo;
import com.figure.op.duty.domain.vo.DutyScheduleInfoListVo;
import com.figure.op.duty.service.IReportTemplateService;
import com.figure.op.duty.service.IDutyScheduleInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌控制器
 * @author fsn
 */
@RestController
@RequestMapping("/reportTemplate")
public class ReportTemplateController {

    @Resource
    private IReportTemplateService ReportTemplateService;

    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<ReportTemplateListVo>> allList() {
        return R.ok(ReportTemplateService.queryList());
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<ReportTemplateVo> info(@PathVariable Integer id) {
        return R.ok(ReportTemplateService.queryById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ReportTemplateBo bo) {
        return R.toAjax(ReportTemplateService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ReportTemplateBo bo) {
        return R.toAjax(ReportTemplateService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(ReportTemplateService.deleteById(id));
    }


    @GetMapping("/page")
    public TableDataInfo<ReportTemplatePageVo> page(ReportTemplateQueryBo queryBo, PageQuery pageQuery) {
        return ReportTemplateService.page(queryBo, pageQuery);
    }
}
