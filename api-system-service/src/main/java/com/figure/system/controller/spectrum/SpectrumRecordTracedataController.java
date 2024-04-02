package com.figure.system.controller.spectrum;

import com.figure.core.base.BaseController;
import com.figure.core.model.spectrum.SpectrumAlarmTracedata;
import com.figure.core.model.spectrum.SpectrumRecordTracedata;
import com.figure.core.query.spectrum.SpectrumRecordTracedataQuery;
import com.figure.core.service.spectrum.ISpectrumRecordTracedataService;
import com.figure.core.service.tdengine.TDSpectrumRecordTraceDataService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.text.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import com.figure.core.util.page.TableDataInfo;

/**
 * <p>
 * 频谱数据详细 前端控制器
 * </p>
 *
 * @author feather
 * @date 2024-03-01 14:10:45
 */
@RestController
@RequestMapping("/spectrumRecordTracedata")
@Api(value = "频谱数据详细相关接口" , tags = "频谱数据详细相关接口")
public class SpectrumRecordTracedataController extends BaseController {

    @Resource
    private ISpectrumRecordTracedataService spectrumRecordTracedataService;

    @Resource
    private TDSpectrumRecordTraceDataService tdSpectrumRecordTraceDataService;

    /**
     * 根据id获取频谱数据详细
     *
     * @param id 频谱数据详细ID
     * @return 频谱数据详细
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取频谱数据详细" , notes = "根据id获取频谱数据详细")
    public SpectrumRecordTracedata selectSpectrumRecordTracedataById(@PathVariable("id") Integer id) {
        return spectrumRecordTracedataService.getById(id);
    }

    /**
     * 查询频谱数据详细列表
     *
     * @param spectrumRecordTracedataQuery 频谱数据详细
     * @return 频谱数据详细集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询频谱数据详细列表" , notes = "查询频谱数据详细列表")
    public TableDataInfo<SpectrumRecordTracedata> selectSpectrumRecordTracedataList(SpectrumRecordTracedataQuery spectrumRecordTracedataQuery) {
        spectrumRecordTracedataQuery.setOrderByColumn("createTime");
        spectrumRecordTracedataQuery.setIsAsc("desc");
        Integer pageNo = spectrumRecordTracedataQuery.getPageNum();
        Integer pageSize = spectrumRecordTracedataQuery.getPageSize();
        spectrumRecordTracedataQuery.setPageNum(null);
        spectrumRecordTracedataQuery.setPageSize(null);
        List<SpectrumRecordTracedata> spectrumAlarmMessageList = tdSpectrumRecordTraceDataService.selectEntityPage(pageNo, pageSize ,spectrumRecordTracedataQuery.autoPageWrapper());

        spectrumRecordTracedataQuery.setOrderByColumn(null);
        spectrumRecordTracedataQuery.setIsAsc(null);
        int count = tdSpectrumRecordTraceDataService.selectCount(spectrumRecordTracedataQuery.autoPageWrapper());

        return toPageResult(spectrumAlarmMessageList,pageNo,pageSize,count);
//        return toPageResult(spectrumRecordTracedataService.list(spectrumRecordTracedataQuery.autoPageWrapper()));
    }

    /**
     * 新增保存频谱数据详细
     *
     * @param spectrumRecordTracedata 频谱数据详细
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存频谱数据详细" , notes = "新增保存频谱数据详细")
    public Map<String,Object> insertSpectrumRecordTracedata(@RequestBody SpectrumRecordTracedata spectrumRecordTracedata) {
        return returnMap(spectrumRecordTracedataService.save(spectrumRecordTracedata),spectrumRecordTracedata.getId());
    }

     /**
     * 批量新增保存频谱数据详细
     *
     * @param spectrumRecordTracedata 频谱数据详细
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存频谱数据详细" , notes = "批量新增保存频谱数据详细")
    public Map<String,Object> saveBatchSpectrumRecordTracedata(@RequestBody List<SpectrumRecordTracedata> spectrumRecordTracedata) {
        return returnMap(spectrumRecordTracedataService.saveBatch(spectrumRecordTracedata), spectrumRecordTracedata);
    }
  
    /**
     * 修改保存频谱数据详细
     *
     * @param spectrumRecordTracedata 频谱数据详细
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存频谱数据详细" , notes = "修改保存频谱数据详细")
    public Map<String,Object> updateSpectrumRecordTracedataById(@RequestBody SpectrumRecordTracedata spectrumRecordTracedata) {
        return returnMap(spectrumRecordTracedataService.updateById(spectrumRecordTracedata));
    }

    /**
     * 批量删除频谱数据详细
     *
     * @param spectrumRecordTracedata 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除频谱数据详细" , notes = "删除频谱数据详细")
    public Map<String,Object> removeSpectrumRecordTracedataByIds(@RequestBody SpectrumRecordTracedata spectrumRecordTracedata) {
        return returnMap(spectrumRecordTracedataService.removeByIds(StringUtils.StringToIntList(spectrumRecordTracedata.getIds())));
    }

}