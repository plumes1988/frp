package com.figure.op.duty.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import com.figure.op.dict.domain.DictDataDO;
import com.figure.op.dict.service.DictDataService;
import com.figure.op.dict.service.DictDataServiceImpl;
import com.figure.op.duty.domain.bo.DutyScheduleInfoQueryBo;
import com.figure.op.duty.domain.bo.DutyWorkRecordBo;
import com.figure.op.duty.domain.bo.DutyWorkRecordQueryBo;
import com.figure.op.duty.domain.vo.*;
import com.figure.op.duty.service.IDutyInfoService;
import com.figure.op.duty.service.IDutyScheduleInfoService;
import com.figure.op.duty.service.IDutyWorkRecordService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 值班记录控制器
 * @author fsn
 */
@RestController
@RequestMapping("/dutyWorkRecord")
public class DutyWorkRecordController {

    @Resource
    private IDutyWorkRecordService dutyWorkRecordService;

    @Resource
    private IDutyScheduleInfoService dutyScheduleInfoService;

    @Resource
    private IDutyInfoService dutyInfoService;

    @Resource
    private DictDataService dictDataService;

    /**
     * 值班记录分页列表
     */
    @GetMapping("/page")
    public TableDataInfo<DutyWorkRecordPageVo> page(DutyWorkRecordQueryBo queryBo, PageQuery pageQuery) {
        return dutyWorkRecordService.page(queryBo, pageQuery);
    }

    /**
     * 值班记录详情
     */
    @GetMapping("/detail/{workRecordId}")
    public R<DutyWorkRecordVo> detail(@PathVariable Integer workRecordId) {
        return R.ok(dutyWorkRecordService.queryById(workRecordId));
    }


    /**
     * 新增值班记录
     */
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody DutyWorkRecordBo bo) {
        return R.toAjax(dutyWorkRecordService.insertByBo(bo));
    }

    /**
     * 更新值班记录
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody DutyWorkRecordBo bo) {
        return R.toAjax(dutyWorkRecordService.updateByBo(bo));
    }

    /**
     * 删除值班记录
     */
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        return R.toAjax(dutyWorkRecordService.deleteById(id));
    }
}
