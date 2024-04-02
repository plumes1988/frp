package com.figure.op.system.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.duty.domain.bo.DutyInfoQueryBo;
import com.figure.op.duty.domain.vo.DutyInfoPageVo;
import com.figure.op.system.domain.TechInfo;
import com.figure.op.system.domain.bo.TechInfoBo;
import com.figure.op.system.domain.bo.TechInfoQueryBo;
import com.figure.op.system.domain.vo.TechInfoListVo;
import com.figure.op.system.domain.vo.TechInfoPageVo;
import com.figure.op.system.domain.vo.TechInfoVo;
import com.figure.op.system.service.ITechInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌控制器
 * @author fsn
 */
@RestController
@RequestMapping("/techInfo")
public class TechInfoController {

    @Resource
    private ITechInfoService techInfoService;


    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<TechInfoListVo>> list(TechInfo techInfo) {
        return R.ok(techInfoService.queryList(techInfo));
    }

    /**
     * 分页列表
     */
    @GetMapping("/page")
    public TableDataInfo<TechInfoPageVo> page(TechInfoQueryBo queryBo, PageQuery pageQuery) {
        return techInfoService.page(queryBo, pageQuery);
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<TechInfoVo> info(@PathVariable Integer id) {
        return R.ok(techInfoService.queryById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TechInfoBo bo) {
        return R.toAjax(techInfoService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TechInfoBo bo) {
        return R.toAjax(techInfoService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(techInfoService.deleteById(id));
    }



}
