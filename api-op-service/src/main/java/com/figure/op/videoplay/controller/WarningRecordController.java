package com.figure.op.videoplay.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.videoplay.domain.bo.WarningRecordListSelectQuery;
import com.figure.op.videoplay.service.WarningRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lizhijie
 * @version 1.0
 * @date 2023/9/19 16:05
 */
@RestController
@Api(tags = "报警记录")
@RequestMapping("/warning_record")
public class WarningRecordController {

    @Resource
    private WarningRecordService warningRecordService;

    @PostMapping("list")
    @ApiOperation("获取记录列表")
    public R list(PageQuery pageQuery,@RequestBody WarningRecordListSelectQuery query){
        return R.ok(warningRecordService.list(pageQuery,query));
    }

    @PostMapping("update")
    @ApiOperation("更新报警")
    public R list(Integer recordId,Integer status){
        return R.ok(warningRecordService.update(recordId,status));
    }

    @PostMapping("part_his_thermometry")
    @ApiOperation("部件历史温度")
    public R partHisThermometry(String partCode,String startTime,String endTime){
        return R.ok(warningRecordService.partHisThermometry(partCode,startTime,endTime));
    }
}
