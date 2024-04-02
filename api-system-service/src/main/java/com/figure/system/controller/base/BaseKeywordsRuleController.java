package com.figure.system.controller.base;

import com.figure.core.base.BaseController;
import com.figure.core.model.base.BaseKeywordsRule;
import com.figure.core.service.base.IBaseKeywordsRuleService;
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
 * 关键词规则 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-04-20 14:03:33
 */
@RestController
@RequestMapping("/baseKeywordsRule")
@Api(value = "关键词规则相关接口" , tags = "关键词规则相关接口")
public class BaseKeywordsRuleController extends BaseController {

    @Resource
    private IBaseKeywordsRuleService baseKeywordsRuleService;

    /**
     * 根据ruleId获取关键词规则
     *
     * @param ruleId 关键词规则ID
     * @return 关键词规则
     */
    @GetMapping("/get/{ruleId}")
    @ApiOperation(value = "根据ruleId获取关键词规则" , notes = "根据ruleId获取关键词规则")
    public BaseKeywordsRule selectBaseKeywordsRuleById(@PathVariable("ruleId") Long ruleId) {
        return baseKeywordsRuleService.getById(ruleId);
    }

    /**
     * 查询关键词规则列表
     *
     * @param baseKeywordsRule 关键词规则
     * @return 关键词规则集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询关键词规则列表" , notes = "查询关键词规则列表")
    public TableDataInfo selectBaseKeywordsRuleList(BaseKeywordsRule baseKeywordsRule) {
        return toPageResult(baseKeywordsRuleService.list());
    }

    /**
     * 新增保存关键词规则
     *
     * @param baseKeywordsRule 关键词规则
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存关键词规则" , notes = "新增保存关键词规则")
    public Map<String,Object>  insertBaseKeywordsRule(@RequestBody BaseKeywordsRule baseKeywordsRule) {
        baseKeywordsRule.setCreateTime(new Date());
        return returnMap(baseKeywordsRuleService.save(baseKeywordsRule));
    }

    /**
     * 修改保存关键词规则
     *
     * @param baseKeywordsRule 关键词规则
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存关键词规则" , notes = "修改保存关键词规则")
    public Map<String,Object>  updateBaseKeywordsRuleById(@RequestBody BaseKeywordsRule baseKeywordsRule) {
        baseKeywordsRule.setUpdateTime(new Date());
        return returnMap(baseKeywordsRuleService.updateById(baseKeywordsRule));
    }

    /**
     * 批量删除关键词规则
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除关键词规则" , notes = "删除关键词规则")
    public Map<String,Object> removeBaseKeywordsRuleByIds(String ids) {
        return returnMap(baseKeywordsRuleService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids))));
    }

}