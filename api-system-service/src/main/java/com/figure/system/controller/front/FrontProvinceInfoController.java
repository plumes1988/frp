package com.figure.system.controller.front;

import com.figure.core.base.BaseController;
import com.figure.core.model.front.FrontProvinceInfo;
import com.figure.core.service.front.IFrontProvinceInfoService;
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
 * 省份信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-05-19 16:32:59
 */
@RestController
@RequestMapping("/frontProvinceInfo")
@Api(value = "省份信息相关接口" , tags = "省份信息相关接口")
public class FrontProvinceInfoController extends BaseController {

    @Resource
    private IFrontProvinceInfoService frontProvinceInfoService;

    /**
     * 根据provinceId获取省份信息
     *
     * @param provinceId 省份信息ID
     * @return 省份信息
     */
    @GetMapping("/get/{provinceId}")
    @ApiOperation(value = "根据provinceId获取省份信息" , notes = "根据provinceId获取省份信息")
    public FrontProvinceInfo selectFrontProvinceInfoById(@PathVariable("provinceId") Integer provinceId) {
        return frontProvinceInfoService.getById(provinceId);
    }

    /**
     * 查询省份信息列表
     *
     * @param frontProvinceInfo 省份信息
     * @return 省份信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询省份信息列表" , notes = "查询省份信息列表")
    public TableDataInfo<FrontProvinceInfo> selectFrontProvinceInfoList(FrontProvinceInfo frontProvinceInfo) {
        return toPageResult(frontProvinceInfoService.list());
    }

    /**
     * 新增保存省份信息
     *
     * @param frontProvinceInfo 省份信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存省份信息" , notes = "新增保存省份信息")
    public Map<String,Object> insertFrontProvinceInfo(@RequestBody FrontProvinceInfo frontProvinceInfo) {
        return returnMap(frontProvinceInfoService.save(frontProvinceInfo),frontProvinceInfo.getProvinceId());
    }

    /**
     * 修改保存省份信息
     *
     * @param frontProvinceInfo 省份信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存省份信息" , notes = "修改保存省份信息")
    public Map<String,Object> updateFrontProvinceInfoById(@RequestBody FrontProvinceInfo frontProvinceInfo) {
        if(frontProvinceInfo.getProvinceId()<0){
            frontProvinceInfo.setProvinceId(null);
            return insertFrontProvinceInfo(frontProvinceInfo);
        }
        return returnMap(frontProvinceInfoService.updateById(frontProvinceInfo),frontProvinceInfo.getProvinceId());
    }

    /**
     * 批量删除省份信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除省份信息" , notes = "删除省份信息")
    public Map<String,Object> removeFrontProvinceInfoByIds(String ids) {
        return returnMap(frontProvinceInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids))));
    }

}