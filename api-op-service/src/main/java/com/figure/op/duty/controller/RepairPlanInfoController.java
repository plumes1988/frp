package com.figure.op.duty.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.duty.domain.bo.DutyScheduleInfoCancelBo;
import com.figure.op.duty.domain.bo.RepairPlanInfoBo;
import com.figure.op.duty.domain.bo.RepairPlanInfoCancelBo;
import com.figure.op.duty.domain.bo.RepairPlanInfoQueryBo;
import com.figure.op.duty.domain.vo.RepairPlanInfoListVo;
import com.figure.op.duty.domain.vo.RepairPlanInfoPageVo;
import com.figure.op.duty.domain.vo.RepairPlanInfoVo;
import com.figure.op.duty.service.IRepairPlanInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 检修计划控制器
 * @author fsn
 */
@RestController
@RequestMapping("/repairPlanInfo")
public class RepairPlanInfoController {

    @Resource
    private IRepairPlanInfoService repairPlanInfoService;

    /**
     * 检修计划全部列表
     */
    @GetMapping("/list")
    public R<List<RepairPlanInfoListVo>> allList() {
        return R.ok(repairPlanInfoService.queryList());
    }

    /**
     * 检修计划分页列表
     */
    @GetMapping("/page")
    public TableDataInfo<RepairPlanInfoPageVo> page(RepairPlanInfoQueryBo queryBo, PageQuery pageQuery) {
        return repairPlanInfoService.page(queryBo, pageQuery);
    }

    /**
     * 检修计划详情
     */
    @GetMapping("/detail/{id}")
    public R<RepairPlanInfoVo> info(@PathVariable Integer id) {
        return R.ok(repairPlanInfoService.queryById(id));
    }

    /**
     * 新增检修计划
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody RepairPlanInfoBo bo) {
        return R.toAjax(repairPlanInfoService.insertByBo(bo));
    }

    /**
     * 作废排班计划
     */
    @PutMapping("/cancel")
    public R<Void> cancel(@Validated @RequestBody RepairPlanInfoCancelBo cancelBo) {
        return R.toAjax(repairPlanInfoService.cancel(cancelBo));
    }

}
