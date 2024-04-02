package com.figure.op.duty.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.duty.domain.bo.DutyInfoBo;
import com.figure.op.duty.domain.bo.DutyInfoQueryBo;
import com.figure.op.duty.domain.bo.DutyScheduleInfoQueryBo;
import com.figure.op.duty.domain.vo.DutyInfoListVo;
import com.figure.op.duty.domain.vo.DutyInfoPageVo;
import com.figure.op.duty.domain.vo.DutyInfoVo;
import com.figure.op.duty.domain.vo.DutyScheduleInfoListVo;
import com.figure.op.duty.service.IDutyInfoService;
import com.figure.op.duty.service.IDutyScheduleInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 值班任务控制器
 * @author fsn
 */
@RestController
@RequestMapping("/dutyInfo")
public class DutyInfoController {

    @Resource
    private IDutyInfoService dutyInfoService;

    @Resource
    private IDutyScheduleInfoService dutyScheduleInfoService;

    /**
     * 全部列表
     */
    @GetMapping("/list")
    public R<List<DutyInfoListVo>> allList() {
        return R.ok(dutyInfoService.queryList());
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    public R<DutyInfoVo> info(@PathVariable Integer id) {
        return R.ok(dutyInfoService.queryById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody DutyInfoBo bo) {
        return R.toAjax(dutyInfoService.insertByBo(bo));
    }

    /**
     * 更新
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody DutyInfoBo bo) {
//        DutyScheduleInfoQueryBo queryBo = new DutyScheduleInfoQueryBo();
//        queryBo.setDutyId(bo.getDutyId());
//        List<DutyScheduleInfoListVo> dutyScheduleInfoListVos = dutyScheduleInfoService.queryList(queryBo);
//        if (dutyScheduleInfoListVos.size() > 0) {
//            return R.fail("当前值班任务已关联排班，不可编辑");
//        }
        return R.toAjax(dutyInfoService.updateByBo(bo));
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
//        DutyScheduleInfoQueryBo queryBo = new DutyScheduleInfoQueryBo();
//        queryBo.setDutyId(id);
//        List<DutyScheduleInfoListVo> dutyScheduleInfoListVos = dutyScheduleInfoService.queryList(queryBo);
//        if (dutyScheduleInfoListVos.size() > 0) {
//            return R.fail("当前值班任务已关联排班，不可删除");
//        }
        return R.toAjax(dutyInfoService.deleteById(id));
    }


    @GetMapping("/page")
    public TableDataInfo<DutyInfoPageVo> page(DutyInfoQueryBo queryBo, PageQuery pageQuery) {
        return dutyInfoService.page(queryBo, pageQuery);
    }
}
