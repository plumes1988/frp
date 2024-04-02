package com.figure.system.controller.signal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.signal.SignalMatrixInfo;
import com.figure.core.model.signal.SignalMatrixInterfaceInfo;
import com.figure.core.service.signal.ISignalMatrixInfoService;
import com.figure.core.service.signal.ISignalMatrixInterfaceInfoService;
import com.figure.core.util.page.TableDataInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 矩阵信息列表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-12-06
 */
@RestController
@RequestMapping("/signalMatrixInfo")
public class SignalMatrixInfoController {
    @Autowired
    ISignalMatrixInfoService signalMatrixInfoService;
    @Autowired
    ISignalMatrixInterfaceInfoService signalMatrixInterfaceInfoService;

    /**
     * 查询矩阵信息列表
     *
     * @param request http请求
     * @return 矩阵信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询矩阵信息列表" , notes = "查询矩阵信息列表")
    public TableDataInfo selectSignalMatrixInfoList(HttpServletRequest request) throws Exception {
        PageWrapper<SignalMatrixInfo> pageWrapper =  new PageHelper(SignalMatrixInfo.class).getPageWrapper(request);
        IPage<SignalMatrixInfo> pages = signalMatrixInfoService.page(pageWrapper.getPage(),pageWrapper.getQueryWrapper());
        return new TableDataInfo(pages);
    }


    /**
     * 新增保存矩阵信息
     *
     * @param signalMatrixInfo 矩阵信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存矩阵信息" , notes = "新增保存矩阵信息")
    public Map<String,Object> insertSignalMatrixInfo(@RequestBody SignalMatrixInfo signalMatrixInfo) {
        saveOrUpdate(signalMatrixInfo);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",signalMatrixInfo.getMatrixId());
        return result;
    }


    /**
     * 修改保存矩阵信息
     *
     * @param signalMatrixInfo 矩阵信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存矩阵信息" , notes = "修改保存矩阵信息")
    public Map<String,Object> updateSignalMatrixInfoById(@RequestBody SignalMatrixInfo signalMatrixInfo) {
        saveOrUpdate(signalMatrixInfo);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",signalMatrixInfo.getMatrixId());
        return result;
    }


    @Transactional
    void saveOrUpdate(SignalMatrixInfo signalMatrixInfo){
        boolean insert = signalMatrixInfo.getMatrixId() <= 0;
        if(insert){
            signalMatrixInfoService.save(signalMatrixInfo);
        }else{
            signalMatrixInfoService.updateById(signalMatrixInfo);
        }
        String ins = signalMatrixInfo.getIns();
        String outs = signalMatrixInfo.getOuts();

        List<SignalMatrixInterfaceInfo> list_ins = new Gson().fromJson(ins, new TypeToken<List<SignalMatrixInterfaceInfo>>(){}.getType());
        List<SignalMatrixInterfaceInfo> list_outs = new Gson().fromJson(outs, new TypeToken<List<SignalMatrixInterfaceInfo>>(){}.getType());

        List<Integer> interfaceIds =  new ArrayList<>();

        list_ins.forEach((SignalMatrixInterfaceInfo item)->interfaceIds.add(item.getInterfaceId()));
        list_outs.forEach((SignalMatrixInterfaceInfo item)->interfaceIds.add(item.getInterfaceId()));

        if(!insert){
            QueryWrapper<SignalMatrixInterfaceInfo> queryWrapper = new QueryWrapper<SignalMatrixInterfaceInfo>();
            queryWrapper.eq("matrixId",signalMatrixInfo.getMatrixId());
            if(interfaceIds.size()>0){
                queryWrapper.notIn("interfaceId",interfaceIds);
                SignalMatrixInterfaceInfo entity = new SignalMatrixInterfaceInfo();
                entity.setIsDelete(1);
                signalMatrixInterfaceInfoService.update(entity,queryWrapper);
            }
            list_ins.forEach((SignalMatrixInterfaceInfo item)->{
                item.setMatrixId(signalMatrixInfo.getMatrixId());
                item.setInterfaceType(0);
                if(item.getInterfaceId()>0){
                    signalMatrixInterfaceInfoService.updateById(item);
                }else{
                    item.setInterfaceId(null);
                    signalMatrixInterfaceInfoService.save(item);
                }
            });
            list_outs.forEach((SignalMatrixInterfaceInfo item)->{
                item.setMatrixId(signalMatrixInfo.getMatrixId());
                item.setInterfaceType(1);
                if(item.getInterfaceId()>0){
                    signalMatrixInterfaceInfoService.updateById(item);
                }else{
                    item.setInterfaceId(null);
                    signalMatrixInterfaceInfoService.save(item);
                }
            });
        }

        if(insert){
            list_ins.forEach((SignalMatrixInterfaceInfo item)->{
                item.setMatrixId(signalMatrixInfo.getMatrixId());
                item.setInterfaceId(null);
                item.setInterfaceType(0);
            });
            list_outs.forEach((SignalMatrixInterfaceInfo item)->{
                item.setMatrixId(signalMatrixInfo.getMatrixId());
                item.setInterfaceId(null);
                item.setInterfaceType(1);
            });
            signalMatrixInterfaceInfoService.saveBatch(list_ins);
            signalMatrixInterfaceInfoService.saveBatch(list_outs);
        }

    }

    /**
     * 批量删除矩阵信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除矩阵信息" , notes = "删除矩阵信息")
    public boolean removeSignalMatrixInfoByIds(String ids) {
        QueryWrapper<SignalMatrixInterfaceInfo> queryWrapper = new QueryWrapper<SignalMatrixInterfaceInfo>();
        queryWrapper.in("matrixId",Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
        signalMatrixInterfaceInfoService.remove(queryWrapper);
        return signalMatrixInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
    }
}

