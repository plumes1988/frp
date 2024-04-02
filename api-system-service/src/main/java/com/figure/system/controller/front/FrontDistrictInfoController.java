package com.figure.system.controller.front;

import com.figure.core.base.BaseController;
import com.figure.core.model.front.FrontDistrictInfo;
import com.figure.core.service.front.IFrontCityInfoService;
import com.figure.core.service.front.IFrontDistrictInfoService;
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
 * 区县信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-05-19 16:32:59
 */
@RestController
@RequestMapping("/frontDistrictInfo")
@Api(value = "区县信息相关接口" , tags = "区县信息相关接口")
public class FrontDistrictInfoController extends BaseController {

    @Resource
    private IFrontDistrictInfoService frontDistrictInfoService;

    @Resource
    private IFrontCityInfoService frontCityInfoService;

    /**
     * 根据districtId获取区县信息
     *
     * @param districtId 区县信息ID
     * @return 区县信息
     */
    @GetMapping("/get/{districtId}")
    @ApiOperation(value = "根据districtId获取区县信息" , notes = "根据districtId获取区县信息")
    public FrontDistrictInfo selectFrontDistrictInfoById(@PathVariable("districtId") Integer districtId) {
        return frontDistrictInfoService.getById(districtId);
    }

    /**
     * 查询区县信息列表
     *
     * @param frontDistrictInfo 区县信息
     * @return 区县信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询区县信息列表" , notes = "查询区县信息列表")
    public TableDataInfo<FrontDistrictInfo> selectFrontDistrictInfoList(FrontDistrictInfo frontDistrictInfo) {
        return toPageResult(frontDistrictInfoService.list());
    }

    /**
     * 新增保存区县信息
     *
     * @param frontDistrictInfo 区县信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存区县信息" , notes = "新增保存区县信息")
    public Map<String,Object> insertFrontDistrictInfo(@RequestBody FrontDistrictInfo frontDistrictInfo) {
        return returnMap(frontDistrictInfoService.save(frontDistrictInfo),frontDistrictInfo.getDistrictId());
    }

    /**
     * 修改保存区县信息
     *
     * @param frontDistrictInfo 区县信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存区县信息" , notes = "修改保存区县信息")
    public Map<String,Object> updateFrontDistrictInfoById(@RequestBody FrontDistrictInfo frontDistrictInfo) {
        frontDistrictInfo.setProvinceId(frontCityInfoService.getById(frontDistrictInfo.getCityId()).getProvinceId());
        if(frontDistrictInfo.getDistrictId()<0){
            frontDistrictInfo.setDistrictId(null);
            return insertFrontDistrictInfo(frontDistrictInfo);
        }
        return returnMap(frontDistrictInfoService.updateById(frontDistrictInfo),frontDistrictInfo.getDistrictId()+1000000);
    }

    /**
     * 批量删除区县信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除区县信息" , notes = "删除区县信息")
    public Map<String,Object> removeFrontDistrictInfoByIds(String ids) {
        return returnMap(frontDistrictInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids))));
    }

}