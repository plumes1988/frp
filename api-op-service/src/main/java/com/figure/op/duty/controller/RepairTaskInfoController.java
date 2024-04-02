package com.figure.op.duty.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.duty.domain.bo.RepairTaskInfoBo;
import com.figure.op.duty.domain.bo.RepairTaskInfoQueryBo;
import com.figure.op.duty.domain.bo.RepairTaskStatusBo;
import com.figure.op.duty.domain.vo.RepairTaskInfoListVo;
import com.figure.op.duty.domain.vo.RepairTaskInfoPageVo;
import com.figure.op.duty.domain.vo.RepairTaskInfoVo;
import com.figure.op.duty.service.IRepairTaskInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 检修任务控制器
 * @author fsn
 */
@RestController
@RequestMapping("/repairTaskInfo")
public class RepairTaskInfoController {

    @Resource
    private IRepairTaskInfoService repairTaskInfoService;

    /**
     * 检修任务全部列表
     */
    @GetMapping("/list")
    public R<List<RepairTaskInfoListVo>> allList() {
        return R.ok(repairTaskInfoService.queryList());
    }

    /**
     * 检修任务分页列表
     */
    @GetMapping("/page")
    public TableDataInfo<RepairTaskInfoPageVo> page(RepairTaskInfoQueryBo queryBo, PageQuery pageQuery) {
        return repairTaskInfoService.page(queryBo, pageQuery);
    }

    /**
     * 检修任务详情
     */
    @GetMapping("/detail/{id}")
    public R<RepairTaskInfoVo> info(@PathVariable Integer id) {
        return R.ok(repairTaskInfoService.queryById(id));
    }

    /**
     * 更新检修任务
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody RepairTaskInfoBo bo) {
        return R.toAjax(repairTaskInfoService.updateByBo(bo));
    }

    /**
     * 更新审核状态
     */
    @PutMapping("/updateStatus")
    public R<Void> updateStatus(@Validated @RequestBody RepairTaskStatusBo bo) {
        return R.toAjax(repairTaskInfoService.updateStatus(bo));
    }
}
