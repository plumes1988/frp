package com.figure.system.controller.op;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.op.OpSchedulePlan;
import com.figure.core.service.op.IOpSchedulePlanService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 班次设置 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-05-30
 */
@RestController
@RequestMapping("/opSchedulePlan")
public class OpSchedulePlanController extends BaseController {

    @Resource
    private IOpSchedulePlanService opSchedulePlanService;

    /**
     * 根据pId获取班次设置
     *
     * @param pId 媒介ID
     * @return 班次设置
     */
    @GetMapping("/get/{pId}")
    @ApiOperation(value = "根据pId获取班次设置", notes = "根据pId获取班次设置")
    public OpSchedulePlan selectOpSchedulePlanById(@PathVariable("pId") Long pId) {
        return opSchedulePlanService.getById(pId);
    }

    /**
     * 查询班次设置列表
     *
     * @param request
     * @return 班次设置集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询班次设置列表", notes = "查询班次设置列表")
    public TableDataInfo<OpSchedulePlan> selectOpSchedulePlanList(HttpServletRequest request) throws Exception {
        PageWrapper<OpSchedulePlan> pageWrapper = new PageHelper(OpSchedulePlan.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();
        IPage<OpSchedulePlan> pages = opSchedulePlanService.page(pageWrapper.getPage(), queryWrapper);
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存班次设置
     *
     * @param opSchedulePlan 班次设置
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存班次设置", notes = "新增保存班次设置")
    public Map<String, Object> insertOpSchedulePlan(@RequestBody OpSchedulePlan opSchedulePlan) {
        opSchedulePlanService.save(opSchedulePlan);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",opSchedulePlan.getPid());
        return result;
    }

    /**
     * 修改保存班次设置
     *
     * @param opSchedulePlan 班次设置
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存班次设置", notes = "修改保存班次设置")
    public Map<String, Object> updateOpSchedulePlanById(@RequestBody OpSchedulePlan opSchedulePlan) {
        return returnMap(opSchedulePlanService.updateById(opSchedulePlan));
    }

    /**
     * 批量删除班次设置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除班次设置", notes = "删除班次设置")
    public Map<String, Object> removeOpSchedulePlanByIds(String ids) {
        return returnMap(opSchedulePlanService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}