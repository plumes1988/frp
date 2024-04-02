package com.figure.system.controller.op;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.op.OpScheduleScheduler;
import com.figure.core.service.op.IOpScheduleSchedulerService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 排班管理 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-05-30
 */
@RestController
@RequestMapping("/opScheduleScheduler")
public class OpScheduleSchedulerController extends BaseController {

    @Resource
    private IOpScheduleSchedulerService opScheduleSchedulerService;

    /**
     * 根据id获取排班管理
     *
     * @param id 媒介ID
     * @return 排班管理
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取排班管理", notes = "根据id获取排班管理")
    public OpScheduleScheduler selectOpScheduleSchedulerById(@PathVariable("id") Long id) {
        return opScheduleSchedulerService.getById(id);
    }

    /**
     * 查询排班管理列表
     *
     * @param request
     * @return 排班管理集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询排班管理列表", notes = "查询排班管理列表")
    public TableDataInfo<OpScheduleScheduler> selectOpScheduleSchedulerList(HttpServletRequest request) throws Exception {
        PageWrapper<OpScheduleScheduler> pageWrapper = new PageHelper(OpScheduleScheduler.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();
        IPage<OpScheduleScheduler> pages = opScheduleSchedulerService.page(pageWrapper.getPage(), queryWrapper);
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存排班管理
     *
     * @param opScheduleScheduler 排班管理
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存排班管理", notes = "新增保存排班管理")
    public Map<String, Object> insertOpScheduleScheduler(@RequestBody OpScheduleScheduler opScheduleScheduler) {
        opScheduleSchedulerService.save(opScheduleScheduler);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",opScheduleScheduler.getId());
        return result;
    }

    /**
     * 修改保存排班管理
     *
     * @param opScheduleScheduler 排班管理
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存排班管理", notes = "修改保存排班管理")
    public Map<String, Object> updateOpScheduleSchedulerById(@RequestBody OpScheduleScheduler opScheduleScheduler) {
        return returnMap(opScheduleSchedulerService.updateById(opScheduleScheduler));
    }

    /**
     * 批量删除排班管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除排班管理", notes = "删除排班管理")
    public Map<String, Object> removeOpScheduleSchedulerByIds(String ids) {
        return returnMap(opScheduleSchedulerService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}