package com.figure.system.controller.device;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.audit.AuditClipInfo;
import com.figure.core.model.device.DeviceDataAnalysisStrategy;
import com.figure.core.model.device.DeviceIndicatorParam;
import com.figure.core.model.device.DeviceIndicatorParamRule;
import com.figure.core.model.sys.SysModulePermission;
import com.figure.core.service.device.IDeviceDataAnalysisStrategyService;
import com.figure.core.service.device.IDeviceIndicatorParamRuleService;
import com.figure.core.service.device.IDeviceIndicatorParamService;
import com.figure.core.util.page.TableDataInfo;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-06-14
 */
@RestController
@RequestMapping("/deviceIndicatorParam")
public class DeviceIndicatorParamController {

    @Autowired
    IDeviceIndicatorParamService deviceIndicatorParamService;

    @Autowired
    IDeviceIndicatorParamRuleService deviceIndicatorParamRuleService;

    @Autowired
    IDeviceDataAnalysisStrategyService deviceDataAnalysisStrategyService;

    /**
     * 查询指标类型列表
     *
     * @param deviceIndicatorParam 指标类型
     * @return 指标类型集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询指标类型列表" , notes = "查询指标类型列表")
    public TableDataInfo<DeviceIndicatorParam> selectDeviceIndicatorParamList(DeviceIndicatorParam deviceIndicatorParam, HttpServletRequest request) throws Exception {
        PageWrapper<DeviceIndicatorParam> pageWrapper =  new PageHelper(DeviceIndicatorParam.class).getPageWrapper(request);
        QueryWrapper<DeviceIndicatorParam> queryWrapper =   pageWrapper.getQueryWrapper();
        IPage<DeviceIndicatorParam> pages = deviceIndicatorParamService.page(pageWrapper.getPage(),queryWrapper);

        for (DeviceIndicatorParam item :pages.getRecords()){
            QueryWrapper qw = new QueryWrapper<>();
            qw.eq("indicatorCode",item.getIndicatorCode());
            item.setRules(deviceIndicatorParamRuleService.list(qw));
            item.setDeviceDataAnalysisStrategys(deviceDataAnalysisStrategyService.list(qw));
        }
        return new TableDataInfo(pages);

    }


    /**
     * 新增保存指标类型
     *
     * @param deviceIndicatorParam 指标类型
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存指标类型" , notes = "新增保存指标类型")
    public Map<String,Object> insertDeviceIndicatorParam(@RequestBody DeviceIndicatorParam deviceIndicatorParam) {
        deviceIndicatorParamService.save(deviceIndicatorParam);
        updateOthers(deviceIndicatorParam);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceIndicatorParam.getId());
        deviceIndicatorParamService.updateCache();
        return result;
    }


    private void updateOthers(DeviceIndicatorParam deviceIndicatorParam) {
        String indicatorCode =  deviceIndicatorParam.getIndicatorCode();
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("indicatorCode",indicatorCode);

        List<Integer> ruleIds =  new ArrayList<>();
        for (DeviceIndicatorParamRule rule : deviceIndicatorParam.getRules()){
            ruleIds.add(rule.getId());
        }

        if(ruleIds.size()>0){
            queryWrapper.notIn("id",ruleIds);
            deviceIndicatorParamRuleService.remove(queryWrapper);
            deviceIndicatorParamRuleService.remove(queryWrapper);
        }

        
        for (DeviceIndicatorParamRule rule : deviceIndicatorParam.getRules()){
            rule.setIndicatorCode(indicatorCode);
            if(rule.getId()<0){
                deviceIndicatorParamRuleService.save(rule);
            }else{
                deviceIndicatorParamRuleService.updateById(rule);
            }
        }
    }


    /**
     * 修改保存指标类型
     *
     * @param deviceIndicatorParam 指标类型
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存指标类型" , notes = "修改保存指标类型")
    public Map<String,Object> updateDeviceIndicatorParamById(@RequestBody DeviceIndicatorParam deviceIndicatorParam) {
        deviceIndicatorParamService.updateById(deviceIndicatorParam);
        updateOthers(deviceIndicatorParam);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",deviceIndicatorParam.getId());
        deviceIndicatorParamService.updateCache();
        return result;
    }

    /**
     * 批量删除指标类型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除指标类型" , notes = "删除指标类型")
    public boolean removeDeviceIndicatorParamByIds(String ids) {
        deviceIndicatorParamService.updateCache();
        return deviceIndicatorParamService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids)));
    }

    @Data
    static class ParamsWarp {
       private String deviceIndicatorParams_str;
    }

    /**
     * 排序指标类型
     *
     * @param paramsWarp 排序指标类型
     * @return 结果
     */
    @PostMapping("/level")
    @ApiOperation(value = "排序模块权限项", notes = "排序模块权限项")
    public Map<String, Object> levelDeviceIndicatorParamByIds(@RequestBody ParamsWarp  paramsWarp) {
        DeviceIndicatorParam[] deviceIndicatorParams = new Gson().fromJson(paramsWarp.getDeviceIndicatorParams_str(), DeviceIndicatorParam[].class);
        Integer index = deviceIndicatorParams.length;
        for(DeviceIndicatorParam deviceIndicatorParam:deviceIndicatorParams){
            deviceIndicatorParam.setLevel(index--);
        }
        deviceIndicatorParamService.updateBatchById(Arrays.asList(deviceIndicatorParams));
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        return result;
    }

}
