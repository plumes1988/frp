package com.figure.op.system.controller;

import com.figure.op.common.domain.R;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.system.domain.SysDept;
import com.figure.op.system.domain.SysDept;
import com.figure.op.system.domain.bo.SysDeptBo;
import com.figure.op.system.domain.vo.SysDeptListVo;
import com.figure.op.system.domain.vo.SysDeptListVo;
import com.figure.op.system.domain.vo.SysDeptVo;
import com.figure.op.system.service.ISysDeptService;
import com.figure.op.system.service.ISysDeptService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 品牌控制器
 * @author fsn
 */
@RestController
@RequestMapping("/sysDept")
public class SysDeptController {

    @Resource
    private ISysDeptService sysDeptService;


    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<SysDeptListVo>> list(SysDept sysDept) {
        List<SysDeptListVo> sysDeptListVos = sysDeptService.queryList(sysDept);
        return R.ok(sysDeptListVos);
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<SysDeptVo> info(@PathVariable Integer id) {
        return R.ok(sysDeptService.queryById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysDeptBo bo) {
        return R.toAjax(sysDeptService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysDeptBo bo) {
        return R.toAjax(sysDeptService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(sysDeptService.deleteById(id));
    }

}
