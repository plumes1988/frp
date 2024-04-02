package com.figure.system.controller.signal;

import com.figure.core.base.BaseController;
import com.figure.core.model.signal.SignalChannelInfo;
import com.figure.core.model.signal.SignalInterfaceInfo;
import com.figure.core.query.signal.SignalChannelInfoQuery;
import com.figure.core.service.signal.ISignalChannelInfoService;
import com.figure.core.service.signal.ISignalInterfaceInfoService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 频道信息 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-05-25 17:10:37
 */
@RestController
@RequestMapping("/signalChannelInfo")
@Api(value = "频道信息相关接口", tags = "频道信息相关接口")
public class SignalChannelInfoController extends BaseController {

    @Resource
    private ISignalChannelInfoService signalChannelInfoService;

    @Resource
    private ISignalInterfaceInfoService signalInterfaceInfoService;

    /**
     * 根据channelId获取频道信息
     *
     * @param channelId 频道信息ID
     * @return 频道信息
     */
    @GetMapping("/get/{channelId}")
    @ApiOperation(value = "根据channelId获取频道信息", notes = "根据channelId获取频道信息")
    public SignalChannelInfo selectSignalChannelInfoById(@PathVariable("channelId") String channelId) {
        return signalChannelInfoService.getById(channelId);
    }

    /**
     * 查询频道信息列表
     *
     * @param signalChannelInfoQuery 频道信息
     * @return 频道信息集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询频道信息列表", notes = "查询频道信息列表")
    public TableDataInfo<SignalChannelInfo> selectSignalChannelInfoList(SignalChannelInfoQuery signalChannelInfoQuery) {
        signalChannelInfoQuery.setOrderByColumn("channelName").setIsAsc("asc");
        return toPageResult(signalChannelInfoService.list(signalChannelInfoQuery.autoPageWrapper()));
    }

    /**
     * 新增保存频道信息
     *
     * @param signalChannelInfo 频道信息
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存频道信息", notes = "新增保存频道信息")
    public Map<String, Object> insertSignalChannelInfo(@RequestBody SignalChannelInfo signalChannelInfo) {
        StringBuffer channelId = new StringBuffer();
        if (signalChannelInfo.getFrequencyId() == null || signalChannelInfo.getFrequencyId().equals("")) {
            String channelNumber = "";
            if (signalChannelInfo.getInterfaceId() != null) {
                SignalInterfaceInfo signalInterfaceInfo = this.signalInterfaceInfoService.getById(signalChannelInfo.getInterfaceId());
                if (signalInterfaceInfo != null) {
                    channelNumber = signalInterfaceInfo.getSerialNumber().toString();
                }
            }
            channelId.append("CC").append(com.figure.core.util.StringUtils.addZeroForNum(signalChannelInfo.getFrontId().toString(), 5))
                    .append(com.figure.core.util.StringUtils.addZeroForNum(signalChannelInfo.getLogicPositionId().toString(), 4))
                    .append(com.figure.core.util.StringUtils.addZeroForNum(signalChannelInfo.getSignalCode().toString(), 3))
                    .append(com.figure.core.util.StringUtils.addZeroForNum("", 7))
                    .append(com.figure.core.util.StringUtils.addZeroForNum(channelNumber, 5))
                    .append(com.figure.core.util.StringUtils.addZeroForNum(signalChannelInfo.getServiceId().toString(), 6));
        } else {
            channelId.append("CC").append(signalChannelInfo.getFrequencyId(), 2, 26)
                    .append(com.figure.core.util.StringUtils.addZeroForNum(signalChannelInfo.getServiceId().toString(), 6));

        }
        signalChannelInfo.setChannelId(channelId.toString());
        this.signalInterfaceInfoService.updateInterfaceSourceId(signalChannelInfo.getInterfaceId(), signalChannelInfo.getChannelId());
        initCreateProps(signalChannelInfo);
        return returnMap(signalChannelInfoService.save(signalChannelInfo));
    }

    /**
     * 修改保存频道信息
     *
     * @param signalChannelInfo 频道信息
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存频道信息", notes = "修改保存频道信息")
    public Map<String, Object> updateSignalChannelInfoById(@RequestBody SignalChannelInfo signalChannelInfo) {
        initUpdateProps(signalChannelInfo);
        return returnMap(signalChannelInfoService.updateById(signalChannelInfo));
    }

    /**
     * 批量删除频道信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除频道信息", notes = "删除频道信息")
    public Map<String, Object> removeSignalChannelInfoByIds(String ids) {
        return returnMap(signalChannelInfoService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids))));
    }

    /**
     * 查询频道信息列表
     *
     * @param signalChannelInfo 频道信息
     * @return 频道信息集合
     */
    @GetMapping("/treeList")
    @ApiOperation(value = "查询频道树信息列表", notes = "查询频道树信息列表")
    public Map<String, Object> selectSignalChannelInfoTreeList(SignalChannelInfo signalChannelInfo) {
        return returnMap(signalChannelInfoService.selectSignalChannelInfoTreeList());
    }
}