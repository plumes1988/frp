package com.figure.system.controller.signal;

import com.figure.core.base.BaseController;
import com.figure.core.model.signal.SignalFrequencyInfo;
import com.figure.core.model.signal.SignalFrequencyInfoList;
import com.figure.core.query.signal.SignalFrequencyInfoQuery;
import com.figure.core.service.signal.ISignalFrequencyInfoService;
import com.figure.core.service.signal.ISignalInterfaceInfoService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 频率信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-05-25 17:10:38
 */
@RestController
@RequestMapping("/signalFrequencyInfo")
@Api(value = "频率信息相关接口" , tags = "频率信息相关接口")
public class SignalFrequencyInfoController extends BaseController {

    @Resource
    private ISignalFrequencyInfoService signalFrequencyInfoService;

    /**
     * 根据frequencyId获取频率信息
     *
     * @param frequencyId 频率信息ID
     * @return 频率信息
     */
    @GetMapping("/get/{frequencyId}")
    @ApiOperation(value = "根据frequencyId获取频率信息" , notes = "根据frequencyId获取频率信息")
    public SignalFrequencyInfo selectSignalFrequencyInfoById(@PathVariable("frequencyId") String frequencyId) {
        return signalFrequencyInfoService.getById(frequencyId);
    }

    /**
     * 查询频率信息列表
     *
     * @param signalFrequencyInfoQuery 频率信息
     * @return 频率信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询频率信息列表", notes = "查询频率信息列表")
    public TableDataInfo<SignalFrequencyInfo> selectSignalFrequencyInfoList(SignalFrequencyInfoQuery signalFrequencyInfoQuery) {
        return toPageResult(signalFrequencyInfoService.list(signalFrequencyInfoQuery.autoPageWrapper()));
    }

    /**
     * 查询频率信息列表
     *
     * @param signalFrequencyInfoQuery 频率信息
     * @return 频率信息集合
     */
    @GetMapping("/treelist")
    @ApiOperation(value = "查询频率信息列表", notes = "查询频率信息列表")
    public TableDataInfo<SignalFrequencyInfoList> selectSignalFrequencyInfoTreeList(SignalFrequencyInfoQuery signalFrequencyInfoQuery) {
        return toPageResult(signalFrequencyInfoService.treelist(signalFrequencyInfoQuery.autoPageWrapper()));
    }

    @Resource
    private ISignalInterfaceInfoService signalInterfaceInfoService;

    /**
     * 新增保存频率信息
     *
     * @param signalFrequencyInfo 频率信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存频率信息", notes = "新增保存频率信息")
    public Map<String, Object> insertSignalFrequencyInfo(@RequestBody SignalFrequencyInfo signalFrequencyInfo) {
        StringBuffer frequencyId = new StringBuffer();
        frequencyId.append("FC")//前缀
                .append(StringUtils.addZeroForNum(signalFrequencyInfo.getFrontId().toString(), 5))
                .append(StringUtils.addZeroForNum(signalFrequencyInfo.getLogicPositionId().toString(), 4))
                .append(StringUtils.addZeroForNum(signalFrequencyInfo.getSignalCode().toString(), 3))
                .append(StringUtils.addZeroForNum(signalFrequencyInfo.getFrequencyValue(), 7))
                .append(StringUtils.addZeroForNum(signalFrequencyInfo.getInterfaceId().toString(), 5))
                .append("000000");
        signalFrequencyInfo.setFrequencyId(frequencyId.toString());
        this.signalInterfaceInfoService.updateInterfaceSourceId(signalFrequencyInfo.getInterfaceId(), signalFrequencyInfo.getFrequencyId());
        return returnMap(signalFrequencyInfoService.save(signalFrequencyInfo));
    }

    /**
     * 修改保存频率信息
     *
     * @param signalFrequencyInfo 频率信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存频率信息" , notes = "修改保存频率信息")
    public Map<String,Object> updateSignalFrequencyInfoById(@RequestBody SignalFrequencyInfo signalFrequencyInfo) {
        return returnMap(signalFrequencyInfoService.updateById(signalFrequencyInfo));
    }

    /**
     * 批量删除频率信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除频率信息" , notes = "删除频率信息")
    public Map<String,Object> removeSignalFrequencyInfoByIds(String ids) {
        return returnMap(signalFrequencyInfoService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}