package com.figure.system.controller.log;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.log.LogUserAction;
import com.figure.core.util.page.TableDataInfo;
import com.figure.core.service.log.ILogUserActionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;


/**
 * <p>
 * 系统用户操作日志表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-03-24
 */
@Slf4j
@RestController
@RequestMapping("/logUserAction")
@Api(value = "用户操作日志信息相关接口", tags = "用户操作日志信息相关接口")
public class LogUserActionController extends BaseController {


    @Resource
    private ILogUserActionService logUserActionService;

    /**
     * 查询用户操作日志信息列表
     *
     * @param
     * @return 用户操作日志信息集合
     */
    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "查询用户操作日志信息列表", notes = "查询用户操作日志信息列表")
    public TableDataInfo selectLogUserActionList(HttpServletRequest request) throws Exception {
        PageWrapper<LogUserAction> pageWrapper =  new PageHelper(LogUserAction.class).getPageWrapper(request);

        QueryWrapper<LogUserAction> queryWrapper = pageWrapper.getQueryWrapper();
        queryWrapper.orderByDesc("optTime");

        IPage<LogUserAction> pages = logUserActionService.page(pageWrapper.getPage(),pageWrapper.getQueryWrapper());

        return new TableDataInfo(pages);
    }

    /**
     * 批量删除用户操作日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除用户操作日志", notes = "删除用户操作日志")
    public Map<String, Object> removeLogServiceByIds(String ids) {
        return returnMap(logUserActionService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }
}
