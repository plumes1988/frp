package com.figure.system.controller.spectrum;

import com.figure.core.base.BaseController;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.spectrum.SpectrumAlarmMessage;
import com.figure.core.model.spectrum.SpectrumRecordMessage;
import com.figure.core.query.spectrum.SpectrumRecordMessageQuery;
import com.figure.core.service.spectrum.ISpectrumRecordMessageService;
import com.figure.core.service.tdengine.TDSpectrumRecordMessageService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频谱数据 前端控制器
 * </p>
 *
 * @author feather
 * @date 2024-01-25 13:33:41
 */
@RestController
@RequestMapping("/spectrumRecordMessage")
@Api(value = "频谱数据相关接口", tags = "频谱数据相关接口")
public class SpectrumRecordMessageController extends BaseController {

    @Resource
    private ISpectrumRecordMessageService spectrumRecordMessageService;

    @Resource
    private TDSpectrumRecordMessageService tdSpectrumRecordMessageService;

    /**
     * 根据id获取频谱数据
     *
     * @param id 频谱数据ID
     * @return 频谱数据
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取频谱数据", notes = "根据id获取频谱数据")
    public SpectrumRecordMessage selectSpectrumRecordMessageById(@PathVariable("id") Integer id) {
        return spectrumRecordMessageService.getById(id);
    }

    /**
     * 查询频谱数据列表
     *
     * @param spectrumRecordMessageQuery 频谱数据
     * @return 频谱数据集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询频谱数据列表", notes = "查询频谱数据列表")
    public TableDataInfo<SpectrumRecordMessage> selectSpectrumRecordMessageList(SpectrumRecordMessageQuery spectrumRecordMessageQuery) {
        spectrumRecordMessageQuery.setOrderByColumn("createTime");
        spectrumRecordMessageQuery.setIsAsc("desc");
        Integer pageNo = spectrumRecordMessageQuery.getPageNum();
        Integer pageSize = spectrumRecordMessageQuery.getPageSize();
        spectrumRecordMessageQuery.setPageNum(null);
        spectrumRecordMessageQuery.setPageSize(null);
        List<SpectrumRecordMessage> spectrumAlarmMessageList = tdSpectrumRecordMessageService.selectEntityPage(pageNo, pageSize ,spectrumRecordMessageQuery.autoPageWrapper());

        spectrumRecordMessageQuery.setOrderByColumn(null);
        spectrumRecordMessageQuery.setIsAsc(null);
        int count = tdSpectrumRecordMessageService.selectCount(spectrumRecordMessageQuery.autoPageWrapper());

        return toPageResult(spectrumAlarmMessageList,pageNo,pageSize,count);

//      return toPageResult(spectrumRecordMessageService.list(spectrumRecordMessageQuery.autoPageWrapper()));
    }

    /**
     * 新增保存频谱数据
     *
     * @param spectrumRecordMessage 频谱数据
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存频谱数据", notes = "新增保存频谱数据")
    public Map<String, Object> insertSpectrumRecordMessage(@RequestBody SpectrumRecordMessage spectrumRecordMessage) {
        return returnMap(spectrumRecordMessageService.save(spectrumRecordMessage), spectrumRecordMessage.getId());
    }

    /**
     * 批量新增保存频谱数据
     *
     * @param spectrumRecordMessage 频谱数据
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存频谱数据", notes = "批量新增保存频谱数据")
    public Map<String, Object> saveBatchSpectrumRecordMessage(@RequestBody List<SpectrumRecordMessage> spectrumRecordMessage) {
        return returnMap(spectrumRecordMessageService.saveBatch(spectrumRecordMessage), spectrumRecordMessage);
    }

    /**
     * 修改保存频谱数据
     *
     * @param spectrumRecordMessage 频谱数据
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存频谱数据", notes = "修改保存频谱数据")
    public Map<String, Object> updateSpectrumRecordMessageById(@RequestBody SpectrumRecordMessage spectrumRecordMessage) {
        return returnMap(spectrumRecordMessageService.updateById(spectrumRecordMessage));
    }

    /**
     * 批量删除频谱数据
     *
     * @param spectrumRecordMessage 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除频谱数据", notes = "删除频谱数据")
    public Map<String, Object> removeSpectrumRecordMessageByIds(@RequestBody SpectrumRecordMessage spectrumRecordMessage) {
        return returnMap(spectrumRecordMessageService.removeByIds(StringUtils.StringToIntList(spectrumRecordMessage.getIds())));
    }

}