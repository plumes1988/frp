package com.figure.op.duty.controller;

import com.figure.op.common.domain.R;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.duty.domain.bo.RepairEventExecuteBo;
import com.figure.op.duty.domain.bo.RepairEventExecuteQueryBo;
import com.figure.op.duty.domain.vo.*;
import com.figure.op.duty.service.IRepairEventExecuteService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌控制器
 * @author fsn
 */
@RestController
@RequestMapping("/repairEventExecute")
public class RepairEventExecuteController {

    @Resource
    private IRepairEventExecuteService RepairEventExecuteService;

    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<RepairEventExecuteListVo>> allList(RepairEventExecuteQueryBo bo) {
        return R.ok(RepairEventExecuteService.queryList(bo));
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<RepairEventExecuteVo> info(@PathVariable Integer id) {
        RepairEventExecuteVo RepairEventExecuteVo = RepairEventExecuteService.queryById(id);
        return R.ok(RepairEventExecuteVo);
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody RepairEventExecuteBo bo) {
        return R.toAjax(RepairEventExecuteService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody RepairEventExecuteBo bo) {
        return R.toAjax(RepairEventExecuteService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(RepairEventExecuteService.deleteById(id));
    }


//    @GetMapping("/page")
//    public TableDataInfo<RepairEventExecutePageVo> page(RepairEventExecuteQueryBo queryBo, PageQuery pageQuery) {
//        return RepairEventExecuteService.page(queryBo, pageQuery);
//    }

}
