package com.figure.op.device.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.device.domain.OpInProduct;
import com.figure.op.device.domain.bo.OpInProductBo;
import com.figure.op.device.domain.bo.OpInProductQueryBo;
import com.figure.op.device.domain.vo.OpInProductListVo;
import com.figure.op.device.domain.vo.OpInProductPageVo;
import com.figure.op.device.domain.vo.OpInProductVo;
import com.figure.op.device.service.IOpInProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 入库管理控制器
 * @author fsn
 */
@RestController
@RequestMapping("/opInProduct")
public class OpInProductController {

    @Resource
    private IOpInProductService opInProductService;

    /**
     * 分页列表
     */
    @GetMapping("/page")
    public TableDataInfo<OpInProductPageVo> page(OpInProductQueryBo queryBo, PageQuery pageQuery) {
        return opInProductService.page(queryBo, pageQuery);
    }

    /**
     * 分页列表2
     */
    @GetMapping("/pageList2")
    public TableDataInfo<OpInProductPageVo> list2(OpInProductQueryBo queryBo, PageQuery pageQuery) {
        return opInProductService.queryPageList2(queryBo, pageQuery);
    }

    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<OpInProductListVo>> list(OpInProduct OpInProduct) {
        return R.ok(opInProductService.queryList(OpInProduct));
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<OpInProductVo> info(@PathVariable Integer id) {
        return R.ok(opInProductService.queryById(id));
    }

    /**
     * 新增入库
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody OpInProductBo bo) {
        return R.toAjax(opInProductService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody OpInProductBo bo) {
        return R.toAjax(opInProductService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(opInProductService.deleteById(id));
    }

}
