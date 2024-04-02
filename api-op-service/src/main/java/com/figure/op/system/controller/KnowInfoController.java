package com.figure.op.system.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.system.domain.KnowInfo;
import com.figure.op.system.domain.bo.KnowInfoBo;
import com.figure.op.system.domain.bo.KnowInfoQueryBo;
import com.figure.op.system.domain.vo.KnowInfoListVo;
import com.figure.op.system.domain.vo.KnowInfoPageVo;
import com.figure.op.system.domain.vo.KnowInfoVo;
import com.figure.op.system.service.IKnowInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌控制器
 * @author fsn
 */
@RestController
@RequestMapping("/knowInfo")
public class KnowInfoController {

    @Resource
    private IKnowInfoService knowInfoService;


    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<KnowInfoListVo>> list(KnowInfo KnowInfo) {
        return R.ok(knowInfoService.queryList(KnowInfo));
    }

    /**
     * 分页列表
     */
    @GetMapping("/page")
    public TableDataInfo<KnowInfoPageVo> page(KnowInfoQueryBo queryBo, PageQuery pageQuery) {
        return knowInfoService.page(queryBo, pageQuery);
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<KnowInfoVo> info(@PathVariable Integer id) {
        return R.ok(knowInfoService.queryById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody KnowInfoBo bo) {
        return R.toAjax(knowInfoService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KnowInfoBo bo) {
        return R.toAjax(knowInfoService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(knowInfoService.deleteById(id));
    }



}
