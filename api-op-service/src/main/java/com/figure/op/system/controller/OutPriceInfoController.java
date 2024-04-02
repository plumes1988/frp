package com.figure.op.system.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.system.domain.OutPriceInfo;
import com.figure.op.system.domain.bo.OutPriceInfoBo;
import com.figure.op.system.domain.bo.OutPriceInfoQueryBo;
import com.figure.op.system.domain.bo.OutPriceInfoStatusBo;
import com.figure.op.system.domain.vo.OperateTimelineListVo;
import com.figure.op.system.domain.vo.OutPriceInfoListVo;
import com.figure.op.system.domain.vo.OutPriceInfoPageVo;
import com.figure.op.system.domain.vo.OutPriceInfoVo;
import com.figure.op.system.helper.LoginHelper;
import com.figure.op.system.service.IOutPriceInfoService;
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
@RequestMapping("/outPriceInfo")
public class OutPriceInfoController {

    @Resource
    private IOutPriceInfoService outPriceInfoService;


    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<OutPriceInfoListVo>> list(OutPriceInfo outPriceInfo) {
        return R.ok(outPriceInfoService.queryList(outPriceInfo));
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<OutPriceInfoVo> info(@PathVariable Integer id) {
        return R.ok(outPriceInfoService.queryById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody OutPriceInfoBo bo) {
        return R.toAjax(outPriceInfoService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody OutPriceInfoBo bo) {
        return R.toAjax(outPriceInfoService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(outPriceInfoService.deleteById(id));
    }

    @GetMapping("/page")
    public TableDataInfo<OutPriceInfoPageVo> page(OutPriceInfoQueryBo queryBo, PageQuery pageQuery) {
        return outPriceInfoService.page(queryBo, pageQuery);
    }

    /**
     * 审核状态变更  0 待提交 1 待审核 2 审核通过 3审核退回
     */
    @PutMapping("/updateStatus")
    public R<Void> updateStatus(@RequestBody OutPriceInfoStatusBo bo) {
        return R.toAjax(outPriceInfoService.updateStatus(bo));
    }

    /**
     * 操作历史时间线
     */
    @GetMapping("/operateTimelineList/{outPriceId}")
    public R<List<OperateTimelineListVo>> operateTimelineList(@PathVariable Integer outPriceId) {
        return R.ok(outPriceInfoService.getOperateTimelineList(outPriceId));
    }

}
