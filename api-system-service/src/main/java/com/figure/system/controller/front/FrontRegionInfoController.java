package com.figure.system.controller.front;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.figure.core.base.BaseController;
import com.figure.core.model.front.FrontRegionInfo;
import com.figure.core.service.front.IFrontRegionInfoService;
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
 * 行政区域信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-05-19 16:32:59
 */
@RestController
@RequestMapping("/frontRegionInfo")
@Api(value = "行政区域信息相关接口" , tags = "行政区域信息相关接口")
public class FrontRegionInfoController extends BaseController {

    @Resource
    private IFrontRegionInfoService frontRegionInfoService;

    /**
     * 根据regionId获取行政区域信息
     *
     * @param regionId 行政区域信息ID
     * @return 行政区域信息
     */
    @GetMapping("/get/{regionId}")
    @ApiOperation(value = "根据regionId获取行政区域信息" , notes = "根据regionId获取行政区域信息")
    public FrontRegionInfo selectFrontRegionInfoById(@PathVariable("regionId") Long regionId) {
        return frontRegionInfoService.getById(regionId);
    }

    /**
     * 查询行政区域信息列表
     *
     * @param frontRegionInfo 行政区域信息
     * @return 行政区域信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询行政区域信息列表" , notes = "查询行政区域信息列表")
    public TableDataInfo<FrontRegionInfo> selectFrontRegionInfoList(FrontRegionInfo frontRegionInfo) {
        return toPageResult(frontRegionInfoService.list());
    }

    /**
     * 新增保存行政区域信息
     *
     * @param frontRegionInfo 行政区域信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存行政区域信息" , notes = "新增保存行政区域信息")
    public Map<String,Object> insertFrontRegionInfo(@RequestBody FrontRegionInfo frontRegionInfo) {
        return returnMap(frontRegionInfoService.save(frontRegionInfo),frontRegionInfo.getRegionId());
    }

    /**
     * 修改保存行政区域信息
     *
     * @param frontRegionInfo 行政区域信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存行政区域信息" , notes = "修改保存行政区域信息")
    public Map<String,Object> updateFrontRegionInfoById(@RequestBody FrontRegionInfo frontRegionInfo) {
        if(frontRegionInfo.getRegionId()==null||frontRegionInfo.getRegionId()<0){
            frontRegionInfo.setRegionId(null);
            return insertFrontRegionInfo(frontRegionInfo);
        }
        boolean update_result = frontRegionInfoService.updateById(frontRegionInfo);

        UpdateWrapper<FrontRegionInfo> updateWrapper = new UpdateWrapper<>();

        updateWrapper.eq("regionId", frontRegionInfo.getRegionId());

        if(frontRegionInfo.getProvincesId()==null){
            updateWrapper.set("provincesId",null);
        }

        if(frontRegionInfo.getCityId()==null){
            updateWrapper.set("cityId",null);
        }

        if(frontRegionInfo.getDistrictsId()==null){
            updateWrapper.set("districtsId",null);
        }

        frontRegionInfoService.update(updateWrapper);

        return returnMap(update_result,frontRegionInfo.getRegionId());
    }

    /**
     * 批量删除行政区域信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除行政区域信息" , notes = "删除行政区域信息")
    public Map<String,Object> removeFrontRegionInfoByIds(String ids) {
        return returnMap(frontRegionInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids))));
    }

}