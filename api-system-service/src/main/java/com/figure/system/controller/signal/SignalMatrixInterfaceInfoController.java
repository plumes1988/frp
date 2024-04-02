package com.figure.system.controller.signal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.signal.SignalMatrixInterfaceInfo;
import com.figure.core.service.signal.ISignalMatrixInterfaceInfoService;
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
 * 矩阵接口信息表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-12-06
 */
@RestController
@RequestMapping("/signalMatrixInterfaceInfo")
public class SignalMatrixInterfaceInfoController {
    @Autowired
    ISignalMatrixInterfaceInfoService signalMatrixInterfaceInfoService;


    /**
     * 查询矩阵接口信息列表
     *
     * @param request http请求
     * @return 矩阵接口信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询矩阵接口信息列表" , notes = "查询矩阵接口信息列表")
    public TableDataInfo selectSignalMatrixInterfaceInfoList(HttpServletRequest request) throws Exception {
        PageWrapper<SignalMatrixInterfaceInfo> pageWrapper =  new PageHelper(SignalMatrixInterfaceInfo.class).getPageWrapper(request);
        IPage<SignalMatrixInterfaceInfo> pages = signalMatrixInterfaceInfoService.page(pageWrapper.getPage(),pageWrapper.getQueryWrapper());
        return new TableDataInfo(pages);
    }


    /**
     * 新增保存矩阵接口信息
     *
     * @param signalMatrixInterfaceInfo 矩阵接口信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存矩阵接口信息" , notes = "新增保存矩阵接口信息")
    public Map<String,Object> insertSignalMatrixInterfaceInfo(@RequestBody SignalMatrixInterfaceInfo signalMatrixInterfaceInfo) {
        signalMatrixInterfaceInfoService.save(signalMatrixInterfaceInfo);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",signalMatrixInterfaceInfo.getInterfaceId());
        return result;
    }


    /**
     * 修改保存矩阵接口信息
     *
     * @param signalMatrixInterfaceInfo 矩阵接口信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存矩阵接口信息" , notes = "修改保存矩阵接口信息")
    public Map<String,Object> updateSignalMatrixInterfaceInfoById(@RequestBody SignalMatrixInterfaceInfo signalMatrixInterfaceInfo) {
        signalMatrixInterfaceInfoService.updateById(signalMatrixInterfaceInfo);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",signalMatrixInterfaceInfo.getInterfaceId());
        return result;
    }

    /**
     * 批量删除矩阵接口信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除矩阵接口信息" , notes = "删除矩阵接口信息")
    public boolean removeSignalMatrixInterfaceInfoByIds(String ids) {
        return signalMatrixInterfaceInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
    }
}