package com.figure.system.controller.log;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.log.LogDeviceStateIndicatorChange;
import com.figure.core.service.device.IDeviceIndicatorParamService;
import com.figure.core.service.log.ILogDeviceStateIndicatorChangeService;
import com.figure.core.service.others.ICommonService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 设备状态值变更日志 前端控制器
 * </p>
 *
 * @author feather
 * @date 2024-01-17 14:21:47
 */
@RestController
@RequestMapping("/logDeviceStateIndicatorChange")
@Api(value = "设备状态值变更日志相关接口", tags = "设备状态值变更日志相关接口")
public class LogDeviceStateIndicatorChangeController extends BaseController {


    @Resource
    private ILogDeviceStateIndicatorChangeService logDeviceStateIndicatorChangeService;

    @Autowired
    IDeviceIndicatorParamService deviceIndicatorParamService;

    /**
     * 查询设备状态值变更日志列表
     *
     * @param request 网络请求
     * @return 设备状态值变更日志集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询设备状态值变更日志列表", notes = "查询设备状态值变更日志列表")
    public TableDataInfo<LogDeviceStateIndicatorChange> selectLogDeviceStateIndicatorChangeList(HttpServletRequest request)  throws Exception{
        PageWrapper<LogDeviceStateIndicatorChange> pageWrapper =  new PageHelper(LogDeviceStateIndicatorChange.class).getPageWrapper(request);
        QueryWrapper<LogDeviceStateIndicatorChange> queryWrapper = pageWrapper.getQueryWrapper();
        queryWrapper.orderByDesc("changeTime");
        IPage<LogDeviceStateIndicatorChange> pages = logDeviceStateIndicatorChangeService.page(pageWrapper.getPage(),pageWrapper.getQueryWrapper());

        logDeviceStateIndicatorChangeService.fillEntityProps(pages.getRecords());

        return new TableDataInfo(pages);
    }

    /**
     * 批量删除设备状态值变更日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除设备状态值变更日志", notes = "删除设备状态值变更日志")
    public Map<String, Object> removeLogServiceByIds(String ids) {
        return returnMap(logDeviceStateIndicatorChangeService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}