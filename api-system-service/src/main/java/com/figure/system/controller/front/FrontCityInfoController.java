package com.figure.system.controller.front;

import com.figure.core.base.BaseController;
import com.figure.core.model.front.FrontCityInfo;
import com.figure.core.service.front.IFrontCityInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import com.figure.core.util.page.TableDataInfo;

/**
 * <p>
 * 城市信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-05-19 10:38:10
 */
@RestController
@RequestMapping("/frontCityInfo")
@Api(value = "城市信息相关接口" , tags = "城市信息相关接口")
public class FrontCityInfoController extends BaseController {

    @Resource
    private IFrontCityInfoService frontCityInfoService;

    /**
     * 根据cityId获取城市信息
     *
     * @param cityId 城市信息ID
     * @return 城市信息
     */
    @GetMapping("/get/{cityId}")
    @ApiOperation(value = "根据cityId获取城市信息" , notes = "根据cityId获取城市信息")
    public FrontCityInfo selectFrontCityInfoById(@PathVariable("cityId") Integer cityId) {
        return frontCityInfoService.getById(cityId);
    }

    /**
     * 查询城市信息列表
     *
     * @param frontCityInfo 城市信息
     * @return 城市信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询城市信息列表" , notes = "查询城市信息列表")
    public TableDataInfo<FrontCityInfo> selectFrontCityInfoList(FrontCityInfo frontCityInfo) {
        return toPageResult(frontCityInfoService.list());
    }

    /**
     * 新增保存城市信息
     *
     * @param frontCityInfo 城市信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存城市信息" , notes = "新增保存城市信息")
    public Map<String,Object> insertFrontCityInfo(@RequestBody FrontCityInfo frontCityInfo) {
        return returnMap(frontCityInfoService.save(frontCityInfo),frontCityInfo.getCityId());
    }

    /**
     * 修改保存城市信息
     *
     * @param frontCityInfo 城市信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存城市信息" , notes = "修改保存城市信息")
    public Map<String,Object> updateFrontCityInfoById(@RequestBody FrontCityInfo frontCityInfo) {
        if(frontCityInfo.getCityId()<0){
            frontCityInfo.setCityId(null);
            return insertFrontCityInfo(frontCityInfo);
        }
        return returnMap(frontCityInfoService.updateById(frontCityInfo),frontCityInfo.getCityId()+500000);
    }

    /**
     * 批量删除城市信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除城市信息" , notes = "删除城市信息")
    public Map<String,Object> removeFrontCityInfoByIds(String ids) {
        return returnMap(frontCityInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids))));
    }

}