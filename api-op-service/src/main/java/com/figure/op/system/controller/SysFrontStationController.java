package com.figure.op.system.controller;

import com.figure.op.common.domain.R;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.system.domain.SysFrontStation;
import com.figure.op.system.domain.bo.SysFrontStationBo;
import com.figure.op.system.domain.vo.SysFrontStationListVo;
import com.figure.op.system.domain.vo.SysFrontStationVo;
import com.figure.op.system.service.ISysFrontStationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌控制器
 * @author fsn
 */
@RestController
@RequestMapping("/sysFrontStation")
public class SysFrontStationController {

    @Resource
    private ISysFrontStationService SysFrontStationService;


    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<SysFrontStationListVo>> list(SysFrontStation SysFrontStation) {
        return R.ok(SysFrontStationService.queryList(SysFrontStation));
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<SysFrontStationVo> info(@PathVariable Integer id) {
        return R.ok(SysFrontStationService.queryById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysFrontStationBo bo) {
        return R.toAjax(SysFrontStationService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysFrontStationBo bo) {
        return R.toAjax(SysFrontStationService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(SysFrontStationService.deleteById(id));
    }

}
