package com.figure.system.controller.signal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.signal.SignalSourceChangeService;
import com.figure.core.service.signal.ISignalSourceChangeServiceService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/**
 * <p>
 * 信源调度服务 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-12-08
 */
@RestController
@RequestMapping("/signalSourceChangeService")
public class SignalSourceChangeServiceController {
    @Autowired
    ISignalSourceChangeServiceService signalSourceChangeServiceService;


    /**
     * 查询信源调度服务列表
     *
     * @param request http请求
     * @return 信源调度服务集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询信源调度服务列表" , notes = "查询信源调度服务列表")
    public TableDataInfo selectSignalSourceChangeServiceList(HttpServletRequest request) throws Exception {
        PageWrapper<SignalSourceChangeService> pageWrapper =  new PageHelper(SignalSourceChangeService.class).getPageWrapper(request);
        IPage<SignalSourceChangeService> pages = signalSourceChangeServiceService.page(pageWrapper.getPage(),pageWrapper.getQueryWrapper());
        return new TableDataInfo(pages);
    }


    /**
     * 新增保存信源调度服务
     *
     * @param signalSourceChangeService 信源调度服务
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存信源调度服务" , notes = "新增保存信源调度服务")
    public Map<String,Object> insertSignalSourceChangeService(@RequestBody SignalSourceChangeService signalSourceChangeService) {
        signalSourceChangeServiceService.save(signalSourceChangeService);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",signalSourceChangeService.getServiceCode());
        return result;
    }


    /**
     * 修改保存信源调度服务
     *
     * @param signalSourceChangeService 信源调度服务
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存信源调度服务" , notes = "修改保存信源调度服务")
    public Map<String,Object> updateSignalSourceChangeServiceById(@RequestBody SignalSourceChangeService signalSourceChangeService) {
        signalSourceChangeServiceService.updateById(signalSourceChangeService);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",signalSourceChangeService.getServiceCode());
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
    public boolean removeSignalSourceChangeServiceByIds(String ids) {
        return signalSourceChangeServiceService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
    }
}
