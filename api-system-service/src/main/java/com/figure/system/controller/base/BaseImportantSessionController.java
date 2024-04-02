package com.figure.system.controller.base;

import com.figure.core.base.BaseController;
import com.figure.core.model.base.BaseImportantSession;
import com.figure.core.query.base.BaseImportantSessionQuery;
import com.figure.core.service.base.IBaseImportantSessionService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 重点时段 前端控制器
 * </p>
 *
 * @author feather
 * @date 2021-04-20 14:03:33
 */
@RestController
@RequestMapping("/baseImportantSession")
@Api(value = "重点时段相关接口" , tags = "重点时段相关接口")
public class BaseImportantSessionController extends BaseController {

    @Resource
    private IBaseImportantSessionService baseImportantSessionService;

    /**
     * 根据importantSessionId获取重点时段
     *
     * @param importantSessionId 重点时段ID
     * @return 重点时段
     */
    @GetMapping("/get/{importantSessionId}")
    @ApiOperation(value = "根据importantSessionId获取重点时段" , notes = "根据importantSessionId获取重点时段")
    public BaseImportantSession selectBaseImportantSessionById(@PathVariable("importantSessionId") Long importantSessionId) {
        return baseImportantSessionService.getById(importantSessionId);
    }

    /**
     * 查询重点时段列表
     *
     * @param baseImportantSessionQuery 重点时段
     * @return 重点时段集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询重点时段列表", notes = "查询重点时段列表")
    public TableDataInfo selectBaseImportantSessionList(BaseImportantSessionQuery baseImportantSessionQuery) {
        return toPageResult(baseImportantSessionService.list(baseImportantSessionQuery.autoPageWrapper()));
    }

    /**
     * 新增保存重点时段
     *
     * @param baseImportantSession 重点时段
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存重点时段" , notes = "新增保存重点时段")
    public Map<String,Object>  insertBaseImportantSession(@RequestBody BaseImportantSession baseImportantSession) {
        baseImportantSession.setCreateTime(new Date());
        return returnMap(baseImportantSessionService.save(baseImportantSession));
    }

    /**
     * 修改保存重点时段
     *
     * @param baseImportantSession 重点时段
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存重点时段" , notes = "修改保存重点时段")
    public Map<String,Object>  updateBaseImportantSessionById(@RequestBody BaseImportantSession baseImportantSession) {
        baseImportantSession.setUpdateTime(new Date());
        return returnMap(baseImportantSessionService.updateById(baseImportantSession));
    }

    /**
     * 批量删除重点时段
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除重点时段" , notes = "删除重点时段")
    public Map<String,Object> removeBaseImportantSessionByIds(String ids) {
        return returnMap(baseImportantSessionService.removeByIds(Arrays.asList(StringUtils.commaDelimitedListToStringArray(ids))));
    }

}