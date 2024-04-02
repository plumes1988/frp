package com.figure.system.controller.op;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.op.OpScheduleCheck;
import com.figure.core.service.op.IOpScheduleCheckService;
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
 * 值班签到表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-06-02
 */
@RestController
@RequestMapping("/opScheduleCheck")
public class OpScheduleCheckController extends BaseController {

    @Resource
    private IOpScheduleCheckService opScheduleCheckService;

    /**
     * 根据id获取值班签到
     *
     * @param id 媒介ID
     * @return 值班签到s
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取值班签到", notes = "根据id获取值班签到")
    public OpScheduleCheck selectOpScheduleCheckById(@PathVariable("id") Long id) {
        return opScheduleCheckService.getById(id);
    }

    /**sss
     * 查询值班签到列表
     *
     * @param request
     * @return 值班签到集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询值班签到列表", notes = "查询值班签到列表")
    public TableDataInfo<OpScheduleCheck> selectOpScheduleCheckList(HttpServletRequest request) throws Exception {
        PageWrapper<OpScheduleCheck> pageWrapper = new PageHelper(OpScheduleCheck.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();
        IPage<OpScheduleCheck> pages = opScheduleCheckService.page(pageWrapper.getPage(), queryWrapper);
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存值班签到
     *
     * @param opSchedulePlan 值班签到
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存值班签到", notes = "新增保存值班签到")
    public Map<String, Object> insertOpScheduleCheck(@RequestBody OpScheduleCheck opSchedulePlan) {
        opScheduleCheckService.save(opSchedulePlan);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",opSchedulePlan.getId());
        return result;
    }

    /**
     * 修改保存值班签到
     *
     * @param opSchedulePlan 值班签到
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存值班签到", notes = "修改保存值班签到")
    public Map<String, Object> updateOpScheduleCheckById(@RequestBody OpScheduleCheck opSchedulePlan) {
        return returnMap(opScheduleCheckService.updateById(opSchedulePlan));
    }

    /**
     * 批量删除值班签到
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除值班签到", notes = "删除值班签到")
    public Map<String, Object> removeOpScheduleCheckByIds(String ids) {
        return returnMap(opScheduleCheckService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}
