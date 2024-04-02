package com.figure.system.controller.signal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.signal.SignalTypeInfo;
import com.figure.core.model.sys.SysDataDictionary;
import com.figure.core.service.signal.ISignalTypeInfoService;
import com.figure.core.service.sys.ISysDataDictionaryService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 信号类型表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-05-18
 */
@RestController
@RequestMapping("/signalTypeInfo")
public class SignalTypeInfoController {

    @Autowired
    ISignalTypeInfoService signalTypeInfoService;

    @Autowired
    ISysDataDictionaryService sysDataDictionaryService;


    /**
     * 查询信号类型列表
     *
     * @param request http请求
     * @return 信号类型集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询信号类型列表" , notes = "查询信号类型列表")
    public TableDataInfo selectSignalTypeInfoList(HttpServletRequest request) throws Exception {
        PageWrapper<SignalTypeInfo> pageWrapper =  new PageHelper(SignalTypeInfo.class).getPageWrapper(request);
        IPage<SignalTypeInfo> pages = signalTypeInfoService.page(pageWrapper.getPage(),pageWrapper.getQueryWrapper());
        return new TableDataInfo(pages);
    }


    /**
     * 新增保存信号类型
     *
     * @param signalTypeInfo 信号类型
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存信号类型" , notes = "新增保存信号类型")
    public Map<String,Object> insertSignalTypeInfo(@RequestBody SignalTypeInfo signalTypeInfo) {
        if(signalTypeInfo.getSignalName()==null){
            QueryWrapper<SysDataDictionary>  queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parentParaType","signalType_par");
            queryWrapper.eq("paraValue",signalTypeInfo.getSignalCode());
            signalTypeInfo.setSignalName(sysDataDictionaryService.list(queryWrapper).get(0).getParaName());
        }
        signalTypeInfoService.save(signalTypeInfo);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",signalTypeInfo.getSignalCode());
        return result;
    }


    /**
     * 修改保存信号类型
     *
     * @param signalTypeInfo 信号类型
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存信号类型" , notes = "修改保存信号类型")
    public Map<String,Object> updateSignalTypeInfoById(@RequestBody SignalTypeInfo signalTypeInfo) {
        if(signalTypeInfo.getSignalName()==null){
            QueryWrapper<SysDataDictionary>  queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parentParaType","signalType_par");
            queryWrapper.eq("paraValue", signalTypeInfo.getSignalCode());
            signalTypeInfo.setSignalName(sysDataDictionaryService.list(queryWrapper).get(0).getParaName());
        }
        signalTypeInfoService.updateById(signalTypeInfo);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",signalTypeInfo.getSignalCode());
        return result;
    }

    /**
     * 批量删除信号类型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除信号类型" , notes = "删除信号类型")
    public boolean removeSignalTypeInfoByIds(String ids) {
        return signalTypeInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
    }
}
