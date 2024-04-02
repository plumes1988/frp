package com.figure.op.performance.controller;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.domain.R;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.performance.domain.PerformanceManageDO;
import com.figure.op.performance.domain.bo.PerformanceManageScoreReqBo;
import com.figure.op.performance.domain.vo.manage.*;
import com.figure.op.performance.service.PerformanceManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;


import javax.validation.*;
import javax.servlet.http.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;


@Api(tags = "绩效评分管理")
@RestController
@RequestMapping("/performance-manage")
@Validated
public class PerformanceManageController {

    @Resource
    private PerformanceManageService manageService;

    /**
     * 打分
     */
    @PostMapping("/score")
    @ApiOperation("打分")
    public R<Void> score(@Valid @RequestBody PerformanceManageScoreReqBo scoreReqBO) {
        return R.toAjax(manageService.score(scoreReqBO));
    }

    /**
     * 获取评价打分结果
     *
     * @param id
     * @return
     */
    @GetMapping("/score-res")
    @ApiOperation("获取打分结果")
    public R scoreRes(@RequestParam("id") Long id) {
        return R.ok(manageService.scoreRes(id));
    }

    @PostMapping("/create")
    @ApiOperation("创建绩效评分管理")
    public R createManage(@Valid @RequestBody PerformanceManageCreateReqVO createReqVO) {
        return R.ok(manageService.createManage(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新绩效评分管理")
    public R updateManage(@Valid @RequestBody PerformanceManageUpdateReqVO updateReqVO) {
        manageService.updateManage(updateReqVO);
        return R.ok(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除绩效评分管理")
    public R deleteManage(@RequestParam("id") Long id) {
        manageService.deleteManage(id);
        return R.ok(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得绩效评分管理")
    public R getManage(@RequestParam("id") Long id) {
        PerformanceManageDO manage = manageService.getManage(id);
        return R.ok(manage);
    }

    @GetMapping("/list")
    @ApiOperation("获得绩效评分管理列表")
    public R getManageList(@RequestParam("ids") Collection<Long> ids) {
        List<PerformanceManageDO> list = manageService.getManageList(ids);
        return R.ok(list);
    }

    @GetMapping("/page")
    @ApiOperation("获得绩效评分管理分页")
    public TableDataInfo getManagePage(@Valid PerformanceManagePageReqVO pageVO, PageQuery pageQuery) {
        return manageService.getManagePage(pageVO, pageQuery);
    }

    /**
     * 得分树形结构
     *
     * @param id 绩效评分id
     * @throws IOException
     */
    @GetMapping("/resScoreDataTree")
    public R resScoreDataTree(@RequestParam("id") Long id) {
        // 获取数据
        return R.ok(manageService.getDateList(id));
    }


    /**
     * 导出
     *
     * @param response
     * @param id       绩效评分id
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response, @RequestParam("id") Long id) throws IOException {
        // 获取当前日期和时间
        Date currentDate = new Date();
        // 创建一个 SimpleDateFormat 对象，指定日期时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        // 格式化日期和时间为字符串
        String formattedDateTime = dateFormat.format(currentDate);
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("绩效评分-"+ formattedDateTime +".xlsx", "UTF-8"));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");

        // 获取数据
        manageService.exportV2(response, id);
    }


}
