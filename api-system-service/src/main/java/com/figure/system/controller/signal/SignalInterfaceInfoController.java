package com.figure.system.controller.signal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.signal.SignalInterfaceInfo;
import com.figure.core.service.signal.ISignalInterfaceInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

import com.figure.core.util.page.TableDataInfo;

/**
 * <p>
 * 信号接口信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-05-25 17:10:38
 */
@RestController
@RequestMapping("/signalInterfaceInfo")
@Api(value = "信号接口信息相关接口", tags = "信号接口信息相关接口")
public class SignalInterfaceInfoController extends BaseController {

    @Resource
    private ISignalInterfaceInfoService signalInterfaceInfoService;

    /**
     * 根据interfaceId获取信号接口信息
     *
     * @param interfaceId 信号接口信息ID
     * @return 信号接口信息
     */
    @GetMapping("/get/{interfaceId}")
    @ApiOperation(value = "根据interfaceId获取信号接口信息", notes = "根据interfaceId获取信号接口信息")
    public SignalInterfaceInfo selectSignalInterfaceInfoById(@PathVariable("interfaceId") Long interfaceId) {
        return signalInterfaceInfoService.getById(interfaceId);
    }

    /**
     * 查询信号接口信息列表
     *
     * @param signalInterfaceInfo 信号接口信息
     * @return 信号接口信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询信号接口信息列表", notes = "查询信号接口信息列表")
    public TableDataInfo<SignalInterfaceInfo> selectSignalInterfaceInfoList(HttpServletRequest request) throws Exception {

        PageWrapper<SignalInterfaceInfo> pageWrapper =  new PageHelper(SignalInterfaceInfo.class).getPageWrapper(request);

        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();

        IPage<SignalInterfaceInfo> pages = signalInterfaceInfoService.page(pageWrapper.getPage(),queryWrapper);

        return new TableDataInfo(pages);
    }

    /**
     * 新增保存信号接口信息
     *
     * @param signalInterfaceInfo 信号接口信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存信号接口信息", notes = "新增保存信号接口信息")
    public Map<String, Object> insertSignalInterfaceInfo(@RequestBody SignalInterfaceInfo signalInterfaceInfo) {
        return returnMap(signalInterfaceInfoService.save(signalInterfaceInfo));
    }

    /**
     * 修改保存信号接口信息
     *
     * @param signalInterfaceInfo 信号接口信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存信号接口信息", notes = "修改保存信号接口信息")
    public Map<String, Object> updateSignalInterfaceInfoById(@RequestBody SignalInterfaceInfo signalInterfaceInfo) {
        return returnMap(signalInterfaceInfoService.updateById(signalInterfaceInfo));
    }

    /**
     * 批量删除信号接口信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除信号接口信息", notes = "删除信号接口信息")
    public Map<String, Object> removeSignalInterfaceInfoByIds(String ids) {
        return returnMap(signalInterfaceInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids))));
    }

}