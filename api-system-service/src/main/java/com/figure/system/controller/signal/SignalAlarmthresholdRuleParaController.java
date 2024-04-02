package com.figure.system.controller.signal;

import com.figure.core.base.BaseController;
import com.figure.core.model.signal.SignalAlarmthresholdRulePara;
import com.figure.core.model.signal.SignalAlarmthresholdRuleParaList;
import com.figure.core.query.signal.SignalAlarmthresholdRuleParaQuery;
import com.figure.core.service.signal.ISignalAlarmthresholdRuleParaService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.text.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Arrays;

import com.figure.core.util.page.TableDataInfo;

/**
 * <p>
 * 信号报警门限规则参数 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-12-17 15:01:23
 */
@RestController
@RequestMapping("/signalAlarmthresholdRulePara")
@Api(value = "信号报警门限规则参数相关接口", tags = "信号报警门限规则参数相关接口")
public class SignalAlarmthresholdRuleParaController extends BaseController {

    @Resource
    private ISignalAlarmthresholdRuleParaService signalAlarmthresholdRuleParaService;

    /**
     * 根据paraId获取信号报警门限规则参数
     *
     * @param paraId 信号报警门限规则参数ID
     * @return 信号报警门限规则参数
     */
    @GetMapping("/get/{paraId}")
    @ApiOperation(value = "根据paraId获取信号报警门限规则参数", notes = "根据paraId获取信号报警门限规则参数")
    public SignalAlarmthresholdRulePara selectSignalAlarmthresholdRuleParaById(@PathVariable("paraId") Integer paraId) {
        return signalAlarmthresholdRuleParaService.getById(paraId);
    }

    /**
     * 查询信号报警门限规则参数列表
     *
     * @param signalAlarmthresholdRuleParaQuery 信号报警门限规则参数
     * @return 信号报警门限规则参数集合
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询信号报警门限规则参数列表", notes = "查询信号报警门限规则参数列表")
    public TableDataInfo<SignalAlarmthresholdRulePara> selectSignalAlarmthresholdRuleParaList(SignalAlarmthresholdRuleParaQuery signalAlarmthresholdRuleParaQuery) {
        if (signalAlarmthresholdRuleParaQuery.getParaId() != null) {
            signalAlarmthresholdRuleParaQuery.setParaIdList(Arrays.asList(Convert.toIntArray(signalAlarmthresholdRuleParaQuery.getParaId())));
            signalAlarmthresholdRuleParaQuery.setParaId(null);
        }
        return toPageResult(signalAlarmthresholdRuleParaService.listByQuery(signalAlarmthresholdRuleParaQuery.autoPageWrapper()));
    }

    /**
     * 新增保存信号报警门限规则参数
     *
     * @param signalAlarmthresholdRuleParaList 信号报警门限规则参数
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存信号报警门限规则参数", notes = "新增保存信号报警门限规则参数")
    public Map<String, Object> insertSignalAlarmthresholdRulePara(@RequestBody SignalAlarmthresholdRuleParaList signalAlarmthresholdRuleParaList) {
        return returnMap(signalAlarmthresholdRuleParaService.saveBatch(signalAlarmthresholdRuleParaList.getSignalAlarmthresholdRuleParaJson()),
                signalAlarmthresholdRuleParaList.getSignalAlarmthresholdRuleParaJson());
    }

    /**
     * 修改保存信号报警门限规则参数
     *
     * @param signalAlarmthresholdRuleParaList 信号报警门限规则参数
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存信号报警门限规则参数", notes = "修改保存信号报警门限规则参数")
    public Map<String, Object> updateSignalAlarmthresholdRuleParaById(@RequestBody SignalAlarmthresholdRuleParaList signalAlarmthresholdRuleParaList) {
        return returnMap(signalAlarmthresholdRuleParaService.updateBatchById(signalAlarmthresholdRuleParaList.getSignalAlarmthresholdRuleParaJson()),
                signalAlarmthresholdRuleParaList.getSignalAlarmthresholdRuleParaJson());
    }

    /**
     * 批量删除信号报警门限规则参数
     *
     * @param signalAlarmthresholdRulePara 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除信号报警门限规则参数", notes = "删除信号报警门限规则参数")
    public Map<String, Object> removeSignalAlarmthresholdRuleParaByIds(@RequestBody SignalAlarmthresholdRulePara signalAlarmthresholdRulePara) {
        return returnMap(signalAlarmthresholdRuleParaService.removeByIds(StringUtils.StringToIntList(signalAlarmthresholdRulePara.getIds())));
    }

}