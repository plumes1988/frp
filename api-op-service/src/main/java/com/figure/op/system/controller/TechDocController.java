package com.figure.op.system.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.system.domain.TechDoc;
import com.figure.op.system.domain.bo.TechDocBo;
import com.figure.op.system.domain.bo.TechDocQueryBo;
import com.figure.op.system.domain.vo.TechDocListVo;
import com.figure.op.system.domain.vo.TechDocPageVo;
import com.figure.op.system.domain.vo.TechDocVo;
import com.figure.op.system.service.ITechDocService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌控制器
 * @author fsn
 */
@RestController
@RequestMapping("/techDoc")
public class TechDocController {

    @Resource
    private ITechDocService TechDocService;


    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<TechDocListVo>> list(TechDoc TechDoc) {
        return R.ok(TechDocService.queryList(TechDoc));
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<TechDocVo> info(@PathVariable Integer id) {
        return R.ok(TechDocService.queryById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TechDocBo bo) {
        return R.toAjax(TechDocService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TechDocBo bo) {
        return R.toAjax(TechDocService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(TechDocService.deleteById(id));
    }

    @GetMapping("/page")
    public TableDataInfo<TechDocPageVo> page(TechDocQueryBo queryBo, PageQuery pageQuery) {
        return TechDocService.page(queryBo, pageQuery);
    }

}
