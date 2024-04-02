package com.figure.system.controller.log;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.log.LogLogin;
import com.figure.core.service.log.ILogLoginService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 *  登录日志 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2024-01-03
 */
@Controller
@Slf4j
@RestController
@RequestMapping("/logLogin")
@Api(value = "登录日志信息相关接口", tags = "登录日志信息相关接口")
public class LogLoginController extends BaseController {

    @Resource
    private ILogLoginService logLoginService;

    /**
     * 查询登录日志信息列表
     *
     * @param
     * @return 登录日志信息集合
     */
    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "查询登录日志信息列表", notes = "查询登录日志信息列表")
    public TableDataInfo selectLogLoginList(HttpServletRequest request) throws Exception {

        PageWrapper<LogLogin> pageWrapper =  new PageHelper(LogLogin.class).getPageWrapper(request);

        QueryWrapper<LogLogin> queryWrapper = pageWrapper.getQueryWrapper();
        queryWrapper.orderByDesc("time");

        IPage<LogLogin> pages = logLoginService.page(pageWrapper.getPage(),pageWrapper.getQueryWrapper());

        logLoginService.fillEntityProps(pages.getRecords());

        return new TableDataInfo(pages);
    }

    /**
     * 批量删除登录日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除登录日志", notes = "删除登录日志")
    public Map<String, Object> removeLogLoginByIds(String ids) {
        return returnMap(logLoginService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }
}
