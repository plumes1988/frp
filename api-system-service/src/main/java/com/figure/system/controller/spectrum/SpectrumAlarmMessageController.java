package com.figure.system.controller.spectrum;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.core.base.BaseController;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.query.spectrum.SpectrumAlarmMessageQuery;
import com.figure.core.service.spectrum.ISpectrumAlarmMessageService;
import com.figure.core.service.tdengine.TDSpectrumAlarmMessageService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频谱报警记录 前端控制器
 * </p>
 *
 * @author feather
 * @date 2023-11-24 16:44:13
 */
@RestController
@RequestMapping("/spectrumAlarmMessage")
@Api(value = "频谱报警记录相关接口", tags = "频谱报警记录相关接口")
public class SpectrumAlarmMessageController extends BaseController {

    @Resource
    private ISpectrumAlarmMessageService spectrumAlarmMessageService;

    @Resource
    private TDSpectrumAlarmMessageService tdSpectrumAlarmMessageService;

    /**
     * 根据alarmId获取频谱报警记录
     *
     * @param alarmId 频谱报警记录ID
     * @return 频谱报警记录
     */
    @GetMapping("/get/{alarmId}")
    @ApiOperation(value = "根据alarmId获取频谱报警记录", notes = "根据alarmId获取频谱报警记录")
    public SpectrumAlarmMessage selectSpectrumAlarmMessageById(@PathVariable("alarmId") Long alarmId) {
        return spectrumAlarmMessageService.getById(alarmId);
    }

    /**
     * 查询频谱报警记录列表
     *
     * @param spectrumAlarmMessageQuery 频谱报警记录
     * @return 频谱报警记录集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询频谱报警记录列表", notes = "查询频谱报警记录列表")
    public TableDataInfo<SpectrumAlarmMessage> selectSpectrumAlarmMessageList(SpectrumAlarmMessageQuery spectrumAlarmMessageQuery) {
        spectrumAlarmMessageQuery.setOrderByColumn("createTime");
        spectrumAlarmMessageQuery.setIsAsc("desc");
        Integer pageNo = spectrumAlarmMessageQuery.getPageNum();
        Integer pageSize = spectrumAlarmMessageQuery.getPageSize();
        spectrumAlarmMessageQuery.setPageNum(null);
        spectrumAlarmMessageQuery.setPageSize(null);
        List<SpectrumAlarmMessage> spectrumAlarmMessageList = tdSpectrumAlarmMessageService.selectEntityPage(pageNo, pageSize ,spectrumAlarmMessageQuery.autoPageWrapper());

        spectrumAlarmMessageQuery.setOrderByColumn(null);
        spectrumAlarmMessageQuery.setIsAsc(null);
        int count = tdSpectrumAlarmMessageService.selectCount(spectrumAlarmMessageQuery.autoPageWrapper());

        return toPageResult(spectrumAlarmMessageList,pageNo,pageSize,count);

//        return toPageResult(spectrumAlarmMessageService.list(spectrumAlarmMessageQuery.autoPageWrapper()));
    }

    /**
     * 新增保存频谱报警记录
     *
     * @param spectrumAlarmMessage 频谱报警记录
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存频谱报警记录", notes = "新增保存频谱报警记录")
    public Map<String, Object> insertSpectrumAlarmMessage(@RequestBody SpectrumAlarmMessage spectrumAlarmMessage) {
        return returnMap((tdSpectrumAlarmMessageService.saveEntity(spectrumAlarmMessage)>0));
    }

    /**
     * 批量新增保存频谱报警记录
     *
     * @param spectrumAlarmMessage 频谱报警记录
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存频谱报警记录", notes = "批量新增保存频谱报警记录")
    public Map<String, Object> saveBatchSpectrumAlarmMessage(@RequestBody List<SpectrumAlarmMessage> spectrumAlarmMessage) {
        return returnMap(spectrumAlarmMessageService.saveBatch(spectrumAlarmMessage), spectrumAlarmMessage);
    }

    /**
     * 修改保存频谱报警记录
     *
     * @param spectrumAlarmMessage 频谱报警记录
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存频谱报警记录", notes = "修改保存频谱报警记录")
    public Map<String, Object> updateSpectrumAlarmMessageById(@RequestBody SpectrumAlarmMessage spectrumAlarmMessage) {
        return returnMap(spectrumAlarmMessageService.updateById(spectrumAlarmMessage));
    }

    /**
     * 批量删除频谱报警记录
     *
     * @param spectrumAlarmMessage 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除频谱报警记录", notes = "删除频谱报警记录")
    public Map<String, Object> removeSpectrumAlarmMessageByIds(@RequestBody SpectrumAlarmMessage spectrumAlarmMessage) {
        return returnMap(spectrumAlarmMessageService.removeByIds(StringUtils.StringToIntList(spectrumAlarmMessage.getIds())));
    }

}