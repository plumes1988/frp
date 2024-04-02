package com.figure.system.controller.base;

import com.figure.core.base.BaseController;
import com.figure.core.model.base.BaseImportantPeriod;
import com.figure.core.query.base.BaseImportantPeriodQuery;
import com.figure.core.service.base.IBaseImportantPeriodService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 重要保障期 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-04-20 14:03:33
 */
@RestController
@RequestMapping("/baseImportantPeriod")
@Api(value = "重要保障期相关接口" , tags = "重要保障期相关接口")
public class BaseImportantPeriodController extends BaseController {

    @Resource
    private IBaseImportantPeriodService baseImportantPeriodService;

    /**
     * 根据importantPeriodId获取重要保障期
     *
     * @param importantPeriodId 重要保障期ID
     * @return 重要保障期
     */
    @GetMapping("/get/{importantPeriodId}")
    @ApiOperation(value = "根据importantPeriodId获取重要保障期" , notes = "根据importantPeriodId获取重要保障期")
    public BaseImportantPeriod selectBaseImportantPeriodById(@PathVariable("importantPeriodId") Long importantPeriodId) {
        return baseImportantPeriodService.getById(importantPeriodId);
    }

    /**
     * 查询重要保障期列表
     *
     * @param baseImportantPeriodQuery 重要保障期
     * @return 重要保障期集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询重要保障期列表", notes = "查询重要保障期列表")
    public TableDataInfo selectBaseImportantPeriodList(BaseImportantPeriodQuery baseImportantPeriodQuery) {
        return toPageResult(baseImportantPeriodService.list(baseImportantPeriodQuery.autoPageWrapper()));
    }

    /**
     * 新增保存重要保障期
     *
     * @param baseImportantPeriod 重要保障期
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存重要保障期" , notes = "新增保存重要保障期")
    public Map<String,Object>  insertBaseImportantPeriod(@RequestBody BaseImportantPeriod baseImportantPeriod) {
        baseImportantPeriod.setCreateTime(new Date());
        return returnMap(baseImportantPeriodService.save(baseImportantPeriod));
    }

    /**
     * 修改保存重要保障期
     *
     * @param baseImportantPeriod 重要保障期
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存重要保障期" , notes = "修改保存重要保障期")
    public Map<String,Object>  updateBaseImportantPeriodById(@RequestBody BaseImportantPeriod baseImportantPeriod) {
        baseImportantPeriod.setUpdateTime(new Date());
        return returnMap(baseImportantPeriodService.updateById(baseImportantPeriod));
    }

    /**
     * 批量删除重要保障期
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除重要保障期" , notes = "删除重要保障期")
    public Map<String,Object> removeBaseImportantPeriodByIds(String ids) {
        return returnMap(baseImportantPeriodService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids))));
    }

}