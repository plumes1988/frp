package com.figure.system.controller.front;

import com.figure.core.base.BaseController;
import com.figure.core.model.front.FrontIllegalFrequency;
import com.figure.core.service.front.IFrontIllegalFrequencyService;
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
 * 黑广播业务配置 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-05-19 16:32:59
 */
@RestController
@RequestMapping("/frontIllegalFrequency")
@Api(value = "黑广播业务配置相关接口" , tags = "黑广播业务配置相关接口")
public class FrontIllegalFrequencyController extends BaseController {

    @Resource
    private IFrontIllegalFrequencyService frontIllegalFrequencyService;

    /**
     * 根据frontId获取黑广播业务配置
     *
     * @param frontId 黑广播业务配置ID
     * @return 黑广播业务配置
     */
    @GetMapping("/get/{frontId}")
    @ApiOperation(value = "根据frontId获取黑广播业务配置" , notes = "根据frontId获取黑广播业务配置")
    public FrontIllegalFrequency selectFrontIllegalFrequencyById(@PathVariable("frontId") Long frontId) {

        return frontIllegalFrequencyService.getById(frontId);
    }

    /**
     * 查询黑广播业务配置列表
     *
     * @param frontIllegalFrequency 黑广播业务配置
     * @return 黑广播业务配置集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询黑广播业务配置列表" , notes = "查询黑广播业务配置列表")
    public TableDataInfo<FrontIllegalFrequency> selectFrontIllegalFrequencyList(FrontIllegalFrequency frontIllegalFrequency) {
        return toPageResult(frontIllegalFrequencyService.list());
    }

    /**
     * 新增保存黑广播业务配置
     *
     * @param frontIllegalFrequency 黑广播业务配置
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存黑广播业务配置" , notes = "新增保存黑广播业务配置")
    public Map<String,Object> insertFrontIllegalFrequency(@RequestBody FrontIllegalFrequency frontIllegalFrequency) {
        frontIllegalFrequencyService.removeById(frontIllegalFrequency.getFrontId());
        initCreateProps(frontIllegalFrequency);
        return returnMap(frontIllegalFrequencyService.save(frontIllegalFrequency));
    }

    /**
     * 修改保存黑广播业务配置
     *
     * @param frontIllegalFrequency 黑广播业务配置
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存黑广播业务配置" , notes = "修改保存黑广播业务配置")
    public Map<String,Object> updateFrontIllegalFrequencyById(@RequestBody FrontIllegalFrequency frontIllegalFrequency) {
        return returnMap(frontIllegalFrequencyService.updateById(frontIllegalFrequency));
    }

    /**
     * 批量删除黑广播业务配置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除黑广播业务配置" , notes = "删除黑广播业务配置")
    public Map<String,Object> removeFrontIllegalFrequencyByIds(String ids) {
        return returnMap(frontIllegalFrequencyService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids))));
    }

}