package com.figure.system.controller.spectrum;

import com.figure.core.base.BaseController;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumAlarmTracedata;
import com.figure.core.model.spectrum.SpectrumRecordTracedata;
import com.figure.core.query.spectrum.SpectrumAlarmMessageQuery;
import com.figure.core.query.spectrum.SpectrumRecordTracedataQuery;
import com.figure.core.service.spectrum.ISpectrumAlarmTracedataService;
import com.figure.core.service.tdengine.TDSpectrumAlarmMessageService;
import com.figure.core.service.tdengine.TDSpectrumAlarmTracedataService;
import com.figure.core.service.tdengine.TDSpectrumRecordTraceDataService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频谱数据详细 前端控制器
 * </p>
 *
 * @author feather
 * @date 2024-03-01 14:10:45
 */
@RestController
@RequestMapping("/spectrumAlarmTracedata")
@Api(value = "频谱数据详细相关接口", tags = "频谱数据详细相关接口")
public class SpectrumAlarmTracedataController extends BaseController {

    @Resource
    private ISpectrumAlarmTracedataService SpectrumAlarmTracedataService;

    @Resource
    private TDSpectrumAlarmTracedataService tdSpectrumAlarmTracedataService;


    @Resource
    private TDSpectrumAlarmMessageService tdSpectrumAlarmMessageService;

    @Resource
    private TDSpectrumRecordTraceDataService tdSpectrumRecordTraceDataService;

    /**
     * 根据id获取频谱数据详细
     *
     * @param id 频谱数据详细ID
     * @return 频谱数据详细
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取频谱数据详细", notes = "根据id获取频谱数据详细")
    public SpectrumAlarmTracedata selectSpectrumAlarmTracedataById(@PathVariable("id") Integer id) {
        return SpectrumAlarmTracedataService.getById(id);
    }

    /**
     * 查询频谱数据详细列表
     *
     * @param spectrumAlarmMessageQuery 频谱数据详细
     * @return 频谱数据详细集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询频谱数据详细列表", notes = "查询频谱数据详细列表")
    public TableDataInfo<SpectrumRecordTracedata> selectSpectrumAlarmTracedataList(SpectrumAlarmMessageQuery spectrumAlarmMessageQuery) {
        Integer pageNo = spectrumAlarmMessageQuery.getPageNum();
        Integer pageSize = spectrumAlarmMessageQuery.getPageSize();
        spectrumAlarmMessageQuery.setPageNum(null);
        spectrumAlarmMessageQuery.setPageSize(null);
        int alarmTimeout = 10;
        List<SpectrumAlarmMessage> spectrumAlarmMessageList = this.tdSpectrumAlarmMessageService.selectEntityPage(pageNo, pageSize, spectrumAlarmMessageQuery.autoPageWrapper());
        if (spectrumAlarmMessageList.size() > 0) {
            SpectrumAlarmMessage spectrumAlarmMessage = spectrumAlarmMessageList.get(0);
            SpectrumRecordTracedataQuery recordTracedataQuery = new SpectrumRecordTracedataQuery();
            recordTracedataQuery.setGEcreateTime(DateHelper.add(spectrumAlarmMessage.getStartTime(), Calendar.SECOND, -alarmTimeout));
            recordTracedataQuery.setLEcreateTime(DateHelper.add(spectrumAlarmMessage.getEndTime(), Calendar.SECOND, alarmTimeout));
            recordTracedataQuery.setSpectrumCode(spectrumAlarmMessage.getSpectrumCode());
            List<SpectrumRecordTracedata> spectrumRecordTracedataList = this.tdSpectrumRecordTraceDataService.selectEntityPage(pageNo, pageSize, recordTracedataQuery.autoPageWrapper());
            return toPageResult(spectrumRecordTracedataList, pageNo, pageSize, spectrumRecordTracedataList.size());
        } else {
            return toPageResult(new ArrayList<>(), 0, 0, 0);
        }
//        return toPageResult(SpectrumAlarmTracedataService.list(SpectrumAlarmTracedataQuery.autoPageWrapper()));
    }

    /**
     * 新增保存频谱数据详细
     *
     * @param SpectrumAlarmTracedata 频谱数据详细
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存频谱数据详细", notes = "新增保存频谱数据详细")
    public Map<String, Object> insertSpectrumAlarmTracedata(@RequestBody SpectrumAlarmTracedata SpectrumAlarmTracedata) {
        return returnMap(SpectrumAlarmTracedataService.save(SpectrumAlarmTracedata), SpectrumAlarmTracedata.getAlarmId());
    }

    /**
     * 批量新增保存频谱数据详细
     *
     * @param SpectrumAlarmTracedata 频谱数据详细
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存频谱数据详细", notes = "批量新增保存频谱数据详细")
    public Map<String, Object> saveBatchSpectrumAlarmTracedata(@RequestBody List<SpectrumAlarmTracedata> SpectrumAlarmTracedata) {
        return returnMap(SpectrumAlarmTracedataService.saveBatch(SpectrumAlarmTracedata), SpectrumAlarmTracedata);
    }

    /**
     * 修改保存频谱数据详细
     *
     * @param SpectrumAlarmTracedata 频谱数据详细
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存频谱数据详细", notes = "修改保存频谱数据详细")
    public Map<String, Object> updateSpectrumAlarmTracedataById(@RequestBody SpectrumAlarmTracedata SpectrumAlarmTracedata) {
        return returnMap(SpectrumAlarmTracedataService.updateById(SpectrumAlarmTracedata));
    }

    /**
     * 批量删除频谱数据详细
     *
     * @param SpectrumAlarmTracedata 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除频谱数据详细", notes = "删除频谱数据详细")
    public Map<String, Object> removeSpectrumAlarmTracedataByIds(@RequestBody SpectrumAlarmTracedata SpectrumAlarmTracedata) {
        return returnMap(SpectrumAlarmTracedataService.removeByIds(StringUtils.StringToIntList(SpectrumAlarmTracedata.getIds())));
    }

}