package com.figure.system.controller.log;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.log.LogDeviceControl;
import com.figure.core.service.device.IDeviceIndicatorParamService;
import com.figure.core.service.log.ILogDeviceControlService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.util.LogDeviceControlExporter;
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
 * 设备操作日志 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-12-05
 */
@RestController
@RequestMapping("/logDeviceControl")
@Api(value = "设备操作日志信息相关接口", tags = "设备操作日志信息相关接口")
public class LogDeviceControlController extends BaseController {

    @Resource
    private ILogDeviceControlService logDeviceControlService;

    @Autowired
    ISysParaService sysParaService;

    @Autowired
    IDeviceIndicatorParamService deviceIndicatorParamService;

    /**
     * 查询设备操作日志信息列表
     *
     * @param
     * @return 设备操作日志信息列表
     */
    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "查询设备操作日志信息列表", notes = "查询设备操作日志信息列表")
    public Object selectLogDeviceControlList(HttpServletRequest request) throws Exception {

        PageWrapper<LogDeviceControl> pageWrapper =  new PageHelper(LogDeviceControl.class).getPageWrapper(request);

        QueryWrapper<LogDeviceControl> queryWrapper = pageWrapper.getQueryWrapper();

        queryWrapper.orderByDesc("requestTime");

        IPage<LogDeviceControl> pages = logDeviceControlService.page(pageWrapper.getPage(),pageWrapper.getQueryWrapper());

        logDeviceControlService.fillEntityProps(pages.getRecords());

        //补全关联属性名称

        String type = request.getParameter("type");

        if("export".equals(type)){

            String paraValue_of_uploadPath = sysParaService.getParamByName("uploadPath");

            String uploadPath = paraValue_of_uploadPath+"/export";

            return LogDeviceControlExporter.export(pages.getRecords(),uploadPath);
        }

        return new TableDataInfo(pages);
    }

    /**
     * 批量删除设备操作日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "批量删除设备操作日志", notes = "批量删除设备操作日志")
    public Map<String, Object> removeLogLoginByIds(String ids) {
        return returnMap(logDeviceControlService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }
}
