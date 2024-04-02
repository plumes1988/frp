package com.figure.system.controller.log;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.log.LogService;
import com.figure.core.redis.MessageProducer;
import com.figure.core.service.log.ILogServiceService;
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
 * 服务运行日志 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2024-03-02
 */
@Controller
@Slf4j
@RestController
@RequestMapping("/logService")
@Api(value = "服务运行信息相关接口", tags = "服务运行信息相关接口")
public class LogServiceController extends BaseController {

    @Resource
    private ILogServiceService logServiceService;

    /**
     * 查询服务运行日志信息列表
     *
     * @param
     * @return 服务运行日志信息集合
     */
    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "查询服务运行日志信息列表", notes = "查询服务运行日志信息列表")
    public TableDataInfo selectLogServiceList(HttpServletRequest request) throws Exception {
        PageWrapper<LogService> pageWrapper =  new PageHelper(LogService.class).getPageWrapper(request);

        QueryWrapper<LogService> queryWrapper = pageWrapper.getQueryWrapper();
        queryWrapper.orderByDesc("timestamp");

        IPage<LogService> pages = logServiceService.page(pageWrapper.getPage(),pageWrapper.getQueryWrapper());

        return new TableDataInfo(pages);
    }

    /**
     * 批量删除服务运行日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除服务运行日志", notes = "删除服务运行日志")
    public Map<String, Object> removeLogServiceByIds(String ids) {
        return returnMap(logServiceService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}
