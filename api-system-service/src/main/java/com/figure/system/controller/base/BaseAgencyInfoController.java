package com.figure.system.controller.base;

import com.figure.core.base.BaseController;
import com.figure.core.model.base.BaseAgencyInfo;
import com.figure.core.service.base.IBaseAgencyInfoService;
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
 * 机构信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-04-20 14:03:32
 */
@RestController
@RequestMapping("/baseAgencyInfo")
@Api(value = "机构信息相关接口" , tags = "机构信息相关接口")
public class BaseAgencyInfoController extends BaseController {

    @Resource
    private IBaseAgencyInfoService baseAgencyInfoService;

    /**
     * 根据agencyId获取机构信息
     *
     * @param agencyId 机构信息ID
     * @return 机构信息
     */
    @GetMapping("/get/{agencyId}")
    @ApiOperation(value = "根据agencyId获取机构信息" , notes = "根据agencyId获取机构信息")
    public BaseAgencyInfo selectBaseAgencyInfoById(@PathVariable("agencyId") Long agencyId) {
        return baseAgencyInfoService.getById(agencyId);
    }

    /**
     * 查询机构信息列表
     *
     * @param baseAgencyInfo 机构信息
     * @return 机构信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询机构信息列表" , notes = "查询机构信息列表")
    public TableDataInfo selectBaseAgencyInfoList(BaseAgencyInfo baseAgencyInfo) {
        return toPageResult(baseAgencyInfoService.list());
    }

    /**
     * 新增保存机构信息
     *
     * @param baseAgencyInfo 机构信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存机构信息" , notes = "新增保存机构信息")
    public Map<String,Object> insertBaseAgencyInfo(@RequestBody BaseAgencyInfo baseAgencyInfo) {
        baseAgencyInfo.setCreateTime(new Date());
        return returnMap(baseAgencyInfoService.save(baseAgencyInfo));
    }

    /**
     * 修改保存机构信息
     *
     * @param baseAgencyInfo 机构信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存机构信息" , notes = "修改保存机构信息")
    public Map<String,Object> updateBaseAgencyInfoById(@RequestBody BaseAgencyInfo baseAgencyInfo) {
        baseAgencyInfo.setUpdateTime(new Date());
        return  returnMap(baseAgencyInfoService.updateById(baseAgencyInfo));
    }

    /**
     * 批量删除机构信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除机构信息" , notes = "删除机构信息")
    public Map<String,Object> removeBaseAgencyInfoByIds(String ids) {
        return returnMap(baseAgencyInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids))));
    }

}