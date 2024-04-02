package com.figure.op.device.controller;

import cn.hutool.core.bean.BeanUtil;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.device.domain.OpOutProduct;
import com.figure.op.device.domain.OpProductInfo;
import com.figure.op.device.domain.bo.OpOutProductBo;
import com.figure.op.device.domain.bo.OpOutProductQueryBo;
import com.figure.op.device.domain.bo.OpOutProductReturnBo;
import com.figure.op.device.domain.bo.OpProductInfoBo;
import com.figure.op.device.domain.vo.*;
import com.figure.op.device.service.IOpOutProductService;
import com.figure.op.device.service.IOpProductInfoService;
import com.figure.op.system.domain.vo.OperateTimelineListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 出库控制器
 * @author fsn
 */
@RestController
@RequestMapping("/opOutProduct")
public class OpOutProductController {

    @Resource
    private IOpOutProductService opOutProductService;

    @Resource
    private IOpProductInfoService productInfoService;

    /**
     * 分页列表1
     */
    @GetMapping("/page")
    public TableDataInfo<OpOutProductPageVo> page(OpOutProductQueryBo queryBo, PageQuery pageQuery) {
        return opOutProductService.page(queryBo, pageQuery);
    }

    /**
     * 分页列表2
     */
    @GetMapping("/pageList2")
    public TableDataInfo<OpOutProductPageVo> list2(OpOutProductQueryBo queryBo, PageQuery pageQuery) {
        return opOutProductService.queryPageList2(queryBo, pageQuery);
    }

    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<OpOutProductListVo>> list(OpOutProduct opOutProduct) {
        return R.ok(opOutProductService.queryList(opOutProduct));
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<OpOutProductVo> info(@PathVariable Integer id) {
        return R.ok(opOutProductService.queryById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody OpOutProductBo bo) {
        return R.toAjax(opOutProductService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody OpOutProductBo bo) {
        return R.toAjax(opOutProductService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(opOutProductService.deleteById(id));
    }

    /**
     * 归还登记
     */
    @PostMapping("/returnProduct")
    public R<Void> returnProduct(@Validated @RequestBody OpOutProductReturnBo returnBo) {
        return R.toAjax(opOutProductService.returnProduct(returnBo));
    }

    /**
     * 归还历史时间线
     */
    @GetMapping("/returnTimelineList/{outId}")
    public R<List<OpOutProductReturnListVo>> returnTimelineList(@PathVariable Integer outId) {
        return R.ok(opOutProductService.getReturnTimelineList(outId));
    }

}
