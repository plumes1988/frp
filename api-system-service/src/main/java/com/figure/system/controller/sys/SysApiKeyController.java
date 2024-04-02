package com.figure.system.controller.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.figure.core.base.BaseController;
import com.figure.core.helper.PageHelper;
import com.figure.core.helper.PageWrapper;
import com.figure.core.model.sys.SysApiKey;
import com.figure.core.service.sys.ISysApiKeyService;
import com.figure.core.util.page.TableDataInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 第三方调用key管理 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-07-17
 */
@RestController
@RequestMapping("/sysApiKey")
public class SysApiKeyController extends BaseController {

    @Resource
    private ISysApiKeyService sysApiKeyService;

    /**
     * 根据id获取第三方调用key
     *
     * @param id 第三方调用key ID
     * @return 第三方调用key
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取第三方调用key", notes = "根据id获取第三方调用key")
    public SysApiKey selectSysApiKeyById(@PathVariable("id") Long id) {
        return sysApiKeyService.getById(id);
    }

    /**
     * 查询第三方调用key列表
     *
     * @param request
     * @return 第三方调用key集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询第三方调用key列表", notes = "查询第三方调用key列表")
    public TableDataInfo<SysApiKey> selectSysApiKeyList(HttpServletRequest request) throws Exception {
        PageWrapper<SysApiKey> pageWrapper = new PageHelper(SysApiKey.class).getPageWrapper(request);
        QueryWrapper queryWrapper = pageWrapper.getQueryWrapper();
        IPage<SysApiKey> pages = sysApiKeyService.page(pageWrapper.getPage(), queryWrapper);
        return new TableDataInfo(pages);
    }

    /**
     * 新增保存第三方调用key
     *
     * @param sysApiKey 第三方调用key
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存第三方调用key", notes = "新增保存第三方调用key")
    public Map<String, Object> insertSysApiKey(@RequestBody SysApiKey sysApiKey) {
        sysApiKeyService.save(sysApiKey);
        Map<String,Object> result = new HashMap<>();
        result.put("status",1);
        result.put("pk",sysApiKey.getId());
        return result;
    }

    /**
     * 修改保存第三方调用key
     *
     * @param sysApiKey 第三方调用key
     * @return 结果
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存第三方调用key", notes = "修改保存第三方调用key")
    public Map<String, Object> updateSysApiKeyById(@RequestBody SysApiKey sysApiKey) {
        return returnMap(sysApiKeyService.updateById(sysApiKey));
    }

    /**
     * 批量删除第三方调用key
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除第三方调用key", notes = "删除第三方调用key")
    public Map<String, Object> removeSysApiKeyByIds(String ids) {
        return returnMap(sysApiKeyService.removeByIds(Arrays.asList(org.springframework.util.StringUtils.commaDelimitedListToStringArray(ids))));
    }

}
