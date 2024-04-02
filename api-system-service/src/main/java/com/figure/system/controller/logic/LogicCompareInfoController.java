package com.figure.system.controller.logic;

import com.figure.core.base.BaseController;
import com.figure.core.model.logic.LogicCompareInfo;
import com.figure.core.model.logic.LogicCompareInfoList;
import com.figure.core.query.logic.LogicCompareInfoQuery;
import com.figure.core.service.logic.ILogicCompareInfoService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 异态比对分组配置 前端控制器
 * </p>
 *
 * @author feather
 * @date 2022-08-23 15:13:15
 */
@RestController
@RequestMapping("/logicCompareInfo")
@Api(value = "异态比对分组配置相关接口", tags = "异态比对分组配置相关接口")
public class LogicCompareInfoController extends BaseController {

    @Resource
    private ILogicCompareInfoService logicCompareInfoService;

    /**
     * 根据id获取异态比对分组配置
     *
     * @param id 异态比对分组配置ID
     * @return 异态比对分组配置
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取异态比对分组配置", notes = "根据id获取异态比对分组配置")
    public LogicCompareInfo selectLogicCompareInfoById(@PathVariable("id") Integer id) {
        return logicCompareInfoService.getById(id);
    }

    /**
     * 查询异态比对分组配置列表
     *
     * @param logicCompareInfoQuery 异态比对分组配置
     * @return 异态比对分组配置集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询异态比对分组配置列表", notes = "查询异态比对分组配置列表")
    public TableDataInfo<LogicCompareInfo> selectLogicCompareInfoList(LogicCompareInfoQuery logicCompareInfoQuery) {

        return toPageResult(logicCompareInfoService.list(logicCompareInfoQuery.autoPageWrapper()));
    }

    /**
     * 查询异态比对分组配置列表
     *
     * @param logicCompareInfoQuery 异态比对分组配置
     * @return 异态比对分组配置集合
     */
    @GetMapping("/treelist")
    @ApiOperation(value = "查询异态比对分组配置列表", notes = "查询异态比对分组配置列表")
    public TableDataInfo<LogicCompareInfoList> selectLogicCompareInfoTreeList(LogicCompareInfoQuery logicCompareInfoQuery) {
        return toPageResult(logicCompareInfoService.treelist(logicCompareInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存异态比对分组配置
     *
     * @param logicCompareInfo 异态比对分组配置
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存异态比对分组配置", notes = "新增保存异态比对分组配置")
    public Map<String, Object> insertLogicCompareInfo(@RequestBody LogicCompareInfo logicCompareInfo) {
        return returnMap(logicCompareInfoService.save(logicCompareInfo), logicCompareInfo.getId());
    }

    /**
     * 批量新增保存异态比对分组配置
     *
     * @param logicCompareInfo 异态比对分组配置
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存异态比对分组配置", notes = "批量新增保存异态比对分组配置")
    public Map<String, Object> saveBatchLogicCompareInfo(@RequestBody List<LogicCompareInfo> logicCompareInfo) {
        return returnMap(logicCompareInfoService.saveBatch(logicCompareInfo), logicCompareInfo);
    }

    /**
     * 修改保存异态比对分组配置
     *
     * @param logicCompareInfo 异态比对分组配置
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存异态比对分组配置", notes = "修改保存异态比对分组配置")
    public Map<String, Object> updateLogicCompareInfoById(@RequestBody LogicCompareInfo logicCompareInfo) {
        return returnMap(logicCompareInfoService.updateById(logicCompareInfo));
    }

    /**
     * 批量删除异态比对分组配置
     *
     * @param logicCompareInfo 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除异态比对分组配置", notes = "删除异态比对分组配置")
    public Map<String, Object> removeLogicCompareInfoByIds(@RequestBody LogicCompareInfo logicCompareInfo) {
        return returnMap(logicCompareInfoService.removeByIds(StringUtils.StringToIntList(logicCompareInfo.getIds())));
    }

}