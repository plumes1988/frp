package com.figure.op.device.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.device.domain.OpProductInfo;
import com.figure.op.device.domain.bo.OpOutProductBo;
import com.figure.op.device.domain.bo.OpProductInfoBo;
import com.figure.op.device.domain.bo.OpProductInfoQueryBo;
import com.figure.op.device.domain.vo.OpProductInfoListVo;
import com.figure.op.device.domain.vo.OpProductInfoPageVo;
import com.figure.op.device.domain.vo.OpProductInfoVo;
import com.figure.op.device.service.IOpProductInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 库存管理控制器
 * @author fsn
 */
@RestController
@RequestMapping("/opProductInfo")
public class OpProductInfoController {

    @Resource
    private IOpProductInfoService opProductInfoService;

    /**
     * 分页列表1
     */
    @GetMapping("/page")
    public TableDataInfo<OpProductInfoPageVo> page(OpProductInfoQueryBo queryBo, PageQuery pageQuery) {
        return opProductInfoService.page(queryBo, pageQuery);
    }

    /**
     * 分页列表2
     */
    @GetMapping("/pageList2")
    public TableDataInfo<OpProductInfoPageVo> list2(OpProductInfoQueryBo queryBo, PageQuery pageQuery) {
        return opProductInfoService.queryPageList2(queryBo, pageQuery);
    }

    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<OpProductInfoListVo>> list(OpProductInfo opProductInfo) {
        return R.ok(opProductInfoService.queryList(opProductInfo));
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<OpProductInfoVo> info(@PathVariable Integer id) {
        return R.ok(opProductInfoService.queryById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody OpProductInfoBo bo) {
        return R.toAjax(opProductInfoService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody OpProductInfoBo bo) {
        return R.toAjax(opProductInfoService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(opProductInfoService.deleteById(id));
    }

}
