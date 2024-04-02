package com.figure.system.controller.signal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.signal.SignalSourceMonitor;
import com.figure.core.service.signal.ISignalSourceMonitorService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 逻辑频道监测规则 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-12-08
 */
@RestController
@RequestMapping("/signalSourceMonitor")
public class SignalSourceMonitorController {

    @Autowired
    ISignalSourceMonitorService signalSourceMonitorService;

    /**
     * 查询信源调度服务列表
     *
     * @param request http请求
     * @return 信源调度服务集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询信源调度服务列表" , notes = "查询信源调度服务列表")
    public TableDataInfo selectSignalSourceMonitorList(HttpServletRequest request) throws Exception {
        PageWrapper<SignalSourceMonitor> pageWrapper =  new PageHelper(SignalSourceMonitor.class).getPageWrapper(request);
        IPage<SignalSourceMonitor> pages = signalSourceMonitorService.page(pageWrapper.getPage(),pageWrapper.getQueryWrapper());
        return new TableDataInfo(pages);
    }


    /**
     * 新增保存信源调度服务
     *
     * @param signalSourceMonitor 信源调度服务
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存信源调度服务" , notes = "新增保存信源调度服务")
    public Map<String,Object> insertSignalSourceMonitor(@RequestBody SignalSourceMonitor signalSourceMonitor) {
        boolean flag = signalSourceMonitorService.save(signalSourceMonitor);
        Map<String,Object> result = new HashMap<>();
        result.put("status",flag?1:0);
        result.put("pk",signalSourceMonitor.getId());
        return result;
    }


    /**
     * 修改保存信源调度服务
     *
     * @param signalSourceMonitor 信源调度服务
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存信源调度服务" , notes = "修改保存信源调度服务")
    public Map<String,Object> updateSignalSourceMonitorById(@RequestBody SignalSourceMonitor signalSourceMonitor) {
        boolean flag = signalSourceMonitorService.updateById(signalSourceMonitor);
        Map<String,Object> result = new HashMap<>();
        result.put("status",flag?1:0);
        result.put("pk",signalSourceMonitor.getId());
        return result;
    }

    /**
     * 批量删除信源调度服务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除信源调度服务" , notes = "删除信源调度服务")
    public boolean removeSignalSourceMonitorByIds(String ids) {
        List<String> idList = Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids));
        boolean flag = signalSourceMonitorService.removeByIds(idList);
        return flag;
    }
}
