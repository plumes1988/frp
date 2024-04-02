package com.figure.system.controller.spectrum;

import com.figure.core.base.BaseController;
import com.figure.core.model.spectrum.SpectrumServiceAlarmMessage;
import com.figure.core.query.spectrum.SpectrumServiceAlarmMessageQuery;
import com.figure.core.service.spectrum.ISpectrumServiceAlarmMessageService;
import com.figure.core.util.StringUtils;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 频谱服务报警记录 前端控制器
 * </p>
 *
 * @author feather
 * @date 2024-03-06 13:22:17
 */
@RestController
@RequestMapping("/spectrumServiceAlarmMessage")
@Api(value = "频谱服务报警记录相关接口" , tags = "频谱服务报警记录相关接口")
public class SpectrumServiceAlarmMessageController extends BaseController {

    @Resource
    private ISpectrumServiceAlarmMessageService spectrumServiceAlarmMessageService;

    /**
     * 根据id获取频谱服务报警记录
     *
     * @param id 频谱服务报警记录ID
     * @return 频谱服务报警记录
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取频谱服务报警记录" , notes = "根据id获取频谱服务报警记录")
    public SpectrumServiceAlarmMessage selectSpectrumServiceAlarmMessageById(@PathVariable("id") Integer id) {
        return spectrumServiceAlarmMessageService.getById(id);
    }

    /**
     * 查询频谱服务报警记录列表
     *
     * @param spectrumServiceAlarmMessageQuery 频谱服务报警记录
     * @return 频谱服务报警记录集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询频谱服务报警记录列表" , notes = "查询频谱服务报警记录列表")
    public TableDataInfo<SpectrumServiceAlarmMessage> selectSpectrumServiceAlarmMessageList(SpectrumServiceAlarmMessageQuery spectrumServiceAlarmMessageQuery) {
        spectrumServiceAlarmMessageQuery.setOrderByColumn("createTime");
        spectrumServiceAlarmMessageQuery.setIsAsc("desc");
        return toPageResult(spectrumServiceAlarmMessageService.list(spectrumServiceAlarmMessageQuery.autoPageWrapper()));
    }

    /**
     * 新增保存频谱服务报警记录
     *
     * @param spectrumServiceAlarmMessage 频谱服务报警记录
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存频谱服务报警记录" , notes = "新增保存频谱服务报警记录")
    public Map<String,Object> insertSpectrumServiceAlarmMessage(@RequestBody SpectrumServiceAlarmMessage spectrumServiceAlarmMessage) {
        return returnMap(spectrumServiceAlarmMessageService.save(spectrumServiceAlarmMessage),spectrumServiceAlarmMessage.getAlarmId());
    }

     /**
     * 批量新增保存频谱服务报警记录
     *
     * @param spectrumServiceAlarmMessage 频谱服务报警记录
     * @return 结果
     */
    @PostMapping("/addbatch")
    @ApiOperation(value = "批量新增保存频谱服务报警记录" , notes = "批量新增保存频谱服务报警记录")
    public Map<String,Object> saveBatchSpectrumServiceAlarmMessage(@RequestBody List<SpectrumServiceAlarmMessage> spectrumServiceAlarmMessage) {
        return returnMap(spectrumServiceAlarmMessageService.saveBatch(spectrumServiceAlarmMessage), spectrumServiceAlarmMessage);
    }
  
    /**
     * 修改保存频谱服务报警记录
     *
     * @param spectrumServiceAlarmMessage 频谱服务报警记录
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存频谱服务报警记录" , notes = "修改保存频谱服务报警记录")
    public Map<String,Object> updateSpectrumServiceAlarmMessageById(@RequestBody SpectrumServiceAlarmMessage spectrumServiceAlarmMessage) {
        return returnMap(spectrumServiceAlarmMessageService.updateById(spectrumServiceAlarmMessage));
    }

    /**
     * 批量删除频谱服务报警记录
     *
     * @param spectrumServiceAlarmMessage 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除频谱服务报警记录" , notes = "删除频谱服务报警记录")
    public Map<String,Object> removeSpectrumServiceAlarmMessageByIds(@RequestBody SpectrumServiceAlarmMessage spectrumServiceAlarmMessage) {
        return returnMap(spectrumServiceAlarmMessageService.removeByIds(StringUtils.StringToIntList(spectrumServiceAlarmMessage.getIds())));
    }

}