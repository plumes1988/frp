package com.figure.op.duty.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.duty.domain.bo.*;
import com.figure.op.duty.domain.vo.RepairOrderInfoListVo;
import com.figure.op.duty.domain.vo.RepairOrderInfoPageVo;
import com.figure.op.duty.domain.vo.RepairOrderInfoVo;
import com.figure.op.duty.service.IRepairOrderInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 品牌控制器
 * @author fsn
 */
@RestController
@RequestMapping("/repairOrderInfo")
public class RepairOrderInfoController {

    @Resource
    private IRepairOrderInfoService RepairOrderInfoService;

    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<RepairOrderInfoListVo>> allList() {
        return R.ok(RepairOrderInfoService.queryList());
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<RepairOrderInfoVo> info(@PathVariable Integer id) {
        return R.ok(RepairOrderInfoService.queryById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody RepairOrderInfoBo bo) {
        return R.toAjax(RepairOrderInfoService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody RepairOrderInfoBo bo) {
        return R.toAjax(RepairOrderInfoService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(RepairOrderInfoService.deleteById(id));
    }


    @GetMapping("/page")
    public TableDataInfo<RepairOrderInfoPageVo> page(RepairOrderInfoQueryBo queryBo, PageQuery pageQuery) {
        return RepairOrderInfoService.page(queryBo, pageQuery);
    }

    /**
     * 维修工单 提交/退回
     */
    @PutMapping("/updateSubmitStatus")
    public R<Void> updateSubmitStatus(@Validated @RequestBody RepairOrderInfoSubmitStatusBo bo) {
        return R.toAjax(RepairOrderInfoService.updateSubmitStatus(bo));
    }

    /**
     * 审核工单 通过/驳回
     */
    @PutMapping("/updateReviewStatus")
    public R<Void> updateReviewStatus(@Validated @RequestBody RepairOrderInfoReviewStatusBo bo) {
        return R.toAjax(RepairOrderInfoService.updateReviewStatus(bo));
    }

    // 工单审核提交 退回  执行状态控制 0 默认 1 通过 2 失败 3 取消

    /**
     * 执行工单 完成/失败/取消
     */
    @PutMapping("/updateActStatus")
    public R<Void> updateActStatus(@Validated @RequestBody RepairOrderInfoActStatusBo bo) {
        return R.toAjax(RepairOrderInfoService.updateActStatus(bo));
    }
}
