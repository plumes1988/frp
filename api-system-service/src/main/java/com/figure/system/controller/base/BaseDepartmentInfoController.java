package com.figure.system.controller.base;

import com.figure.core.base.BaseController;
import com.figure.core.model.base.BaseDepartmentInfo;
import com.figure.core.service.base.IBaseDepartmentInfoService;
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
 * 单位信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-04-20 14:03:33
 */
@RestController
@RequestMapping("/baseDepartmentInfo")
@Api(value = "单位信息相关接口" , tags = "单位信息相关接口")
public class BaseDepartmentInfoController extends BaseController {

    @Resource
    private IBaseDepartmentInfoService baseDepartmentInfoService;

    /**
     * 根据departmentId获取单位信息
     *
     * @param departmentId 单位信息ID
     * @return 单位信息
     */
    @GetMapping("/get/{departmentId}")
    @ApiOperation(value = "根据departmentId获取单位信息" , notes = "根据departmentId获取单位信息")
    public BaseDepartmentInfo selectBaseDepartmentInfoById(@PathVariable("departmentId") Long departmentId) {
        return baseDepartmentInfoService.getById(departmentId);
    }

    /**
     * 查询单位信息列表
     *
     * @param baseDepartmentInfo 单位信息
     * @return 单位信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询单位信息列表" , notes = "查询单位信息列表")
    public TableDataInfo selectBaseDepartmentInfoList(BaseDepartmentInfo baseDepartmentInfo) {
        return toPageResult(baseDepartmentInfoService.list());
    }

    /**
     * 新增保存单位信息
     *
     * @param baseDepartmentInfo 单位信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存单位信息" , notes = "新增保存单位信息")
    public Map<String,Object> insertBaseDepartmentInfo(@RequestBody BaseDepartmentInfo baseDepartmentInfo) {
        baseDepartmentInfo.setCreateTime(new Date());
        return returnMap(baseDepartmentInfoService.save(baseDepartmentInfo));
    }

    /**
     * 修改保存单位信息
     *
     * @param baseDepartmentInfo 单位信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存单位信息" , notes = "修改保存单位信息")
    public Map<String,Object>  updateBaseDepartmentInfoById(@RequestBody BaseDepartmentInfo baseDepartmentInfo) {
        baseDepartmentInfo.setUpdateTime(new Date());
        return returnMap(baseDepartmentInfoService.updateById(baseDepartmentInfo));
    }

    /**
     * 批量删除单位信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除单位信息" , notes = "删除单位信息")
    public Map<String,Object>  removeBaseDepartmentInfoByIds(String ids) {
        return returnMap(baseDepartmentInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids))));
    }

}